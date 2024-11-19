package com.ProyectoFinalJava.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
public class DisqueriaDTO {
    @Schema(description = "Id de la disquería", example = "1")
    private Long id;
    
    @Schema(description = "Nombre de la disquería", example = "Disqueria")
    private String nombre;
    
    @Schema(description = "Dirección de la disquería", example = "AV 123")
    private String direccion;
    
    @Schema(description = "Teléfono de la disquería", example = "99887766")
    private String telefono;

    private Set<ClienteDTO> clientes;
    private Set<ProductoDTO> productos;

    public DisqueriaDTO(Long id, String nombre, String direccion, String telefono, Set<ClienteDTO> clientes, Set<ProductoDTO> productos) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.clientes = clientes;
        this.productos = productos;
    }
}

