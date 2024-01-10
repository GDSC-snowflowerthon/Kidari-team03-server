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
        Long userId = 1L; //내 id
        List<AttackDto> alarmList = attackService.findAlarms(userId);
        //내 newAlarm을 check 해야 함
        return ResponseEntity.ok().body(alarmList);
    }

    @PatchMapping("/user/attack")
    public ResponseEntity<?> attackUser (@RequestParam String nickname) {
        Long userId = 1L; //내 아이디 받아옴

        attackService.makeAttack(userId, nickname);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
