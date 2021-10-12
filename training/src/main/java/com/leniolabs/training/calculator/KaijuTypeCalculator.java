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
        if (10 <= maxValue &&  maxValue <= 13) {
            type = KaijuType.ONE;
        }

        if (14 <= maxValue &&  maxValue <= 18) {
            type = KaijuType.TWO;
        }

        if (18 <= maxValue &&  maxValue <= 22) {
            type = KaijuType.THREE;
        }
        return type;
    }

}
