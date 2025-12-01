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

        // Restamos el stock
        producto.setStock(producto.getStock() - cantidad);

        // Guardamos el cambio
        productoRepository.save(producto);
    }

    @PostConstruct
    public void init() {

        // Categorías
        Categoria catFuego = Categoria.builder()
                .nombre("Fuego")
                .descripcion("Dragones de clase fogonero")
                .build();
        categoriaRepository.save(catFuego);

        Categoria catAgua = Categoria.builder()
                .nombre("Agua")
                .descripcion("Dragones marinos y de las profundidades")
                .build();
        categoriaRepository.save(catAgua);

        Categoria catMisterio = Categoria.builder()
                .nombre("Misterio")
                .descripcion("Clase desconocida o híbrida")
                .build();
        categoriaRepository.save(catMisterio);


        productoRepository.save(
                Producto.builder()
                        .nombre("Peluche Pesadilla Monstruosa")
                        .precio(12.99)
                        .categoria(catFuego)
                        .historia("Pequeño pero con mucho carácter. Ojos bordados a mano.")
                        .imagen("/multimedia/Pesadilla-monstruosa.png")
                        .color("Rojo Fuego")
                        .stock(15)
                        .size(Producto.Size.S) // Talla Pequeña
                        .build()
        );

        productoRepository.save(
                Producto.builder()
                        .nombre("Cojín Nadder Mortífero")
                        .precio(19.99)
                        .categoria(catAgua)
                        .historia("Ideal para dormir la siesta. Relleno de plumas de ganso.")
                        .imagen("/multimedia/Nadder-mortifero.png")
                        .color("Azul y Amarillo")
                        .stock(5) // Poco stock
                        .size(Producto.Size.L) // Talla Grande
                        .build()
        );

        productoRepository.save(
                Producto.builder()
                        .nombre("Furia Nocturna Premium")
                        .precio(29.99)
                        .categoria(catMisterio)
                        .historia("Suave, adorable y con alas de fieltro reforzado. Edición limitada.")
                        .imagen("/multimedia/furia-nocturna-plush.png") // Asegúrate de tener esta foto o usa otra
                        .color("Negro Azabache")
                        .stock(50)
                        .size(Producto.Size.M) // Talla Mediana
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