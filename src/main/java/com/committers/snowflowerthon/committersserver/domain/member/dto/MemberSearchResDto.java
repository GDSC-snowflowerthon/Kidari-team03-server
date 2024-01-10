package com.committers.snowflowerthon.committersserver.domain.member.dto;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MemberSearchResDto { // 유저 검색 결과에서 사용됨
    private String nickname;
    private Long snowmanHeight;
    private boolean isFollowed;

    public static MemberSearchResDto toDto(Member member) {
        return MemberSearchResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .isFollowed(false) // 추후 친구여부 찾는 메소드 만들어야
                .build();
    }
}
