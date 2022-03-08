
package projetointegrador;


import java.time.LocalDate;

public class LinhaTabelaRelatorio {
    private int id;
    private String cliente;
    private int codigo;
    private String produto;
    private int quantidade;
    private double valor;
    private int idPedido;
    private LocalDate dataPedido;

    public LinhaTabelaRelatorio(int id, String cliente, int codigo, String produto, int quantidade, double valor, int idPedido, LocalDate dataPedido) {
        this.id = id;
        this.cliente = cliente;
        this.codigo = codigo;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }
    
    

    
   

    
    
}
