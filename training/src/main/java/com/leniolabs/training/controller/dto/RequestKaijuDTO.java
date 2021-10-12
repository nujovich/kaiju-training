package com.leniolabs.training.controller.dto;

public class RequestKaijuDTO {

    private String dna;

    public RequestKaijuDTO() {
    }

    public RequestKaijuDTO(String dna) {
        this.dna = dna;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }
}
