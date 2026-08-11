package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacaoTutorComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Test
    @DisplayName("Deve permitir que o tutor solicite a adoção de um pet quando não houver adoções em andamento")
    void devePermitirTutorSolicitarAdocaoPet(){
        // ARRANGE
        given(adocaoRepository.existsByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);
        // ACT + ASSERT
        assertDoesNotThrow(() -> validacaoTutorComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

    @Test
    @DisplayName("Não deve permitir que o tutor solicite a adoção de um pet quando houver adoções em andamento")
    void naoDevePermitirTutorSolicitarAdocaoPet() {
        // ARRANGE
        given(adocaoRepository.existsByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);
        // ACT + ASSERT
        assertThrows(ValidacaoException.class, () -> validacaoTutorComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

}