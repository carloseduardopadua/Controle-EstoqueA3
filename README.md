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

### 1. Categoria
- **1.1** — O sistema deve permitir cadastrar, editar, consultar e excluir categorias.
- **1.2** — Cada categoria deve ter nome, tamanho (Pequeno, Médio ou Grande) e embalagem (Lata, Vidro ou Plástico).

### 2. Produto
- **2.1** — O sistema deve permitir cadastrar, editar, consultar e excluir produtos.
- **2.2** — Cada produto deve estar vinculado a uma categoria.
- **2.3** — O sistema deve permitir reajustar o preço de todos os produtos por um percentual informado.

### 3. Movimentação
- **3.1** — O sistema deve registrar entradas e saídas de produtos com data e quantidade.
- **3.2** — O sistema deve atualizar o saldo do produto automaticamente após cada movimentação.
- **3.3** — O sistema deve exibir um aviso quando a quantidade ficar abaixo do mínimo após uma saída.
- **3.4** — O sistema deve exibir um aviso quando a quantidade ultrapassar o máximo após uma entrada.

---

## 4. Requisitos não funcionais

- **4.1** — O sistema deve ser desenvolvido em Java.
- **4.2** — O banco de dados utilizado deve ser o MySQL.
- **4.3** — A interface gráfica deve ser desenvolvida com Java Swing.
- **4.4** — O sistema deve utilizar o padrão de projeto DAO para acesso ao banco de dados.
- **4.5** — O código deve estar organizado em pacotes: entidade, dao, visao e util.
- **4.6** — O sistema deve rodar em ambiente desktop (Windows, Linux ou macOS).
