# **Compiladores - Analisador Semântico**
* Vitor Gabriel Orsin - 801575

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

## Como usar
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
