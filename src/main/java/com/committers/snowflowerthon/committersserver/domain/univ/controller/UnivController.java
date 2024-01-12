package com.committers.snowflowerthon.committersserver.domain.univ.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivRegisterDto;
import com.committers.snowflowerthon.committersserver.domain.univ.dto.UnivSearchDto;
import com.committers.snowflowerthon.committersserver.domain.univ.service.UnivService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1")
public class UnivController {

    private final UnivService univService;

    @GetMapping("/univ")
    public ResponseEntity<?> searchUniversity(@RequestParam String univName) {
        UnivSearchDto universities = univService.searchUnivName(univName);
        return ResponseEntity.ok().body(ApiResponse.success(universities));
    }

    @PatchMapping("/univ/update")
    public ResponseEntity<?> updateUniversity(@RequestParam String univName, @RequestParam Boolean isRegistered) {
        UnivRegisterDto registration = univService.updateRegistration(univName, isRegistered); // isRegistered는 그 대학교에 내가 등록되었는지
        if (registration == null) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.UNIV_CANNOT_BE_REGISTERED));
        } else {
            return ResponseEntity.ok().body(ApiResponse.success(registration));
        }
    }
}