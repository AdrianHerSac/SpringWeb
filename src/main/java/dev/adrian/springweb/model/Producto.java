package dev.adrian.springweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private String nombre;

    private Double precio;

    private String color;

    private String historia;

    @Column(length = 1000)
    private String imagen;

    private int stock;

    @Enumerated(EnumType.STRING)
    private Size size;

    public enum Size {
        S, M, L, XL
    }
}