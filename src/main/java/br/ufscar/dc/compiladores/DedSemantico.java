package br.ufscar.dc.compiladores;

import br.ufscar.dc.compiladores.tabelas.TabelaCampeoes;
import br.ufscar.dc.compiladores.tabelas.TabelaCampeoes.EntradaCampeao;
import br.ufscar.dc.compiladores.tabelas.TabelaDeSimbolos;
import br.ufscar.dc.compiladores.tabelas.TabelaDeSimbolos.TipoDed;
import br.ufscar.dc.compiladores.tabelas.TabelaSkills;
import br.ufscar.dc.compiladores.tabelas.TabelaSkills.EntradaSkill;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Map;

import static br.ufscar.dc.compiladores.DeDParser.*;
import static br.ufscar.dc.compiladores.DedUtils.*;

public class DedSemantico extends DeDBaseVisitor {
    Escopo escopo = new Escopo();

    @Override
    public Object visitPrograma(ProgramaContext ctx) {
        return super.visitPrograma(ctx);
    }

    @Override
    public Object visitDef_stat(Def_statContext ctx) {
        TabelaDeSimbolos curEscopo = escopo.getEscopoSimbolos();

        TerminalNode statIdent = ctx.IDENT();
        if (curEscopo.existe(statIdent.getText())) // ERRO: declarou o mesmo stat duas vezes
        {
            adicionarErroSemantico(ctx.start, "Stat " + statIdent.getText() + " já declarado anteriormente");
        }
        else
        {
            TipoDed tipo = TipoDed.STAT;
            curEscopo.adicionar(statIdent.getText(), tipo);
        }

        return super.visitDef_stat(ctx);
    }

    @Override
    public Object visitDef_class(Def_classContext ctx) {
        TabelaDeSimbolos curEscopo = escopo.getEscopoSimbolos();

        TerminalNode statIdent = ctx.IDENT();
        if (curEscopo.existe(statIdent.getText())) // ERRO: declarou a mesma classe duas vezes
        {
            adicionarErroSemantico(ctx.start, "Classe " + statIdent.getText() + " já declarado anteriormente");
        }
        else
        {
            TipoDed tipo = TipoDed.CLASSE;
            curEscopo.adicionar(statIdent.getText(), tipo);
        }

        return super.visitDef_class(ctx);
    }

    @Override
    public Object visitDef_skill(Def_skillContext ctx) {
        TabelaSkills tabelaSkills = escopo.getEscopoSkills();

        TerminalNode skillIdent = ctx.IDENT();
        if (tabelaSkills.existe(skillIdent.getText())) // ERRO: Declarou a mesma skill duas vezes
        {
            adicionarErroSemantico(ctx.start, "Skill "
                    + skillIdent.getText()
                    + " já declarado anteriormente");
            return super.visitDef_skill(ctx);
        }

        EntradaSkill novaSkill = new EntradaSkill(skillIdent.getText());

        // Checa requerimentos da skill (OPCIONAL)
        if (ctx.skill_reqs() != null)
        {
            TabelaDeSimbolos tabela = escopo.getEscopoSimbolos();

            TerminalNode classref = ctx.skill_reqs().class_ref().IDENT();
            if (!tabela.existe(classref.getText())) // ERRO: Classe do requisito não existe
            {
                adicionarErroSemantico(ctx.skill_reqs().class_ref().start, "Classe " + classref.getText() + " não foi declarado");
                return super.visitDef_skill(ctx);
            }
            novaSkill.setClasseReq(classref.getText());

            for (Stat_tupleContext stat : ctx.skill_reqs().stat_tuple())
            {
                TerminalNode statIdent = stat.IDENT();
                if (!tabela.existe(statIdent.getText())) // ERRO: Algum stat do requisito não existe
                {
                    adicionarErroSemantico(stat.start, "Stat " + statIdent.getText() + " não foi declarado");
                    return super.visitDef_skill(ctx);
                }

                novaSkill.setStatReq(new TabelaCampeoes.CampeaoStat(statIdent.getText(), Integer.valueOf(stat.NUM_INT().getText())));
            }
        }

        tabelaSkills.adicionar(novaSkill);
        return super.visitDef_skill(ctx);
    }

