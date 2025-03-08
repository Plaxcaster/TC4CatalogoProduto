package horizonleap.catalogo.produto.api.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import horizonleap.catalogo.produto.api.model.dadosPedidoConcluido;
import horizonleap.catalogo.produto.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class PedidoFeitoConsumer {

    private ProdutoService service;

    @Bean
    public Consumer<dadosPedidoConcluido> pedidoConcluido() {
        return pedido -> {
            // TODO incluir processo de serialização nos eventos para conseguirmos recuperar
            // a lista de itens do pedido
            // pedido.getListaItens()
            // .forEach(item -> service.reduceProductQuantity(item.getIdProduto(),
            // item.getQuantidade()));
        };
    }

}
