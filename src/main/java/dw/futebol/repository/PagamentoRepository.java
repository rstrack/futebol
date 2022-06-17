package dw.futebol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.futebol.model.Pagamento;
import dw.futebol.model.*;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

    //CORRIGIR -> objetivo é buscar pagamentos de um jogador especifico
    List<Pagamento> findByJogador(Jogador jogador);
}
