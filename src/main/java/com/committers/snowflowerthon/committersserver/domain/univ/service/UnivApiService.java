package com.committers.snowflowerthon.committersserver.domain.univ.service;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivApiDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivSearchDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivUpdateResDto;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.UnivRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnivApiService {

    private final UnivRepository univRepository;
    private static final String API_URL = "http://universities.hipolabs.com";

    public UnivSearchDto searchUnivName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<UnivApiDto>> response = restTemplate.exchange(
                API_URL + "/search?name=" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );
        List<UnivApiDto> responseList = response.getBody();
        String univName = responseList.get(0).getName();
        UnivSearchDto univSearched = UnivSearchDto.builder() // 최상위 검색 결과 하나만 반환
                .univName(univName)
//                .totalHeight(1) // 대학교 총합 눈사람 높이 받아옴
                .isRegistered(checkIsRegistered(univName)) // 사용자의 등록여부 받아옴
                .build();
        return univSearched;
    }


    public Boolean checkIsRegistered(String univName){
//        Member member = memberservice.getAuthMember();
//        Univ univ = validationService.valUniv(univ); // 이름으로 탐색하는지 확인하기
//        if (member.getUniv() != univ) {
//            return false;
//        } else {
//            return true;
//        }
        return null;
    }

    public UnivUpdateResDto registerUniv(String univName, Boolean isRegistered){
//        Member member = memberservice.getAuthMember();
//        Univ univ = validationService.valUniv(univ);
//        if (checkIsRegistered(univ)){
//            // cancel
//        }
        // 사용자의 Univ univ와 id가 일치하는지 확인. query 변수로는 univName을 받음.
        // validationService에서 대학교 찾아서
        // 등록 학생수 +1, 높이 합하고, 사용자의
        return null;
    }
}
