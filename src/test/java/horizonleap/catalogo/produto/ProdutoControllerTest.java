package horizonleap.catalogo.produto;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import horizonleap.catalogo.produto.controller.ProdutoController;
import horizonleap.catalogo.produto.model.ProdutoModel;
import horizonleap.catalogo.produto.service.BatchService;
import horizonleap.catalogo.produto.service.ProdutoService;

public class ProdutoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private BatchService batchService;

    @Mock
    private ScheduledExecutorService scheduledExecutorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProdutoModel product = new ProdutoModel("Produto XY", "Descricao do Produto XY", 100L);
        product.setId(1);
        when(produtoService.getProductById(1)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Produto XY"));

        verify(produtoService, times(1)).getProductById(1);
    }

    @Test
    public void testUpdateProductQuantity() throws Exception {
        ProdutoModel product = new ProdutoModel("Produto XY", "Descricao do Produto XY", 100L);
        product.setId(1);
        product.setQuantidadeEstoque(20);
        when(produtoService.updateProductQuantity(1, 20)).thenReturn(product);

        mockMvc.perform(put("/produtos/1")
                        .param("quantity", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidadeEstoque").value(20));

        verify(produtoService, times(1)).updateProductQuantity(1, 20);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk());

        verify(produtoService, times(1)).deleteProduct(1);
    }

    @Test
    public void testUploadCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "produtos.csv", "text/csv", "nome,descricao,preco,quantidadeEstoque\nProduto XY,Descricao A,100,10".getBytes());

        mockMvc.perform(multipart("/produtos/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Arquivo CSV carregado com sucesso!"));
    }

    @Test
    public void testScheduleUploadCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "produtos.csv", "text/csv", "nome,descricao,preco,quantidadeEstoque\nProduto XY,Descricao A,100,10".getBytes());

        mockMvc.perform(multipart("/produtos/schedule-upload")
                        .file(file)
                        .param("scheduleDateTime", "2024-12-09 14:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().string("Processamento agendado para 2024-12-09 14:00:00 com sucesso!"));
    }
}
