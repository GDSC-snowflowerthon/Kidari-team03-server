package com.committers.snowflowerthon.committersserver.domain.item.service;

import com.committers.snowflowerthon.committersserver.domain.item.dto.ItemDto;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemDto getItemDto(Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            return ItemDto.toDto(optionalItem.get());
        } else {
            return null; //에러
        }
    }

    public void updateItem(Long id, ItemDto reqItemDto) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item updatedItem = ItemDto.toEntity(reqItemDto, id);
            itemRepository.save(updatedItem);
        } else {
            //에러
        }
    }
}