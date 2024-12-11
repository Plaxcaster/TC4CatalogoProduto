package horizonleap.catalogo.produto.api.model;

import java.util.UUID;

import lombok.Data;

@Data
public class ItemPedido {
    private UUID id;

    private Integer quantidade;

    private Integer idProduto;
}
