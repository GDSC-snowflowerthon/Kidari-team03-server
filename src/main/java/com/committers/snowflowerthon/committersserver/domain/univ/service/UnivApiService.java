package com.committers.snowflowerthon.committersserver.domain.univ.service;

import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivApiDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UnivApiService {

    private static final String API_URL = "http://universities.hipolabs.com";

    public String searchUnivName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<UnivApiDto>> response = restTemplate.exchange(
                API_URL + "/search?name=" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );
        String univApiName = response.getBody().get(0).getName();
        return univApiName;
    }
}
