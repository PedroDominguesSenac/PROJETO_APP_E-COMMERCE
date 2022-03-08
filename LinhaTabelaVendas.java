
package projetointegrador;

public class LinhaTabelaVendas {
    private int codigo;
    private String nome;
    private String fabricante;
    private double preco;
    private String categoria;
    private int quantidade;
    private double total;

    public LinhaTabelaVendas(int codigo, String nome, String fabricante, double preco, String categoria, int quantidade, double total) {
        this.codigo = codigo;
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.total = total;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
