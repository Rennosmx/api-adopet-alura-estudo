package br.com.alura.adopet.api.validacoes;


import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes  validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Test
    @DisplayName("Deve permitir solicitação de pet caso o tutor não tenha chegado ao limite de 5 adoções.")
    void devePermitirSolicitacaoPetAoTutorAbaixoDoLimiteDeAdocoes() {
        //ARRANGE
        given(adocaoRepository.countByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.APROVADO)).willReturn(4);
        //ACT + ASSERT
        assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

    @Test
    @DisplayName("Não deve permitir solicitação de pet caso o tutor tenha chegado ao limite de 5 adoções.")
    void naoDevePermitirSolicitacaoPetAoTutorQueTenhaChegadoAoLimiteDeAdocoes() {
        //ARRANGE
         given(adocaoRepository.countByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.APROVADO)).willReturn(5);
        //ACT + ASSERT
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

}