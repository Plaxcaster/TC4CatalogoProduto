package horizonleap.catalogo.produto.batch;

import horizonleap.catalogo.produto.model.ProdutoModel;
import org.springframework.batch.item.ItemProcessor;

public class ProdutoItemProcessor implements ItemProcessor<ProdutoModel, ProdutoModel> {
    @Override
    public ProdutoModel process(ProdutoModel item) {
        // Apply any processing logic here (e.g., validation, transformation)
        return item;
    }
}
