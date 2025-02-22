package org.example.manage_web.controller;

import org.example.manage_web.model.AuthUser;
import org.example.manage_web.model.Profile;
import org.example.manage_web.repository.AuthUserRepository;
import org.example.manage_web.repository.ItemRepository;
import org.example.manage_web.repository.ProfileRepository;
import org.example.manage_web.repository.FoodRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteController {
    private final AuthUserRepository authUserRepository;
    private final ProfileRepository profileRepository;
    private final ItemRepository itemRepository;
    private final FoodRepository foodRepository;

    public DeleteController(AuthUserRepository authUserRepository, ProfileRepository profileRepository,
            ItemRepository itemRepository, FoodRepository foodRepository) {
        this.authUserRepository = authUserRepository;
        this.profileRepository = profileRepository;
        this.itemRepository = itemRepository;
        this.foodRepository = foodRepository;
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        session.setAttribute("activeTab", "members");

        try {
            if (authUserRepository.existsById(id)) {
                authUserRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "사용자 삭제 성공!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "사용자 삭제 중 문제가 발생했습니다.");
        }
        return "redirect:/";
    }

    @PostMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        session.setAttribute("activeTab", "products");

        try {
            if (itemRepository.existsById(id)) {
                itemRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "물건 삭제 성공!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "물건을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "물건 삭제 중 문제가 발생했습니다.");
        }
        return "redirect:/";
    }

    @PostMapping("/food/delete/{id}")
    public String deleteFood(@PathVariable("id") Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        session.setAttribute("activeTab", "foods");

        try {
            if (foodRepository.existsById(id)) {
                foodRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "음식 삭제 성공!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "음식을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "음식 삭제 중 문제가 발생했습니다.");
        }
        return "redirect:/";
    }

}
