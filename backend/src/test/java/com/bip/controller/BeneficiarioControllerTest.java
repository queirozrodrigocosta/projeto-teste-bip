package com.bip.controller;

import com.bip.domain.Beneficiario;
import com.bip.service.BeneficiarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficiarioController.class)
public class BeneficiarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficiarioService beneficiarioService;

    @Test
    public void listarBeneficiarios() throws Exception {
        Beneficiario b1 = new Beneficiario();
        b1.setId(1L);
        b1.setNome("Teste");
        b1.setCpf("12345678901");
        b1.setSaldo(new BigDecimal("1000"));

        when(beneficiarioService.listar()).thenReturn(Arrays.asList(b1));

        mockMvc.perform(get("/api/beneficiarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nome").value("Teste"));
    }
}
