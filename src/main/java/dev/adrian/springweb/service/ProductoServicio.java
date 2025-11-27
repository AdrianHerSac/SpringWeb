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
    private ProductoRepository repositorio;

    // Métodos existentes...
    public List<Producto> findAll() {
        return repositorio.findAll();
    }

    public Producto findById(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Producto guardar(Producto producto) {
        return repositorio.save(producto);
    }

    public List<Producto> buscar(String busqueda) {
        // Llama al método "mágico" que creamos en el repositorio
        return repositorio.findByNombreContainingIgnoreCase(busqueda);
    }

    public List<Producto> findByCategoria(String categoria) {
        return repositorio.findByCategoria(categoria);
    }

    // --- CORRECCIÓN DEL INIT ---

    @PostConstruct
    public void init() {
        // Error anterior: "productos.add(...)" -> No existe la lista 'productos'.
        // Solución: Usar repositorio.save(...)

        repositorio.save(
                Producto.builder()
                        .nombre("Dragón Rojo")
                        .precio(9.99)
                        .categoria("Fuego") // Asegúrate de tener este campo en tu modelo
                        .descripcion("Criatura mitológica coleccionable")
                        .build()
        );

        repositorio.save(
                Producto.builder()
                        .nombre("Dragón Azul")
                        .precio(19.99)
                        .categoria("Agua")
                        .descripcion("Dragón de agua con escamas brillantes")
                        .build()
        );
    }
}