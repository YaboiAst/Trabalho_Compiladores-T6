package br.ufscar.dc.compiladores;

import br.ufscar.dc.compiladores.tabelas.TabelaCampeoes;
import br.ufscar.dc.compiladores.tabelas.TabelaDeSimbolos;
import br.ufscar.dc.compiladores.tabelas.TabelaSkills;

import java.util.LinkedList;
import java.util.List;

public class Escopo {
    private LinkedList<TabelaDeSimbolos> pilhaTabela;
    private LinkedList<TabelaCampeoes> pilhaCampeoes;
    private LinkedList<TabelaSkills> pilhaSkills;

    public Escopo(){
        pilhaTabela = new LinkedList<>();
        pilhaCampeoes = new LinkedList<>();
        pilhaSkills = new LinkedList<>();

        novoEscopo();
    }

    public Escopo(TabelaDeSimbolos simbolos)
    {
        pilhaTabela = new LinkedList<>();
        pilhaTabela.push(simbolos);
    }

    public void novoEscopo()
    {
        pilhaTabela.push(new TabelaDeSimbolos());
        pilhaCampeoes.push(new TabelaCampeoes());
        pilhaSkills.push(new TabelaSkills());
    }

    public TabelaDeSimbolos getEscopoSimbolos() { return  pilhaTabela.peek(); }
    public TabelaCampeoes   getEscopoCampeoes() { return  pilhaCampeoes.peek(); }
    public TabelaSkills     getEscopoSkills()   { return  pilhaSkills.peek(); }

    public void delEscopo(){
        pilhaTabela.pop();
        pilhaCampeoes.pop();
        pilhaSkills.pop();
    }

    public List<TabelaDeSimbolos> getPilhaTabelaSimbolos(){
        return pilhaTabela;
    }
    public List<TabelaCampeoes>   getPilhaTabelaCampeoes(){
        return pilhaCampeoes;
    }
    public List<TabelaSkills>     getPilhaTabelaSkills()   { return pilhaSkills; }

}
