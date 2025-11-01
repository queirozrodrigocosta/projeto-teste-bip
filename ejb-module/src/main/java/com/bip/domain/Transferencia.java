package com.bip.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_origem_id", nullable = false)
    private Beneficiario beneficiarioOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_destino_id", nullable = false)
    private Beneficiario beneficiarioDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}