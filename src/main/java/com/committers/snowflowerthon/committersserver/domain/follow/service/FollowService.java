package com.committers.snowflowerthon.committersserver.domain.follow.service;

import com.committers.snowflowerthon.committersserver.auth.config.AuthenticationUtils;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.follow.dto.FollowPatchedDto;
import com.committers.snowflowerthon.committersserver.domain.follow.entity.Follow;
import com.committers.snowflowerthon.committersserver.domain.follow.entity.FollowRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final ValidationService validationService;
    private final FollowRepository followRepository;

    public Follow createFollow(Member buddy){
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        return Follow.builder()
                .buddyId(buddy.getId())
                .member(member)
                .build();
    }

    public Follow getFollow(Member buddy){ // 반환값이 NULL일 수 있음
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        return followRepository.findByMemberAndBuddyId(member, buddy.getId())
                .orElse(null);
    }

    public boolean followStatus(Member buddy) {
        Follow follow = getFollow(buddy);
        if (follow == null) {
            return false;
        }
        return true;
    }

    public FollowPatchedDto changeFollowStatus(String nickname, boolean isFollowed) {
        Member buddy = validationService.valMember(nickname);
        Follow follow = getFollow(buddy);
        if (follow != null && isFollowed) {
            followRepository.delete(follow); // 해당 팔로우 가져와 삭제
        } else if (follow == null && !isFollowed) {
            Follow newFollow = createFollow(buddy); //새로운 팔로우 생성
            followRepository.save(newFollow); // 새로운 팔로우 저장
        } else {
            return null;
        }
        return new FollowPatchedDto(nickname, !isFollowed); //응답 생성해 반환
    }

    public List<Member> getBuddyList() {
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        List<Follow> followList = followRepository.findAllByMember(member);
        if (followList == null || followList.isEmpty()) {
            return null;
        }
        List<Member> buddyList = new ArrayList<>();
        for (Follow follow : followList) {
            Member buddy = validationService.valMember(follow.getBuddyId());
            buddyList.add(buddy);
        }
        return buddyList;
    }
}