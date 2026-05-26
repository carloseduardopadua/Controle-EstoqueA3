package modelo;
import java.util.Date;

public class Movimentacao {
    private int id;
    private Produto produto; // Relacionamento com a classe Produto
    private Date dataMovimentacao;
    private int quantidade;
    private String tipo; // "Entrada" ou "Saída"

    public Movimentacao() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public java.sql.Date getDataMovimentacao() { return (java.sql.Date) dataMovimentacao; }
    public void setDataMovimentacao(Date dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}