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
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    JogadorRepository jrep;

    @Autowired
    PagamentoRepository prep;

    // LISTAR TODOS OS PAGAMENTOS OU PAGAMENTOS DE UM DETERMINADO JOGADOR (PELO
    // NOME)
    @GetMapping()
    public ResponseEntity<List<Pagamento>> getAllPagamentos(@RequestParam(required = false) String nome) {
        try {
            List<Pagamento> lp = new ArrayList<Pagamento>();

            if (nome == null)
                prep.findAll().forEach(lp::add);
            else {
                prep.findByJogador(jrep.findByNome(nome)).forEach(lp::add);
            }
            if (lp.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(lp, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // listar pagamentos em determinado ano e mes
    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<Pagamento>> getPagamentoByAnoAndMes(@PathVariable("ano") short ano,
            @PathVariable("mes") short mes) {

        try {
            List<Pagamento> pgs = prep.findByAnoAndMes(ano, mes);

            if (pgs.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(pgs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // listar pagamentos em determinado ano
    @GetMapping("/{ano}")
    public ResponseEntity<List<Pagamento>> getPagamentoByAno(@PathVariable("ano") short ano) {

        try {
            List<Pagamento> pgs = prep.findByAno(ano);

            if (pgs.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(pgs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // adicionar pagamento de um jogador
    @PostMapping("/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento p) {

        try {
            Optional<Jogador> _j = jrep.findById(p.getIdJogador());
            if (_j.isPresent()) {
                Pagamento _p = prep.save(new Pagamento(p.getAno(), p.getMes(), p.getValor(), _j.get()));
                return new ResponseEntity<>(_p, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // atualizar dados de um pagamento
    @PutMapping("/pagamentos/{cod_pagamento}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("cod_pagamento") long cod_pagamento,
            @RequestBody Pagamento p) {
        Optional<Pagamento> data = prep.findById(cod_pagamento);

        if (data.isPresent()) {
            Pagamento pag = data.get();
            pag.setJogador(p.getJogador());
            pag.setValor(p.getValor());
            pag.setMes(p.getMes());
            pag.setAno(p.getAno());
            return new ResponseEntity<>(prep.save(pag), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // deletar um pagamento
    @DeleteMapping("/{cod_pagamento}")
    public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("cod_pagamento") long cod_pagamento) {
        try {
            prep.deleteById(cod_pagamento);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}