package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacaoPetComAdocaoEmAndamento;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto  solicitacaoAdocaoDto;

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção de pet quando o status da adoção for em andamento")
    void naoDeveriaPermitirSolicitacaoDeAdocaoEmAndamento() {
        //ARRANGE
        BDDMockito.when(adocaoRepository.existsByPetIdAndStatus(solicitacaoAdocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(true);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validacaoPetComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

    @Test
    @DisplayName("Deveria permitir solicitação de adoção de pet quando o status da adoção não for em andamento")
    void deveriaPermitirSolicitacaoDeAdocaoEmAndamento() {
        //ARRANGE
        BDDMockito.when(adocaoRepository.existsByPetIdAndStatus(solicitacaoAdocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(false);

        //ASSERT + ACT
        assertDoesNotThrow(() -> validacaoPetComAdocaoEmAndamento.validar(solicitacaoAdocaoDto));
    }

}