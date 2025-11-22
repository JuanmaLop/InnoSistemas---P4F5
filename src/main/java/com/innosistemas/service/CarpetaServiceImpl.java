package com.innosistemas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innosistemas.dto.CarpetaDTO;
import com.innosistemas.dto.CarpetaTreeNodeDTO;
import com.innosistemas.dto.CarpetaTreeNodeDTO.DocumentoSimpleDTO;
import com.innosistemas.entity.Carpeta;
import com.innosistemas.repository.CarpetaRepository;
import com.innosistemas.repository.DocumentoRepository;

@Service
public class CarpetaServiceImpl implements CarpetaService {

    private final CarpetaRepository carpetaRepository;
    private final DocumentoRepository documentoRepository;

    public CarpetaServiceImpl(CarpetaRepository carpetaRepository, DocumentoRepository documentoRepository) {
        this.carpetaRepository = carpetaRepository;
        this.documentoRepository = documentoRepository;
    }

    @Override
    @Transactional
    public CarpetaDTO crearCarpeta(Integer proyectoId, Integer padreId, String nombre, Integer usuarioId) {
        if (carpetaRepository.existsByProyectoIdAndPadreIdAndNombre(proyectoId, padreId, nombre)) {
            throw new IllegalArgumentException("Ya existe una carpeta con ese nombre en el mismo nivel.");
        }
        Carpeta carpeta = new Carpeta();
        carpeta.setProyectoId(proyectoId);
        carpeta.setPadreId(padreId);
        carpeta.setNombre(nombre);
        carpeta.setUsuarioCreadorId(usuarioId);
        Carpeta guardada = carpetaRepository.save(carpeta);
        return new CarpetaDTO(guardada.getId(), guardada.getNombre(), guardada.getProyectoId(), guardada.getPadreId());
    }

    @Override
    public List<CarpetaDTO> listarPorProyectoYPadre(Integer proyectoId, Integer padreId) {
        return carpetaRepository.findByProyectoIdAndPadreId(proyectoId, padreId).stream()
                .map(c -> new CarpetaDTO(c.getId(), c.getNombre(), c.getProyectoId(), c.getPadreId()))
                .collect(Collectors.toList());
    }

    @Override
    public CarpetaTreeNodeDTO obtenerArbolProyecto(Integer proyectoId, boolean incluirDocumentos) {
        CarpetaTreeNodeDTO root = new CarpetaTreeNodeDTO(null, "ROOT");
        // Documentos en raíz (carpeta null)
        if (incluirDocumentos) {
            documentoRepository.findByProyectoIdAndCarpetaIdIsNull(proyectoId).forEach(d ->
                root.getDocumentos().add(new DocumentoSimpleDTO(d.getId(), d.getTitulo()))
            );
        }
        // Carpetas raíz
        List<CarpetaDTO> raices = listarPorProyectoYPadre(proyectoId, null);
        for (CarpetaDTO dto : raices) {
            root.getHijos().add(construirNodoRecursivo(dto.getId(), dto.getNombre(), proyectoId, incluirDocumentos, 0));
        }
        return root;
    }

    private CarpetaTreeNodeDTO construirNodoRecursivo(Integer carpetaId, String nombre, Integer proyectoId, boolean incluirDocumentos, int profundidad) {
        CarpetaTreeNodeDTO nodo = new CarpetaTreeNodeDTO(carpetaId, nombre);
        // Añadir documentos de esta carpeta
        if (incluirDocumentos) {
            documentoRepository.findByProyectoIdAndCarpetaId(proyectoId, carpetaId).forEach(d ->
                nodo.getDocumentos().add(new DocumentoSimpleDTO(d.getId(), d.getTitulo()))
            );
        }
        // Limite defensivo de profundidad (evitar ciclos inesperados) - 25 niveles
        if (profundidad > 25) {
            return nodo; // cortar
        }
        // Hijos
        carpetaRepository.findByProyectoIdAndPadreId(proyectoId, carpetaId).forEach(c ->
            nodo.getHijos().add(construirNodoRecursivo(c.getId(), c.getNombre(), proyectoId, incluirDocumentos, profundidad + 1))
        );
        return nodo;
    }
}
