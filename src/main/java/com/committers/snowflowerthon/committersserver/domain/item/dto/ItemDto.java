package com.committers.snowflowerthon.committersserver.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ItemDto {
    private Long snowId;
    private Long hatId;
    private Long decoId;
}
