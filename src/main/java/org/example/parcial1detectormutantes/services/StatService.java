package org.example.parcial1detectormutantes.services;

import org.example.parcial1detectormutantes.repositories.ADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatService {
    @Autowired
    private ADNRepository ADNRepository;

    public Map<String, Object> getDnaStats() {
        long countMutantDna = ADNRepository.countByverificaMutant(true);
        long countHumanDna = ADNRepository.countByverificaMutant(false);
        double ratio = countHumanDna == 0 ? 0 : (double) countMutantDna / countHumanDna;

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", countMutantDna);
        stats.put("count_human_dna", countHumanDna);
        stats.put("ratio", ratio);

        return stats;
    }
}