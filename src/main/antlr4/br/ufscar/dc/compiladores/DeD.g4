grammar DeD;

// ESC
fragment
ESC_SEQ	: '\\\'' | '\\n';

// DIRECOES
DIRECOES: DIR_ABS | DIR_REL;
DIR_ABS : 'norte' | 'sul' | 'leste' | 'oeste';
DIR_REL : 'cima' | 'baixo' | 'direita' | 'esqueda';

// ALCANCE
ALCANCE : ALCANCE_PONTO | ALCANCE_LINHA | ALCANCE_CRUZ | ALCANCE_RAIO;
ALCANCE_PONTO : 'PONTO()';
ALCANCE_LINHA : 'LINHA(' DIRECOES ',' NUM_INT ')';
ALCANCE_CRUZ  : 'CRUZ(' NUM_INT ')';
ALCANCE_RAIO  : 'RAIO(' NUM_INT ')';

// STATUS
TIPO_STATUS : 'queimando' | 'envenenado' | 'confuso' | 'paralisado';

// Tipos de números
NUM_INT	 : ('0'..'9')+ ;
// Identificadores
IDENT : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

// Espaços em branco
WS : ( ' '| '\t' | '\r' | '\n' ) {skip();} ;

// Comentário
COMENTARIO : '{' ~('\n'|'\r'|'{'|'}' )* '}' '\r'? '\n'? {skip();} ;

// Cadeia
CADEIA :  '"' (~( '"'|'\\' |'\n'|'\r')| ESC_SEQ)* '"' ;

// Erros de comentário / cadeia sem fechamento
COMENTARIO_ABERTO: '{' ~('\n'|'\r'|'{'|'}' )* '\r'? '\n'? ;
CADEIA_ABERTA: '"' (~( '"'|'\\' |'\n'|'\r')| ESC_SEQ)* '\r'? '\n'? ;

// RAIZ
programa: 'DECLARE:' (decl_stats_classes)* 'END'
          'SKILLS'  (def_skill)* 'END'
          'CAMPEOES' (def_champion)* 'END'
          EOF;

// STATS E CLASSES -- DECLARACAO
decl_stats_classes: def_stat | def_class;
def_stat: 'statdef' IDENT ';';
def_class: 'classdef' IDENT ';';

// STATS E CLASSES -- REFERENCIANDO
stat_tuple : IDENT ':' NUM_INT;
class_ref : IDENT;

// SKILL
def_skill: 'skilldef' IDENT
               skill_reqs?
               def_skillinfo
               (acoes)+
           'endskilldef';
skill_reqs : 'Require:'
                'classe:' class_ref
                'stats:'
                    stat_tuple*
                'endstats'
             'endRequire';
def_skillinfo: 'alcance:' ALCANCE
               'custo:' NUM_INT
               'cooldown:' NUM_INT;
    // AÇOES
acoes: acao_dano | acao_curar | acao_mover | acao_empurrar | acao_status;
acao_dano     : 'DANO(' NUM_INT ',' ALCANCE  ')';
acao_curar    : 'CURA(' NUM_INT ',' ALCANCE  ')';
acao_mover    : 'MOVE(' NUM_INT ',' DIRECOES ')';
acao_empurrar : 'EMPURRA(' NUM_INT ',' DIRECOES ')';
acao_status   : 'STATUS('  NUM_INT ',' TIPO_STATUS ')';

// CAMPEOES
def_champion: 'champdef' IDENT ':'
                'classe:' class_ref
                'stats:' (stat_tuple)* 'endstats'
                'skills:' championskilldef 'endskills'
              'endchampdef';
championskilldef: 'skill:' IDENT
                  'skill:' IDENT
                  'skill:' IDENT
                  'skill:' IDENT;

// Definição final de Erro
ERRO: . ;