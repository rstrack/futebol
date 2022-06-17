package dw.futebol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.futebol.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
    
}
