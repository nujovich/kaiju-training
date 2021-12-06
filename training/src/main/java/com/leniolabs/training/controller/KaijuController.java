package com.leniolabs.training.controller;

import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.controller.dto.RequestKaijuDTO;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.service.KaijuService;
import com.leniolabs.training.utils.RegexConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://ul33g.csb.app/", maxAge = 3600)
@RestController
@RequestMapping("/lenio-training/kaiju/v1")
public class KaijuController {

    @Autowired
    private KaijuService kaijuService;

    @PostMapping("/dna")
    public ResponseEntity<KaijuType> createKaiju(@RequestBody RequestKaijuDTO requestKaijuDTO) {
        String dna = requestKaijuDTO.getDna();
        if (StringUtils.isBlank(dna) || !containsNumberAndLetters(dna)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(kaijuService.createKaiju(dna));
    }

    @GetMapping("/stats")
    public ResponseEntity<KaijuResponseDTO> getStats(@RequestParam KaijuType kaijuType){
        KaijuType type = kaijuType == null ? KaijuType.ALL : kaijuType;
        KaijuResponseDTO responseDTO = kaijuService.getPercentageOfSamplesEvaluated(type);
        return ResponseEntity.ok(responseDTO);
    }

    private boolean containsNumberAndLetters(String dna){
        return dna.toUpperCase().matches(RegexConstants.UPPER_ALPHA_REGEX) && dna.matches(RegexConstants.NUM_REGEX);
    }
}
