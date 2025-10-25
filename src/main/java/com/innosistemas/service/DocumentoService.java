package com.innosistemas.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.io.IOException;
import com.innosistemas.entity.Documento;
import com.innosistemas.repository.DocumentoRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class DocumentoService {
    private final GridFsTemplate gridFsTemplate;
    private final DocumentoRepository documentoRepository;

    public DocumentoService(GridFsTemplate gridFsTemplate, DocumentoRepository documentoRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.documentoRepository = documentoRepository;
    }

    public Documento uploadAndSaveDocumento(MultipartFile file, String titulo, Integer proyectoId, Integer usuarioId)
            throws IOException {
        ObjectId fileId;
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
                // Log y opcionalmente programar job para limpiar luego
                // logger.warn("No se pudo borrar GridFS file tras fallo en metadata: " + fileId, cleanupEx);
            }
            throw ex; // propaga error al caller (HTTP 5xx o manejado)
        }
    }

    public Documento saveDocumento(String gridFsFileId, String titulo, Integer proyectoId, Integer usuarioId) {
        Documento documento = new Documento();
        documento.setTitulo(titulo);
        documento.setProyectoId(proyectoId);
        documento.setUsuarioId(usuarioId);
        documento.setRutaDoc(gridFsFileId);
        documento.setFechaModificacion(new java.sql.Date(System.currentTimeMillis()));
        return documentoRepository.save(documento);
    }
    // public GridFSFile getGridFsFile(String gridFsFileId) { /*...*/ }
    // public InputStream download(String gridFsFileId) { /*...*/ }
}
