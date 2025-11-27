package dev.adrian.springweb.service;

import dev.adrian.springweb.model.Producto;
import dev.adrian.springweb.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

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
        productoRepository.save(
                Producto.builder()
                        .nombre("Pesadilla Monstruosa")
                        .precio(9.99)
                        .categoria("Fuego")
                        .descripcion("Criatura mitológica coleccionable")
                        .imagen("/multimedia/Pesadilla-monstruosa.png")
                        .build()
        );

        productoRepository.save(
                Producto.builder()
                        .nombre("Dragón Azul")
                        .precio(19.99)
                        .categoria("Agua")
                        .descripcion("Dragón de agua con escamas brillantes")
                        .build()
        );

        productoRepository.save(
                Producto.builder()
                        .nombre("Dragón Negro")
                        .precio(99.99)
                        .categoria("")
                        .descripcion("Dragón de agua con escamas brillantes")
                        .build()
        );
    }
}