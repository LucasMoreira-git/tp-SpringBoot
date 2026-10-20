package com.example.TpSpringBoot.dto.pedido;

import java.time.LocalDate;

public record PedidoCreate(
        LocalDate fecha,
        String estado,
        Double total,
        String formaPago,
        Long usuarioId) {
}
