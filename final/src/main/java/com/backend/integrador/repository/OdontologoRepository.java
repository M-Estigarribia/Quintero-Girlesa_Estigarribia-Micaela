package com.backend.integrador.repository;

import com.backend.integrador.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    boolean existsByMatricula(String matricula);
}
