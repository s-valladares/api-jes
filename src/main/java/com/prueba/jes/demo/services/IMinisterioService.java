package com.prueba.jes.demo.services;

import com.prueba.jes.demo.domains.MinisterioPublico;

import java.util.List;

public interface IMinisterioService {

    List<MinisterioPublico> getAll();
    MinisterioPublico getId(Long id);
    MinisterioPublico create(MinisterioPublico mp);
    void delete(Long id);
}
