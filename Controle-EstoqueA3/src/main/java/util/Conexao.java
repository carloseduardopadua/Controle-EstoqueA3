package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public static Connection conectar() {

        Connection conn = null;

        try {

            String url =
                    "jdbc:mysql://localhost:3306/controle_estoque";

            String usuario = "root";
            String senha = "";

            conn = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

        } catch (Exception e) {

            System.out.println(
                    "Erro de conexão: "
                    + e.getMessage()
            );
        }

        return conn;
    }
}