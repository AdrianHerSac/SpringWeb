package dev.adrian.springweb.controller;

import dev.adrian.springweb.model.Producto;
import dev.adrian.springweb.service.CategoriaServicio;
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

    @Autowired
    private CategoriaServicio categoriaServicio;

    @GetMapping
    public String listar(@RequestParam(required = false) String busqueda, Model model) {
        List<Producto> productos;

        if (busqueda != null && !busqueda.isEmpty()) {
            log.info("Buscando bestia: " + busqueda);
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            log.info("Listando todo el bestiario");
            productos = productoServicio.findAll();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("total", productos.size());

        return "productos/lista";
    }

    @GetMapping("/crear")
    public String formularioCrear(Model model) {
        log.info("Abriendo formulario de incubación");
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("titulo", "Incubar Nueva Bestia");
        return "productos/form";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        log.info("Consultando bestia id: " + id);
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            log.warn("Bestia no encontrada en los registros");
            return "error/404"; // Asegúrate de que creaste el archivo en templates/error/404.peb
        }

        model.addAttribute("producto", producto);
        return "productos/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        log.info("Editando bestia id: " + id);
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            log.warn("Bestia no encontrada en los registros");
            return "redirect:/error/404";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("titulo", "Reentrenar Bestia (Editar)");
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        log.info("Registrando bestia en el libro: {}", producto);
        productoServicio.guardar(producto);

        return "redirect:/productos";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        log.info("Sacrificando bestia id: {}", id);
        productoServicio.deleteById(id);

        return "redirect:/productos";
    }
}