package dw.futebol.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dw.futebol.repository.JogadorRepository;
import dw.futebol.repository.PagamentoRepository;
import dw.futebol.model.*;

@RestController
@RequestMapping("/api")
public class PagamentoController {
    
    @Autowired
    JogadorRepository jrep;

    @Autowired
    PagamentoRepository prep;

    //LISTAR TODOS OS PAGAMENTOS OU PAGAMENTOS DE UM DETERMINADO JOGADOR
    @GetMapping("/pagamentos")
    public ResponseEntity<List<Pagamento>> getAllPagamentos(@RequestParam(required=false) String nome){
        try{
            List<Pagamento> lp = new ArrayList<Pagamento>();

            if (nome == null)
                prep.findAll().forEach(lp::add);
            else{
                prep.findByJogador(jrep.findByNome(nome)).forEach(lp::add);
            }
            if (lp.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
            return new ResponseEntity<>(lp, HttpStatus.OK); 
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //listar pagamentos em determinado ano e mes
    @GetMapping("/pagamentos/{ano}/{mes}")
    public ResponseEntity<Pagamento> getPagamentoByAnoAndMes(@PathVariable("ano") short ano, @PathVariable("mes") short mes){
        Optional<Pagamento> pgs = prep.findByAnoAndMes(ano, mes);

        if (pgs.isPresent())
            return new ResponseEntity<>(pgs.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //adicionar pagamento de um jogador
    @PostMapping("/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento p){
        
        try {
            Pagamento _p = prep.save(new Pagamento(p.getAno(), p.getMes(), p.getValor(), p.getJogador())); //ver como passar o Jogador
            return new ResponseEntity<>(_p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}