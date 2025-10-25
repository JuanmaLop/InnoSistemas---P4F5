package com.innosistemas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.innosistemas.entity.Documento;
import com.innosistemas.service.DocumentoService;

@RestController
@RequestMapping("/api/documento")
public class DocumentoController {
    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Documento> uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("proyectoId") Integer proyectoId,
            @RequestParam("usuarioId") Integer usuarioId) {

        try {
            Documento docSaved = documentoService.uploadAndSaveDocumento(file, titulo, proyectoId, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(docSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
