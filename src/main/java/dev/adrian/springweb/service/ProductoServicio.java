package dev.adrian.springweb.service;

import dev.adrian.springweb.model.Categoria;
import dev.adrian.springweb.model.Producto;
import dev.adrian.springweb.repository.CategoriaRepository;
import dev.adrian.springweb.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> buscar(String busqueda) {
        return productoRepository.findByNombreContainingIgnoreCase(busqueda);
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> findByCategoria(String categoria) {
        return List.of();
    }

    @PostConstruct
    public void init() {
        // 1. FUEGO
        Categoria catFuego = Categoria.builder()
                .nombre("Fuego")
                .descripcion("Dragones de clase fogonero")
                .build();
        categoriaRepository.save(catFuego);

        productoRepository.save(
                Producto.builder()
                        .nombre("Pesadilla Monstruosa")
                        .precio(9.99)
                        .categoria(catFuego)
                        .historia("El dragón más temperamental del mundo vikingo.")
                        .imagen("/multimedia/Pesadilla-monstruosa.png")
                        .build()
        );

        // 2. AGUA (Corregido: Nombre y descripción cambiados)
        Categoria catAgua = Categoria.builder()
                .nombre("Agua") // <--- ANTES PONÍA "Fuego"
                .descripcion("Dragones marinos y de las profundidades")
                .build();
        categoriaRepository.save(catAgua);

        productoRepository.save(
                Producto.builder()
                        .nombre("Dragón Azul")
                        .precio(19.99)
                        .categoria(catAgua)
                        .historia("Dragón de agua con escamas brillantes")
                        .build()
        );

        // 3. MISTERIO (Corregido: Nombre y descripción cambiados)
        Categoria catDesconocida = Categoria.builder()
                .nombre("Misterio") // <--- ANTES PONÍA "Fuego"
                .descripcion("Clase desconocida o híbrida")
                .build();
        categoriaRepository.save(catDesconocida);

        productoRepository.save(
                Producto.builder()
                        .nombre("Furia Nocturna")
                        .precio(99.99)
                        .categoria(catDesconocida)
                        .historia("La cría impía del rayo y la muerte misma.")
                        .build()
        );
    }
}