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
    public String listar(
            @RequestParam(required = false) String busqueda,
            Model model
    ) {
        List<Producto> productos;

        if (busqueda != null && !busqueda.isEmpty()) {
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            productos = productoServicio.findAll();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("total", productos.size());
        return "index";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            return "redirect:/productos?error=notfound";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("relacionados",
                productoServicio.findByCategoria(producto.getCategoria()));
        return "productos/detalle";
    }

    @GetMapping("/crear")
    public String formularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        // Guardaremos la vista en la carpeta 'productos' para mantener el orden
        return "productos/form";
    }

    // 2. Recibir los datos y guardar (POST /productos/guardar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        log.info("Intentando guardar el producto: {}", producto); // Usamos tu @Slf4j

        // Asegúrate de que este método exista en tu servicio
        productoServicio.guardar(producto);

        return "redirect:/productos"; // Redirige al listado tras guardar
    }
}