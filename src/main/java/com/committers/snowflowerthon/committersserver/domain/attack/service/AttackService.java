package com.committers.snowflowerthon.committersserver.domain.attack.service;

import com.committers.snowflowerthon.committersserver.domain.attack.Dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.Attack;
import com.committers.snowflowerthon.committersserver.domain.attack.entity.AttackRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
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
                atk.checkAttack(); //isChecked 를 true 로 설정
            }
        }
        return attackDtoList;
    }

    public static AttackDto formatAttackDto(Attack attack){ //time 형식 지정
        LocalDateTime attackedTime = attack.getTime();
        String timeString = attackedTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        return AttackDto.builder()
                .time(timeString)
                .attacker(attack.getAttacker())
                .isChecked(attack.getIsChecked())
                .build();
    }

    public void makeAttack(String attackerName, String attackedName){
        Member attackerName = memberService.getMemberByNickname(attackedName);
        Member attackedName = memberService.getMemberByNickname(attackedName);
        attackedName.
        //공격받은 사람의 newAlarm 을 true 로 만듬
    }
}
