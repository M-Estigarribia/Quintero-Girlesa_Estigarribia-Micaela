package com.backend.service;

import com.backend.entity.Odontologo;
import com.backend.repository.OdontologoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private final OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
    }

    public Odontologo obtenerOdontologo(Long id) {
        Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);
        return optionalOdontologo.orElse(null);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.findAll();
    }
}