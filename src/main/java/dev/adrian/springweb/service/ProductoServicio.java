package dev.adrian.springweb.service;

import dev.adrian.springweb.model.Categoria;
import dev.adrian.springweb.model.Producto;
import dev.adrian.springweb.model.Usuario;
import dev.adrian.springweb.repository.CategoriaRepository;
import dev.adrian.springweb.repository.ProductoRepository;
import dev.adrian.springweb.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Categoria catAgua = Categoria.builder()
                .nombre("Agua")
                .descripcion("Dragones marinos y de las profundidades")
                .build();
        categoriaRepository.save(catAgua);

        productoRepository.save(
                Producto.builder()
                        .nombre("Nadder Mortifero")
                        .precio(19.99)
                        .categoria(catAgua)
                        .historia("Dragón con escamas brillantes")
                        .imagen("/multimedia/Nadder-mortifero.png")
                        .build()
        );

        Categoria catDesconocida = Categoria.builder()
                .nombre("Misterio")
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

        if (usuarioRepository.findByUsername("Hipo").isEmpty()) {
            Usuario hipo = new Usuario();
            hipo.setUsername("Hipo");
            hipo.setPassword(passwordEncoder.encode("1234"));
            hipo.setRol("ADMIN");
            usuarioRepository.save(hipo);
            System.out.println("✅ USUARIO HIPO CREADO");
        }
    }
}