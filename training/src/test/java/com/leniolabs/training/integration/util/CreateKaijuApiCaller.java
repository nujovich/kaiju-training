package com.leniolabs.training.integration.util;

import com.leniolabs.training.controller.dto.RequestKaijuDTO;
import com.leniolabs.training.integration.constants.KaijuEndpointConstants;
import com.leniolabs.training.model.KaijuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class CreateKaijuApiCaller {

    @Autowired
    private EndpointUtil endpointUtil;


    public KaijuType createKaiju(String dna) throws Exception {
        RequestKaijuDTO kaijuDTO = new RequestKaijuDTO(dna);

        MvcResult mvcResult = endpointUtil.postCall(KaijuEndpointConstants.POST_KAIJU, kaijuDTO)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        KaijuType responseKaijuType = endpointUtil.getResponseObject(mvcResult, KaijuType.class);

        return responseKaijuType;
    }
}