    @Override
    public Object visitDef_champion(Def_championContext ctx) {
        TabelaCampeoes tabelaCampeoes = escopo.getEscopoCampeoes();

        TerminalNode campIdent = ctx.IDENT();
        if (tabelaCampeoes.existe(campIdent.getText())) // ERRO: Declarou o mesmo campeão duas vezes
        {
            adicionarErroSemantico(ctx.start, "Skill "
                    + campIdent.getText()
                    + " já declarado anteriormente");
            return super.visitDef_champion(ctx);
        }

        EntradaCampeao novoCamp = new EntradaCampeao(campIdent.getText());
        TabelaDeSimbolos tabela = escopo.getEscopoSimbolos();

        TerminalNode classref = ctx.class_ref().IDENT();
        if (!tabela.existe(classref.getText())) // ERRO: A classe do campeão não existe
        {
            adicionarErroSemantico(ctx.class_ref().start, "Classe " + classref.getText() + " não foi declarado");
            return super.visitDef_champion(ctx);
        }
        novoCamp.setClasse(classref.getText());

        for (Stat_tupleContext stat : ctx.stat_tuple())
        {
            TerminalNode statIdent = stat.IDENT();
            if (!tabela.existe(statIdent.getText())) // ERRO: Algum dos stats do campeão não existe
            {
                adicionarErroSemantico(stat.start, "Stat " + statIdent.getText() + " não foi declarado");
                return super.visitDef_champion(ctx);
            }

            novoCamp.setStat(new TabelaCampeoes.CampeaoStat(statIdent.getText(), Integer.valueOf(stat.NUM_INT().getText())));
        }

        TabelaSkills tabelaSkills = escopo.getEscopoSkills();
        int skillCount = 0;
        // Checa as skills
        for (TerminalNode skillIdent : ctx.championskilldef().IDENT())
        {
            if (!tabelaSkills.existe(skillIdent.getText())) // ERRO: Skill não existe
            {
                adicionarErroSemantico(ctx.start, "Skill " + skillIdent.getText() + " não foi declarada");
                return super.visitDef_champion(ctx);
            }

            String classReq = tabelaSkills.getClassReq(skillIdent.getText());
            if (classReq == null)
            {
                novoCamp.setSkill(skillCount++, skillIdent.getText());
                continue;
            }

            if (!classReq.equals(novoCamp.getClasse())) // ERRO: Campeao não atende ao requisito de classe da skill
            {
                adicionarErroSemantico(ctx.championskilldef().start,
                        "Skill " + skillIdent.getText() + " requere a classe " + classReq
                                  + " -- Classe do campeão: " + novoCamp.getClasse());
                return super.visitDef_champion(ctx);
            }

            Map<String, Integer> skillStatReq = tabelaSkills.getStat(skillIdent.getText());
            // Checa todas os stats requeridos pela skill
            for (String statNome : skillStatReq.keySet())
            {
                Integer statValue = novoCamp.getStat(statNome);
                if (statValue == null || statValue < skillStatReq.get(statNome)) // ERRO: Campeao não atende a algum requisito de stat da skill
                {
                    StringBuilder msgErro = new StringBuilder();
                    msgErro
                            .append("Skill ").append(skillIdent.getText())
                            .append(" requere: ").append(skillStatReq.get(statNome)).append(" de ").append(statNome)
                            .append(" -- stat do campeão: ");
                    if (statValue == null) msgErro.append("indefinido");
                    else msgErro.append(statValue.toString());

                    adicionarErroSemantico(ctx.championskilldef().start, msgErro.toString());

                    return super.visitDef_champion(ctx);
                }
            }

            novoCamp.setSkill(skillCount++, skillIdent.getText());
        }

        tabelaCampeoes.adicionar(novoCamp);
        return super.visitDef_champion(ctx);
    }
}
