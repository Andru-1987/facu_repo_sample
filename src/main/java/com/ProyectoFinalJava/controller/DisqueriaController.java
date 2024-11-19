package com.ProyectoFinalJava.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoFinalJava.DTO.DisqueriaCreateDTO;
import com.ProyectoFinalJava.DTO.DisqueriaDTO;
import com.ProyectoFinalJava.services.DisqueriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.ProyectoFinalJava.utils.ApiResponseMsg;

@RestController
@RequestMapping("/api/disquerias")
public class DisqueriaController {
    @Autowired
    private final DisqueriaService disqueriaService;

    public DisqueriaController(DisqueriaService disqueriaService) {
        this.disqueriaService = disqueriaService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas las disquerias", description = "Retorna todas las disquerias")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = DisqueriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Disquerias no encontradas", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                    @ExampleObject(name = "DisqueriaNotFound", value = "{\"message\": \"Disqueria no encontrada\"}", description = "Disquerías no encontradas")
            }))
    })
    public ResponseEntity<List<DisqueriaDTO>> getAllDisquerias(@RequestParam(value = "includeRelations", defaultValue = "false") boolean includeRelations) {
        return ResponseEntity.ok(disqueriaService.getAllDisquerias(includeRelations));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una disqueria por su id", description = "Retorna la disqueria asociada al id y sus relaciones")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = DisqueriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Disqueria no encontrada", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DisqueriaNotFound", value = "{\"message\": \"Disqueria no encontrada\"}", description = "Disquería no encontrada")
        }))
    })
    public ResponseEntity<?> getDisqueriaById(@PathVariable("id") Long id) {
        try {
            Optional<DisqueriaDTO> disqueria = disqueriaService.getDisqueriaById(id, false);
            return ResponseEntity.ok(disqueria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("usuario no encontrado", e.getMessage()));
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Crear una disqueria", description = "Retorna la disquería creada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = DisqueriaCreateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Disquería no creada", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DisqueriaNotCreated", value = "{\"message\": \"Disquería no creada\"}", description = "No se pudo crear la disquería")
        }))
    })
    public ResponseEntity<DisqueriaDTO> createDisqueria(@RequestBody DisqueriaCreateDTO disqueriaCreateDTO) {
        DisqueriaDTO createdDisqueria = disqueriaService.saveDisqueria(disqueriaCreateDTO);
        return ResponseEntity.ok(createdDisqueria);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una disqueria por su id", description = "Retorna un mensaje indicando si la disquería fue eliminada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = DisqueriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Disquería no eliminada", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DisqueriaNotDeleted", value = "{\"message\": \"Disquería no eliminada\"}", description = "Disquería no se pudo eliminar")
        }))
    })
    public ResponseEntity<?> deleteDisqueria(@PathVariable("id") Long id) {
        try {
            disqueriaService.deleteDisqueria(id);
            return ResponseEntity.ok().body(new ApiResponseMsg("Disquería eliminada", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Error", "No se pudo eliminar la disquería"));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar una disqueria por su id", description = "Retorna la disquería modificada.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = DisqueriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Disquería no encontrada", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "DisqueriaNotFound", value = "{\"message\": \"Disquería no encontrada\"}", description = "Disquería no encontrada ni modificada")
        }))
    })
    public ResponseEntity<DisqueriaDTO> updateDisqueria(@PathVariable Long id, @RequestBody DisqueriaCreateDTO disqueriaCreateDTO) {
        DisqueriaDTO updatedDisqueria = disqueriaService.updateDisqueria(id, disqueriaCreateDTO);
        return ResponseEntity.ok(updatedDisqueria);
    }
}
