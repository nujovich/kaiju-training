package com.leniolabs.training.calculator;

import com.leniolabs.training.model.KaijuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class KaijuTypeCalculator {

    @Autowired
    private KaijuDNADecoder dnaDecoder;

    public KaijuType calculateTypeFromDNA(String dna) {
        Integer maxValue = dnaDecoder.decodeDNA(dna);
        return Arrays.stream(KaijuType.values())
                .filter(kaijuType -> kaijuType.isBetweenItsMaxAndMin(maxValue))
                .findFirst()
                .orElseGet(() -> KaijuType.UNKNOWN);
    }
}
