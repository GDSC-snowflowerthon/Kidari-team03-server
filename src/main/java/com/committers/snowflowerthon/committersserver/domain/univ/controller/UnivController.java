package com.committers.snowflowerthon.committersserver.domain.univ.controller;

import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivSearchDto;
import com.committers.snowflowerthon.committersserver.domain.univ.service.UnivService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UnivController {

    private final UnivService univService;

    @GetMapping("/univ")
    public ResponseEntity<UnivSearchDto> searchUniversity(@RequestParam String univName) {
        UnivSearchDto universities = univService.searchUnivName(univName);
        return ResponseEntity.ok(universities);
    }

    @PatchMapping("/univ/update")
    public ResponseEntity<?> updateUniversity(@RequestParam String univName, @RequestParam Boolean isRegistered) {
        Boolean registrationResult = univService.registerUniv(univName, isRegistered); // isRegistered는 그 대학교에 내가 등록되었는지
        if (registrationResult == null) {
            //반환값 변경해야
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            //반환값 변경해야
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}