package com.leniolabs.training.model;

public enum KaijuType {

    ALL(0, 23),
    ONE(10, 13),
    TWO(14, 18),
    THREE(18, 22),
    UNKNOWN(0 , 0);

    private int min, max;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    KaijuType(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
