package com.leniolabs.training.calculator;

import com.leniolabs.training.model.KaijuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KaijuTypeCalculator {

    @Autowired
    private KaijuDNADecoder dnaDecoder;

    public KaijuType calculateTypeFromDNA(String dna) {
        KaijuType type = KaijuType.UNKNOWN;
        Integer maxValue = dnaDecoder.decodeDNA(dna);
        if (KaijuType.ONE.getMin() <= maxValue &&  maxValue <= KaijuType.ONE.getMax()) {
            type = KaijuType.ONE;
        }

        if (KaijuType.TWO.getMin() <= maxValue &&  maxValue <= KaijuType.TWO.getMax()) {
            type = KaijuType.TWO;
        }

        if (KaijuType.THREE.getMin() <= maxValue &&  maxValue <= KaijuType.THREE.getMax()) {
            type = KaijuType.THREE;
        }
        return type;
    }

}
