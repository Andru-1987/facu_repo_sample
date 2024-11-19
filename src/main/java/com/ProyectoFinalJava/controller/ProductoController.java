package com.ProyectoFinalJava.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoFinalJava.DTO.ProductoDTO;
import com.ProyectoFinalJava.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import com.ProyectoFinalJava.utils.ApiResponseMsg;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping(path = "/all")
    @Operation(summary = "Obtener todos los productos", description = "Retorna todos los productos")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                    @ExampleObject(name = "ProductsNotFound", value = "{\"message\": \"Product not found\"}", description = "Productos no encontrados")
            }))})
    public ResponseEntity<?> getAllProductos() {
        try {
            List<ProductoDTO> productos = productoService.getAllProductos();
            return ResponseEntity.ok().body(new ApiResponseMsg("Lista de productos", productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("NO HAY PRODUCTOS", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su id", description = "Retorna el producto asociado al id proporcionado")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotFound", value = "{\"message\": \"Product not found\"}", description = "Producto no encontrado")
        }))})
    public ResponseEntity<?> getProductoById(@PathVariable("id") Long id){
        try {
            Optional<ProductoDTO> producto = productoService.getProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Producto no encontrado", e.getMessage()));
        }
    }

    @PostMapping("/createProduct")
    @Operation(summary = "Crear un producto asociado a una disqueria", description = "Retorna el producto creado")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotCreated", value = "{\"message\": \"Product not created\"}", description = "Productos no creado")
        }))})
    public ResponseEntity<ProductoDTO> createProduct(@RequestBody ProductoDTO productoDTO){
        ProductoDTO createdProduct = productoService.saveProducto(productoDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un producto", description = "Retorna mensaje de producto eliminado")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Products not deleted", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductsNotDeleted", value = "{\"message\": \"Product not deleted\"}", description = "Productos no borrado")
        }))})
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok().body(new ApiResponseMsg("Producto Eliminado", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Error: No se pudo eliminar el Producto", null));
        }
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Modifica el stock de un producto asociado a un ID", description = "Exige ademas del id, el nuevo stock que hay que modificar. Tiene una validacion la cual impide que el stock quede en saldo negativo. El campo nuevo stock admite numeros negativos y positivos.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Stock not modified", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "StockNotModified", value = "{\"message\": \"Stock not modified\"}", description = "Stock no modificado")
        }))})
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam int nuevoStock) {
        try {
            productoService.updateStockProducto(id, nuevoStock);
            return ResponseEntity.ok().body(new ApiResponseMsg("Stock Actualizado", id));
        } catch (IllegalArgumentException e) {
            // Mensaje específico para errores de stock negativo
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            // Error genérico en caso de que el producto no sea encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con id: " + id);
        }
    }
}

