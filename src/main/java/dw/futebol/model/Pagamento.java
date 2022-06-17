package dw.futebol.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cod_pagamento;

    @Column
    private short ano;

    @Column
    private short mes;

    @Column
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cod_jogador")
    private Jogador jogador;

    public Pagamento(short ano, short mes, BigDecimal valor){
        this.ano = ano;
        this.mes=mes;
        this.valor=valor;
    }

    public short getAno() {
        return this.ano;
    }

    public void setAno(short ano) {
        this.ano = ano;
    }

    public short getMes() {
        return this.mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }




}
