package horizonleap.catalogo.produto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import horizonleap.catalogo.produto.model.ProdutoModel;
import horizonleap.catalogo.produto.repository.ProdutoRepository;
import horizonleap.catalogo.produto.service.ProdutoService;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        ProdutoModel product = new ProdutoModel("Produto XA", "Descricao do Produto XA", 100L);
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(product);

        ProdutoModel created = produtoService.addProduct(product);

        assertEquals("Produto XA", created.getNome());
        verify(produtoRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductQuantity() {
        ProdutoModel product = new ProdutoModel("Produto XA", "Descricao do Produto XA", 100L);
        product.setId(1);
        product.setQuantidadeEstoque(10);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(product));
        when(produtoRepository.save(any(ProdutoModel.class))).thenReturn(product);

        ProdutoModel updated = produtoService.updateProductQuantity(1, 20);

        assertEquals(20, updated.getQuantidadeEstoque());
        verify(produtoRepository, times(1)).findById(1);
        verify(produtoRepository, times(1)).save(product);
    }

}
