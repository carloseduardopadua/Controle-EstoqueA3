package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import modelo.Produto;
import util.Conexao;

public class ProdutoDAO {

    Connection conn;

    public ProdutoDAO() {
        // Usa o método padrão que você já criou na sua classe Conexao
        conn = Conexao.conectar();
    }

    // Método clássico de cadastro de produto (Mantido do seu original)
    public void cadastrarProduto(Produto produto) {
        String sql = """
                     INSERT INTO produto
                     (
                     nome,
                     preco_unitario,
                     unidade,
                     quantidade_estoque,
                     quantidade_minima,
                     quantidade_maxima,
                     categoria
                     )
                     VALUES (?, ?, ?, ?, ?, ?, ?)
                     """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPrecoUnitario());
            ps.setString(3, produto.getUnidade());
            ps.setInt(4, produto.getQuantidadeEstoque());
            ps.setInt(5, produto.getQuantidadeMinima());
            ps.setInt(6, produto.getQuantidadeMaxima());
            ps.setString(7, produto.getCategoria());

            ps.execute();
            ps.close();

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    // Método clássico de listagem (Mantido do seu original)
    public ArrayList<Produto> listarProdutos() {
        ArrayList<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();

                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPrecoUnitario(rs.getDouble("preco_unitario"));
                p.setUnidade(rs.getString("unidade"));
                p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                p.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                p.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                p.setCategoria(rs.getString("categoria"));

                lista.add(p);
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }

    // =========================================================================
    // METODOS ADICIONADOS PARA OS RELATÓRIOS DA TELA (LINKADOS COM OS BOTÕES)
    // =========================================================================

    // 1. Relatório: Lista de Preços (Chamado pelos botões "Listas de Preço" e "Atualizar")
    public List<Vector<Object>> obterListaDePrecos() {
        List<Vector<Object>> lista = new ArrayList<>();
        String sql = "SELECT nome, preco_unitario, unidade, categoria FROM produto ORDER BY nome ASC";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("nome"));
                linha.add(rs.getDouble("preco_unitario"));
                linha.add(rs.getString("unidade"));
                linha.add(rs.getString("categoria"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na lista de preços no DAO: " + e.getMessage());
        }
        return lista;
    }

    // 2. Relatório: Balanço Físico/Financeiro (Chamado pelo botão "Balanço Estoque")
    public List<Vector<Object>> obterBalancoFisicoFinanceiro() {
        List<Vector<Object>> lista = new ArrayList<>();
        String sql = "SELECT nome, quantidade_estoque, preco_unitario, (quantidade_estoque * preco_unitario) AS total FROM produto ORDER BY nome ASC";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("nome"));
                linha.add(rs.getInt("quantidade_estoque"));
                linha.add(rs.getDouble("preco_unitario"));
                linha.add(rs.getDouble("total"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar balanço no DAO: " + e.getMessage());
        }
        return lista;
    }

    // 3. Relatório: Estoque Crítico (Chamado pelo botão "Estoque baixo")
    public List<Vector<Object>> obterProdutosAbaixoMinimo() {
        List<Vector<Object>> lista = new ArrayList<>();
        String sql = "SELECT nome, quantidade_minima, quantidade_estoque FROM produto WHERE quantidade_estoque < quantidade_minima";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("nome"));
                linha.add(rs.getInt("quantidade_minima"));
                linha.add(rs.getInt("quantidade_estoque"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar estoque baixo no DAO: " + e.getMessage());
        }
        return lista;
    }

    // 4. Relatório: Produtos por Categoria (Chamado pelo botão "Produtos por Categoria")
    public List<Vector<Object>> obterQtdProdutosPorCategoria() {
        List<Vector<Object>> lista = new ArrayList<>();
        String sql = "SELECT categoria, COUNT(id) AS total_produtos FROM produto GROUP BY categoria";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("categoria"));
                linha.add(rs.getInt("total_produtos"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro no relatório por categoria no DAO: " + e.getMessage());
        }
        return lista;
    }

    // 5. Relatório: Maior Entrada e Saída (Chamado pelo botão "Maior Movimentação")
    public List<Vector<Object>> obterMaioresMovimentacoes() {
        List<Vector<Object>> lista = new ArrayList<>();
        
        String sqlEntrada = "SELECT p.nome, SUM(m.quantidade) AS total, 'Entrada' AS tipo " +
                            "FROM movimentacao m " +
                            "INNER JOIN produto p ON m.id_produto = p.id " +
                            "WHERE m.tipo = 'Entrada' " +
                            "GROUP BY p.id, p.nome " +
                            "ORDER BY total DESC LIMIT 1";
                            
        String sqlSaida = "SELECT p.nome, SUM(m.quantidade) AS total, 'Saída' AS tipo " +
                          "FROM movimentacao m " +
                          "INNER JOIN produto p ON m.id_produto = p.id " +
                          "WHERE m.tipo = 'Saída' " +
                          "GROUP BY p.id, p.nome " +
                          "ORDER BY total DESC LIMIT 1";
        
        // Executa busca de Entrada
        try (PreparedStatement stmt = conn.prepareStatement(sqlEntrada); 
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("tipo"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getInt("total"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Sem dados para entradas no histórico: " + e.getMessage());
        }
        
        // Executa busca de Saída
        try (PreparedStatement stmt = conn.prepareStatement(sqlSaida); 
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("tipo"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getInt("total"));
                lista.add(linha);
            }
        } catch (SQLException e) {
            System.out.println("Sem dados para saídas no histórico: " + e.getMessage());
        }
        
        return lista;
    }

    public boolean registrarMovimentacao(String nomeProduto, String tipoTransacao, int quantidade) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Produto> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}