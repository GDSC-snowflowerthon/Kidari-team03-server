package com.committers.snowflowerthon.committersserver.domain.attack.service;

import com.committers.snowflowerthon.committersserver.domain.attack.Dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.Attack;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.AttackRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttackService {
    private final AttackRepository attackRepository;
    private final MemberService memberService;

    public List<AttackDto> findAlarms(Long id) {
        Member member = memberService.getMemberById(id);
        List<Attack> attackedList = attackRepository.findAllByMember(member); // 내가 공격받은 목록 가져오기
        attackedList.sort(Comparator.comparing(Attack::getTime).reversed()); //
        List<AttackDto> attackDtoList = new ArrayList<>();
        for (Attack atk : attackedList) {
            AttackDto attackDto = formatAttackDto(atk);
            attackDtoList.add(attackDto);
            if (!atk.getIsChecked()) {
                atk.checkAttack(); //atk의 isChecked 를 true 로 설정
            }
        }
        return attackDtoList;
    }

    public AttackDto formatAttackDto(Attack attack){ //time 형식 지정
        LocalDateTime attackedTime = attack.getTime();
        String timeString = attackedTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        String attackerName = getAttackerName(attack);
        return AttackDto.builder()
                .time(timeString)
                .attacker(attackerName)
                .isChecked(attack.getIsChecked())
                .build();
    }

    public void makeAttack(Long attackerId, String attackedName){
        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        Member attackedMember = memberService.getMemberByNickname(attackedName);
        attackedMember.alarmUnchecked(); // 공격받은 사람의 newAlarm 을 true 로 만듬
        Attack newAttack = Attack.builder()
                .time(now)
                .attackerId(attackerId)
                .isChecked(false)
                .member(attackedMember)
                .build();
        attackRepository.save(newAttack);
    }

    public String getAttackerName(Attack attack){
        Member attacker = memberService.getMemberById(attack.getAttackerId());
        return attacker.getNickname();
    }
}
