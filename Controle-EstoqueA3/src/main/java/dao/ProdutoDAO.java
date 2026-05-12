package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Produto;
import util.Conexao;

public class ProdutoDAO {

    Connection conn;

    public ProdutoDAO() {

        conn = Conexao.conectar();
    }

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

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, produto.getNome());

            ps.setDouble(2,
                    produto.getPrecoUnitario());

            ps.setString(3,
                    produto.getUnidade());

            ps.setInt(4,
                    produto.getQuantidadeEstoque());

            ps.setInt(5,
                    produto.getQuantidadeMinima());

            ps.setInt(6,
                    produto.getQuantidadeMaxima());

            ps.setString(7,
                    produto.getCategoria());

            ps.execute();

            ps.close();

        } catch (Exception e) {

            System.out.println(
                    "Erro ao cadastrar: "
                    + e.getMessage()
            );
        }
    }

    public ArrayList<Produto> listarProdutos() {

        ArrayList<Produto> lista =
                new ArrayList<>();

        String sql = "SELECT * FROM produto";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Produto p = new Produto();

                p.setId(rs.getInt("id"));

                p.setNome(
                        rs.getString("nome")
                );

                p.setPrecoUnitario(
                        rs.getDouble("preco_unitario")
                );

                p.setUnidade(
                        rs.getString("unidade")
                );

                p.setQuantidadeEstoque(
                        rs.getInt("quantidade_estoque")
                );

                p.setQuantidadeMinima(
                        rs.getInt("quantidade_minima")
                );

                p.setQuantidadeMaxima(
                        rs.getInt("quantidade_maxima")
                );

                p.setCategoria(
                        rs.getString("categoria")
                );

                lista.add(p);
            }

        } catch (Exception e) {

            System.out.println(
                    "Erro ao listar: "
                    + e.getMessage()
            );
        }

        return lista;
    }
}