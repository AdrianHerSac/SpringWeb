package dev.adrian.springweb.repository;

import dev.adrian.springweb.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Spring crea la consulta: SELECT * FROM producto WHERE nombre LIKE %busqueda%
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Spring crea la consulta: SELECT * FROM producto WHERE categoria = categoria
    List<Producto> findByCategoria(String categoria);
}