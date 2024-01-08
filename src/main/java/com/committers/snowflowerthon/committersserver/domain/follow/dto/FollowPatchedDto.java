package com.committers.snowflowerthon.committersserver.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FollowPatchedDto {
    private String nickname;
    private boolean isFollowed;
}
