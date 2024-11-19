package com.ProyectoFinalJava.mapper;

import org.springframework.stereotype.Component;
import com.ProyectoFinalJava.DTO.ClienteDTO;
import com.ProyectoFinalJava.model.Cliente;
import com.ProyectoFinalJava.model.Disqueria;
import java.util.stream.Collectors;
import java.util.Set;

@Component
public class ClienteMapper {

    public ClienteDTO toDTOCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        Set<Long> disqueriaIds = cliente.getDisquerias().stream()
                .map(Disqueria::getId)
                .collect(Collectors.toSet());

        return ClienteDTO.builder()
                .id(cliente.getId())
                .name(cliente.getName())
                .email(cliente.getEmail())
                .phone(cliente.getPhone())
                .disqueriaIds(disqueriaIds)  
                .build();
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("El clienteDTO no puede ser nulo");
        }

        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setName(clienteDTO.getName());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setPhone(clienteDTO.getPhone());
        return cliente;
    }
}
