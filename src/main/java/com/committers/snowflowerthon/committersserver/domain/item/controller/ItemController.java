package com.committers.snowflowerthon.committersserver.domain.item.controller;

import com.committers.snowflowerthon.committersserver.domain.item.dto.ItemDto;
import com.committers.snowflowerthon.committersserver.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://kidari.site")
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/") //꾸미기 화면에서 사용하는 api
    public ResponseEntity<ItemDto> getItems(){
        ItemDto itemDto = itemService.getCurrentItems();
        return ResponseEntity.ok(itemDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto reqItemDto) {
        if (reqItemDto == null) {
            return ResponseEntity.badRequest().build();
        }
        ItemDto itemDto = itemService.updateItem(reqItemDto);
        return ResponseEntity.ok(itemDto);
    }
}
