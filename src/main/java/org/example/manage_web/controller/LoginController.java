package org.example.manage_web.controller;

import java.util.Optional;

import org.example.manage_web.model.AuthUser;
import org.example.manage_web.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AuthUserService authUserService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpSession session,
            Model model, RedirectAttributes redirectAttributes) {

        Optional<AuthUser> user = authUserService.findByUsername(username);

        if (user.isPresent() && user.get().getIsSuperuser() > 0
                && authUserService.checkPassword(password, user.get().getPassword())) {
            // 로그인 성공 (is_superuser가 1-4인 경우만)
            session.setAttribute("loggedInUser", user.get().getUsername());
            session.setAttribute("userRole", user.get().getIsSuperuser());
            redirectAttributes.addFlashAttribute("successMessage", "로그인 성공!");
            return "redirect:/"; // 홈 화면으로 리다이렉트
        } else {
            // 로그인 실패
            redirectAttributes.addFlashAttribute("errorMessage", "관리자 권한이 없거나 로그인 정보가 올바르지 않습니다.");
            return "redirect:/login"; // 로그인 화면으로 리턴
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session != null) {
            session.invalidate();
        }

        redirectAttributes.addFlashAttribute("successMessage", "로그아웃 성공!");
        return "redirect:/login";
    }
}
