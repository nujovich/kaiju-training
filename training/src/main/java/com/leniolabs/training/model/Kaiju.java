package com.leniolabs.training.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "KAIJU")
@Data
public class Kaiju {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DNA", nullable=false, length=100)
    private String dna;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable=false, length = 15)
    private KaijuType type;



    public Kaiju() {
    }

    public Kaiju(String dna, KaijuType type) {
        this.dna = dna;
        this.type = type;
    }

    public Kaiju(Long id, String dna, KaijuType type) {
        this.id = id;
        this.dna = dna;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public KaijuType getType() {
        return type;
    }

    public void setType(KaijuType type) {
        this.type = type;
    }


}
