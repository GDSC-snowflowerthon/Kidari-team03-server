package com.committers.snowflowerthon.committersserver.domain.attack.service;

import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.attack.dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.Attack;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.AttackRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttackService {
    private final ValidationService validationService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AttackRepository attackRepository;

    // AttackController의 getAlarms 관련

    public List<AttackDto> findAlarms() {
        Member member = memberService.getAuthMember();
        member.alarmChecked();  // 내 알림을 확인된 상태로 변경
        memberRepository.save(member);

        List<Attack> attackedList = attackRepository.findAllByMember(member); // 내가 공격받은 목록 가져오기
        if (attackedList == null || attackedList.isEmpty()) //공격받은 기록이 없는 경우
            return null;
        attackedList.sort(Comparator.comparing(Attack::getTime).reversed()); // 시간을 최신순으로 정렬

        List<AttackDto> attackDtoList = new ArrayList<>(); // 응답 형식 반환 및 개별 공격 확인 위해 AttackDto 리스트 생성
        for (Attack attack : attackedList) {
            attackDtoList.add(formatToAttackDto(attack)); // 형식 변환해 리스트에 넣음
            if (!attack.getIsChecked()) { // isChecked가 true: 이미 이전에 확인 완료, false: 아직 안 확인(이번에 확인)
                attack.checkAttack();
            }
            attackRepository.save(attack); // 저장소에 공격 정보 업데이트해 저장
        }
        return attackDtoList;
    }

    public AttackDto formatToAttackDto(Attack attack){ // 응답 형식을 변환(AttackDto; time 형식도 지정한 패턴으로 변환)
        LocalDateTime attackedTime = attack.getTime();
        String timeString = attackedTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Long memberId = attack.getAttackerId();
        String attackerName = validationService.valMember(memberId).getNickname();
        return AttackDto.builder()
                .time(timeString)
                .attacker(attackerName)  // 공격자의 이름을 찾아 String형으로 저장
                .isChecked(attack.getIsChecked())
                .build();
    }
    
    // AttackController의 attackUser 관련

    public Boolean tryAttack(String attackedName){ // 공격을 시도함
        Member member = memberService.getAuthMember();
        if (!member.useSnowflake()) { // true: 눈송이 소모함, false: 눈송이 소모 불가
            return false;
        }
        memberRepository.save(member); // 변경된 눈송이 갯수 저장
        createAttack(member.getId(), attackedName); // 공격 생성
        return true;
    }

    public void createAttack(Long attackerId, String attackedName){
        LocalDateTime time = LocalDateTime.now(); // 현재 시각
        Member attackedMember = validationService.valMember(attackedName); // 공격받은 사람
        attackedMember.alarmUnchecked(); // 공격받은 사람의 newAlarm 을 true 로
        Attack newAttack = Attack.builder()
                .time(time)
                .attackerId(attackerId)
                .isChecked(false)
                .member(attackedMember)
                .build();
        Long snowmanHeight = attackedMember.getSnowmanHeight(); // 공격받은 사람의 눈사람
        if (snowmanHeight > 1) {
            attackedMember.updateSnowmanHeight(snowmanHeight - 1); // 공격받은 사람의 눈사람 키를 줄임
        }
        memberRepository.save(attackedMember);
        attackRepository.save(newAttack);
    }
}
