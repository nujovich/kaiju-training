package com.leniolabs.training.service;

import com.leniolabs.training.calculator.KaijuTypeCalculator;
import com.leniolabs.training.controller.dto.KaijuTypePercentage;
import com.leniolabs.training.model.Kaiju;
import com.leniolabs.training.repository.KaijuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.service.KaijuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class KaijuServiceTest {

    @InjectMocks
    private KaijuServiceImp underTest;

    @Mock
    private KaijuRepository kaijuRepository;

    @Mock
    private KaijuTypeCalculator kaijuTypeCalculator;

    private final static String DNA = "6576996abc567099unmi543ytiju46643";


    @Test
    public void createKaiju_existentKaiju(){
        // Given
        Kaiju kaiju = mock(Kaiju.class);
        KaijuType expectedType = KaijuType.THREE;
        when(kaiju.getType()).thenReturn(expectedType);
        when(kaijuRepository.findByDna(eq(DNA))).thenReturn(kaiju);

        // When
        KaijuType actualType = underTest.createKaiju(DNA);

        // Then
        assertEquals(expectedType, actualType);
        verify(kaijuRepository, times(1)).findByDna(eq(DNA));
        verify(kaiju, times(1)).getType();
        verifyNoMoreInteractions(kaijuRepository);
        verifyNoMoreInteractions(kaiju);
        verifyNoInteractions(kaijuTypeCalculator);
    }

    @Test
    public void createKaiju_nonExistentKaiju(){
        // Given
        KaijuType expectedType = KaijuType.TWO;
        Kaiju kaijuToBePersisted = new Kaiju(DNA, expectedType);
        when(kaijuTypeCalculator.calculateTypeFromDNA(eq(DNA))).thenReturn(expectedType);

        // When
        KaijuType actualType = underTest.createKaiju(DNA);

        // Then
        assertEquals(expectedType, actualType);
        verify(kaijuRepository,times(1)).findByDna(eq(DNA));
        verify(kaijuRepository, times(1)).save(eq(kaijuToBePersisted));
        verify(kaijuTypeCalculator, times(1)).calculateTypeFromDNA(eq(DNA));
        verifyNoMoreInteractions(kaijuRepository);
        verifyNoMoreInteractions(kaijuTypeCalculator);
    }

    @Test
    public void getPercentageOfSamplesEvaluated() {
        // Given
        underTest = new KaijuServiceImp(kaijuTypeCalculator, kaijuRepository, 80);
        List<KaijuType> kaijuTypes = Arrays.asList(KaijuType.ONE);
        when(kaijuRepository.findAllKaijuType()).thenReturn(kaijuTypes);
        KaijuTypePercentage kaijuTypePercentage = new KaijuTypePercentage(KaijuType.ONE, 100.00);
        KaijuResponseDTO expectedResponse = buildKaijuResponseGTO(Arrays.asList(kaijuTypePercentage), KaijuServiceImp.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD);

        // When
        KaijuResponseDTO gottenResponse = underTest.getPercentageOfSamplesEvaluated(KaijuType.ONE);

        // Then
        assertNotNull(gottenResponse);
        List<KaijuTypePercentage> gottenKaijuTypePercentages = gottenResponse.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(gottenKaijuTypePercentages));
        assertEquals(1, gottenKaijuTypePercentages.size());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getKaijuType(), gottenKaijuTypePercentages.get(0).getKaijuType());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getPercentage(), gottenKaijuTypePercentages.get(0).getPercentage());
        assertEquals(expectedResponse.getMessage(), gottenResponse.getMessage());
    }

    @Test
    public void getPercentageOfSamplesEvaluated_fiftyPercent() {
        // Given
        underTest = new KaijuServiceImp(kaijuTypeCalculator, kaijuRepository, 80);
        List<KaijuType> kaijuTypes = Arrays.asList(KaijuType.ONE, KaijuType.THREE);
        when(kaijuRepository.findAllKaijuType()).thenReturn(kaijuTypes);
        KaijuTypePercentage kaijuTypePercentage = new KaijuTypePercentage(KaijuType.ONE, 50.00);
        KaijuResponseDTO expectedResponse = buildKaijuResponseGTO(Arrays.asList(kaijuTypePercentage), KaijuServiceImp.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD);

        // When
        KaijuResponseDTO gottenResponse = underTest.getPercentageOfSamplesEvaluated(KaijuType.ONE);

        // Then
        assertNotNull(gottenResponse);
        List<KaijuTypePercentage> gottenKaijuTypePercentages = gottenResponse.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(gottenKaijuTypePercentages));
        assertEquals(1, gottenKaijuTypePercentages.size());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getKaijuType(), gottenKaijuTypePercentages.get(0).getKaijuType());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getPercentage(), gottenKaijuTypePercentages.get(0).getPercentage());
        assertEquals(expectedResponse.getMessage(), gottenResponse.getMessage());
    }

    @Test
    public void getPercentageOfSamplesEvaluated_thiryThreePercent() {
        // Given
        underTest = new KaijuServiceImp(kaijuTypeCalculator, kaijuRepository, 80);
        List<KaijuType> kaijuTypes = Arrays.asList(KaijuType.ONE, KaijuType.THREE, KaijuType.TWO, KaijuType.UNKNOWN, KaijuType.UNKNOWN, KaijuType.ONE);
        when(kaijuRepository.findAllKaijuType()).thenReturn(kaijuTypes);
        KaijuTypePercentage kaijuTypePercentage = new KaijuTypePercentage(KaijuType.ONE, 33.0);
        KaijuResponseDTO expectedResponse = buildKaijuResponseGTO(Arrays.asList(kaijuTypePercentage), KaijuServiceImp.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD);

        // When
        KaijuResponseDTO gottenResponse = underTest.getPercentageOfSamplesEvaluated(KaijuType.ONE);

        // Then
        assertNotNull(gottenResponse);
        List<KaijuTypePercentage> gottenKaijuTypePercentages = gottenResponse.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(gottenKaijuTypePercentages));
        assertEquals(1, gottenKaijuTypePercentages.size());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getKaijuType(), gottenKaijuTypePercentages.get(0).getKaijuType());
        assertEquals(expectedResponse.getKaijuTypePercentages().get(0).getPercentage(), gottenKaijuTypePercentages.get(0).getPercentage());
        assertEquals(expectedResponse.getMessage(), gottenResponse.getMessage());
    }

    @Test
    public void getPercentageOfSamplesEvaluated_caseAll() {
        // Given
        underTest = new KaijuServiceImp(kaijuTypeCalculator, kaijuRepository, 80);
        List<KaijuType> kaijuTypes = Arrays.asList(KaijuType.ONE, KaijuType.THREE, KaijuType.TWO, KaijuType.UNKNOWN, KaijuType.UNKNOWN, KaijuType.ONE);
        when(kaijuRepository.findAllKaijuType()).thenReturn(kaijuTypes);
        KaijuTypePercentage kaijuTypePercentageOne = new KaijuTypePercentage(KaijuType.ONE, 33.0);
        KaijuTypePercentage kaijuTypePercentageUnknown = new KaijuTypePercentage(KaijuType.UNKNOWN, 33.0);
        KaijuTypePercentage kaijuTypePercentageThree = new KaijuTypePercentage(KaijuType.THREE, 16.0);
        KaijuTypePercentage kaijuTypePercentageTwo = new KaijuTypePercentage(KaijuType.TWO, 16.0);
        KaijuResponseDTO expectedResponse = buildKaijuResponseGTO(Arrays.asList(kaijuTypePercentageOne, kaijuTypePercentageThree, kaijuTypePercentageUnknown, kaijuTypePercentageTwo)
                , KaijuServiceImp.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD);

        // When
        KaijuResponseDTO gottenResponse = underTest.getPercentageOfSamplesEvaluated(KaijuType.ALL);

        // Then
        assertNotNull(gottenResponse);
        List<KaijuTypePercentage> gottenKaijuTypePercentages = gottenResponse.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(gottenKaijuTypePercentages));
        assertEquals(KaijuType.values().length -1 , gottenKaijuTypePercentages.size()); //Type ALL is not evaluated in a particular way
        assertEquals(gottenResponse.getMessage(), KaijuServiceImp.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD);
        expectedResponse.getKaijuTypePercentages().stream().forEach(expectedKaijuTypePercentage -> {
            KaijuTypePercentage kaijuTypePercentage = gottenKaijuTypePercentages.stream().filter(gottenKaijuTypePercentage -> expectedKaijuTypePercentage.getKaijuType()
                    .equals(gottenKaijuTypePercentage.getKaijuType()))
                    .findFirst()
                    .get();
            assertEquals(expectedKaijuTypePercentage.getPercentage(), kaijuTypePercentage.getPercentage());
        });
    }

    private KaijuResponseDTO buildKaijuResponseGTO(List<KaijuTypePercentage> kaijuTypePercentages, String message) {
        KaijuResponseDTO kaijuResponseDTO = new KaijuResponseDTO();
        kaijuResponseDTO.setKaijuTypePercentages(kaijuTypePercentages);
        kaijuResponseDTO.setMessage(message);
        return kaijuResponseDTO;
    }

}
