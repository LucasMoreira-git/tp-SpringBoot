package com.example.TpSpringBoot.dto.producto;

import java.time.LocalDateTime;

public record ProductoDto(
        Long id,
        boolean eliminado,
        LocalDateTime createdAt,
        String nombre,
        Double precio,
        String descripcion,
        Integer stock,
        String imagen,
        Boolean disponible,
        Long categoriaId) {
}
