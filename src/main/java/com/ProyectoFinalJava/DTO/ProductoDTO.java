package com.ProyectoFinalJava.DTO;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ProductoDTO {
    @Schema(description = "Id of the product", example = "0")
    private Long id;

    @Schema(description = "Name of the product", example = "Meddle")
    private String nombre;

    @Schema(description = "Price of the product", example = "5.8")
    private Double precio;

    @Schema(description = "Stock of the product", example = "12")
    private int stock;

    @Schema(description = "Category of the product", example = "album")
    private String categoria;

    private Set<Long> disqueriaIds;

    public ProductoDTO(Long id, String nombre, Double precio, int stock, String categoria, Set<Long> disqueriaIds) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.disqueriaIds = disqueriaIds;
    }
}


