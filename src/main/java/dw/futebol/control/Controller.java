package dw.futebol.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dw.futebol.repository.JogadorRepository;
import dw.futebol.repository.PagamentoRepository;
import dw.futebol.model.*;

@RestController
@RequestMapping("/")
public class Controller {
    
    @Autowired
    JogadorRepository jrep;

    @Autowired
    PagamentoRepository prep;

    //LISTAR TODOS OS PAGAMENTOS OU PAGAMENTOS DE UM DETERMINADO JOGADOR -> TERMINAR!

    @GetMapping("/pagamentos")
    public ResponseEntity<List<Pagamento>> getAllPagamentos(@RequestParam(required=false) String nome){
        try{
            List<Pagamento> lp = new ArrayList<Pagamento>();

            if (nome == null)
                prep.findAll().forEach(lp::add);
            else{
                //prep.findByJogador(jrep.findByNomeContaining(nome)); <- RESOLVER ALGO PARECIDO
            }
            if (lp.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
            return new ResponseEntity<>(lp, HttpStatus.OK); 
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
