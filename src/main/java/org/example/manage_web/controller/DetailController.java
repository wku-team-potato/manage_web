package org.example.manage_web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.manage_web.model.AuthUser;
import org.example.manage_web.model.Food;
import org.example.manage_web.model.HeightWeightRecord;
import org.example.manage_web.model.Item;
import org.example.manage_web.model.MealRecord;
import org.example.manage_web.model.Profile;
import org.example.manage_web.model.PurchaseRecord;
import org.example.manage_web.repository.AuthUserRepository;
import org.example.manage_web.repository.FoodRepository;
import org.example.manage_web.repository.HeightWeightRecordRepository;
import org.example.manage_web.repository.ItemRepository;
import org.example.manage_web.repository.MealRecordRepository;
import org.example.manage_web.repository.ProfileRepository;
import org.example.manage_web.repository.PurchaseRecordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DetailController {

    private final ProfileRepository profileRepository;
    private final HeightWeightRecordRepository heightWeightRecordRepository;
    private final ItemRepository itemRepository;
    private final PurchaseRecordRepository purchaseRecordRepository;
    private final MealRecordRepository mealRecordRepository;
    private final FoodRepository foodRepository;
    private final AuthUserRepository authUserRepository;

    public DetailController(HeightWeightRecordRepository heightWeightRecordRepository,
            ProfileRepository profileRepository, ItemRepository itemRepository,
            PurchaseRecordRepository purchaseRecordRepository,
            MealRecordRepository mealRecordRepository,
            FoodRepository foodRepository,
            AuthUserRepository authUserRepository) {
        this.profileRepository = profileRepository;
        this.heightWeightRecordRepository = heightWeightRecordRepository;
        this.itemRepository = itemRepository;
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.mealRecordRepository = mealRecordRepository;
        this.foodRepository = foodRepository;
        this.authUserRepository = authUserRepository;
    }

    @GetMapping("/detail/user/{id}")
    public String detail_user(@PathVariable("id") Long id, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        String loggerInUser = (String) session.getAttribute("loggedInUser");

        if (loggerInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<AuthUser> user = authUserRepository.findById(id);
        Optional<Profile> profile = profileRepository.findByAuthUser_Id(id);
        Optional<List<HeightWeightRecord>> heightWeightRecord = heightWeightRecordRepository.findByAuthUser_Id(id);
        Optional<List<PurchaseRecord>> purchaseRecord = purchaseRecordRepository.findByUser_id(id);
        Optional<List<MealRecord>> mealRecord = mealRecordRepository.findByAuthUser_Id(id);

        session.setAttribute("activeTab", "members");

        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("username", loggerInUser);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("profile", profile.orElse(null));
        model.addAttribute("heightWeightRecord", heightWeightRecord.orElse(Collections.emptyList()));
        model.addAttribute("purchaseRecord", purchaseRecord.orElse(Collections.emptyList()));
        model.addAttribute("mealRecord", mealRecord.orElse(Collections.emptyList()));

        return "detail_user";
    }

    @GetMapping("/detail/item/{id}")
    public String detail_item(@PathVariable("id") Long id, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        String loggerInUser = (String) session.getAttribute("loggedInUser");

        if (loggerInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<Item> item = itemRepository.findById(id);
        Optional<List<PurchaseRecord>> purchaseRecord = purchaseRecordRepository.findByItem_id(id);

        // 물건 관리 탭 활성화 상태 저장
        session.setAttribute("activeTab", "products");

        model.addAttribute("username", loggerInUser);
        model.addAttribute("item", item.orElse(null));
        model.addAttribute("purchaseRecord", purchaseRecord.orElse(Collections.emptyList()));

        return "detail_item";
    }

    @GetMapping("/detail/food/{id}")
    public String detail_food(@PathVariable("id") Long id, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        String loggerInUser = (String) session.getAttribute("loggedInUser");

        if (loggerInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션 만료로 로그인이 필요합니다.");
            return "redirect:/login";
        }

        Optional<Food> food = foodRepository.findById(id);

        // 음식 관리 탭 활성화 상태 저장
        session.setAttribute("activeTab", "foods");

        model.addAttribute("username", loggerInUser);
        model.addAttribute("food", food.orElse(null));

        return "detail_food";
    }

    @PostMapping("/user/role/{id}")
    public String updateUserRole(@PathVariable("id") Long id,
            @RequestParam("role") int role,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Integer userRole = (Integer) session.getAttribute("userRole");

        if (loggedInUser == null || userRole == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "세션이 만료되었습니다.");
            return "redirect:/login";
        }

        // 권한 검증: 최고 관리자(1)와 회원 관리자(2)만 권한 수정 가능
        if (userRole != 4 && userRole != 3) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/detail/user/" + id;
        }

        Optional<AuthUser> targetUser = authUserRepository.findById(id);
        Optional<AuthUser> currentUser = authUserRepository.findByUsername(loggedInUser);

        if (targetUser.isEmpty() || currentUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
            return "redirect:/";
        }

        // 본인의 권한은 수정 불가
        if (targetUser.get().getUsername().equals(loggedInUser)) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인의 권한은 수정할 수 없습니다.");
            return "redirect:/detail/user/" + id;
        }

        // 권한 부여 규칙 검증
        if (!isValidRoleChange(userRole, role)) {
            redirectAttributes.addFlashAttribute("errorMessage", "부여할 수 없는 권한입니다.");
            return "redirect:/detail/user/" + id;
        }

        targetUser.get().setIsSuperuser(role);
        authUserRepository.save(targetUser.get());

        redirectAttributes.addFlashAttribute("successMessage", "권한이 성공적으로 수정되었습니다.");
        return "redirect:/detail/user/" + id;
    }

    // 권한 변경이 유효한지 검사하는 메서드
    private boolean isValidRoleChange(int currentUserRole, int targetRole) {
        // 최고 관리자(4)는 모든 권한 부여 가능
        if (currentUserRole == 4) {
            return targetRole >= 0 && targetRole <= 4;
        }
        // 회원 관리자(3)는 자신보다 낮은 권한만 부여 가능 (물건 관리자(2), 음식 관리자(1), 일반 사용자(0))
        else if (currentUserRole == 3) {
            return targetRole >= 0 && targetRole <= 2;
        }
        return false;
    }
}
