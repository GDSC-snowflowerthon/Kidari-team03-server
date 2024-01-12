package com.committers.snowflowerthon.committersserver.domain.attack.controller;

import com.committers.snowflowerthon.committersserver.domain.attack.dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.service.AttackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://kidari.site")
@RequestMapping("/api/v1")
public class AttackController {

    public final AttackService attackService;

    @GetMapping("/home/alarm")
    public ResponseEntity<List<AttackDto>> getAlarms() {
        List<AttackDto> alarmList = attackService.findAlarms();
        return ResponseEntity.ok().body(alarmList); // null일 수 있음
    }

    @PatchMapping("/user/attack")
    public ResponseEntity<?> attackUser (@RequestParam String nickname) {
        if (attackService.tryAttack(nickname)) {
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }
}
