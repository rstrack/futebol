package dw.futebol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dw.futebol.model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    Jogador findByNome(String nome);

}
