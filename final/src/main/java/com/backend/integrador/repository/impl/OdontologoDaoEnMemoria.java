package com.backend.dao.impl;

import com.backend.dao.IDao;
import com.backend.entity.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {
    private static final Logger LOGGER = Logger.getLogger(OdontologoDaoEnMemoria.class);

    private List<Odontologo> odontologoRepository;

    public OdontologoDaoEnMemoria() {
        this.odontologoRepository = new ArrayList<>();
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        odontologoRepository.add(odontologo);
        LOGGER.info("Se ha registrado al odontólogo: " + odontologo);
        return odontologo;
    }

    @Override
    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        // Tu implementación aquí
    }

    @Override
    public boolean eliminarOdontologo(int id) {
        // Tu implementación aquí
    }

    @Override
    public Odontologo obtenerOdontologo(int id) {
        // Tu implementación aquí
    }

    @Override
    public List<Odontologo> listarOdontologos() {
        // Tu implementación aquí
    }
}