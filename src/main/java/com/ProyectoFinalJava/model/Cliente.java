package com.ProyectoFinalJava.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "cliente_disqueria",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "disqueria_id")
    )
    private Set<Disqueria> disquerias = new HashSet<>();

    public Cliente(){

    }

    public Cliente(Long id, String name, String email, String phone, Set<Disqueria> disquerias) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.disquerias = disquerias;
    }

    public Set<Disqueria> getDisquerias() {
        return disquerias;
    }

    public void setDisquerias(Set<Disqueria> disquerias) {
        this.disquerias = disquerias;
    }
};

