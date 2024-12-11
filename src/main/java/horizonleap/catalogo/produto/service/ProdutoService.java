package horizonleap.catalogo.produto.service;

import java.io.InvalidClassException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import horizonleap.catalogo.produto.model.ProdutoModel;
import horizonleap.catalogo.produto.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoModel> getAllProducts() {
        return produtoRepository.findAll();
    }

    public Optional<ProdutoModel> getProductById(Integer id) {
        return produtoRepository.findById(id);
    }

    public ProdutoModel addProduct(ProdutoModel product) {
        return produtoRepository.save(product);
    }

    public ProdutoModel updateProductQuantity(Integer id, Integer quantidade) {
        Optional<ProdutoModel> productOpt = produtoRepository.findById(id);
        if (productOpt.isPresent()) {
            ProdutoModel product = productOpt.get();
            product.setQuantidadeEstoque(quantidade);
            return produtoRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Integer id) {
        produtoRepository.deleteById(id);
    }

    public void reduceProductQuantity(Integer id , Integer quantidade){
        ProdutoModel produto = getProductById(id).orElseThrow();

        log.info("tinha " + produto.getQuantidadeEstoque() + " em estoque");
        
        if (produto.getQuantidadeEstoque() < quantidade){
            //TODO tratamento caso não haja estoque suficiente
        }
        Integer novaQuantidade = produto.getQuantidadeEstoque() - quantidade;

        updateProductQuantity(id , novaQuantidade);

        log.info("Agora tem " + novaQuantidade + " em estoque");
    }
}
