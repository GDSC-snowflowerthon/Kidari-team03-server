package com.committers.snowflowerthon.committersserver.domain.item.service;

import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty())
            return null;
        else

    }
    public Item updateItem(Item currentItem, Item selectedItem) {
        Long itemId = currentItem.getId();
        Item updatedItem = Item.builder()
                .id(optionalItem.get().getId())
                .snowId(selectedItem.getSnowId())
                .hatId(selectedItem.getHatId())
                .decoId(selectedItem.getDecoId())
                .build();

        return itemRepository.update();
        if (optionalItem.isPresent()) {
            Item updatedItem = Item.builder()
                    .id(optionalItem.get().getId())
                    .snowId(selectedItem.getSnowId())
                    .hatId(selectedItem.getHatId())
                    .decoId(selectedItem.getDecoId())
                    .build();
            return itemRepository.save(updatedItem);
        }
        return null;
    }
}