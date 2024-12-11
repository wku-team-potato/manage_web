package org.example.manage_web.controller;

import org.example.manage_web.model.Item;
import org.example.manage_web.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.example.manage_web.model.Food;
import org.example.manage_web.repository.FoodRepository;

@Controller
public class CreateController {

    private final ItemRepository itemRepository;
    private final FoodRepository foodRepository;

    public CreateController(ItemRepository itemRepository, FoodRepository foodRepository) {
        this.itemRepository = itemRepository;
        this.foodRepository = foodRepository;
    }

    @GetMapping("/create/item")
    public String create_item(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        session.setAttribute("activeTab", "products");
        model.addAttribute("username", loggedInUser);

        return "create_item";
    }

    @PostMapping("/create/item")
    public String createItem(@ModelAttribute Item item, HttpSession session, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        try {
            itemRepository.save(item);
            redirectAttributes.addFlashAttribute("successMessage", "아이템이 성공적으로 생성되었습니다.");
            return "redirect:/detail/item/" + item.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "아이템 생성 중 오류가 발생했습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/create/food")
    public String createFood(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        session.setAttribute("activeTab", "foods");
        model.addAttribute("username", loggedInUser);
        return "create_food";
    }

    @PostMapping("/create/food")
    public String createFood(@ModelAttribute Food food, HttpSession session, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        try {
            foodRepository.save(food);
            redirectAttributes.addFlashAttribute("successMessage", "음식이 성공적으로 등록되었습니다.");
            return "redirect:/detail/food/" + food.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "음식 등록 중 오류가 발생했습니다.");
            return "redirect:/";
        }
    }
}
