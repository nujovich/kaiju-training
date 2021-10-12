package com.leniolabs.training.controller.dto;

import java.util.List;

public class KaijuResponseDTO {

    private String message;
    private List<KaijuTypePercentage> kaijuTypePercentages;

    public KaijuResponseDTO() {
    }

    public KaijuResponseDTO(String message, List<KaijuTypePercentage> kaijuTypePercentages) {
        this.message = message;
        this.kaijuTypePercentages = kaijuTypePercentages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KaijuTypePercentage> getKaijuTypePercentages() {
        return kaijuTypePercentages;
    }

    public void setKaijuTypePercentages(List<KaijuTypePercentage> kaijuTypePercentages) {
        this.kaijuTypePercentages = kaijuTypePercentages;
    }
}
