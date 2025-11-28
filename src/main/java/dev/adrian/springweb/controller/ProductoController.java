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
    public String home(@RequestParam(required = false) String busqueda, Model model) {
        List<Producto> productos;
        if (busqueda != null && !busqueda.isEmpty()) {
            log.info("Buscando: " + busqueda);
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            log.info("Buscando");
            productos = productoServicio.findAll();
        }
        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/lista")
    public String listar(@RequestParam(required = false) String busqueda, Model model) {
        List<Producto> productos;
        if (busqueda != null && !busqueda.isEmpty()) {
            log.info("Buscando: " + busqueda);
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            log.info("Buscando");
            productos = productoServicio.findAll();
        }
        model.addAttribute("productos", productos);
        return "productos/lista";
    }

    @GetMapping("/crear")
    public String formularioCrear(Model model) {
        log.info("Creando producto");
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("titulo", "Incubar Nueva Bestia");
        return "productos/form";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        log.info("Buscando producto por el id: " + id);
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            log.info("Producto no encontrado");
            return "redirect:/productos?error=notfound";
        }

        model.addAttribute("producto", producto);
        return "productos/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        log.info("Buscando producto por el id: " + id + ", para editar");
        Producto producto = productoServicio.findById(id);
        if (producto == null) {
            log.info("No existe el producto con el id: {}", id);
            return "redirect:/productos?error=notfound";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("titulo", "Editar Producto");
        return "productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        log.info("Guardando producto: {}", producto);
        productoServicio.guardar(producto);
        return "redirect:/productos/lista";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        log.info("Borrando producto con el id: {}", id);
        productoServicio.deleteById(id);
        return "redirect:/productos";
    }
}