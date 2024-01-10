package com.committers.snowflowerthon.committersserver.domain.item.service;

import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.ItemException;
import com.committers.snowflowerthon.committersserver.domain.item.dto.ItemDto;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberService memberService;

    public ItemDto getCurrentItems(){
        Member member = memberService.getAuthMember();
        Long itemId = member.getItem().getId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ErrorCode.ITEM_NOT_FOUND));
        return ItemDto.toDto(item);
    }

    public ItemDto updateItem(ItemDto reqItemDto) {
        Member member = memberService.getAuthMember();
        Long itemId = member.getId();
        if (reqItemDto.getSnowId() < 0 || reqItemDto.getSnowId() >= 5
            ||  reqItemDto.getHatId() < 0 || reqItemDto.getHatId() >= 5
            ||  reqItemDto.getDecoId() < 0 || reqItemDto.getDecoId() >= 5) {
            throw new ItemException(ErrorCode.ITEM_NOT_FOUND); //해당 아이디의 아이템 존재 x
        }
        Item updatedItem = ItemDto.toEntity(reqItemDto, itemId);
        itemRepository.save(updatedItem);
        return ItemDto.toDto(updatedItem);
    }
}