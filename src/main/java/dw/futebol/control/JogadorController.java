package dw.futebol.control;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dw.futebol.model.Jogador;
import dw.futebol.repository.JogadorRepository;

@RestController
@RequestMapping("/api")
public class JogadorController {
    @Autowired
    JogadorRepository jrep;
    //listar todos os jogadores ou jogadores com nome (ou parte dele) passado por parametro
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

    //adicionar jogador
    //Pode haver erro em datas devido ao fuso horário
    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> createjogador(@RequestBody Jogador j){
        try {
            Jogador _j = jrep.save(new Jogador(j.getNome(), j.getEmail(), j.getDatanasc()));
            return new ResponseEntity<>(_j, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //modificar dados de um jogador 
    @PutMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<Jogador> Put(@PathVariable("cod_jogador") long cod_jogador, @RequestBody Jogador j)
    {
        Optional<Jogador> data = jrep.findById(cod_jogador);

        if (data.isPresent())
        {
            Jogador jog = data.get();
            jog.setNome(j.getNome());
            jog.setEmail(j.getEmail());
            jog.setDatanasc(j.getDatanasc());

            return new ResponseEntity<>(jrep.save(jog), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    //deletar um jogador
    @DeleteMapping("/jogadores/{cod_jogador}")
    public ResponseEntity<HttpStatus> deletejogador(@PathVariable("cod_jogador") long cod_jogador)
    {
        try {
            jrep.deleteById(cod_jogador);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
