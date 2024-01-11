package com.committers.snowflowerthon.committersserver.domain.univ.service;

import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.UnivException;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivRegisterResultDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivSearchDto;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.UnivRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnivService {

    private final ValidationService validationService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final UnivApiService univApiService;
    private final UnivRepository univRepository;

    public void createUniv(String univName){
        Univ univ = Univ.builder()
                .univName(univName)
                .totalHeight(0L)
                .belonged(0L)
                .build();
        univRepository.save(univ);
    }

    public UnivSearchDto searchUnivName(String name) {
        String univApiName = univApiService.searchUnivName(name);
        Univ univ = validationService.valUniv(univApiName);
        if (univ == null){  // 세계 대학 API에 포함되나 생성되지 않았다면 생성
            createUniv(univApiName);
            return UnivSearchDto.builder()
                    .univName(univApiName)
                    .totalHeight(0L)
                    .isRegistered(false)
                    .build();
        }
        else {
            return UnivSearchDto.builder() // 최상위 검색 결과 하나만 반환
                    .univName(univ.getUnivName())
                    .totalHeight(univ.getTotalHeight())
                    .isRegistered(registrationStatus(univ)) // 사용자의 등록여부 받아옴
                    .build();
        }
    }

    public Boolean registrationStatus(Univ univ){ //true or false
        Member member = memberService.getAuthMember();
        if (member.getUniv() == univ) {
            return true;
        } else {
            return false;
        }
    }


    public Boolean registerUniv(String univName, Boolean isRegistered){
        Univ searchedUniv = validationService.valUniv(univName);
        if (searchedUniv == null){
            throw new UnivException(ErrorCode.UNIV_NOT_FOUND);
        }
        Member member = memberService.getAuthMember();
        Univ myUniv = member.getUniv();

        Boolean status = registrationStatus(searchedUniv);
        if (status != isRegistered) {
            throw new UnivException(ErrorCode.UNIV_NOT_FOUND);
        }
        if (status) {
            if (isRegistered) {
                //등록 취소
                //member.updateUnivId(null);
                //univ에서 belong 정보 업데이트
                //totalHeight 정보 업데이트
                //univRepo에 저장
                member.updateUniv(null);
                memberRepository.save(member);
                return true;
            } else {
                return false; // 등록 불가능한 상태
            }
        }
        else {
            if (isRegistered){
                throw new UnivException(ErrorCode.UNIV_NOT_FOUND); // 추후 에러코드 변경 // 불가능한 상태, 에러
            } else {
                //신규 등록
                //member.updateUnivId(univ);
                //univ에서 belong 정보 업데이트
                //totalHeight 정보 업데이트
                //univRepo에 저장
                return true;
            }
        }
    }
    // 사용자의 Univ univ와 id가 일치하는지 확인. query 변수로는 univName을 받음.
    // validationService에서 대학교 찾아서
    // 등록 학생수 +1, 높이 합하고, 사용자의

    // 랭킹에서 리스트 반환
    public List<Univ> getAllUnivList() {
        List<Univ> univList = univRepository.findAll();
        if (univList == null || univList.isEmpty()) {
            throw new UnivException(ErrorCode.UNIV_NOT_FOUND); // 대학교 찾아지지 않음 -> 더미 대학교 넣어둬야?
        }
        univList.sort(Comparator.comparing(Univ::getTotalHeight).reversed());
        return univList;
    }
}
