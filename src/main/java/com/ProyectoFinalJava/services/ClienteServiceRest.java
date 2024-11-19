package com.ProyectoFinalJava.services;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ProyectoFinalJava.DTO.ClienteDTO;
import com.ProyectoFinalJava.mapper.ClienteMapper;
import com.ProyectoFinalJava.model.Cliente;
import com.ProyectoFinalJava.model.Disqueria; 
import com.ProyectoFinalJava.repository.ClienteRepository;
import com.ProyectoFinalJava.repository.DisqueriaRepository; 
import jakarta.transaction.Transactional;

@Service
public class ClienteServiceRest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final ClienteMapper clienteMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DisqueriaRepository disqueriaRepository;

    public ClienteServiceRest(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteDTO> getAllClients() {
        List<ClienteDTO> clientesDB = clienteRepository.findAll().stream()
                .map(clienteMapper::toDTOCliente)
                .collect(Collectors.toList());

        Cliente[] clientesAPI = restTemplate.getForObject(BASE_URL, Cliente[].class);

        if (clientesAPI != null) {
            for (Cliente cliente : clientesAPI) {
                clientesDB.add(clienteMapper.toDTOCliente(cliente));
            }
        }

        return clientesDB;
    }

    public ClienteDTO getClientById(Long id) {
        Optional<Cliente> optionalClient = clienteRepository.findById(id);

        if (optionalClient.isPresent()) {
            return clienteMapper.toDTOCliente(optionalClient.get());
        } else {
            ClienteDTO clienteDTO = restTemplate.getForObject(BASE_URL + "/{id}", ClienteDTO.class, id);
            if (clienteDTO != null) {
                return clienteDTO;
            } else {
                throw new RuntimeException("Cliente no encontrado ni en la base de datos ni en la API externa");
            }
        }
    }

    @Transactional
    public ClienteDTO saveClientFromApi(Long id) {
        ClienteDTO clienteDTO = restTemplate.getForObject(BASE_URL + "/{id}", ClienteDTO.class, id);

        if (clienteDTO != null) {
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            Cliente savedCliente = clienteRepository.save(cliente);
            return clienteMapper.toDTOCliente(savedCliente);
        } else {
            throw new RuntimeException("Cliente no encontrado en la API con ID: " + id);
        }
    }
    
    public ClienteDTO saveClienteDTO(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        if (clienteDTO.getDisqueriaIds() != null && !clienteDTO.getDisqueriaIds().isEmpty()) { 
            Set<Disqueria> disquerias = new HashSet<>();

            for (Long disqueriaId : clienteDTO.getDisqueriaIds()) { 
                Optional<Disqueria> optionalDisqueria = disqueriaRepository.findById(disqueriaId);
                optionalDisqueria.ifPresent(disquerias::add);
            }

            cliente.setDisquerias(disquerias);
        }

        Cliente savedCliente = clienteRepository.save(cliente);

        return clienteMapper.toDTOCliente(savedCliente);
    }

    public void deleteClient(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            restTemplate.delete(BASE_URL + "/{id}", id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
    }

    public ClienteDTO updateClienteDTO(Long id, ClienteDTO clienteDTO){
        return clienteRepository.findById(id)
        .map(cliente -> {
            cliente.setName(clienteDTO.getName());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setPhone(clienteDTO.getPhone());
            return clienteMapper.toDTOCliente(clienteRepository.save(cliente));
        })
        .orElse(null);
    }
}