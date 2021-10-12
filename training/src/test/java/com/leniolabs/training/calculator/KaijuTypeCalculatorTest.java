package com.leniolabs.training.calculator;

import com.leniolabs.training.model.KaijuType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KaijuTypeCalculatorTest {

    @InjectMocks
    private KaijuTypeCalculator underTest;

    private final static String DNA = "123jmg456";

    @Mock
    private KaijuDNADecoder dnaDecoder;

    @Test
    public void calculateTypeFromDNA_kaijuTypeOne() {
        // Given
        KaijuType expectedKaijuType = KaijuType.ONE;
        executeTestCaseAndAssertGottenValue(expectedKaijuType, 12);

    }

    @Test
    public void calculateTypeFromDNA_kaijuTypeTwo() {
        // Given
        KaijuType expectedKaijuType = KaijuType.TWO;
        executeTestCaseAndAssertGottenValue(expectedKaijuType, 15);

    }

    @Test
    public void calculateTypeFromDNA_kaijuTypeThree() {
        // Given
        KaijuType expectedKaijuType = KaijuType.THREE;
        executeTestCaseAndAssertGottenValue(expectedKaijuType, 19);

    }

    @Test
    public void calculateTypeFromDNA_kaijuTypeUnknownBecauseOfTooHighMaxValueOfDecodedDNA() {
        // Given
        KaijuType expectedKaijuType = KaijuType.UNKNOWN;
        executeTestCaseAndAssertGottenValue(expectedKaijuType, 24);

    }

    @Test
    public void calculateTypeFromDNA_kaijuTypeUnknownBecauseOfTooLowMaxValueOfDecodedDNA() {
        // Given
        KaijuType expectedKaijuType = KaijuType.UNKNOWN;
        executeTestCaseAndAssertGottenValue(expectedKaijuType, 5);
    }


    private void executeTestCaseAndAssertGottenValue(KaijuType expectedKaijuType, Integer maxValueOfDecodedDNA) {
        when(dnaDecoder.decodeDNA(eq(DNA))).thenReturn(maxValueOfDecodedDNA);

        // When
        KaijuType gottenKaijuType = underTest.calculateTypeFromDNA(DNA);

        //Then
        assertEquals(expectedKaijuType, gottenKaijuType);
        verify(dnaDecoder, times(1)).decodeDNA(eq(DNA));
        verifyNoMoreInteractions(dnaDecoder);
    }
}
