package br.ufscar.dc.compiladores.tabelas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaDeSimbolos {
    public enum TipoDed {
        INVALIDO,
        STAT,
        CLASSE
    }

    public static class EntradaTabelaDeSimbolos{
        String nome;
        TipoDed tipo;
        List<String> dependenciasTipo;

        public EntradaTabelaDeSimbolos(String nome, TipoDed tipo){
            this.nome = nome;
            this.tipo = tipo;
            this.dependenciasTipo = null;
        }

        public EntradaTabelaDeSimbolos(String nome, TipoDed tipo, String... dependencias){
            this.nome = nome;
            this.tipo = tipo;
            this.dependenciasTipo = new ArrayList<>(List.of(dependencias));
        }
    }

    private final Map<String, EntradaTabelaDeSimbolos> tabela;

    public TabelaDeSimbolos() {
        this.tabela = new HashMap<>();
    }

    public void adicionar(EntradaTabelaDeSimbolos input){
        tabela.put(input.nome, input);
    }

    public void adicionar(String nome, TipoDed tipo) {
        EntradaTabelaDeSimbolos novaEntrada = new EntradaTabelaDeSimbolos(nome, tipo);
        adicionar(novaEntrada);
    }

    public void adicionar(String nome, TipoDed tipo, String... dependencias) {
        EntradaTabelaDeSimbolos novaEntrada = new EntradaTabelaDeSimbolos(nome, tipo, dependencias);
        adicionar(novaEntrada);
    }

    public boolean existe(String nome){
        return  tabela.containsKey(nome);
    }

    public TipoDed verificar(String nome){
        return tabela.get(nome).tipo;
    }

    public List<String> getDependencies(String nome, TipoDed tipo)
    {
        var dep = tabela.get(nome).dependenciasTipo;

        List<String> nomeDependencias = new ArrayList<>();
        for (String dependencia : dep)
        {
            if (tabela.get(dependencia).tipo == tipo) nomeDependencias.add(dependencia);
        }

        return nomeDependencias;
    }
}
