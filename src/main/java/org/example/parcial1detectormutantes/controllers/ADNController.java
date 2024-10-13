package org.example.parcial1detectormutantes.controllers;

import org.example.parcial1detectormutantes.services.ADNService;
import org.example.parcial1detectormutantes.services.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController //manejará peticiones HTTP
@CrossOrigin(origins = "*")
@RequestMapping("/mutant")
public class ADNController {

    @Autowired
    private ADNService ADNService;
    @Autowired
    private StatService statsService;

    @PostMapping("")
             //debe tener una clave llamada "dna" y su valor debe ser un arreglo de cadenas (String[]).
    public ResponseEntity<?> verificaMutant(@RequestBody Map<String, String[]> dna) {
        // Verificar si el array de ADN está vacío o nulo
        if (dna.get("dna") == null || dna.get("dna").length == 0) {
            return new ResponseEntity<>("El ADN está vacío", HttpStatus.BAD_REQUEST);
        }

        // Verificar si la matriz de ADN es cuadrada
        String[] dnaArray = dna.get("dna");
        int length = dnaArray.length;
        for (String row : dnaArray) {
            if (row.length() != length) {
                return new ResponseEntity<>("La matriz de ADN no es cuadrada", HttpStatus.BAD_REQUEST);
            }
        }
        boolean verificaMutant = ADNService.verificaMutant(dna.get("dna"));
        ADNService.saveDna(dna.get("dna"), verificaMutant);

        if (verificaMutant) {
            return new ResponseEntity<>("Mutant detected", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = statsService.getDnaStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}