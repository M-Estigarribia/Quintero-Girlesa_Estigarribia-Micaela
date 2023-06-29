package com.backend.integrador.repository;

import com.backend.integrador.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // para checkear que el turno no esté duplicado
    int countByPacienteIdAndOdontologoIdAndFechaHora (Long pacienteId, Long OdontologoId, LocalDateTime fechaHora);

}
