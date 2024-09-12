package br.ufscar.dc.compiladores;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class DedUtils {

    // ERROR LISTENER ---------------------------------------------------
    public static List<String> errosSemanticos = new ArrayList<>();
    public static void adicionarErroSemantico(Token t, String mensagem){
        int linha = t.getLine();

        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }

    // XML WRITER ---------------------------------------------------
    static StringBuilder output;
    public static void MapStringBuilder(StringBuilder out)
    {
        output = out;
    }

    static int ident = 0;
    public static void OpenTag(String tagName)
    {
        for (int i = 0; i < ident; i++) output.append("   ");

        output.append("<").append(tagName).append(">");
        output.append("\n");
        ident++;
    }
    public static void CloseTag(String tagName)
    {
        ident--;
        for (int i = 0; i < ident; i++) output.append("   ");

        output.append("</").append(tagName).append(">");
        output.append("\n");
    }
    public static void WriteTag(String tagName, String line)
    {
        for (int i = 0; i < ident; i++) output.append("   ");

        output.append("<").append(tagName).append("> ");
        output.append(line);
        output.append(" </").append(tagName).append(">");
        output.append("\n");
    }
}
