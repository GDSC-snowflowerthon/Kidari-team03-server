package com.committers.snowflowerthon.committersserver.domain.member.dto;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchResDto { // 유저 검색 결과에서 사용됨
    private String nickname;
    private Long snowmanHeight;
    private boolean isFollowed;

    public static MemberSearchResDto toDto(Member member, Boolean followStatus) {
        return MemberSearchResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .isFollowed(followStatus)
                .build();
    }
}
