package com.bip.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.bip.domain.Beneficiario;
import com.bip.domain.Transferencia;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        Beneficiario origem = em.find(Beneficiario.class, idOrigem);
        Beneficiario destino = em.find(Beneficiario.class, idDestino);

        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Beneficiario não encontrado");
        }

        if (!origem.temSaldo(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        origem.sacar(valor);
        destino.depositar(valor);

        em.merge(origem);
        em.merge(destino);

        Transferencia transferencia = new Transferencia();
        transferencia.setBeneficiarioOrigem(origem);
        transferencia.setBeneficiarioDestino(destino);
        transferencia.setValor(valor);

        em.persist(transferencia);
    }

    public Beneficiario findById(Long id) {
        return em.find(Beneficiario.class, id);
    }
}