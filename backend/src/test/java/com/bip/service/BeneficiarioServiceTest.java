package com.bip.service;

import com.bip.domain.Beneficiario;
import com.bip.domain.Transferencia;
import com.bip.repository.BeneficiarioRepository;
import com.bip.repository.TransferenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeneficiarioServiceTest {

    @Mock
    private BeneficiarioRepository beneficiarioRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private BeneficiarioService beneficiarioService;

    @Test
    public void transferirComSaldoSuficiente() {
        Beneficiario origem = new Beneficiario();
        origem.setId(1L);
        origem.setSaldo(new BigDecimal("1000"));
        origem.setVersion(0);

        Beneficiario destino = new Beneficiario();
        destino.setId(2L);
        destino.setSaldo(new BigDecimal("500"));
        destino.setVersion(0);

        when(beneficiarioRepository.findById(1L)).thenReturn(Optional.of(origem));
        when(beneficiarioRepository.findById(2L)).thenReturn(Optional.of(destino));
        when(beneficiarioRepository.save(any(Beneficiario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transferencia resultado = beneficiarioService.transferir(1L, 2L, new BigDecimal("100"));

        assertNotNull(resultado);
        assertEquals(new BigDecimal("900"), origem.getSaldo());
        assertEquals(new BigDecimal("600"), destino.getSaldo());
    }

    @Test
    public void transferirSemSaldoSuficiente() {
        Beneficiario origem = new Beneficiario();
        origem.setId(1L);
        origem.setSaldo(new BigDecimal("50"));
        origem.setVersion(0);

        when(beneficiarioRepository.findById(1L)).thenReturn(Optional.of(origem));

        assertThrows(RuntimeException.class, () -> 
            beneficiarioService.transferir(1L, 2L, new BigDecimal("100"))
        );
    }

    @Test
    public void transferirComValorInvalido() {
        Beneficiario origem = new Beneficiario();
        origem.setId(1L);
        origem.setSaldo(new BigDecimal("1000"));

        when(beneficiarioRepository.findById(1L)).thenReturn(Optional.of(origem));

        assertThrows(RuntimeException.class, () -> 
            beneficiarioService.transferir(1L, 2L, new BigDecimal("0"))
        );
    }
}