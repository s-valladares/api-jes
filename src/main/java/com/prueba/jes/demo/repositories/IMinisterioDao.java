package com.prueba.jes.demo.repositories;

import com.prueba.jes.demo.domains.MinisterioPublico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMinisterioDao extends JpaRepository<MinisterioPublico, Long> {
}
