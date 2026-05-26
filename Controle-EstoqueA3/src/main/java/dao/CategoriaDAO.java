package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
import util.Conexao;

public class CategoriaDAO {

    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (nome, tamanho, embalagem) VALUES (?, ?, ?)";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());
                stmt.setString(2, categoria.getTamanho());
                stmt.setString(3, categoria.getEmbalagem());
                stmt.executeUpdate();
            } finally {
                conn.close();
            }
        }
    }

    public void alterar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE id = ?";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());
                stmt.setString(2, categoria.getTamanho());
                stmt.setString(3, categoria.getEmbalagem());
                stmt.setInt(4, categoria.getId());
                stmt.executeUpdate();
            } finally {
                conn.close();
            }
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id = ?";
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } finally {
                conn.close();
            }
        }
    }

    public List<Categoria> listarTodas() throws SQLException {
        String sql = "SELECT * FROM categoria ORDER BY nome";
        List<Categoria> lista = new ArrayList<>();
        Connection conn = Conexao.conectar();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql); 
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setTamanho(rs.getString("tamanho"));
                    c.setEmbalagem(rs.getString("embalagem"));
                    lista.add(c);
                }
            } finally {
                conn.close();
            }
        }
        return lista;
    }

    /**
     * Método gerado automaticamente pela interface. 
     * Agora ele reaproveita o 'listarTodas()' para retornar os dados corretamente.
     * @return 
     */
   public Iterable<Categoria> listarCategorias() {
        try {
            return listarTodas();
        } catch (SQLException e) {
            // ISSO VAI MOSTRAR O ERRO REAL EM UMA JANELA NA TELA
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Erro de Conexão/SQL: " + e.getMessage(), 
                "Erro Crítico", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            
            System.out.println("Erro ao listar categorias na interface: " + e.getMessage());
            return new ArrayList<>(); 
        }
   }}