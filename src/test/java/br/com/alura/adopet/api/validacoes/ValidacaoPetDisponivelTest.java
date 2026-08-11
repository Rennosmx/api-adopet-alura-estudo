package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    @InjectMocks
    private ValidacaoPetDisponivel validacaoPetDisponivel;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Test
    void deveriaPermitirSolcitacaoDeAdocaoPet() {
        //ARRANGE
        BDDMockito.when(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).thenReturn(pet);
        BDDMockito.when(pet.getAdotado()).thenReturn(false);

         //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacaoPetDisponivel.validar(solicitacaoAdocaoDto));
    }

    @Test
    void naoDeveriaPermitirSolcitacaoDeAdocaoPet() {
        //ARRANGE
        BDDMockito.when(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).thenReturn(pet);
        BDDMockito.when(pet.getAdotado()).thenReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class,() -> validacaoPetDisponivel.validar(solicitacaoAdocaoDto));
    }

}