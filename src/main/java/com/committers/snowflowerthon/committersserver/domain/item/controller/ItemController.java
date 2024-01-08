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
    @GetMapping("/")
    public ResponseEntity<ItemDto> getItem(){
        Item item = member.getItem();
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        ItemDto itemDto = new ItemDto(item.getSnowId(), item.getHatId(), item.getDecoId());
        return ResponseEntity.ok(itemDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ItemDto> patchItem(@RequestBody Item selectedItem) {
        if (selectedItem == null) {
            return ResponseEntity.badRequest().build();
        }
        Item existingItem = itemService.getItemById(selectedItem.getId());
        if (existingItem == null) {
            return ResponseEntity.notFound().build();
        }
        Item updatedItem = itemService.updateItem(member.getItem(), selectedItem);
        ItemDto itemDto = ItemDto.builder()
                .snowId(updatedItem.getSnowId())
                .hatId(updatedItem.getHatId())
                .decoId(updatedItem.getDecoId())
                .build();
        if (updatedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemDto);
    }
}
