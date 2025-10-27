package com.innosistemas.service;

import com.innosistemas.entity.VersionDocumento;
import com.innosistemas.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VersionDocumentoService {
    private VersionDocumentoRepository versionDocumentoRepository;

    public VersionDocumentoService(VersionDocumentoRepository versionDocumentoRepository) {
        this.versionDocumentoRepository = versionDocumentoRepository;
    }

    public List<VersionDocumento> obtenerVersionesDocumento(Integer documentoId) {
        return versionDocumentoRepository.findByDocumentoIdOrderByFechaVersionDesc(documentoId);
    }
}
