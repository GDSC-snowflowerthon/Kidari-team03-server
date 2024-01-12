package com.committers.snowflowerthon.committersserver.domain.ranking.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyUnivRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingBuddyDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingUnivDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/buddy/own")
    public ResponseEntity<?> rankingBuddyOwn(){
        MyRankDto myRankDto = rankingService.getMyRank();
        return ResponseEntity.ok().body(ApiResponse.success(myRankDto));
    }

    @GetMapping("/buddy/list")
    public ResponseEntity<?> rankingBuddyList() {
        List<RankingBuddyDto> rankingList = rankingService.getBuddyRanking();
        return ResponseEntity.ok().body(ApiResponse.success(rankingList));
    }

    @GetMapping("/univ/own")
    public ResponseEntity<?> rankingUnivOwn(){
        MyUnivRankDto myUnivRankDto = rankingService.getMyUnivRank();
        if (myUnivRankDto == null){
            return ResponseEntity.ok().body(ApiResponse.success(new MyUnivRankDto("none", 0)));
        }
        return ResponseEntity.ok().body(ApiResponse.success(myUnivRankDto));
    }

    @GetMapping("/univ/list")
    public ResponseEntity<?> rankingUnivList() {
        List<RankingUnivDto> rankingList = rankingService.getUnivRanking();
        if (rankingList == null){
            return ResponseEntity.ok().body(ApiResponse.success()); // 아직 DB에 대학교가 없음
        }
        return ResponseEntity.ok().body(ApiResponse.success(rankingList));
    }

}
