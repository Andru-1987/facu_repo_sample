package com.ProyectoFinalJava.model;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Disqueria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String telefono;

    @ManyToMany(mappedBy = "disquerias", fetch = FetchType.LAZY)
    private List<Cliente> clientes = new ArrayList<>();

    @ManyToMany(mappedBy = "disquerias", fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();

    public Disqueria(Long id, String nombre, String direccion, String telefono, List<Cliente> clientes,
                     List<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.clientes = clientes;
        this.productos = productos;
    }
}
