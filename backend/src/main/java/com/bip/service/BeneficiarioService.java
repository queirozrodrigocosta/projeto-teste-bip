package com.bip.service;

import com.bip.domain.Beneficiario;
import com.bip.domain.Transferencia;
import com.bip.repository.BeneficiarioRepository;
import com.bip.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;
    private final TransferenciaRepository transferenciaRepository;

    public List<Beneficiario> listar() {
        return beneficiarioRepository.findAll();
    }

    public Beneficiario obter(Long id) {
        return beneficiarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Beneficiario não encontrado"));
    }

    @Transactional
    public Beneficiario criar(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    @Transactional
    public Beneficiario atualizar(Long id, Beneficiario beneficiario) {
        Beneficiario existente = obter(id);
        existente.setNome(beneficiario.getNome());
        existente.setCpf(beneficiario.getCpf());
        return beneficiarioRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        beneficiarioRepository.deleteById(id);
    }

    @Transactional
    public Transferencia transferir(Long idOrigem, Long idDestino, BigDecimal valor ) {
        Beneficiario origem = beneficiarioRepository.findById(idOrigem)
            .orElseThrow(() -> new RuntimeException("Beneficiario origem não encontrado"));
        
        Beneficiario destino = beneficiarioRepository.findById(idDestino)
            .orElseThrow(() -> new RuntimeException("Beneficiario destino não encontrado"));

        if (!origem.temSaldo(valor)) {
            throw new RuntimeException("Saldo insuficiente");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        origem.sacar(valor);
        destino.depositar(valor);

        beneficiarioRepository.save(origem);
        beneficiarioRepository.save(destino);

        Transferencia transferencia = new Transferencia();
        transferencia.setBeneficiarioOrigem(origem);
        transferencia.setBeneficiarioDestino(destino);
        transferencia.setValor(valor);

        return transferenciaRepository.save(transferencia);
    }
}
