package horizonleap.catalogo.produto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import horizonleap.catalogo.produto.model.ProdutoModel;
import horizonleap.catalogo.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @Operation(summary = "Obter todos os produtos", description = "Recuperar uma lista de todos os produtos")
    public List<ProdutoModel> getAllProducts() {
        return produtoService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter produto por ID", description = "Recuperar um produto pelo seu ID")
    public Optional<ProdutoModel> getProductById(@PathVariable Integer id) {
        return produtoService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo produto", description = "Criar um novo produto")
    public ProdutoModel addProduct(@RequestBody ProdutoModel product) {
        return produtoService.addProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar quantidade do produto", description = "Atualizar a quantidade de um produto existente pelo seu ID")
    public ProdutoModel updateProductQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        return produtoService.updateProductQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Excluir um produto pelo seu ID")
    public void deleteProduct(@PathVariable Integer id) {
        produtoService.deleteProduct(id);
    }
}