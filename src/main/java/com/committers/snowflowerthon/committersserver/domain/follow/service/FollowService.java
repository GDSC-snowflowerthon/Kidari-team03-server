package com.committers.snowflowerthon.committersserver.domain.follow.service;

import com.committers.snowflowerthon.committersserver.domain.follow.entity.Follow;
import com.committers.snowflowerthon.committersserver.domain.follow.entity.FollowRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final Member member; // 추후 로그인한 유저 받아와야

    public boolean followStatus(Long buddyId) {
        Optional<Follow> optionalFollow = followRepository.findByMemberAndBuddyId(member, buddyId);
        if (optionalFollow.isPresent()) { //찾은 결과가 있다면
            return true;
        } else {
            return false;
        }
    }
    public void changeFollowStatus(Long buddyId, boolean isFollowed) {
        Optional<Follow> optionalFollow = followRepository.findByMemberAndBuddyId(member, buddyId);
        if (optionalFollow.isPresent() && isFollowed) {
            Follow follow = optionalFollow.get(); // 해당 팔로우 가져와 삭제
            followRepository.delete(follow);
        } else if (optionalFollow.isEmpty() && !isFollowed) {
            Follow follow = Follow.builder()
                    .member(member)
                    .buddyId(buddyId)
                    .build();
            followRepository.save(follow); // 새로운 팔로우 저장
        } else {
            throw new RuntimeException(); //DB의 상태와 불일치 검증
        }
    }

    public List<Member> getBuddyList() { //친구 목록 받아오기
        List<Follow> followList = followRepository.findAllByMember(member);
        if (followList == null || followList.isEmpty()) {
            return null;
            //친구가 없는 경우 처리
        }
        List<Member> buddyList = new ArrayList<>();
        for (Follow follow : followList) {
            Member buddy = memberService.getMemberById(follow.getBuddyId());
            buddyList.add(buddy);
        }
        return buddyList;
    }
}