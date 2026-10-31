package com.example.TpSpringBoot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Producto producto;

    protected DetallePedido() {
    }

    public DetallePedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.setCantidad(cantidad);
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        if (cantidad == null || cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        this.cantidad = cantidad;
        this.subtotal = producto == null || producto.getPrecio() == null
                ? null
                : producto.getPrecio() * cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }
}
