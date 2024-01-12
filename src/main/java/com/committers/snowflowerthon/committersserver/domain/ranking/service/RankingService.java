package com.committers.snowflowerthon.committersserver.domain.ranking.service;

import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.MyUnivRankDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingBuddyDto;
import com.committers.snowflowerthon.committersserver.domain.ranking.dto.RankingUnivDto;
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

    public List<RankingBuddyDto> getBuddyRanking() {
        Member member = memberService.getAuthMember();
        List<Member> buddyList = followService.getBuddyList();
        // 친구가 없어도 랭킹 띄우기를 진행함
        buddyList.add(member); // 사용자를 추가한 후 내림차순 정렬
        buddyList.sort(Comparator.comparing(Member::getSnowmanHeight).reversed());
        List<RankingBuddyDto> rankingList = new ArrayList<>();
        for (Member buddy : buddyList) {
            RankingBuddyDto rankingBuddyDto = new RankingBuddyDto(buddy.getNickname(), buddy.getSnowmanHeight());
            rankingList.add(rankingBuddyDto);
        }
        return rankingList;
    }

    // University Ranking

    public MyUnivRankDto getMyUnivRank() {
        Univ myUniv = memberService.getAuthMember().getUniv();
        if (myUniv == null){ // 소속된 대학이 없음
            return null;
        }
        List<Univ> univList = univService.getAllUnivList();
        return new MyUnivRankDto(myUniv.getUnivName(), univList.indexOf(myUniv) + 1);
    }

    public List<RankingUnivDto> getUnivRanking() {
        List<Univ> univList = univService.getAllUnivList();
        if (univList == null || univList.isEmpty())
            return null; // 아직 유저가 등록된 대학교가 존재하지 않음
        List<RankingUnivDto> rankingList = new ArrayList<>();
        for (Univ univ : univList) {
            if (univ.getBelonged() == 0) // 1명 이상이 등록된 대학교만 띄움
                continue;
            RankingUnivDto rankingUnivDto = new RankingUnivDto(univ.getUnivName(), univ.getTotalHeight());
            rankingList.add(rankingUnivDto);
        }
        return rankingList;
    }
}
