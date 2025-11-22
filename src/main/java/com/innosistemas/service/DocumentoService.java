package com.innosistemas.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.innosistemas.dto.DocumentoResumen;
import com.innosistemas.entity.AuditoriaDocumento;
import com.innosistemas.entity.Documento;
import com.innosistemas.entity.VersionDocumento;
import com.innosistemas.repository.AuditoriaDocumentoRepository;
import com.innosistemas.repository.DocumentoRepository;
import com.innosistemas.repository.VersionDocumentoRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.transaction.Transactional;

@Service
public class DocumentoService {
    private final GridFsTemplate gridFsTemplate;
    private final DocumentoRepository documentoRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final AuditoriaDocumentoRepository auditoriaDocumentoRepository;

    public DocumentoService(GridFsTemplate gridFsTemplate, DocumentoRepository documentoRepository, 
                           VersionDocumentoRepository versionDocumentoRepository,
                           AuditoriaDocumentoRepository auditoriaDocumentoRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.documentoRepository = documentoRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.auditoriaDocumentoRepository = auditoriaDocumentoRepository;
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
        documento.setFechaModificacion(new Date(System.currentTimeMillis()));
        return documentoRepository.save(documento);
    }

    // Actualiza un documento existente y crea una nueva versión
    @Transactional
    public Documento updateDocumento(Integer documentoId, MultipartFile file, String nuevoTitulo, Integer usuarioId, String detallesCambios) 
            throws IOException {
        Documento documentoAct = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + documentoId));
        
        // Guarda la versión actual antes de que actualicemos el documento
        VersionDocumento versionDoc = new VersionDocumento();
        versionDoc.setDocumentoId(documentoId);
        versionDoc.setTituloDocumento(documentoAct.getTitulo());
        versionDoc.setFechaVersion(new Date(System.currentTimeMillis()));
        versionDoc.setDetallesCambios(detallesCambios);
        versionDocumentoRepository.save(versionDoc);

        ObjectId fileId;
        // Sube el nuevo archivo a GridFS
        try (InputStream inputStream = file.getInputStream()) {
            DBObject metadata = new BasicDBObject();
            metadata.put("contentType", file.getContentType());
            metadata.put("originalFilename", file.getOriginalFilename());
            fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType(), metadata);
        }

        // Actualiza los datos del documento actual
        documentoAct.setTitulo(nuevoTitulo);
        documentoAct.setRutaDoc(fileId.toHexString());
        documentoAct.setFechaModificacion(new Date(System.currentTimeMillis()));
        documentoAct.setUsuarioId(usuarioId);

        return documentoRepository.save(documentoAct);
    }

    // Obtiene todos los documentos asociados a un proyecto (habilitados y deshabilitados)
    public List<Documento> findAllDocumentosByProyectoId(Integer proyectoId) {
        return documentoRepository.findByProyectoId(proyectoId);
    }
    
    // Obtiene solo los documentos habilitados de un proyecto
    public List<Documento> findDocumentosHabilitadosByProyectoId(Integer proyectoId) {
        return documentoRepository.findByProyectoIdAndEstado(proyectoId, "Habilitado");
    }

    // Obtiene el resumen de documentos del usuario (con nombres de proyecto y equipo)
    public List<DocumentoResumen> findResumenByUsuarioId(Integer usuarioId) {
        return documentoRepository.findResumenByUsuarioId(usuarioId);
    }

    // Borrado lógico: cambia el estado a "Deshabilitado" y registra en auditoría
    @Transactional
    public void deshabilitarDocumento(Integer documentoId, Integer usuarioId) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + documentoId));
        documento.setEstado("Deshabilitado");
        documentoRepository.save(documento);
        
        // Registrar en auditoría
        AuditoriaDocumento auditoria = new AuditoriaDocumento(
            documentoId,
            "DESHABILITADO",
            usuarioId,
            "Documento marcado como deshabilitado"
        );
        auditoriaDocumentoRepository.save(auditoria);
    }

    // Restaurar documento: cambia el estado a "Habilitado" y registra en auditoría
    @Transactional
    public void habilitarDocumento(Integer documentoId, Integer usuarioId) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + documentoId));
        documento.setEstado("Habilitado");
        documentoRepository.save(documento);
        
        // Registrar en auditoría
        AuditoriaDocumento auditoria = new AuditoriaDocumento(
            documentoId,
            "HABILITADO",
            usuarioId,
            "Documento restaurado"
        );
        auditoriaDocumentoRepository.save(auditoria);
    }

    // Descarga el archivo de GridFS por su ID
    public GridFsResource downloadByGridFsId(String gridFsId) throws IOException {
        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(gridFsId))));
        return gridFsTemplate.getResource(gridFsFile);
    }
}
