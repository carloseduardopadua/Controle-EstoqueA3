package modelo;

public class Produto {
    private int id;
    private String nome;
    private double precoUnitario;
    private String unidade;
    private int qtdEstoque;
    private int qtdMinima;
    private int qtdMaxima;
    private Categoria categoria;

    // =======================================================
    // 
    // =======================================================
    
    // 
    public int getQuantidadeEstoque() { return this.qtdEstoque; }
    public void setQuantidadeEstoque(int qtdEstoque) { this.qtdEstoque = qtdEstoque; }

    // 
    public int getQuantidadeMinima() { return this.qtdMinima; }
    public void setQuantidadeMinima(int qtdMinima) { this.qtdMinima = qtdMinima; }

    // 
    public int getQuantidadeMaxima() { return this.qtdMaxima; }
    public void setQuantidadeMaxima(int qtdMaxima) { this.qtdMaxima = qtdMaxima; }

    // 
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    /**
     * 
     * @param nomeCategoria
     */
    public void setCategoria(String nomeCategoria) {
        if (this.categoria == null) {
            this.categoria = new Categoria();
        }
        this.categoria.setNome(nomeCategoria);
    }

    // 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
}