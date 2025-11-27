package dev.adrian.springweb.controller;

import dev.adrian.springweb.model.Producto;
import dev.adrian.springweb.service.ProductoServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public String home(@RequestParam(required = false) String busqueda, Model model) {
        List<Producto> productos;
        if (busqueda != null && !busqueda.isEmpty()) {
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            productos = productoServicio.findAll();
        }
        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/lista")
    public String listar(@RequestParam(required = false) String busqueda, Model model) {
        List<Producto> productos;
        if (busqueda != null && !busqueda.isEmpty()) {
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            productos = productoServicio.findAll();
        }
        model.addAttribute("productos", productos);
        return "productos/lista";
    }

    @GetMapping("/crear")
    public String formularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Crear Nuevo Producto");
        return "productos/form"; // Reutilizaremos la misma vista 'form'
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.findById(id);
        if (producto == null) {
            return "redirect:/productos?error=notfound";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "productos/form"; // Reutilizamos el form
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        log.info("Guardando producto: {}", producto);
        productoServicio.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        log.info("Borrando producto id: {}", id);
        productoServicio.deleteById(id);
        return "redirect:/productos";
    }
}