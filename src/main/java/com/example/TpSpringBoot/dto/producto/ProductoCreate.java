package com.example.TpSpringBoot.dto.producto;

public record ProductoCreate(
        String nombre,
        Double precio,
        String descripcion,
        Integer stock,
        String imagen,
        Boolean disponible,
        Long categoriaId) {
}
