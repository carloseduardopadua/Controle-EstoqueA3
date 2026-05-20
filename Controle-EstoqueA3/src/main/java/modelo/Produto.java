package modelo;

/**
 * Classe que representa um produto do estoque.
 *
 * <p>Armazena as informações de um produto, como nome, preço,
 * unidade de medida, quantidades e categoria.</p>
 *
 * @author Equipe A3
 * @version 1.0
 * @since 2026
 */
public class Produto {

    /** Identificador único do produto. */
    private int id;

    /** Nome do produto. */
    private String nome;

    /** Preço unitário do produto. */
    private double precoUnitario;

    /** Unidade de medida do produto (ex: kg, un, L). */
    private String unidade;

    /** Quantidade atual do produto em estoque. */
    private int quantidadeEstoque;

    /** Quantidade mínima permitida no estoque. */
    private int quantidadeMinima;

    /** Quantidade máxima permitida no estoque. */
    private int quantidadeMaxima;

    /** Categoria à qual o produto pertence. */
    private String categoria;

    /**
     * Construtor padrão sem argumentos.
     */
    public Produto() {
    }

    /**
     * Construtor com todos os atributos do produto.
     *
     * @param id                identificador único do produto
     * @param nome              nome do produto
     * @param precoUnitario     preço unitário do produto
     * @param unidade           unidade de medida do produto
     * @param quantidadeEstoque quantidade atual em estoque
     * @param quantidadeMinima  quantidade mínima permitida no estoque
     * @param quantidadeMaxima  quantidade máxima permitida no estoque
     * @param categoria         categoria do produto
     */
    public Produto(int id, String nome,
            double precoUnitario,
            String unidade,
            int quantidadeEstoque,
            int quantidadeMinima,
            int quantidadeMaxima,
            String categoria) {

        this.id = id;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.unidade = unidade;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
        this.categoria = categoria;
    }

    /**
     * Retorna o identificador do produto.
     *
     * @return id do produto
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do produto.
     *
     * @param id identificador do produto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return nome do produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome nome do produto
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço unitário do produto.
     *
     * @return preço unitário do produto
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /**
     * Define o preço unitário do produto.
     *
     * @param precoUnitario preço unitário do produto
     */
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    /**
     * Retorna a unidade de medida do produto.
     *
     * @return unidade de medida do produto
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Define a unidade de medida do produto.
     *
     * @param unidade unidade de medida do produto
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Retorna a quantidade atual do produto em estoque.
     *
     * @return quantidade em estoque
     */
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    /**
     * Define a quantidade atual do produto em estoque.
     *
     * @param quantidadeEstoque quantidade em estoque
     */
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    /**
     * Retorna a quantidade mínima permitida no estoque.
     *
     * @return quantidade mínima do produto
     */
    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    /**
     * Define a quantidade mínima permitida no estoque.
     *
     * @param quantidadeMinima quantidade mínima do produto
     */
    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    /**
     * Retorna a quantidade máxima permitida no estoque.
     *
     * @return quantidade máxima do produto
     */
    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    /**
     * Define a quantidade máxima permitida no estoque.
     *
     * @param quantidadeMaxima quantidade máxima do produto
     */
    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    /**
     * Retorna a categoria do produto.
     *
     * @return categoria do produto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     *
     * @param categoria categoria do produto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}