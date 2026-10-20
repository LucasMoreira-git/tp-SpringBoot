package com.example.TpSpringBoot.dto.pedido;

import java.time.LocalDate;

public record PedidoEdit(
        Long id,
        LocalDate fecha,
        String estado,
        Double total,
        String formaPago,
        Long usuarioId) {
}
