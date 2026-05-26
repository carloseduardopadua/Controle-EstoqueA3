## Participantes

1. Carlos Eduardo Bernis de Pádua - 10726111700 - https://github.com/carloseduardopadua
2. Pedro Schlemper Medeiros - 1072616755 - https://github.com/pedrosm0
3. Gustavo Moraes Camisão - 10726112001 - https://github.com/gmcamisao
4. Gabriel Camponogara Dal Cortivo - 1072617343 - https://github.com/gabrielcamponogara

## Sistema desenvolvido em Java com banco de dados em MySql, para contolar o estoque de uma empresa. O sistema permite cadastrar produtos e categorias, registrar entrada e saída do estoque e gerar relatórios.

-----

### Tecnologias

• Java

• MySql

-----

### Funcionalidades

• Categorias: Cadastrar, editar, consultar e excluir categorias.

• Produtos: Casatrar, editar, consultar, excluir e reajustar o preço de todos os produtos por um percentual.

• Movimentação: Registrar entrada ou saída de produtos.

-----

### Relatórios

1. Lista de preços de todos os produtos em ordem alfabética
2. Balanço com quantidade e valor total de cada produto no estoque
3. Produtos que estão abaixo da quantidade mínima
4. Quantidade de produtos por categoria
5. Produto que teve mais entrada e o que tebe mais saída

## Requisitos funcionais

RF001 — Cadastro de Categorias

O sistema deve permitir cadastrar categorias.

RF002 — Edição de Categorias

O sistema deve permitir editar categorias cadastradas.

RF003 — Consulta de Categorias

O sistema deve permitir consultar categorias cadastradas.

RF004 — Exclusão de Categorias

O sistema deve permitir excluir categorias cadastradas.

RF005 — Dados da Categoria

Cada categoria deve possuir:

nome;
tamanho (Pequeno, Médio ou Grande);
embalagem (Lata, Vidro ou Plástico).

RF006 — Cadastro de Produtos

O sistema deve permitir cadastrar produtos.

RF007 — Edição de Produtos

O sistema deve permitir editar produtos cadastrados.

RF008 — Consulta de Produtos

O sistema deve permitir consultar produtos cadastrados.

RF009 — Exclusão de Produtos

O sistema deve permitir excluir produtos cadastrados.

RF010 — Vinculação de Produto

Cada produto deve estar vinculado a uma categoria.

RF011 — Reajuste de Preço

O sistema deve permitir reajustar o preço de todos os produtos com base em um percentual informado.

RF012 — Registro de Movimentações

O sistema deve registrar entradas e saídas de produtos contendo data e quantidade.

RF013 — Atualização Automática de Saldo

O sistema deve atualizar automaticamente o saldo do produto após cada movimentação.

RF014 — Aviso de Quantidade Mínima

O sistema deve exibir um aviso quando a quantidade do produto ficar abaixo do mínimo após uma saída.

RF015 — Aviso de Quantidade Máxima

O sistema deve exibir um aviso quando a quantidade do produto ultrapassar o máximo após uma entrada.

---

## 4. Requisitos não funcionais

RNF001 — Linguagem de Programação

O sistema deve ser desenvolvido em Java.

RNF002 — Banco de Dados

O sistema deve utilizar o MySQL como banco de dados.

RNF003 — Interface Gráfica

A interface gráfica deve ser desenvolvida utilizando Java Swing.

RNF004 — Padrão DAO

O sistema deve utilizar o padrão de projeto DAO para acesso ao banco de dados.

RNF005 — Organização em Pacotes

O código-fonte deve estar organizado nos pacotes:

entidade/modelo;
dao;
visao;
util.
RNF006 — Ambiente Desktop

O sistema deve executar em ambiente desktop nos sistemas operacionais Windows, Linux e macOS.
