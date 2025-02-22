package org.example.manage_web.controller;

import java.util.Optional;

import org.example.manage_web.model.Food;
import org.example.manage_web.model.Item;
import org.example.manage_web.repository.FoodRepository;
import org.example.manage_web.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EditController {

    private final ItemRepository itemRepository;
    private final FoodRepository foodRepository;

    public EditController(ItemRepository itemRepository, FoodRepository foodRepository) {
        this.itemRepository = itemRepository;
        this.foodRepository = foodRepository;
    }

    @GetMapping("/edit/item/{id}")
    public String edit_item(@PathVariable("id") Long id, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        model.addAttribute("username", loggedInUser);

        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            return "redirect:/detail/item/" + id;
        }
        model.addAttribute("item", item.orElse(null));

        return "edit_item";
    }

    @PostMapping("/edit/item/{id}")
    public String itemUpdate(
            @PathVariable("id") Long id,
            @ModelAttribute("item") Item updatedItem,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<Item> existingItem = itemRepository.findById(id);
        if (existingItem.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "존재하지 않는 아이템입니다.");
            return "redirect:/";
        }

        existingItem.get().setName(updatedItem.getName());
        existingItem.get().setDescription(updatedItem.getDescription());
        existingItem.get().setPrice(updatedItem.getPrice());
        existingItem.get().setImg(updatedItem.getImg());

        itemRepository.save(existingItem.get());

        redirectAttributes.addFlashAttribute("successMessage", "아이템 수정 성공!");
        return "redirect:/detail/item/" + id;
    }

    @GetMapping("/edit/food/{id}")
    public String editFood(@PathVariable("id") Long id, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<Food> food = foodRepository.findById(id);
        if (food.isEmpty()) {
            return "redirect:/detail/food/" + id;
        }
        model.addAttribute("username", loggedInUser);
        model.addAttribute("food", food.orElse(null));

        return "edit_food";
    }

    @PostMapping("/edit/food/{id}")
    public String foodUpdate(
            @PathVariable("id") Long id,
            @ModelAttribute("food") Food updatedFood,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<Food> existingFood = foodRepository.findById(id);
        if (existingFood.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "존재하지 않는 음식입니다.");
            return "redirect:/";
        }

        // 기존 음식 정보 업데이트
        Food food = existingFood.get();
        food.setName(updatedFood.getName());
        food.setServingSize(updatedFood.getServingSize());
        food.setEnergy(updatedFood.getEnergy());
        food.setCarbohydrate(updatedFood.getCarbohydrate());
        food.setProtein(updatedFood.getProtein());
        food.setFat(updatedFood.getFat());
        food.setSugars(updatedFood.getSugars());
        food.setCholesterol(updatedFood.getCholesterol());
        food.setTransFat(updatedFood.getTransFat());

        foodRepository.save(food);

        redirectAttributes.addFlashAttribute("successMessage", "음식 정보 수정 성공!");
        return "redirect:/detail/food/" + id;
    }
}
