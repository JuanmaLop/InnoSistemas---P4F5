package com.innosistemas.service;

import java.util.List;
import com.innosistemas.dto.CarpetaDTO;
import com.innosistemas.dto.CarpetaTreeNodeDTO;

public interface CarpetaService {
    CarpetaDTO crearCarpeta(Integer proyectoId, Integer padreId, String nombre, Integer usuarioId);
    List<CarpetaDTO> listarPorProyectoYPadre(Integer proyectoId, Integer padreId);
    CarpetaTreeNodeDTO obtenerArbolProyecto(Integer proyectoId, boolean incluirDocumentos);
}
