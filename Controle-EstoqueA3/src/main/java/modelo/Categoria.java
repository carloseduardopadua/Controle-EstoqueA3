package modelo;

public class Categoria {
    private int id;
    private String nome;
    private String tamanho; // Pequeno, Médio, Grande
    private String embalagem; // Lata, Vidro, Plástico

    public Categoria() {}

    public Categoria(int id, String nome, String tamanho, String embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setString(String nome) { this.nome = nome; } // Corrigido para setNome
    public void setNome(String nome) { this.nome = nome; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    public String getEmbalagem() { return embalagem; }
    public void setEmbalagem(String embalagem) { this.embalagem = embalagem; }
    
    @Override
    public String toString() {
        return this.nome; // Facilita colocar a categoria em ComboBox na tela
    }
}