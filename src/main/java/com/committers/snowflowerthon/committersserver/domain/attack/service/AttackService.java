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
    private final AttackRepository attackRepository;
    private final MemberRepository memberRepository;
    private final ValidationService validationService;
    private final MemberService memberService;

    public List<AttackDto> findAlarms() {
        Member member = memberService.getAuthMember();
        member.alarmChecked();
        memberRepository.save(member);
        List<Attack> attackedList = attackRepository.findAllByMember(member); // 내가 공격받은 목록 가져오기
        attackedList.sort(Comparator.comparing(Attack::getTime).reversed()); // 시간을 최신순으로 정렬
        List<AttackDto> attackDtoList = new ArrayList<>();
        for (Attack attack : attackedList) {
            AttackDto attackDto = formatAttackDto(attack);
            attackDtoList.add(attackDto);
            if (!attack.getIsChecked()) {
                attack.checkAttack(); // 개별 atk의 isChecked 를 true 로 설정
            }
            attackRepository.save(attack); // 저장소 업데이트
        }
        return attackDtoList;
    }

    public AttackDto formatAttackDto(Attack attack){ //time 형식 지정
        LocalDateTime attackedTime = attack.getTime();
        String timeString = attackedTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Long memberId = attack.getAttackerId();
        String attackerName = validationService.valMember(memberId).getNickname();
        return AttackDto.builder()
                .time(timeString)
                .attacker(attackerName)
                .isChecked(attack.getIsChecked())
                .build();
    }

    public Boolean tryAttack(String attackedName){
        Member member = memberService.getAuthMember();
        if (!member.useSnowflake()) { // 눈송이 소모 불가
            return false;
        }
        memberRepository.save(member);
        createAttack(member.getId(), attackedName);
        return true;
    }

    public void createAttack(Long attackerId, String attackedName){
        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        Member attackedMember = validationService.valMember(attackedName);
        attackedMember.alarmUnchecked(); // 공격받은 사람의 newAlarm 을 true 로 만듬
        Attack newAttack = Attack.builder()
                .time(now)
                .attackerId(attackerId)
                .isChecked(false)
                .member(attackedMember)
                .build();
        Long snowmanHeight = attackedMember.getSnowmanHeight();
        if (snowmanHeight > 1) {
            attackedMember.updateSnowmanHeight(snowmanHeight - 1); // 공격받은 사람 눈사람 키 줄이기
        }
        memberRepository.save(attackedMember);
        attackRepository.save(newAttack);
    }
}
