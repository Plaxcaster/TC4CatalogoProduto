package horizonleap.catalogo.produto.batch;

import org.springframework.batch.item.ItemProcessor;

import horizonleap.catalogo.produto.model.ProdutoModel;

public class ProdutoItemProcessor implements ItemProcessor<ProdutoModel, ProdutoModel> {
    @Override
    public ProdutoModel process(ProdutoModel item) {
        // Apply any processing logic here (e.g., validation, transformation)
        return item;
    }
}
