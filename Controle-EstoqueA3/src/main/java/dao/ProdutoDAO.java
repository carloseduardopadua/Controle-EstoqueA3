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

/**
 * Classe responsável pelo acesso ao banco de dados da entidade Produto.
 *
 * <p>Realiza operações de cadastro, listagem e geração de relatórios
 * relacionados aos produtos do estoque.</p>
 *
 * @author Equipe A3
 * @version 1.0
 * @since 2026
 */
public class ProdutoDAO {

    /** Conexão com o banco de dados. */
    Connection conn;

    /**
     * Construtor que inicializa a conexão com o banco de dados.
     */
    public ProdutoDAO() {
        conn = Conexao.conectar();
    }

    /**
     * Cadastra um novo produto no banco de dados.
     *
     * @param produto objeto {@link Produto} com os dados a serem inseridos
     */
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

    /**
     * Retorna uma lista com todos os produtos cadastrados no banco de dados.
     *
     * @return {@link ArrayList} de {@link Produto} com todos os registros encontrados
     */
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

    /**
     * Retorna a lista de preços de todos os produtos em ordem alfabética.
     *
     * <p>Utilizada no relatório de lista de preços.</p>
     *
     * @return lista de vetores contendo nome, preço unitário, unidade e categoria de cada produto
     */
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

    /**
     * Retorna o balanço físico e financeiro de todos os produtos em ordem alfabética.
     *
     * <p>Inclui quantidade em estoque, preço unitário e valor total por produto.</p>
     *
     * @return lista de vetores contendo nome, quantidade, preço unitário e valor total de cada produto
     */
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

    /**
     * Retorna os produtos cuja quantidade em estoque está abaixo da quantidade mínima.
     *
     * <p>Utilizada no relatório de estoque crítico.</p>
     *
     * @return lista de vetores contendo nome, quantidade mínima e quantidade atual de cada produto
     */
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

    /**
     * Retorna a quantidade de produtos distintos agrupados por categoria.
     *
     * <p>Utilizada no relatório de produtos por categoria.</p>
     *
     * @return lista de vetores contendo o nome da categoria e a quantidade de produtos
     */
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

    /**
     * Retorna o produto com maior total de entradas e o produto com maior total de saídas.
     *
     * <p>Utilizada no relatório de maior movimentação do estoque.</p>
     *
     * @return lista de vetores contendo tipo (Entrada/Saída), nome do produto e quantidade total
     */
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

    /**
     * Registra uma movimentação de estoque para um produto.
     *
     * @param nomeProduto   nome do produto a ser movimentado
     * @param tipoTransacao tipo da movimentação: "Entrada" ou "Saída"
     * @param quantidade    quantidade a ser movimentada
     * @return {@code true} se a operação foi realizada com sucesso, {@code false} caso contrário
     * @throws UnsupportedOperationException método ainda não implementado
     */
    public boolean registrarMovimentacao(String nomeProduto, String tipoTransacao, int quantidade) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Retorna uma lista de produtos cadastrados.
     *
     * @return lista de {@link Produto}
     * @throws UnsupportedOperationException método ainda não implementado
     */
    public List<Produto> listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void alterarProduto(Produto produto){

    String sql = """
                 UPDATE produto
                 SET
                 nome = ?,
                 preco_unitario = ?,
                 unidade = ?,
                 quantidade_estoque = ?,
                 quantidade_minima = ?,
                 quantidade_maxima = ?,
                 categoria = ?
                 WHERE id = ?
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

        ps.setInt(8, produto.getId());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto alterado!");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());

    }
}
    public void excluirProduto(int id){

    String sql = "DELETE FROM produto WHERE id = ?";

    try {

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto excluído!");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());

    }
}
}