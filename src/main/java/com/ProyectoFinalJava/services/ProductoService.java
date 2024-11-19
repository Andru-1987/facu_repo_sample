package com.ProyectoFinalJava.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProyectoFinalJava.DTO.ProductoDTO;
import com.ProyectoFinalJava.mapper.ProductoMapper;
import com.ProyectoFinalJava.model.Disqueria;
import com.ProyectoFinalJava.model.Producto;
import com.ProyectoFinalJava.repository.DisqueriaRepository;
import com.ProyectoFinalJava.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private final ProductoRepository productoRepository;
    @Autowired
    private final ProductoMapper productoMapper;
    @Autowired
    private DisqueriaRepository disqueriaRepository;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    public List<ProductoDTO> getAllProductos(){
        if (productoRepository.findAll().isEmpty()) {
            throw new RuntimeException("No se encontraron productos");
        }

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDTOProducto)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> getProductoById(Long id){
        if (productoRepository.findById(id).isEmpty()) {
            throw new RuntimeException("No se encontraron productos");
        }
        return productoRepository.findById(id).map(productoMapper::toDTOProducto);
    }

    public ProductoDTO saveProducto(ProductoDTO productoDTO) {

        Producto producto = productoMapper.toEntity(productoDTO);

        if (productoDTO.getDisqueriaIds() != null && !productoDTO.getDisqueriaIds().isEmpty()) {
            Set<Disqueria> disquerias = new HashSet<>();

            for (Long disqueriaId : productoDTO.getDisqueriaIds()) {
                Optional<Disqueria> optionalDisqueria = disqueriaRepository.findById(disqueriaId);
                optionalDisqueria.ifPresent(disquerias::add);
            }
            producto.setDisquerias(disquerias);
        }
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTOProducto(savedProducto);
    }

    public void deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new RuntimeException("El producto no existe");
        }
    }

    public ProductoDTO updateStockProducto(Long productoId, int nuevoStock) {
        return productoRepository.findById(productoId)
            .map(producto -> {
                int stockActual = producto.getStock();
                int stockFinal = stockActual + nuevoStock;
                if (stockFinal < 0) {
                    throw new IllegalArgumentException("El stock no puede ser negativo.");
                }

            producto.setStock(stockFinal);
            return productoMapper.toDTOProducto(productoRepository.save(producto));
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));
    }
}
