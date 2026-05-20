package principal;

import visao.FrmMenuPrincipal;

/**
 * Classe principal do Sistema de Controle de Estoque.
 *
 * <p>Responsável por inicializar e exibir a tela principal da aplicação.</p>
 *
 * @author Equipe A3
 * @version 1.0
 * @since 2026
 */
public class ControleEstoqueA3 {

    /**
     * Método principal que inicia a aplicação.
     *
     * <p>Cria uma instância do menu principal e torna a janela visível.</p>
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {

        FrmMenuPrincipal tela =
                new FrmMenuPrincipal();

        tela.setVisible(true);
    }
}