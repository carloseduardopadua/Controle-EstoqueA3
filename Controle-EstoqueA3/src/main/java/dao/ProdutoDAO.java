package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import modelo.Categoria;
import modelo.Produto;
import util.Conexao;

public class ProdutoDAO {

    // ==========================================
    // MÉTODOS EXIGIDOS PELA TELA DE CADASTRO
    // ==========================================

    public List<Produto> listarProdutos() {
        String sql = "SELECT p.*, c.nome AS categoria_nome, c.tamanho, c.embalagem " +
                     "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id ORDER BY p.nome";
        List<Produto> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql); 
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPrecoUnitario(rs.getDouble("preco_unitario"));
                    p.setUnidade(rs.getString("unidade"));
                    p.setQuantidadeEstoque(rs.getInt("qtd_estoque"));
                    p.setQuantidadeMinima(rs.getInt("qtd_minima"));
                    p.setQuantidadeMaxima(rs.getInt("qtd_maxima"));
                    
                    if (rs.getObject("categoria_id") != null) {
                        Categoria c = new Categoria(
                            rs.getInt("categoria_id"),
                            rs.getString("categoria_nome"),
                            rs.getString("tamanho"),
                            rs.getString("embalagem")
                        );
                        p.setCategoria(c);
                    }
                    lista.add(p);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao listar todos os produtos: " + e.getMessage());
            } finally {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
        return lista;
    }

    public void cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco_unitario, unidade, qtd_estoque, qtd_minima, qtd_maxima) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, produto.getNome());
                stmt.setDouble(2, produto.getPrecoUnitario());
                stmt.setString(3, produto.getUnidade());
                stmt.setInt(4, produto.getQuantidadeEstoque());
                stmt.setInt(5, produto.getQuantidadeMinima());
                stmt.setInt(6, produto.getQuantidadeMaxima());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao inserir produto: " + e.getMessage());
            } finally {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
    }

    public void alterarProduto(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco_unitario = ?, unidade = ?, qtd_estoque = ?, qtd_minima = ?, qtd_maxima = ? WHERE id = ?";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, produto.getNome());
                stmt.setDouble(2, produto.getPrecoUnitario());
                stmt.setString(3, produto.getUnidade());
                stmt.setInt(4, produto.getQuantidadeEstoque());
                stmt.setInt(5, produto.getQuantidadeMinima());
                stmt.setInt(6, produto.getQuantidadeMaxima());
                stmt.setInt(7, produto.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao alterar produto: " + e.getMessage());
            } finally {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
    }

    public void excluirProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erro ao excluir produto: " + e.getMessage());
            } finally {
                try { conn.close(); } catch (SQLException e) {}
            }
        }
    }

    // ==========================================
    // MÉTODOS EXIGIDOS PELA TELA DE MOVIMENTAÇÃO
    // ==========================================

    // Chamado por carregarProdutosNoComboBox()
    public List<Produto> listar() {
        return listarProdutos();
    }

    // Chamado pelo btnSalvar da movimentação
    public boolean registrarMovimentacao(String nomeProduto, String tipoTransacao, int quantidade) {
        Connection conn = Conexao.conectar();
        if (conn == null) return false;

        PreparedStatement stmtBusca = null;
        PreparedStatement stmtAtualiza = null;
        PreparedStatement stmtHistorico = null;
        ResultSet rs = null;

        try {
            // 1. Busca o produto atual para verificar ID e saldo de estoque
            String sqlBusca = "SELECT id, qtd_estoque FROM produto WHERE nome = ?";
            stmtBusca = conn.prepareStatement(sqlBusca);
            stmtBusca.setString(1, nomeProduto);
            rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int idProduto = rs.getInt("id");
                int estoqueAtual = rs.getInt("qtd_estoque");
                int novoEstoque = estoqueAtual;

                // 2. Calcula a nova quantidade em estoque dependendo da transação
                if (tipoTransacao.equalsIgnoreCase("Entrada")) {
                    novoEstoque += quantidade;
                } else if (tipoTransacao.equalsIgnoreCase("Saída")) {
                    if (quantidade > estoqueAtual) {
                        return false; // Saldo insuficiente para saída
                    }
                    novoEstoque -= quantidade;
                }

                // Desliga o commit automático para fazer uma transação segura (ACID)
                conn.setAutoCommit(false);

                // 3. Atualiza o estoque do produto
                String sqlAtualiza = "UPDATE produto SET qtd_estoque = ? WHERE id = ?";
                stmtAtualiza = conn.prepareStatement(sqlAtualiza);
                stmtAtualiza.setInt(1, novoEstoque);
                stmtAtualiza.setInt(2, idProduto);
                stmtAtualiza.executeUpdate();

                // 4. Insere o registro na tabela de histórico de movimentação
                String sqlHistorico = "INSERT INTO movimentacao (produto_id, tipo, quantidade) VALUES (?, ?, ?)";
                stmtHistorico = conn.prepareStatement(sqlHistorico);
                stmtHistorico.setInt(1, idProduto);
                stmtHistorico.setString(2, tipoTransacao);
                stmtHistorico.setInt(3, quantidade);
                stmtHistorico.executeUpdate();

                // Confirma as duas operações juntas
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transacao de movimentacao: " + e.getMessage());
            try { conn.rollback(); } catch (SQLException ex) {}
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtBusca != null) stmtBusca.close();
                if (stmtAtualiza != null) stmtAtualiza.close();
                if (stmtHistorico != null) stmtHistorico.close();
                conn.close();
            } catch (SQLException e) {}
        }
        return false;
    }

    // ==========================================
    // MÉTODOS EXIGIDOS PELA TELA DE RELATÓRIOS
    // ==========================================

    public List<Vector<Object>> obterListaDePrecos() {
        String sql = "SELECT p.nome, p.preco_unitario, p.unidade, c.nome AS categoria " +
                     "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id ORDER BY p.nome ASC";
        return executarRelatorio(sql, 4);
    }

    public List<Vector<Object>> obterBalancoFisicoFinanceiro() {
        String sql = "SELECT p.nome, p.qtd_estoque, p.preco_unitario, (p.qtd_estoque * p.preco_unitario) AS valor_total " +
                     "FROM produto p ORDER BY p.nome ASC";
        return executarRelatorio(sql, 4);
    }

    public List<Vector<Object>> obterQtdProdutosPorCategoria() {
        String sql = "SELECT c.nome AS categoria, COUNT(p.id) AS qtd_produtos " +
                     "FROM categoria c LEFT JOIN produto p ON p.categoria_id = c.id " +
                     "GROUP BY c.nome ORDER BY c.nome ASC";
        return executarRelatorio(sql, 2);
    }

    public List<Vector<Object>> obterMaioresMovimentacoes() {
        String sql = "SELECT 'Mais Entrada' AS tipo, p.nome, SUM(m.quantidade) AS total " +
                     "FROM movimentacao m JOIN produto p ON m.produto_id = p.id " +
                     "WHERE m.tipo = 'Entrada' GROUP BY p.id " +
                     "UNION " +
                     "SELECT 'Mais Saída' AS tipo, p.nome, SUM(m.quantidade) AS total " +
                     "FROM movimentacao m JOIN produto p ON m.produto_id = p.id " +
                     "WHERE m.tipo = 'Saída' GROUP BY p.id " +
                     "ORDER BY total DESC LIMIT 2";
        return executarRelatorio(sql, 3);
    }

    public List<Vector<Object>> obterProdutosAbaixoMinimo() {
        String sql = "SELECT p.nome, p.qtd_minima, p.qtd_estoque " +
                     "FROM produto p WHERE p.qtd_estoque < p.qtd_minima ORDER BY p.nome ASC";
        return executarRelatorio(sql, 3);
    }

    private List<Vector<Object>> executarRelatorio(String sql, int colunas) {
        List<Vector<Object>> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vector<Object> linha = new Vector<>();
                    for (int i = 1; i <= colunas; i++) {
                        linha.add(rs.getObject(i));
                    }
                    lista.add(linha);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao gerar dados do relatório: " + e.getMessage());
            } finally {
                try { conn.close(); } catch (SQLException ex) {}
            }
        }
        return lista;
    }
}