package com.leniolabs.training.integration;

import com.leniolabs.training.TrainingApplicationTests;
import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.controller.dto.KaijuTypePercentage;
import com.leniolabs.training.integration.constants.KaijuEndpointConstants;
import com.leniolabs.training.integration.util.CreateKaijuApiCaller;
import com.leniolabs.training.integration.util.EndpointUtil;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.repository.KaijuRepository;
import com.leniolabs.training.utils.ResponseMessageConstants;
import org.hsqldb.lib.MultiValueHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetKaijuStatsTest extends TrainingApplicationTests {

    @Autowired
    private EndpointUtil endpointUtil;

    @Autowired
    private CreateKaijuApiCaller createKaijuApiCaller;

    @BeforeEach
    public void setup() throws Exception {
        endpointUtil.setup();
    }

    @Test
    public void getStats_noStatesGeneratedYet() throws Exception {
        // Given - When
        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType=ALL")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);
        assertNotNull(kaijuResponseDTO);
        assertTrue(kaijuResponseDTO.getKaijuTypePercentages().stream().allMatch(kaijuTypePercentage -> 0.0 == kaijuTypePercentage.getPercentage()));
    }


    @Test
    public void getStatsFromExistentKaijuOnDB() throws Exception {
        // Given
        String dna = "123jmg456";
        KaijuType type = createKaijuApiCaller.createKaiju(dna);


        // When
        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType="+type)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        // Then
        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);
        assertNotNull(kaijuResponseDTO);
        List<KaijuTypePercentage> kaijuTypePercentages = kaijuResponseDTO.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(kaijuTypePercentages));
        assertEquals(1, kaijuTypePercentages.size());
        KaijuTypePercentage gottenKaijuPercentage = kaijuTypePercentages.get(0);
        assertNotNull(gottenKaijuPercentage);
        assertEquals(100.0, gottenKaijuPercentage.getPercentage());
        assertEquals(type, gottenKaijuPercentage.getKaijuType());
        assertEquals(ResponseMessageConstants.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD, kaijuResponseDTO.getMessage());
    }

    @Test
    public void getStatsFromExistentKaijuOnDB_twoTypesCreates() throws Exception {
        // Given
        String dna = "123jmg456";
        KaijuType type = createKaijuApiCaller.createKaiju(dna);
        assertEquals(KaijuType.TWO, type);

        String dna2 = "6576996abc567099unmi543ytiju46643";
        KaijuType type2 = createKaijuApiCaller.createKaiju(dna2);
        assertEquals(KaijuType.UNKNOWN, type2);

        // When
        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType="+KaijuType.ALL)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);
        assertNotNull(kaijuResponseDTO);
        List<KaijuTypePercentage> kaijuTypePercentages = kaijuResponseDTO.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(kaijuTypePercentages));
        assertEquals(4, kaijuTypePercentages.size());
        List<KaijuTypePercentage> kaijuTypePercentajeWithFiftyPercent = kaijuResponseDTO.getKaijuTypePercentages().stream()
                .filter(kaijuTypePercentage -> 50.0 == kaijuTypePercentage.getPercentage())
                .collect(Collectors.toList());
        assertTrue(kaijuTypePercentajeWithFiftyPercent.stream()
                .anyMatch(kaijuTypePercentage -> type.equals(kaijuTypePercentage.getKaijuType())));
        assertTrue(kaijuTypePercentajeWithFiftyPercent.stream()
                .anyMatch(kaijuTypePercentage -> type2.equals(kaijuTypePercentage.getKaijuType())));
        assertEquals(ResponseMessageConstants.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD, kaijuResponseDTO.getMessage());
    }


    @Test
    public void getStatsFromExistentKaijuOnDB_onlyUnknownType() throws Exception {
        // Given
        String dna = "6576996abc567099unmi543ytiju46643";
        KaijuType type = createKaijuApiCaller.createKaiju(dna);
        assertEquals(KaijuType.UNKNOWN, type);


        // When
        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType="+type)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        // Then
        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);
        assertNotNull(kaijuResponseDTO);
        List<KaijuTypePercentage> kaijuTypePercentages = kaijuResponseDTO.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(kaijuTypePercentages));
        assertEquals(1, kaijuTypePercentages.size());
        KaijuTypePercentage gottenKaijuPercentage = kaijuTypePercentages.get(0);
        assertNotNull(gottenKaijuPercentage);
        assertEquals(100.0, gottenKaijuPercentage.getPercentage());
        assertEquals(type, gottenKaijuPercentage.getKaijuType());
        assertEquals(ResponseMessageConstants.UNKNOWN_SAMPLES_REACHED_THRESHOLD, kaijuResponseDTO.getMessage());
    }

}
