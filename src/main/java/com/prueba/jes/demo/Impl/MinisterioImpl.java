package com.prueba.jes.demo.Impl;

import com.prueba.jes.demo.domains.MinisterioPublico;
import com.prueba.jes.demo.repositories.IMinisterioDao;
import com.prueba.jes.demo.services.IMinisterioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MinisterioImpl implements IMinisterioService {

    @Autowired
    IMinisterioDao ministerioDao;

    @Override
    public List<MinisterioPublico> getAll() {
        return ministerioDao.findAll();
    }

    @Override
    public MinisterioPublico getId(Long id) {
        return ministerioDao.findById(id).orElse(null);
    }

    @Override
    public MinisterioPublico create(MinisterioPublico mp) {
        return ministerioDao.save(mp);
    }

    @Override
    public void delete(Long id) {
        ministerioDao.deleteById(id);
    }
}
