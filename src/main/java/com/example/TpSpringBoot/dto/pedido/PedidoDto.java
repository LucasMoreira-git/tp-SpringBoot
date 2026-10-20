package com.example.TpSpringBoot.dto.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PedidoDto(
        Long id,
        boolean eliminado,
        LocalDateTime createdAt,
        LocalDate fecha,
        String estado,
        Double total,
        String formaPago,
        Long usuarioId) {
}
