package com.ProyectoFinalJava.mapper;

import org.springframework.stereotype.Component;

import com.ProyectoFinalJava.DTO.DisqueriaCreateDTO;
import com.ProyectoFinalJava.DTO.DisqueriaDTO;
import com.ProyectoFinalJava.model.Disqueria;

import java.util.stream.Collectors;

@Component
public class DisqueriaMapper {

    private final ClienteMapper clienteMapper;
    private final ProductoMapper productoMapper;

    public DisqueriaMapper(ClienteMapper clienteMapper, ProductoMapper productoMapper) {
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
    }

    public DisqueriaDTO toDTODisqueria(Disqueria disqueria, boolean includeRelations) {
        if (disqueria == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        DisqueriaDTO.DisqueriaDTOBuilder builder = DisqueriaDTO.builder()
                .id(disqueria.getId())
                .nombre(disqueria.getNombre())
                .direccion(disqueria.getDireccion())
                .telefono(disqueria.getTelefono());

        if (includeRelations) {
            builder.clientes(disqueria.getClientes().stream()
                    .map(clienteMapper::toDTOCliente)
                    .collect(Collectors.toSet()));
            builder.productos(disqueria.getProductos().stream()
                    .map(productoMapper::toDTOProducto)
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public Disqueria toEntity(DisqueriaCreateDTO disqueriaCreateDTO) {
        if (disqueriaCreateDTO == null) {
            throw new IllegalArgumentException("El disqueriaCreateDTO no puede ser nulo");
        }

        Disqueria disqueria = new Disqueria();
        disqueria.setId(disqueriaCreateDTO.getId());
        disqueria.setNombre(disqueriaCreateDTO.getNombre());
        disqueria.setDireccion(disqueriaCreateDTO.getDireccion());
        disqueria.setTelefono(disqueriaCreateDTO.getTelefono());

        return disqueria;
    }
};
