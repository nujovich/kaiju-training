package com.leniolabs.training.integration;

import com.leniolabs.training.TrainingApplicationTests;
import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.controller.dto.KaijuTypePercentage;
import com.leniolabs.training.integration.constants.KaijuEndpointConstants;
import com.leniolabs.training.integration.util.CreateKaijuApiCaller;
import com.leniolabs.training.integration.util.EndpointUtil;
import com.leniolabs.training.model.KaijuType;
import org.hsqldb.lib.MultiValueHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType=ALL")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);

        assertNotNull(kaijuResponseDTO);
        assertTrue(kaijuResponseDTO.getKaijuTypePercentages().stream().allMatch(kaijuTypePercentage -> 0.0 == kaijuTypePercentage.getPercentage()));
    }


    @Test
    public void getStatsFromExistentKaijuOnDB() throws Exception {
        String dna = "123jmg456";
        KaijuType type = createKaijuApiCaller.createKaiju(dna);

        MvcResult mvcResult = endpointUtil.getCall(KaijuEndpointConstants.GET_STATS+"?kaijuType="+type)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        KaijuResponseDTO kaijuResponseDTO = endpointUtil.getResponseObject(mvcResult, KaijuResponseDTO.class);

        assertNotNull(kaijuResponseDTO);
        List<KaijuTypePercentage> kaijuTypePercentages = kaijuResponseDTO.getKaijuTypePercentages();
        assertFalse(CollectionUtils.isEmpty(kaijuTypePercentages));
        assertEquals(1, kaijuTypePercentages.size());
        KaijuTypePercentage gottenKaijuPercentage = kaijuTypePercentages.get(0);
        assertNotNull(gottenKaijuPercentage);
        assertEquals(100.0, gottenKaijuPercentage.getPercentage());
        assertEquals(type, gottenKaijuPercentage.getKaijuType());
    }

}
