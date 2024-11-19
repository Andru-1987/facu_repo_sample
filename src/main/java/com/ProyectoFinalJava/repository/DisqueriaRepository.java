package com.ProyectoFinalJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProyectoFinalJava.model.Disqueria;
import java.util.List;

@Repository
public interface DisqueriaRepository extends JpaRepository <Disqueria, Long> {
    List<Disqueria> findByNombre(String nombre);
}