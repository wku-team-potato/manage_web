package org.example.manage_web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.AuthUser;
import org.example.manage_web.model.Food;
import org.example.manage_web.model.Item;
import org.example.manage_web.model.Profile;
import org.example.manage_web.repository.AuthUserRepository;
import org.example.manage_web.repository.FoodRepository;
import org.example.manage_web.repository.ItemRepository;
import org.example.manage_web.repository.ProfileRepository;
import org.example.manage_web.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

    private final AuthUserRepository authUserRepository;
    private final ProfileRepository profileRepository;
    private final ItemRepository itemRepository;
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    public IndexController(AuthUserRepository authUserRepository, ProfileRepository profileRepository,
            ItemRepository itemRepository, FoodRepository foodRepository, FoodService foodService) {
        this.authUserRepository = authUserRepository;
        this.profileRepository = profileRepository;
        this.itemRepository = itemRepository;
        this.foodRepository = foodRepository;
        this.foodService = foodService;
    }

    // 메인 페이지 엔드포인트
    @GetMapping("/")
    public String index(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String loggerInUser = (String) session.getAttribute("loggedInUser");
        Integer userRole = (Integer) session.getAttribute("userRole");

        if (loggerInUser == null || userRole == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<AuthUser> authUser = authUserRepository.findByUsername(loggerInUser);
        if (authUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
            return "redirect:/login";
        }

        // 권한에 따른 활성 탭 설정
        String activeTab;
        switch (userRole) {
            case 4: // 최고 관리자
                activeTab = (String) session.getAttribute("activeTab");
                if (activeTab == null || activeTab.isEmpty()) {
                    activeTab = "members";
                }
                break;
            case 3: // 회원 관리자
                activeTab = "members";
                break;
            case 2: // 물건 관리자
                activeTab = "products";
                break;
            case 1: // 음식 관리자
                activeTab = "foods";
                break;
            default:
                redirectAttributes.addFlashAttribute("errorMessage", "잘못된 권한입니다.");
                return "redirect:/login";
        }

        session.setAttribute("activeTab", activeTab);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("userRole", userRole);
        model.addAttribute("authuser", authUser.get().getUsername());

        // 권한에 따른 데이터 로딩
        if (userRole == 4 || userRole == 3) {
            Optional<List<Profile>> profile = Optional.of(profileRepository.findAll());
            model.addAttribute("profile", profile.orElse(Collections.emptyList()));
        }

        if (userRole == 4 || userRole == 2) {
            Optional<List<Item>> item = Optional.of(itemRepository.findAll());
            model.addAttribute("item", item.orElse(Collections.emptyList()));
        }

        if (userRole == 4 || userRole == 1) {
            Optional<List<Food>> food = Optional.of(foodRepository.findAll());
            model.addAttribute("food", food.orElse(Collections.emptyList()));
        }

        return "index";
    }
}
