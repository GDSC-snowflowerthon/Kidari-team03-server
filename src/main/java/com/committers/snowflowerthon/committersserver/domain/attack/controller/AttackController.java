package com.committers.snowflowerthon.committersserver.domain.attack.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.domain.attack.dto.AttackDto;
import com.committers.snowflowerthon.committersserver.domain.attack.service.AttackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1")
public class AttackController {

    public final AttackService attackService;

    @GetMapping("/home/alarm") // 알림 조회 (공격받은 목록 조회)
    public ResponseEntity<?> getAlarms() {
        List<AttackDto> attackedList = attackService.findAlarms();
        return ResponseEntity.ok().body(ApiResponse.success(attackedList)); // null일 수 있음
    }

    @PatchMapping("/user/attack")
    public ResponseEntity<?> attackUser(@RequestParam String nickname) {
        if (attackService.tryAttack(nickname)) {
            return ResponseEntity.ok().body(ApiResponse.success()); // 공격 성공
        }
        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.SNOWFLAKE_CANNOT_BE_USED)); // 눈송이를 사용할 수 없는 경우
    }
}
