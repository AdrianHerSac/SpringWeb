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
        log.info("Buscando productos por búsqueda {}", busqueda);
        List<Producto> productos;

        if (busqueda != null && !busqueda.isEmpty()) {
            productos = productoServicio.buscar(busqueda);
            model.addAttribute("busqueda", busqueda);
        } else {
            productos = productoServicio.findAll();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("total", productos.size());

        return "productos/lista";
    }

    @GetMapping("/crear")
    public String formularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("tallas", Producto.Size.values());
        model.addAttribute("titulo", "Incubar Nuevo Peluche");
        return "productos/form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            return "redirect:/productos?error=notfound";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaServicio.findAll());
        model.addAttribute("tallas", Producto.Size.values());
        model.addAttribute("titulo", "Zurcir/Editar Peluche");
        return "productos/form";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            return "error/404";
        }

        model.addAttribute("producto", producto);
        return "productos/detalle";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoServicio.guardar(producto);
        return "redirect:/productos";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        productoServicio.deleteById(id);
        return "redirect:/productos";
    }

    @GetMapping("/compra/{id}")
    public String vistaCompra(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.findById(id);

        if (producto == null) {
            return "redirect:/productos";
        }

        if (producto.getStock() <= 0) {
            return "redirect:/productos/" + id + "?error=nostock";
        }

        model.addAttribute("producto", producto);
        return "productos/compra";
    }

    @PostMapping("/compra")
    public String procesarCompra(@RequestParam Long id,
                                 @RequestParam int cantidad) {
        log.info("Procesando compra id: {} cantidad: {}", id, cantidad);

        try {
            productoServicio.reducirStock(id, cantidad);
        } catch (Exception e) {
            return "redirect:/productos/" + id + "?error=stock_insuficiente";
        }
        return "redirect:/productos?exito=compra_realizada";
    }
}