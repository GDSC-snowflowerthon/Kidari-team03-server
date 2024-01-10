package com.committers.snowflowerthon.committersserver.domain.ranking.service;

import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
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


    public int getMyRanking() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        buddyList.add(member);
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        return buddyList.indexOf(member) + 1;
    }

    public List<RankingBuddyDto> getBuddyRanking() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        buddyList.add(member);
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        List<RankingBuddyDto> buddyRanking = new ArrayList<>();
        for (Member buddy : buddyList) {
            RankingBuddyDto brd = new RankingBuddyDto(buddy.getNickname(), buddy.getSnowmanHeight());
            buddyRanking.add(brd);
        }
        return buddyRanking;
    }
}
