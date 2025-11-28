package dev.adrian.springweb.service;

import dev.adrian.springweb.model.Categoria;
import dev.adrian.springweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
}