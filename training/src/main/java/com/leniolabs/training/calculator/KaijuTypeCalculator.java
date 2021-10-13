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
        if (KaijuType.ONE.isBetweenItsMaxAndMin(maxValue)) {
            type = KaijuType.ONE;
        }

        if (KaijuType.TWO.isBetweenItsMaxAndMin(maxValue)) {
            type = KaijuType.TWO;
        }

        if (KaijuType.THREE.isBetweenItsMaxAndMin(maxValue)) {
            type = KaijuType.THREE;
        }
        return type;
    }

}
