package com.committers.snowflowerthon.committersserver.domain.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingBuddyDto {
    private String nickname;
    private Long snowmanHeight;
}
