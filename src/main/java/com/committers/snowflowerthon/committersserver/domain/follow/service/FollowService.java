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

    // Follow 생성
    public Follow createFollow(Member buddy){
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        return Follow.builder()
                .buddyId(buddy.getId())
                .member(member)
                .build();
    }

    // 팔로우 관계를 변경함
    public FollowPatchedDto changeFollowStatus(String nickname, Boolean isFollowed) {
        Member buddy = validationService.valMember(nickname);
        Follow follow = getFollowRelation(buddy);
        if (follow != null && isFollowed) {
            followRepository.delete(follow); // 해당 팔로우 가져와 삭제
        } else if (follow == null && !isFollowed) {
            Follow newFollow = createFollow(buddy); //새로운 팔로우 생성
            followRepository.save(newFollow); // 새로운 팔로우 저장
        } else {
            return null; // 사용자 '본인'인 경우, 또는 isFollowed가 잘못된 값이 넘어왔을 수 있음
        }
        return new FollowPatchedDto(nickname, !isFollowed); //응답 생성해 반환: 보내는 isFollowed 값이 변경됨
    }

    // 1:1 연결된 Follow 관계를 받아옴
    public Follow getFollowRelation(Member buddy){ // 반환값이 NULL일 수 있음
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        return followRepository.findByMemberAndBuddyId(member, buddy.getId())
                .orElse(null);
    }

    // otherMember를 내가 팔로우 중인지, 현재 팔로우 상태를 반환함
    public Boolean followStatus(Member otherMember) {
        Follow follow = getFollowRelation(otherMember);
        if (follow == null) {
            return false;
        }
        return true;
    }

    // 내가 Follow하고 있는 사용자들 불러옴
    public List<Member> getBuddyList() {
        Long memberId = AuthenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        List<Follow> followList = followRepository.findAllByMember(member);
        if (followList == null || followList.isEmpty()) { // 팔로우 중인 사람이 없음
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