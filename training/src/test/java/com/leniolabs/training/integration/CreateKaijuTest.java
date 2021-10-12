package com.leniolabs.training.integration;

import com.leniolabs.training.TrainingApplicationTests;
import com.leniolabs.training.controller.dto.RequestKaijuDTO;
import com.leniolabs.training.integration.constants.KaijuEndpointConstants;
import com.leniolabs.training.integration.util.EndpointUtil;
import com.leniolabs.training.model.Kaiju;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.repository.KaijuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateKaijuTest extends TrainingApplicationTests {

    @Autowired
    private EndpointUtil endpointUtil;

    @SpyBean
    private KaijuRepository kaijuRepository;

    @BeforeEach
    public void setup() throws Exception {
        endpointUtil.setup();
    }

    @Test
    public void createUnExistentKaiju() throws Exception {
        // Given
        String dna = "123jmg456";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        // When
        MvcResult mvcResult = endpointUtil.postCall(KaijuEndpointConstants.POST_KAIJU, requestKaijuDTO)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        KaijuType responseKaijuType = endpointUtil.getResponseObject(mvcResult, KaijuType.class);
        assertEquals(KaijuType.TWO,responseKaijuType);
        verify(kaijuRepository, times(1)).save(any(Kaiju.class));
        verifyNoMoreInteractions(kaijuRepository);
    }

    @Test
    public void returnExistingKaijuTypeOnSecondCall() throws Exception {
        // Given
        String dna = "123jmg456";
        RequestKaijuDTO requestKaijuDTO = new RequestKaijuDTO(dna);

        MvcResult mvcResult = endpointUtil.postCall(KaijuEndpointConstants.POST_KAIJU, requestKaijuDTO)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        KaijuType responseKaijuType = endpointUtil.getResponseObject(mvcResult, KaijuType.class);
        assertEquals(KaijuType.TWO,responseKaijuType);

        // When
        MvcResult mvcResultSecondCall = endpointUtil.postCall(KaijuEndpointConstants.POST_KAIJU, requestKaijuDTO)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        KaijuType responseKaijuTypeSecondCall = endpointUtil.getResponseObject(mvcResult, KaijuType.class);
        assertEquals(KaijuType.TWO,responseKaijuType);

        // There was only one interaction since the entity under that dna was already created
        verify(kaijuRepository, times(1)).save(any(Kaiju.class));
    }

}
