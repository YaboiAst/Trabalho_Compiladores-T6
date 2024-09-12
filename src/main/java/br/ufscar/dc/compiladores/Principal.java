package br.ufscar.dc.compiladores;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.PrintWriter;

import static br.ufscar.dc.compiladores.DedUtils.*;

/*
 * Vitor Gabriel Orsin - 801575
 *
 * ----- T6 -------------------
 * Compilador que interpreta uma linguagem criada para gerar dados de personagens
 *  para o jogo Dungeons And Devs (GAMSo)
 *
 * Realiza a análise semântica de um arquivo de entrada e escreve uma saída em XML
 */

public class Principal {

    public static void main(String[] args) {
        // Terminar execução se os argumentos estiverem incorretos
        if(args.length != 2){
            System.out.println("Argumentos inválidos, use: " +
                    "java -jar target/alguma-lexico-1.0-SNAPSHOT-jar-with-dependencies.jar"
                    + "<Caminho do arquivo de entrada>"
                    + "<Caminho do arquivo de saída>");
            return;
        }

        String input = args[0];     // Caminho com o arquivo contendo o código em LA
        String output = args[1];    // Caminho com o arquivo que armazenará os tokens

        try (PrintWriter pw = new PrintWriter(output)) {
            CharStream cs = CharStreams.fromFileName(input); // Leitura do arquivo de entrada

            // Gera Tokens
            DeDLexer lex = new DeDLexer(cs); // instancia o analisador léxico
            CommonTokenStream tokens = new CommonTokenStream(lex); // Gera a sequência de tokens

            // Analise sinstatica
            DeDParser par = new DeDParser(tokens); // instancia o parser
            DeDParser.ProgramaContext tree = par.programa();

            // Analise semantica
            DedSemantico semantico = new DedSemantico();
            semantico.visitPrograma(tree);

            if (!errosSemanticos.isEmpty())
            {
                for (String error : errosSemanticos)
                {
                    pw.println(error);
                }

                pw.close();
                return;
            }
            pw.close();

            DedConvert converter = new DedConvert();
            converter.visitPrograma(tree);

            PrintWriter xmlOut = new PrintWriter(args[1]);
            xmlOut.print(converter.output.toString());
            xmlOut.close();
        }
        catch (IOException ex) {
            System.err.println("Arquivo não encontrado: "+ args[1]);
        }
    }
}