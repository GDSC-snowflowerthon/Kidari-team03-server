package com.committers.snowflowerthon.committersserver.domain.follow.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FollowPatchedDto {
    private String nickname;
    private boolean isFollowed;
}
