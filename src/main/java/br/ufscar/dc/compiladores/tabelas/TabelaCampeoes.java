package br.ufscar.dc.compiladores.tabelas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TabelaCampeoes {

    public static class CampeaoStat
    {
        String nome;
        Integer valor;

        public CampeaoStat(String nome, Integer valor)
        {
            this.nome = nome;
            this.valor = valor;
        }
    }

    public static class EntradaCampeao
    {
        String nome;
        String classe;
        Map<String, Integer> stats;
        String[] skills;

        public EntradaCampeao(String nome) {
            this.nome = nome;
        }

        public void setClasse(String classe) { this.classe = classe; }
        public void setStat(CampeaoStat stat) {
            if (this.stats == null) this.stats = new HashMap<>();
            this.stats.put(stat.nome, stat.valor);
        }
        public void setStats(CampeaoStat... stats) {
            this.stats = new HashMap<>();

            for(CampeaoStat stat : stats)
            {
                this.stats.put(stat.nome, stat.valor);
            }
        }
        public void setSkill(int skillIdx, String skillNome) {
            if (skills == null) skills = new String[4];
            this.skills[skillIdx] = skillNome;
        }

        public String getClasse() {
            return classe;
        }
        public Integer getStat(String statNome)
        {
            return stats.getOrDefault(statNome, null);
        }
    }

    private HashMap<String, EntradaCampeao> campeoes;

    public TabelaCampeoes() { campeoes = new HashMap<>(); }

    public void adicionar(EntradaCampeao campeao)
    {
        campeoes.put(campeao.nome, campeao);
    }

    public boolean existe(String nome)
    {
        return campeoes.containsKey(nome);
    }

    public void remover(String nome)
    {
        campeoes.remove(nome);
    }
}
