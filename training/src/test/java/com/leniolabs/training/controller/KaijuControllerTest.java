package com.leniolabs.training.controller;


import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.controller.dto.RequestKaijuDTO;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.service.KaijuService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class KaijuControllerTest {

    @InjectMocks
    private KaijuController underTest;

    @Mock
    private KaijuService kaijuService;

    @Test
    public void createKaiju() {
        // Given
        KaijuType kaijuType = KaijuType.THREE;
        ResponseEntity expectedResponse = ResponseEntity.ok(kaijuType);
        String dna = "123abc456fff890";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);
        when(kaijuService.createKaiju(eq(dna))).thenReturn(kaijuType);

        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse,actualResponse);
        verify(kaijuService, times(1)).createKaiju(eq(dna));
        verifyNoMoreInteractions(kaijuService);
    }

    @Test
    public void createKaiju_noNumbersOnDNA() {
        // Given
        ResponseEntity expectedResponse = ResponseEntity.badRequest().build();
        String dna = "abc&dff!$";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(kaijuService, never()).createKaiju(anyString());
    }

    @Test
    public void createKaiju_noLettersOnDNA() {
        // Given
        ResponseEntity expectedResponse = ResponseEntity.badRequest().build();
        String dna = "123456";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(kaijuService, never()).createKaiju(anyString());
    }

    @Test
    public void createKaiju_blankDNA() {
        // Given
        ResponseEntity expectedResponse = ResponseEntity.badRequest().build();
        String dna = " ";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);


        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(kaijuService, never()).createKaiju(anyString());
    }

    @Test
    public void createKaiju_emptyDNA() {
        // Given
        ResponseEntity expectedResponse = ResponseEntity.badRequest().build();
        String dna = "";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(kaijuService, never()).createKaiju(anyString());
    }

    @Test
    public void createKaiju_nullDNA() {
        // Given
        ResponseEntity expectedResponse = ResponseEntity.badRequest().build();
        String dna = "";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        // When
        ResponseEntity<KaijuType> actualResponse = underTest.createKaiju(requestKaijuDTO);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verifyNoInteractions(kaijuService);
    }

    @Test
    public void getStats_kaijuTypeOne() {
        // Given
        KaijuType kaijuType = KaijuType.ONE;
        executeTestCaseAndAssertResult(KaijuType.ONE, kaijuType);
    }

    @Test
    public void getStats_kaijuTypeTwo() {
        // Given
        KaijuType kaijuType = KaijuType.TWO;
        executeTestCaseAndAssertResult(KaijuType.TWO, kaijuType);
    }

    @Test
    public void getStats_kaijuTypeThree() {
        // Given
        KaijuType kaijuType = KaijuType.THREE;
        executeTestCaseAndAssertResult(KaijuType.THREE, kaijuType);
    }

    @Test
    public void getStats_kaijuTypeUnknown() {
        // Given
        KaijuType kaijuType = KaijuType.UNKNOWN;
        executeTestCaseAndAssertResult(KaijuType.UNKNOWN, kaijuType);
    }

    @Test
    public void getStats_kaijuTypeAll() {
        // Given
        KaijuType kaijuType = KaijuType.ALL;
        executeTestCaseAndAssertResult(KaijuType.ALL, kaijuType);
    }

    @Test
    public void getStats_kaijuTypeIsNull() {
        // Given
        KaijuType kaijuType = null;
        executeTestCaseAndAssertResult(KaijuType.ALL, kaijuType);
    }

    private void executeTestCaseAndAssertResult(KaijuType expectedKaijuType, KaijuType kaijuType) {
        KaijuResponseDTO expectedKaijuKaijuResponseDTO = new KaijuResponseDTO();
        ResponseEntity expectedResponse = ResponseEntity.ok(expectedKaijuKaijuResponseDTO);
        when(kaijuService.getPercentageOfSamplesEvaluated(eq(expectedKaijuType))).thenReturn(expectedKaijuKaijuResponseDTO);

        // When
        ResponseEntity<KaijuResponseDTO> actualResponse = underTest.getStats(kaijuType);

        // Then
        assertEquals(expectedResponse, actualResponse);
        verify(kaijuService, times(1)).getPercentageOfSamplesEvaluated(eq(expectedKaijuType));
        verifyNoMoreInteractions(kaijuService);
    }

}
