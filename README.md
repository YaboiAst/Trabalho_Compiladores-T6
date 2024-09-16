# **Compiladores T6 - Compilador Dungeons And Devs**
* Vitor Gabriel Orsin - 801575

## Sobre a linguagem
A linguagem lida pelo compilador foi desenvolvida para representar a criação dos dados usados pelo jogo Dungeons And Devs (desenvolvido pela Gamso)
Essa linguagem define os componentes principais dos dados:
* Stats
* Classes
* Skills
* Campeões (ou personagens jogáveis)
   
As skills podem ter requisitos que precisam ser atendidos para poderem fazer parte do set de skills de um campeão
Por exemplo, a skill "Bola de fogo" pode estar disponível somente para magos; Ou ainda, a Skill "Dash" só pode ser usada por campeões com velocidade maior que 10

## Pré-requisitos
Para conseguir executar corretamente o programa, verifique a versão do Java e do Maven na sua máquina:
```
java -version
mvn -v
```
**Versão necessária** <br>
Java : 17 <br>
Maven: 3.6.3 <br>

## Como compilar
Com esse repositório clonado em sua máquina, navegue até o diretório do programa e compile usando o Maven:
```
mvn clean
mvn package
```
Ou use o .sh
```
# Permitir execução do script
chmod +x build.sh

./build.sh
```

## Modo de uso
Utilize o java para executar o programa, passando os argumentos:
* O caminho literal até o arquivo contendo o código em LA
* O caminho literal até o arquivo onde o programa escreverá a saída
```
java -jar target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar ~/path/to/input/file.txt ~/path/to/output/file.txt
```

## Rodando os casos de teste
O projeto contém uma pasta com casos de teste que cobrem todos os possíveis erros semânticos. 
Para rodar todos os casos de teste, você pode executar o .sh
```
# Permitir execução do script
chmod +x run_tests.sh

./run_tests.sh
```
