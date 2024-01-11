package com.committers.snowflowerthon.committersserver.domain.univ.controller;

import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivRegisterResultDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivSearchDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivUpdateResDto;
import com.committers.snowflowerthon.committersserver.domain.univ.service.UnivApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UnivController {

    private final UnivApiService univApiService;

    @GetMapping("/univ")
    public ResponseEntity<UnivSearchDto> searchUniversity(@RequestParam String univName) {
        UnivSearchDto universities = univApiService.searchUnivName(univName);
        return ResponseEntity.ok(universities);
    }

    @PatchMapping("/univ/update")
    public ResponseEntity<UnivUpdateResDto> updateUniversity(@RequestParam String univName, @RequestParam Boolean isRegistered) {
        UnivUpdateResDto registerResult = univApiService.registerUniv(univName, isRegistered);
        if (registerResult.getStatus() == 200) {
            return ResponseEntity.status(HttpStatus.OK).body(registerResult);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerResult);
        }
    }

    // 대학교 랭킹 추가 필요
}