package com.committers.snowflowerthon.committersserver.domain.ranking.controller;

import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/buddy/own")
    public ResponseEntity<MyRankDto> rankingBuddyOwn(){
        MyRankDto myRankDto = rankingService.getMyRank();
        return ResponseEntity.ok(myRankDto);
    }

    @GetMapping("/buddy/list")
    public ResponseEntity<List<RankDto>> rankingBuddyList() {
        List<RankDto> rankDto = rankingService.getBuddyRanking();
        return ResponseEntity.ok(rankDto);
    }

    @GetMapping("/univ/own")
    public ResponseEntity<MyRankDto> rankingUnivOwn(){
        MyRankDto myUnivRankDto = rankingService.getMyUnivRank();
        return ResponseEntity.ok(myUnivRankDto);
    }

    @GetMapping("/univ/list")
    public ResponseEntity<List<RankDto>> rankingUnivList() {
        List<RankDto> rankingUnivDto = rankingService.getUnivRanking();
        return ResponseEntity.ok(rankingUnivDto);
    }

}
