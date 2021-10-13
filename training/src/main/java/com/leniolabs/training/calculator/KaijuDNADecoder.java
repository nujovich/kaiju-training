package com.leniolabs.training.calculator;

import com.leniolabs.training.utils.RegexConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class KaijuDNADecoder {

    private Integer k;

    public KaijuDNADecoder(@Value("${kajiu.k.value}") Integer k) {
        this.k = k;
    }


    public Integer decodeDNA(String dna) {
        String numbersJoined = String.join("", dna.split(RegexConstants.REMOVE_NOT_NUMBERS));
        char[] numbers = numbersJoined.toCharArray();
        int initialIndex = 0;
        int limit = k;
        int maxNumber = 0;
        //Map<String, Integer> sumOfNumbersMap = new HashMap<>();
        while(limit <= numbers.length) {
            String groupOfNumbers = numbersJoined.substring(initialIndex, limit);
            Integer sumOfEachNumber = sumEachDigit(groupOfNumbers);
            maxNumber = sumOfEachNumber > maxNumber ? sumOfEachNumber : maxNumber;
            //sumOfNumbersMap.put(groupOfNumbers, sumOfEachNumber);
            initialIndex++;
            limit++;
        }
        return maxNumber;//Collections.max(sumOfNumbersMap.values());
    }

    private Integer sumEachDigit(String number) {
        return String.valueOf(number)
                .chars()
                .map(Character::getNumericValue)
                .sum();
    }
}
