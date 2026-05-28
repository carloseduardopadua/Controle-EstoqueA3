package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe utilitária responsável por gerenciar a conexão com o banco de dados.
 *
 * <p>Utiliza o driver JDBC para estabelecer a conexão com o MySQL.</p>
 *
 * @author Equipe A3
 * @version 1.0
 * @since 2026
 */
public class Conexao {

    /** URL de conexão com o banco de dados MySQL. */
    private static final String URL = "jdbc:mysql://localhost:3306/controleestoque";

    /** Usuário do banco de dados. */
    private static final String USUARIO = "user_estoque";

    /** Senha do banco de dados. */
    private static final String SENHA = "senha_grupo123";

    /**
     * Estabelece e retorna uma conexão com o banco de dados.
     *
     * <p>Em caso de falha na conexão, exibe a mensagem de erro no console
     * e retorna {@code null}.</p>
     *
     * @return objeto {@link Connection} com a conexão ativa,
     *         ou {@code null} se a conexão falhar
     */
    public static Connection conectar() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA
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