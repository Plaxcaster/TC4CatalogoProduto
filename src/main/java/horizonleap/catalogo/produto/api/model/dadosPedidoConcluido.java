package horizonleap.catalogo.produto.api.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class dadosPedidoConcluido {
    private UUID id;

    private List<ItemPedido> listaItens;

    private Integer cliente;

    private String nomeCliente;
    
    private String endereco;

    private String CPF;

}
