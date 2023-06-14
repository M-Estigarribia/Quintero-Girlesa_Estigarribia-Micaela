package com.backend.service;

import com.backend.dao.IDao;
import com.backend.entity.Odontologo;

import java.util.List;

public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoIDao.guardarOdontologo(odontologo);
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        return odontologoIDao.actualizarOdontologo(odontologo);
    }

    public boolean eliminarOdontologo(int id) {
        return odontologoIDao.eliminarOdontologo(id);
    }

    public Odontologo obtenerOdontologo(int id) {
        return odontologoIDao.obtenerOdontologo(id);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoIDao.listarOdontologos();
    }
}