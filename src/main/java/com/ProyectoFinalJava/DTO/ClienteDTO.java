package com.ProyectoFinalJava.DTO;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {
    @Schema(description = "Unique identifier of the user", example = "0")
    private Long id;
    @Schema(description = "Name of the user", example = "Eric Clapton")
    private String name;
    @Schema(description = "Email address of the user", example = "eric.clapton@example.com")
    private String email;
    @Schema(description = "Phone number of the user", example = "78912371239")
    private String phone;
    private Set<Long> disqueriaIds;


    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String name, String email, String phone, Set<Long> disqueriaIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.disqueriaIds = disqueriaIds;
    }

    public Set<Long> getDisqueriaIds() {
        return disqueriaIds;
    }

    public void setDisqueriaIds(Set<Long> disqueriaIds) {
        this.disqueriaIds = disqueriaIds;
    }
}
