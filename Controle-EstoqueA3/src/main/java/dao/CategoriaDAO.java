/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Categoria;
import util.Conexao;

/**
 *
 * @author RTX4060
 */
public class CategoriaDAO {
    Connection conn;

    public CategoriaDAO() {
        conn = Conexao.conectar();
    }

    public void cadastrarCategoria(Categoria c){

        String sql = """
                     INSERT INTO categoria
                     (nome, tamanho, embalagem)
                     VALUES (?, ?, ?)
                     """;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getNome());
            ps.setString(2, c.getTamanho());
            ps.setString(3, c.getEmbalagem());

            ps.execute();
            ps.close();

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());

        }
    }

    public ArrayList<Categoria> listarCategorias(){

        ArrayList<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Categoria c = new Categoria();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTamanho(rs.getString("tamanho"));
                c.setEmbalagem(rs.getString("embalagem"));

                lista.add(c);

            }

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());

        }

        return lista;
    }
}

