package dw.futebol.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dw.futebol.model.Jogador;
import dw.futebol.repository.JogadorRepository;

@RestController
@RequestMapping("/api")
public class JogadorController {
    
    @Autowired
    JogadorRepository jrep;

    @GetMapping("/jogadores")
    public ResponseEntity<List<Jogador>> getAllJogadores(@RequestParam(required=false) String nome){
        try{
            List<Jogador> lj = new ArrayList<Jogador>();

            if (nome == null)
                jrep.findAll().forEach(lj::add);
            else{
                jrep.findByNomeContaining(nome).forEach(lj::add);
            }
            if (lj.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(lj, HttpStatus.OK); 
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> createjogador(@RequestBody Jogador j){
        try {
            Jogador _j = jrep.save(new Jogador(j.getNome(), j.getEmail(), j.getDatanasc()));
            return new ResponseEntity<>(_j, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
