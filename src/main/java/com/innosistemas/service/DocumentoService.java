package com.innosistemas.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;
import java.io.IOException;
import com.innosistemas.entity.Documento;
import com.innosistemas.repository.DocumentoRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class DocumentoService {
    private final GridFsTemplate gridFsTemplate;
    private final DocumentoRepository documentoRepository;

    public DocumentoService(GridFsTemplate gridFsTemplate, DocumentoRepository documentoRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.documentoRepository = documentoRepository;
    }

    // Sube el archivo a GridFS y guarda el documento en postgresql
    public Documento uploadAndSaveDocumento(MultipartFile file, String titulo, Integer proyectoId, Integer usuarioId)
            throws IOException {
        ObjectId fileId;
        // Sube el archivo a GridFS
        try (InputStream inputStream = file.getInputStream()) {
            DBObject metadata = new BasicDBObject();
            metadata.put("contentType", file.getContentType());
            metadata.put("originalFilename", file.getOriginalFilename());
            fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType(), metadata);
        }

        String gridFsFileId = fileId.toHexString();

        try {
            return saveDocumento(gridFsFileId, titulo, proyectoId, usuarioId);
        } catch (RuntimeException ex) {
            try {
                gridFsTemplate.delete(new Query(Criteria.where("_id").is(fileId)));
            } catch (Exception cleanupEx) {
                // log cleanupEx
            }
            throw ex; // propaga error al caller (HTTP 5xx o manejado)
        }
    }

    // Guarda el documento en postgresql
    public Documento saveDocumento(String gridFsFileId, String titulo, Integer proyectoId, Integer usuarioId) {
        Documento documento = new Documento();
        documento.setTitulo(titulo);
        documento.setProyectoId(proyectoId);
        documento.setUsuarioId(usuarioId);
        documento.setRutaDoc(gridFsFileId);
        documento.setFechaModificacion(new java.sql.Date(System.currentTimeMillis()));
        return documentoRepository.save(documento);
    }

    // Obtiene todos los documentos asociados a un proyecto
    public List<Documento> findAllDocumentosByProyectoId(Integer proyectoId) {
        return documentoRepository.findByProyectoId(proyectoId);
    }

    // Descarga el archivo de GridFS por su ID
    public GridFsResource downloadByGridFsId(String gridFsId) throws IOException {
        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(gridFsId))));
        return gridFsTemplate.getResource(gridFsFile);
    }
}
