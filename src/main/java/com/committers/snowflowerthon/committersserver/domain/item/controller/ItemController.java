package com.committers.snowflowerthon.committersserver.domain.item.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.domain.item.dto.ItemDto;
import com.committers.snowflowerthon.committersserver.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*", "https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item") // 아이템 조회
    public ResponseEntity<?> getItems(){
        ItemDto itemDto = itemService.getCurrentItems();
        return ResponseEntity.ok().body(ApiResponse.success(itemDto));
    }

    @PatchMapping("/item/update") // 아이템 변경
    public ResponseEntity<?> patchItems(@RequestBody ItemDto reqItemDto) {
        if (reqItemDto == null) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.ITEM_NOT_FOUND));
        }
        ItemDto itemDto = itemService.patchNewItems(reqItemDto);
        return ResponseEntity.ok().body(ApiResponse.success(itemDto));
    }
}
