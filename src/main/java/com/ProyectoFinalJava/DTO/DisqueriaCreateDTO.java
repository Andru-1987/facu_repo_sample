package com.ProyectoFinalJava.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisqueriaCreateDTO {
    private Long id;
    
    @Schema(description = "Nombre de la disquería", example = "Disqueria")
    private String nombre;
    
    @Schema(description = "Dirección de la disquería", example = "Av 321")
    private String direccion;
    
    @Schema(description = "Teléfono de la disquería", example = "123780282")
    private String telefono;

    public DisqueriaCreateDTO() {
    }

    public DisqueriaCreateDTO(Long id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
};