package org.example.manage_web.service;

import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.Item;
import org.example.manage_web.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item with id " + id + " not found"));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        Optional<Item> existingItem = itemRepository.findById(id);

        if (existingItem.isPresent()) {
            Item item = existingItem.get();
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
            item.setImg(updatedItem.getImg());
            item.setCreatedAt(updatedItem.getCreatedAt());
            return itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }
    }

    public Boolean deleteItemById(Long id) {
        try {
            if (itemRepository.existsById(id)) {
                itemRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
