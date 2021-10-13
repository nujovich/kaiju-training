package com.leniolabs.training.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class KaijuDNADecoderTest {

    private KaijuDNADecoder underTest;

    @BeforeEach
    private void setup(){
        underTest = new KaijuDNADecoder(3);
    }

    @Test
    public void decodeDNA_exampleGottenFromInstructions() {
        // Given
        String dna = "6576996abc567099unmi543ytiju46643";

        // When
        Integer gottenMaxValue = underTest.decodeDNA(dna);

        //Then
        assertEquals(24 ,gottenMaxValue);
    }

    @Test
    public void decodeDNA_shorterCase() {
        // Given
        String dna = "123jmg456";
        //123 = 6
        //234 = 9
        //345 = 12
        //456 = 15

        // When
        Integer gottenMaxValue = underTest.decodeDNA(dna);

        //Then
        assertEquals(15, gottenMaxValue);
    }
}
