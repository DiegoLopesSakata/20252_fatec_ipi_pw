package com.fatec.loja;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
    @Query(value="select * from produto where destaque > 0 order by destaque", nativeQuery = true)
    public List<Produto> listarVitrine();

    @Query(value="select * from produto where nome like ?1 or keyword like ?1",nativeQuery = true)
    List<Produto> fazerBusca(String termo);
}
