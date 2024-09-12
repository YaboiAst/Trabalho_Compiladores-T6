package br.ufscar.dc.compiladores.tabelas;

import java.util.HashMap;
import java.util.Map;

public class TabelaSkills {

    public static class EntradaSkill
    {
        String nome;
        String classeReq;
        Map<String, Integer> statsReq;

        public EntradaSkill(String nome)
        {
            this.nome = nome;
            this.classeReq = null;
            this.statsReq = null;
        }

        public void setClasseReq(String classeReq) {
            this.classeReq = classeReq;
        }

        public void setStatReq(TabelaCampeoes.CampeaoStat stat) {
            if (this.statsReq == null) this.statsReq = new HashMap<>();
            this.statsReq.put(stat.nome, stat.valor);
        }

        public void setStatsReq(TabelaCampeoes.CampeaoStat... stats) {
            this.statsReq = new HashMap<>();

            for(TabelaCampeoes.CampeaoStat stat : stats)
            {
                this.statsReq.put(stat.nome, stat.valor);
            }
        }
    }

    private HashMap<String, EntradaSkill> skills;

    public TabelaSkills() { skills = new HashMap<>(); }

    public void adicionar(EntradaSkill campeao)
    {
        skills.put(campeao.nome, campeao);
    }

    public boolean existe(String nome)
    {
        return skills.containsKey(nome);
    }

    public void remover(String nome)
    {
        skills.remove(nome);
    }

    public String getClassReq(String nome)
    {
        return skills.get(nome).classeReq;
    }

    public Map<String, Integer> getStat(String nome)
    {
        return skills.get(nome).statsReq;
    }

    public Integer getStatReq(String nome, String statValue)
    {
        return skills.get(nome).statsReq.get(statValue);
    }
}
