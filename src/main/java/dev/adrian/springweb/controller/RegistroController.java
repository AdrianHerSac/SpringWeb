package dev.adrian.springweb.controller;

import dev.adrian.springweb.service.DetalleUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private DetalleUsuarioServicio usuarioServicio;

    @GetMapping("/registro")
    public String formularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            usuarioServicio.registrarNuevoUsuario(username, password);
            // Si sale bien, redirigimos al login con mensaje de éxito
            return "redirect:/login?registrado=true";
        } catch (RuntimeException e) {
            // Si falla (ej: usuario duplicado), volvemos al formulario con el error
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}