package com.leniolabs.training.service;

import com.leniolabs.training.calculator.KaijuTypeCalculator;
import com.leniolabs.training.controller.dto.KaijuResponseDTO;
import com.leniolabs.training.controller.dto.KaijuTypePercentage;
import com.leniolabs.training.model.Kaiju;
import com.leniolabs.training.model.KaijuType;
import com.leniolabs.training.repository.KaijuRepository;
import com.leniolabs.training.utils.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional
public class KaijuServiceImp implements KaijuService {

    private KaijuTypeCalculator kaijuTypeCalculator;
    private KaijuRepository repository;
    private Integer unknownPercentageAllowed;

    @Autowired
    public KaijuServiceImp(KaijuTypeCalculator kaijuTypeCalculator, KaijuRepository repository, @Value("${kajiu.unknown.percentage.allowed}") Integer unknownPercentageAllowed) {
        this.kaijuTypeCalculator = kaijuTypeCalculator;
        this.repository = repository;
        this.unknownPercentageAllowed = unknownPercentageAllowed;
    }

    @Override
    public KaijuType createKaiju(String dna) {
        Kaiju kaijuFromDb = repository.findByDna(dna);
        if (kaijuFromDb != null){
            return kaijuFromDb.getType();
        }
        KaijuType typeToCreate = kaijuTypeCalculator.calculateTypeFromDNA(dna);
        repository.save(new Kaiju(dna, typeToCreate));
        return typeToCreate;
    }

    @Override
    public KaijuResponseDTO getPercentageOfSamplesEvaluated(KaijuType type) {
        List<KaijuType> kaijuTypes = repository.findAllKaijuType();
        int totalOfKaijus = kaijuTypes.size();
        KaijuResponseDTO kaijuResponseDTO = new KaijuResponseDTO();
        kaijuResponseDTO.setKaijuTypePercentages(new ArrayList<>());

        Map<KaijuType, Long> groupedKaijuTypes = kaijuTypes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long numberOfUnknown = groupedKaijuTypes.get(KaijuType.UNKNOWN) == null ? 0L : groupedKaijuTypes.get(KaijuType.UNKNOWN);
        Double unknownPercentage = getPercentage(totalOfKaijus, numberOfUnknown);


        if (KaijuType.ALL.equals(type) || KaijuType.ONE.equals(type)) {
            kaijuResponseDTO.getKaijuTypePercentages()
                    .add(buildKaijuPercentaje(KaijuType.ONE, getPercentage(totalOfKaijus, groupedKaijuTypes.get(KaijuType.ONE))));
        }

        if (KaijuType.ALL.equals(type) || KaijuType.TWO.equals(type)) {
            kaijuResponseDTO.getKaijuTypePercentages()
                    .add(buildKaijuPercentaje(KaijuType.TWO, getPercentage(totalOfKaijus, groupedKaijuTypes.get( KaijuType.TWO))));
        }

        if (KaijuType.ALL.equals(type) || KaijuType.THREE.equals(type)) {
            kaijuResponseDTO.getKaijuTypePercentages()
                    .add(buildKaijuPercentaje(KaijuType.THREE, getPercentage(totalOfKaijus, groupedKaijuTypes.get(KaijuType.THREE))));
        }

        if (KaijuType.ALL.equals(type) || KaijuType.UNKNOWN.equals(type)) {
            kaijuResponseDTO.getKaijuTypePercentages().add(buildKaijuPercentaje(KaijuType.UNKNOWN, unknownPercentage));
        }

        String message = unknownPercentage > Double.valueOf(unknownPercentageAllowed) ?
                ResponseMessageConstants.UNKNOWN_SAMPLES_REACHED_THRESHOLD : ResponseMessageConstants.UNKNOWN_SAMPLES_UNDER_ACCEPTED_THRESHOLD;
        kaijuResponseDTO.setMessage(message);

        return kaijuResponseDTO;
    }

    private KaijuTypePercentage buildKaijuPercentaje(KaijuType type, Double percentage) {
        return new KaijuTypePercentage(type, percentage);
    }


    private Double getPercentage(Integer total, Long totalForAType) {
        totalForAType = totalForAType == null ? 0L : totalForAType;
        Double percentage = 0.0;
        if (totalForAType > 0){
            percentage = Double.valueOf(totalForAType * 100 / total);
        }
        return percentage;
    }
}
