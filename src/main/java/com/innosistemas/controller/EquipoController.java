package com.innosistemas.controller;

import org.springframework.web.bind.annotation.RestController;

import com.innosistemas.entity.Equipo;
import com.innosistemas.service.EquipoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


@RestController
@RequestMapping("/api/equipo")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PostMapping("/crearEquipo")
    public ResponseEntity<Equipo> createEquipo(@RequestParam String nombre, @RequestParam String descripcion, @RequestBody List<String> correosUsuarios) {
       Equipo equipo = equipoService.createEquipoConUsuarios(nombre, descripcion, correosUsuarios);
       return ResponseEntity.ok(equipo);
    }
    

}
