package com.example.TpSpringBoot.config;

import com.example.TpSpringBoot.dto.categoria.CategoriaCreate;
import com.example.TpSpringBoot.dto.pedido.PedidoCreate;
import com.example.TpSpringBoot.dto.producto.ProductoCreate;
import com.example.TpSpringBoot.dto.usuario.UsuarioCreate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Configuration
public class CargaDeDatosInicial {

    @Bean
    CommandLineRunner cargarDatosIniciales() {
        return args -> {
            List<UsuarioCreate> usuarios = List.of(
                    new UsuarioCreate("Ana", "Gomez", "ana@mail.com", "111111111", "1234", "USUARIO"),
                    new UsuarioCreate("Luis", "Perez", "luis@mail.com", "222222222", "1234", "USUARIO"));

            List<CategoriaCreate> categorias = List.of(
                    new CategoriaCreate("Electronica", "Dispositivos tecnologicos"),
                    new CategoriaCreate("Ropa", "Indumentaria"),
                    new CategoriaCreate("Hogar", "Muebles y decoracion"));

            List<ProductoCreate> productos = List.of(
                    new ProductoCreate("Notebook", 850000.0, "Notebook para trabajo", 10, "notebook.jpg", true, 1L),
                    new ProductoCreate("Teclado", 45000.0, "Teclado mecanico", 20, "teclado.jpg", true, 1L),
                    new ProductoCreate("Mouse", 25000.0, "Mouse inalambrico", 30, "mouse.jpg", true, 1L),
                    new ProductoCreate("Monitor", 180000.0, "Monitor full HD", 8, "monitor.jpg", true, 1L),
                    new ProductoCreate("Campera", 120000.0, "Campera de invierno", 15, "campera.jpg", true, 2L),
                    new ProductoCreate("Remera", 30000.0, "Remera de algodon", 40, "remera.jpg", true, 2L),
                    new ProductoCreate("Pantalon", 70000.0, "Pantalon de vestir", 25, "pantalon.jpg", true, 2L),
                    new ProductoCreate("Silla", 95000.0, "Silla ergonomica", 12, "silla.jpg", true, 3L),
                    new ProductoCreate("Lampara", 22000.0, "Lampara de escritorio", 18, "lampara.jpg", true, 3L),
                    new ProductoCreate("Mesa", 160000.0, "Mesa auxiliar", 6, "mesa.jpg", true, 3L));

            List<PedidoCreate> pedidos = List.of(
                    new PedidoCreate(LocalDate.now(), "PENDIENTE", 895000.0, "TARJETA", 1L),
                    new PedidoCreate(LocalDate.now(), "CONFIRMADO", 150000.0, "TRANSFERENCIA", 2L),
                    new PedidoCreate(LocalDate.now(), "TERMINADO", 257000.0, "EFECTIVO", 1L));

            Map<Long, List<Long>> productosPorPedido = Map.of(
                    1L, List.of(1L, 2L),
                    2L, List.of(5L, 6L),
                    3L, List.of(8L, 9L));

            if (productosPorPedido.values().stream().anyMatch(detalles -> detalles.size() < 2)) {
                throw new IllegalStateException("Cada pedido debe tener al menos dos detalles.");
            }

            System.out.printf(
                    "DTO inicializados: %d usuarios, %d pedidos, %d categorias y %d productos.%n",
                    usuarios.size(), pedidos.size(), categorias.size(), productos.size());
            System.out.printf("Detalles asociados: %d pedidos, con al menos 2 productos cada uno.%n",
                    productosPorPedido.size());
        };
    }
}
