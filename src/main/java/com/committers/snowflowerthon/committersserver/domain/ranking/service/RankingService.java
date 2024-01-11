package com.committers.snowflowerthon.committersserver.domain.ranking.service;

import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingBuddyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final MemberService memberService;
    private final FollowService followService;

    public MyRankDto getMyRank() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        buddyList.add(member); // 사용자를 추가한 후 내림차순 정렬해 사용자의 순위를 구함
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        return new MyRankDto(buddyList.indexOf(member) + 1);
    }

    public List<RankingBuddyDto> getBuddyRanking() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        buddyList.add(member); // 사용자를 추가한 후 내림차순 정렬
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        List<RankingBuddyDto> buddyRankingList = new ArrayList<>();
        for (Member buddy : buddyList) {
            RankingBuddyDto buddyDto = new RankingBuddyDto(buddy.getNickname(), buddy.getSnowmanHeight());
            buddyRankingList.add(buddyDto);
        }
        return buddyRankingList;
    }
}
