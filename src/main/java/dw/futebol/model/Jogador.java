package dw.futebol.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jogador")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cod_jogador;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String email;

    @Column
    private Date datanasc;
    
    public Jogador(){

    }

    public Jogador(String nome, String email, Date datanasc){
        this.nome=nome;
        this.email=email;
        this.datanasc=datanasc;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatanasc() {
        return this.datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

}
