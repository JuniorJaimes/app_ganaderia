package com.ganaderia.infrastructure.adapter.in.web.ui;

import com.ganaderia.application.port.in.AutenticarUsuarioUseCase;
import com.ganaderia.application.port.in.RegistrarUsuarioUseCase;
import com.ganaderia.domain.model.enums.Rol;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebAuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public WebAuthController(AutenticarUsuarioUseCase autenticarUsuarioUseCase, RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas.");
        }
        if (registered != null) {
            model.addAttribute("message", "Registro exitoso. Inicie sesión.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpServletResponse response) {
        try {
            String token = autenticarUsuarioUseCase.autenticar(email, password);
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            // cookie.setSecure(true); // Dejar comentado si se prueba en localhost sin HTTPS
            cookie.setMaxAge(24 * 60 * 60); // 1 día
            response.addCookie(cookie);
            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("roles", Rol.values());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("rol") Rol rol,
                                  Model model) {
        try {
            registrarUsuarioUseCase.registrar(email, password, rol);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Error en el registro: " + e.getMessage());
            model.addAttribute("roles", Rol.values());
            return "register";
        }
    }
}
