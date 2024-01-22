package com.logistica.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tipoPorducto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private LocalDateTime fechaEntrega;

    @Column(nullable = false)
    private Long numeroGuia;

    @Column(nullable = false)
    private BigDecimal valorEnvio;

    @Column(nullable = false)
    private String placaFlota;

    private BigDecimal descuento;

    private BigDecimal valorNeto;

    @ManyToOne
    @JoinColumn(name = "id_lugar_entrega_fk", referencedColumnName = "id", nullable = false)
    private LugarEntrega lugarEntrega;

    @ManyToOne
    @JoinColumn(name = "id_tipo_envio_fk", referencedColumnName = "id", nullable = false)
    private TipoEnvio tipoEnvio;

    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

}