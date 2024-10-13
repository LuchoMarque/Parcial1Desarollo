package org.example.parcial1detectormutantes.repositories;

import org.example.parcial1detectormutantes.entities.ADN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ADNRepository extends JpaRepository<ADN, Long> {
    Optional<ADN> findByDna(String dna); // Optional<ADN> puede o no contener un valor
    long countByverificaMutant(boolean verificaMutant);
}

//<ADN, Long> trabaja con ADN y el id es de tipo long