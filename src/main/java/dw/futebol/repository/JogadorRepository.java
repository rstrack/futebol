package dw.futebol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.futebol.model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    Optional<Jogador> findById(Long cod);

    Jogador findByNome(String nome);

    List<Jogador> findByNomeContaining(String nome);

}
