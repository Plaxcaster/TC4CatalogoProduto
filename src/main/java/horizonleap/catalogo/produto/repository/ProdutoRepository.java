package horizonleap.catalogo.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import horizonleap.catalogo.produto.model.ProdutoModel;





@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel,Integer>{

}
