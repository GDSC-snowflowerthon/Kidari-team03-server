package com.committers.snowflowerthon.committersserver.domain.univ.service;

import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.UnivException;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivRegisterDto;
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

    // 새로운 대학교 생성
    public void createUniv(String univName){
        Univ univ = Univ.builder()
                .univName(univName)
                .totalHeight(0L)
                .belonged(0L)
                .build();
        univRepository.save(univ);
    }

    // 대학교 이름으로 검색
    public UnivSearchDto searchUnivName(String name) {
        String univApiName = univApiService.searchUnivName(name);
        if (univApiName == null) { // 찾은 대학교 이름이 세계 대학 API에 포함되지 않는 경우 새로운 대학교를 생성하지 않음.
            return null;
        }
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

    // 현재 해당 대학교에 등록중인지 확인
    public Boolean registrationStatus(Univ univ){ //true or false
        Member member = memberService.getAuthMember();
        if (member.getUniv() == univ) {
            return true;
        } else {
            return false;
        }
    }

    // 대학교 등록 or 취소
    public UnivRegisterDto updateRegistration(String univName, Boolean isRegistered){ //isRegistered는 현재 newUniv의 등록여부임
        Univ newUniv = validationService.valUniv(univName);
        if (newUniv == null){
            throw new UnivException(ErrorCode.UNIV_NOT_FOUND);
        }
        Boolean matchUniv = registrationStatus(newUniv); // 검증: 해당 대학교를 등록 중인지 확인
        if (matchUniv != isRegistered){
            throw new UnivException(ErrorCode.UNIV_REGISTER_BAD_REQUEST); // 이 결과는 일치해야 하므로 에러 발생!
        }

        Member member = memberService.getAuthMember();
        Univ myUniv = validationService.valUniv(member.getUniv().getId()); // null일 경우 내가 등록중인 대학교가 없음
        Univ updatedUniv;
        if (myUniv == null && !isRegistered) { // 새 대학교 등록
            updatedUniv = Univ.builder()
                    .univName(newUniv.getUnivName())
                    .totalHeight(newUniv.getTotalHeight() + member.getSnowmanHeight())
                    .belonged(newUniv.getBelonged() + 1)
                    .build();
            member.updateUniv(updatedUniv);
        }
        else if (myUniv != null && isRegistered) { //등록 취소
            updatedUniv = Univ.builder()
                    .univName(myUniv.getUnivName())
                    .totalHeight(myUniv.getTotalHeight() - member.getSnowmanHeight())
                    .belonged(myUniv.getBelonged() - 1)
                    .build();
            member.updateUniv(null);
        }
        else {
            throw new UnivException(ErrorCode.UNIV_REGISTER_BAD_REQUEST);
        }
        memberRepository.save(member);
        univRepository.save(updatedUniv);
        return new UnivRegisterDto(newUniv.getUnivName(), !isRegistered);
    }

    // 대학교 랭킹을 위해 대학 목록 반환
    public List<Univ> getAllUnivList() {
        List<Univ> univList = univRepository.findAll();
        if (univList == null || univList.isEmpty()) {
            return null;
        }
        univList.sort(Comparator.comparing(Univ::getTotalHeight).reversed());
        return univList;
    }
}
