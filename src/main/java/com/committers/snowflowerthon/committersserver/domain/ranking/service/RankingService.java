package com.committers.snowflowerthon.committersserver.domain.ranking.service;

import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankDto;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.service.UnivService;
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
    private final UnivService univService;

    // Buddy Ranking

    public MyRankDto getMyRank() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        buddyList.add(member); // 사용자를 추가한 후 내림차순 정렬해 사용자의 순위를 구함
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        return new MyRankDto(buddyList.indexOf(member) + 1);
    }

    public List<RankDto> getBuddyRanking() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        /*if (buddyList == null) { // 친구가 없다면 1등이 불가..?
            return null;
        }*/
        buddyList.add(member); // 사용자를 추가한 후 내림차순 정렬
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        List<RankDto> rankingList = new ArrayList<>();
        for (Member buddy : buddyList) {
            RankDto rankDto = new RankDto(buddy.getNickname(), buddy.getSnowmanHeight());
            rankingList.add(rankDto);
        }
        return rankingList;
    }

    // University Ranking

    public MyRankDto getMyUnivRank() {
        Univ myUniv = memberService.getAuthMember().getUniv();
        if (myUniv == null)
            return null; // 소속된 대학이 없음
        List<Univ> univList = univService.getAllUnivList();
        return new MyRankDto(univList.indexOf(myUniv) + 1);
    }

    public List<RankDto> getUnivRanking() {
        Univ myUniv = memberService.getAuthMember().getUniv();
        List<Univ> univList = univService.getAllUnivList();
        List<RankDto> rankingList = new ArrayList<>();
        for (Univ univ : univList) {
            RankDto rankDto = new RankDto(univ.getUnivName(), univ.getTotalHeight());
            rankingList.add(rankDto);
        }
        return rankingList;

    }
}
