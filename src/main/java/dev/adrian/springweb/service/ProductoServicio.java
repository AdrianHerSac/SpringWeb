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

    public Producto findByIdYSumarVisita(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setVisitas(producto.getVisitas() + 1);
            productoRepository.save(producto);
        }
        return producto;
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public void reducirStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock");
        }
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    @PostConstruct
    public void init() {
        // --- 1. CATEGORÍAS ---
        Categoria catFuego = Categoria.builder()
                .nombre("Fuego")
                .descripcion("Dragones de clase fogonero")
                .build();
        categoriaRepository.save(catFuego);

        Categoria catAgua = Categoria.builder()
                .nombre("Agua")
                .descripcion("Dragones marinos")
                .build();
        categoriaRepository.save(catAgua);

        Categoria catMisterio = Categoria.builder()
                .nombre("Misterio")
                .descripcion("Clase desconocida")
                .build();
        categoriaRepository.save(catMisterio);

        // --- 2. PRODUCTOS ---
        productoRepository.save(Producto.builder()
                .nombre("Peluche Pesadilla Monstruosa")
                .precio(12.99).categoria(catFuego)
                .historia("Pequeño pero con carácter.")
                .imagen("/multimedia/Pesadilla-monstruosa.png")
                .color("Rojo Fuego")
                .stock(15)
                .size(Producto.Size.S)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Cojín Nadder Mortífero")
                .precio(19.99).categoria(catAgua)
                .historia("Ideal para la siesta.")
                .imagen("/multimedia/Nadder-mortifero.png")
                .color("Azul")
                .stock(5)
                .size(Producto.Size.L)
                .build());

        productoRepository.save(Producto.builder()
                .nombre("Furia Nocturna Premium")
                .precio(29.99)
                .categoria(catMisterio)
                .historia("Edición limitada.")
                .imagen("/multimedia/furia-nocturna.png")
                .color("Negro")
                .stock(50)
                .size(Producto.Size.M)
                .build());

        // --- 3. USUARIO ADMIN (Hipo) ---
        if (usuarioRepository.findByUsername("Hipo").isEmpty()) {
            Usuario hipo = new Usuario();
            hipo.setUsername("Hipo");
            hipo.setPassword(passwordEncoder.encode("1234"));
            hipo.setRol("ADMIN");
            usuarioRepository.save(hipo);
        }

        // --- 4. Manager (Estoico) ---
        if (usuarioRepository.findByUsername("Estoico").isEmpty()) {
            Usuario odin = new Usuario();
            odin.setUsername("Odin");
            odin.setPassword(passwordEncoder.encode("qwerty"));
            odin.setRol("Manager");
            usuarioRepository.save(odin);
        }

        // --- 5. SUPER ADMIN (Odin) ---
        if (usuarioRepository.findByUsername("Odin").isEmpty()) {
            Usuario odin = new Usuario();
            odin.setUsername("Odin");
            odin.setPassword(passwordEncoder.encode("Socorro"));
            odin.setRol("SUPER_ADMIN");
            usuarioRepository.save(odin);
        }
    }
}