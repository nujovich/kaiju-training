package com.leniolabs.training.controller.dto;

import com.leniolabs.training.model.KaijuType;

public class KaijuTypePercentage {

    private KaijuType kaijuType;
    private Double percentage;

    public KaijuTypePercentage() {
    }

    public KaijuTypePercentage(KaijuType kaijuType, Double percentage) {
        this.kaijuType = kaijuType;
        this.percentage = percentage;
    }

    public KaijuType getKaijuType() {
        return kaijuType;
    }

    public void setKaijuType(KaijuType kaijuType) {
        this.kaijuType = kaijuType;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}
