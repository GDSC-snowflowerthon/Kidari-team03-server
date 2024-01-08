package com.committers.snowflowerthon.committersserver.domain.item.controller;

import com.committers.snowflowerthon.committersserver.domain.item.dto.ItemDto;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.service.ItemService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.MetaMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    private final Member member; //임시

    @GetMapping("/") //꾸미기 화면에서 사용하는 api
    public ResponseEntity<ItemDto> getItem(){
        Long itemId = member.getItem().getId();
        return ResponseEntity.ok(itemDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto reqItemDto) {
        if (reqItemDto == null) {
            return ResponseEntity.badRequest().build();
        }
        Long itemId = member.getItem().getId();
        itemService.updateItem(itemId, reqItemDto);
        ItemDto itemDto = itemService.getItemDto(itemId);
        return ResponseEntity.ok(itemDto);
    }
}
