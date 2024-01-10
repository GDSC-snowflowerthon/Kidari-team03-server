package com.committers.snowflowerthon.committersserver.domain.attack.controller;

import com.committers.snowflowerthon.committersserver.domain.attack.Dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.service.AttackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AttackController {

    public final AttackService attackService;

    @GetMapping("/home/alarm")
    public ResponseEntity<?> getAlarms() {
        Long id = 1L; //내 id
        List<AttackDto> alarmList = attackService.findAlarms(id);
        return ResponseEntity.ok().body(alarmList);
        //내 id 가 공격받은 아이디 인걸 찾아서 리턴함
        //반환하는 AttackDto 는 isChecked 가 바뀌지 않아야 하고
        //반환하지 않는 Attack 는 isChecked 가 바뀌어야 함
    }

    @PatchMapping("/user/attack")
    public ResponseEntity<?> attackUser (@RequestParam String nickname) {
        String myName = 1; //내 닉네임 받아옴
        attackService.makeAttack(myName, nickname);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
