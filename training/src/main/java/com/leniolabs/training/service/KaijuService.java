package com.leniolabs.training.service;

import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.model.KaijuType;

import java.util.List;

public interface KaijuService {

    KaijuType createKaiju(String dna);

    KaijuResponseDTO getPercentageOfSamplesEvaluated(KaijuType type);
}
