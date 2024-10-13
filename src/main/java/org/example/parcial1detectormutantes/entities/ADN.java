package org.example.parcial1detectormutantes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ADN implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "ADN",columnDefinition = "text", unique = true)
    private String dna;

    private boolean verificaMutant;


    //Este método toma un array de cadenas (String[] dna) como argumento y lo convierte en una representación de texto (String)
    public void setADN(String[] dna) {
        this.dna = Arrays.toString(dna);
    }




            //Hace lo inverso al otro proceso
    public String[] getDnaArray() {

        String cleanDna = this.dna.substring(1, this.dna.length() - 1);

        return cleanDna.split(", ");
    }
}
