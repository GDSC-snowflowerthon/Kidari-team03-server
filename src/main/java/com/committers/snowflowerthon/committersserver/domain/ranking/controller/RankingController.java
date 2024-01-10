package com.committers.snowflowerthon.committersserver.domain.ranking.controller;

import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingBuddyDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/ranking/buddy/own")
    public ResponseEntity<MyRankDto> rankingBuddyOwn(){
        MyRankDto myRankDto = rankingService.getMyRank();
        return ResponseEntity.ok(myRankDto);
    }

    @GetMapping("/ranking/buddy/list")
    public ResponseEntity<RankingBuddyDto> rankingBuddyList() {

        RankingBuddyDto rankingBuddyDto = rankingService.getRankingList();
        return ResponseEntity.ok(rankingBuddyDto);
    }
}
