package org.example.parcial1detectormutantes.services;


import org.example.parcial1detectormutantes.entities.ADN;
import org.example.parcial1detectormutantes.repositories.ADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ADNService {

    @Autowired
    private ADNRepository ADNRepository;

    public boolean verificaMutant(String[] dna) {
        // Validar que el ADN solo contenga A, T, G, C
        if (!validcaracter(dna)) {
            throw new IllegalArgumentException("DNA must only contain the characters A, T, G, C.");
        }

        int contador = 0; // Contador de secuencias encontradas
        int size = dna.length;

        // Verificar horizontal, vertical y diagonal usando métodos separados
        contador += controlhorizontal(dna,size);
        contador += controlvertical(dna,size);
        contador += controldiagonal(dna,size);

        // Es mutante si encuentra más de una secuencia
        return contador > 1;
    }

    //Validar que el ADN solo contenga A, T, G, C
    private boolean validcaracter(String[] dna) {
        return IntStream.range(0, dna.length)
                .allMatch(i -> dna[i].matches("[ATGC]+")); // Verificar que cada fila contenga solo A, T, G, C
    }


    private int controlhorizontal (String[] dna,int size) {
        return IntStream.range(0, size)
                .map(i -> checkSequence(dna[i]))
                .sum();
    }

    private int controlvertical (String[] dna,int size) {
        return IntStream.range(0, size)
                .map(j -> {
                    StringBuilder column = new StringBuilder();
                    IntStream.range(0, dna.length)
                            .forEach(i -> column.append(dna[i].charAt(j)));
                    return checkSequence(column.toString());
                })
                .sum();
    }

    private int controldiagonal (String[] dna,int size) {

        // Diagonales de izquierda a derecha
        int leftToRightDiagonals = IntStream.range(0, size)
                .map(i -> {
                    StringBuilder diagonal = new StringBuilder();
                    IntStream.range(0, i + 1)
                            .filter(j -> i - j < size)
                            .forEach(j -> diagonal.append(dna[i - j].charAt(j)));
                    return checkSequence(diagonal.toString());
                })
                .sum();


        int rightToLeftDiagonals = IntStream.range(0, size)
                .map(i -> {
                    StringBuilder diagonal = new StringBuilder();
                    IntStream.range(0, i + 1)
                            .filter(j -> i - j < size)
                            .forEach(j -> diagonal.append(dna[i - j].charAt(size - 1 - j)));
                    return checkSequence(diagonal.toString());
                })
                .sum();

        return leftToRightDiagonals + rightToLeftDiagonals;
    }

    private int checkSequence(String sequence) {
        int count = 0;
        char currentChar = sequence.charAt(0);
        int currentStreak = 1;

        // Contar secuencias de 4 caracteres iguales
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == currentChar) {
                currentStreak++;
                if (currentStreak == 4) {
                    count++;
                    currentStreak = 0; // Reiniciar para contar otras secuencias
                }
            } else {
                currentChar = sequence.charAt(i);
                currentStreak = 1;
            }
        }

        return count;
    }

    public ADN saveDna(String[] dna, boolean verificaMutant) {
        String dnaAsString = Arrays.toString(dna); //arreglo de ADN a una cadena

        // se busca si yua existe ese adn
        Optional<ADN> existingDna = ADNRepository.findByDna(dnaAsString);


        if (existingDna.isPresent()) {
            return existingDna.get();  // Si se encuentra uno igual retorna el existente
        }

        ADN ADNEntity = new ADN();
        ADNEntity.setADN(dna);//Guarda cadena de ADN
        ADNEntity.setVerificaMutant(verificaMutant);//TRUE si es mutante
        return ADNRepository.save(ADNEntity);
    }
}