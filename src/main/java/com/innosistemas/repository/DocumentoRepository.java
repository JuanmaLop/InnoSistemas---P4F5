package com.innosistemas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innosistemas.entity.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer>{
    
}
