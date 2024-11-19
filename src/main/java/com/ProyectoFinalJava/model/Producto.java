package com.ProyectoFinalJava.model;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private int stock;
    private String categoria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "producto_disqueria",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "disqueria_id")
    )
    private Set<Disqueria> disquerias;

    public Producto(Long id, String nombre, Double precio, int stock, String categoria, Set<Disqueria> disquerias) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.disquerias = disquerias;
    }
}

