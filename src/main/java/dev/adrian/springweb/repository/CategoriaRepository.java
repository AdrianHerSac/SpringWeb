package dev.adrian.springweb.repository;

import dev.adrian.springweb.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Page<Categoria> findAll(Specification<Categoria> spec, Pageable pageable);

    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
}