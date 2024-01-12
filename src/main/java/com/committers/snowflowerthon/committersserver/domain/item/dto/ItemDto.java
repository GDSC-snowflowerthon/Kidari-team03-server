package com.committers.snowflowerthon.committersserver.domain.item.dto;

import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long snowId;
    private Long hatId;
    private Long decoId;

    public static ItemDto toDto(Item item){
        return ItemDto.builder()
                .snowId(item.getSnowId())
                .hatId(item.getHatId())
                .decoId(item.getDecoId())
                .build();
    }

    public static Item toEntity(ItemDto itemDto, Long itemId){
        // reqItemDto를 item으로 바꿔서 사용할 때 필요함
        return Item.builder()
                .id(itemId)
                .snowId(itemDto.getSnowId())
                .hatId(itemDto.getHatId())
                .decoId(itemDto.getDecoId())
                .build();
    }
}
