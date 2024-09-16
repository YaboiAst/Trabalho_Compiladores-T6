#!/bin/bash

cd ..
./build.sh

cd CasosDeTeste/

# Caso 1
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso1-Sucesso.txt Saida/out_Caso1.txt

# Caso 2
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso2-ErroStatDef.txt Saida/out_Caso2.txt

# Caso 3
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso3-ErroClassDef.txt Saida/out_Caso3.txt

# Caso 4
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso4-ErroSkillDef.txt Saida/out_Caso4.txt

# Caso 5
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso5-ErroSkillClassReq.txt Saida/out_Caso5.txt

# Caso 6
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso6-ErroSkillStatReq.txt Saida/out_Caso6.txt

# Caso 7
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso7-ErroCampDef.txt Saida/out_Caso7.txt

# Caso 8
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso8-ErroCampClassReq.txt Saida/out_Caso8.txt

# Caso 9
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso9-ErroCampStat.txt Saida/out_Caso9.txt

# Caso 10
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso10-ErroCampSkillReq1.txt Saida/out_Caso10.txt

# Caso 11
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso11-ErroCampSkillReq2.1.txt Saida/out_Caso11.txt

# Caso 12
java -jar ../target/t6-1.0-SNAPSHOT-jar-with-dependencies.jar Entrada/in_Caso12-ErroCampSkillReq2.2.txt Saida/out_Caso12.txt
