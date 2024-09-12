package br.ufscar.dc.compiladores;

import org.antlr.v4.runtime.tree.TerminalNode;

import static br.ufscar.dc.compiladores.DeDParser.*;
import static br.ufscar.dc.compiladores.DedUtils.*;

public class DedConvert extends DeDBaseVisitor{
    StringBuilder output;

    Escopo tabelas;

    public DedConvert(){
        this.tabelas = new Escopo();

        this.output = new StringBuilder();
        MapStringBuilder(this.output);
    }

    @Override
    public Object visitPrograma(ProgramaContext ctx) {
        output.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        OpenTag("root");

        //
        OpenTag("declare");
        ctx.decl_stats_classes().forEach(this::visitDecl_stats_classes);
        CloseTag("declare");

        output.append("\n");

        //
        OpenTag("skills");
        ctx.def_skill().forEach(this::visitDef_skill);
        CloseTag("skills");

        output.append("\n");

        //
        OpenTag("campeoes");
        ctx.def_champion().forEach(this::visitDef_champion);
        CloseTag("campeoes");

        CloseTag("root");

        return null;
    }

    @Override
    public Object visitDef_stat(Def_statContext ctx) {
        WriteTag("statdef", ctx.IDENT().getText());
        return null;
    }
    @Override
    public Object visitDef_class(Def_classContext ctx) {
        WriteTag("classdef", ctx.IDENT().getText());
        return null;
    }


    @Override
    public Object visitDef_skill(Def_skillContext ctx) {
        OpenTag("skill");
        WriteTag("name", ctx.IDENT().getText());

        if (ctx.skill_reqs() != null) visitSkill_reqs(ctx.skill_reqs());

        visitDef_skillinfo(ctx.def_skillinfo());

        OpenTag("acoes");
        ctx.acoes().forEach(this::visitAcoes);
        CloseTag("acoes");

        CloseTag("skill");
        return null;
    }

    @Override
    public Object visitSkill_reqs(Skill_reqsContext ctx) {
        OpenTag("require");
        WriteTag("classe", ctx.class_ref().IDENT().getText());

        OpenTag("stats");
        for (Stat_tupleContext statTuple : ctx.stat_tuple())
            WriteTag(statTuple.IDENT().getText(), statTuple.NUM_INT().getText());
        CloseTag("stats");

        CloseTag("require");
        return null;
    }

    @Override
    public Object visitDef_skillinfo(Def_skillinfoContext ctx) {
        WriteTag("alcance", ctx.ALCANCE().getText());
        WriteTag("custo", ctx.NUM_INT(0).getText());
        WriteTag("cooldown", ctx.NUM_INT(1).getText());
        return null;
    }

    @Override
    public Object visitAcoes(AcoesContext ctx)
    {
        if      (ctx.acao_dano()     != null) visitAcao_dano(ctx.acao_dano());
        else if (ctx.acao_curar()    != null) visitAcao_curar(ctx.acao_curar());
        else if (ctx.acao_empurrar() != null) visitAcao_empurrar(ctx.acao_empurrar());
        else if (ctx.acao_mover()    != null) visitAcao_mover(ctx.acao_mover());
        else if (ctx.acao_status()   != null) visitAcao_status(ctx.acao_status());
        return null;
    }
    @Override
    public Object visitAcao_dano(Acao_danoContext ctx) {
        OpenTag("dano");
        WriteTag("qtd", ctx.NUM_INT().getText());
        WriteTag("alcance", ctx.ALCANCE().getText());
        CloseTag("dano");
        return null;
    }
    @Override
    public Object visitAcao_curar(Acao_curarContext ctx) {
        OpenTag("cura");
        WriteTag("qtd", ctx.NUM_INT().getText());
        WriteTag("alcance", ctx.ALCANCE().getText());
        CloseTag("cura");
        return null;
    }
    @Override
    public Object visitAcao_empurrar(Acao_empurrarContext ctx) {
        OpenTag("empurra");
        WriteTag("qtd", ctx.NUM_INT().getText());
        WriteTag("direcao", ctx.DIRECOES().getText());
        CloseTag("empurra");
        return null;
    }
    @Override
    public Object visitAcao_mover(Acao_moverContext ctx) {
        OpenTag("mover");
        WriteTag("qtd", ctx.NUM_INT().getText());
        WriteTag("direcao", ctx.DIRECOES().getText());
        CloseTag("mover");
        return null;
    }
    @Override
    public Object visitAcao_status(Acao_statusContext ctx) {
        OpenTag("status");
        WriteTag("qtd", ctx.NUM_INT().getText());
        WriteTag("statusAplicado", ctx.TIPO_STATUS().getText());
        CloseTag("status");
        return null;
    }

    @Override
    public Object visitDef_champion(Def_championContext ctx) {
        OpenTag("campeao");
        WriteTag("name", ctx.IDENT().getText());
        WriteTag("classe", ctx.class_ref().IDENT().getText());

        OpenTag("champStats");
        for (Stat_tupleContext statTuple : ctx.stat_tuple())
            WriteTag(statTuple.IDENT().getText(), statTuple.NUM_INT().getText());
        CloseTag("champStats");

        OpenTag("champSkills");
        for (TerminalNode skillIdent : ctx.championskilldef().IDENT())
            WriteTag("skill", skillIdent.getText());
        CloseTag("champSkills");

        CloseTag("campeao");
        return null;
    }
}
