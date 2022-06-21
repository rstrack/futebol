package dw.futebol.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //LISTAR TODOS OS PAGAMENTOS OU PAGAMENTOS DE UM DETERMINADO JOGADOR (PELO NOME)
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
    public ResponseEntity<List<Pagamento>> getPagamentoByAnoAndMes(@PathVariable("ano") short ano, @PathVariable("mes") short mes){
        
        try{
            List<Pagamento> pgs = prep.findByAnoAndMes(ano, mes);

            if (pgs.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(pgs, HttpStatus.OK); 
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //adicionar pagamento de um jogador
    @PostMapping("/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento p){
        
        try {
            Pagamento _p = prep.save(new Pagamento(p.getAno(), p.getMes(), p.getValor(), p.getJogador()));
            return new ResponseEntity<>(_p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //atualizar dados de um pagamento
    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("id") long id, @RequestBody Pagamento p)
    {
        Optional<Pagamento> data = prep.findById(id);

        if (data.isPresent())
        {
            Pagamento pag = data.get();
            pag.setJogador(pag.getJogador());
            pag.setValor(pag.getValor());
            pag.setMes(pag.getMes());
            pag.setAno(pag.getAno());
            return new ResponseEntity<>(prep.save(pag), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    //deletar um pagamento
    @DeleteMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("cod_pagamento") long cod_pagamento)
    {
        try {
            prep.deleteById(cod_pagamento);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}