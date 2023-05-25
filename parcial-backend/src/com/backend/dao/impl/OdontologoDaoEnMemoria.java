package com.backend.dao.impl;

import com.backend.dao.IDao;
import com.backend.entity.Odontologo;
import org.apache.log4j.Logger;

import java.util.List;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {
    private static final Logger LOGGER = Logger.getLogger(OdontologoDaoEnMemoria.class);

    private List<Odontologo> odontologoRepository;

    public OdontologoDaoEnMemoria(List<Odontologo> odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        odontologoRepository.add(odontologo);
        LOGGER.info("Se ha registrado al odontólogo: " + odontologo);
        return odontologo;
    }

    @Override
    public List<Odontologo> listarOdontologos() {
        LOGGER.info("Listado de todos los odontólogos: " + odontologoRepository);
        return odontologoRepository;
    }
}
