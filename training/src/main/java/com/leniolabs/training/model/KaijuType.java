package com.leniolabs.training.model;

public enum KaijuType {

    ALL(0, 0),
    ONE(10, 13),
    TWO(14, 18),
    THREE(18, 22),
    UNKNOWN(0 , 0);

    private int min, max;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isBetweenItsMaxAndMin(Integer value) {
        return this.getMin() <= value &&  value <= this.getMax();
    }

    private KaijuType(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
