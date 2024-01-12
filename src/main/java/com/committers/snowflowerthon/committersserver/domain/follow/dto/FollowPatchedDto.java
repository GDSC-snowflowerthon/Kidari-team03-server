package com.committers.snowflowerthon.committersserver.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowPatchedDto {
    private String nickname;
    private boolean isFollowed;
}
