package com.innosistemas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innosistemas.entity.VersionDocumento;

@Repository
public interface VersionDocumentoRepository extends JpaRepository<VersionDocumento, Integer> {
    List<VersionDocumento> findByDocumentoIdOrderByFechaVersionDesc(Integer documentoId);
}
