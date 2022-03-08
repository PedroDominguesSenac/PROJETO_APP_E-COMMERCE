package projetointegrador;

public class LinhaTabelaCadastro {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String logadouro;
    private String numero;
    private String complemento;
    private String telefone;
    private String estado;
    private String cidade;
    private String cep;
    
    public LinhaTabelaCadastro(int id, String nome, String cpf, String email, String logadouro, String numero,
            String complemento, String telefone, String estado, String cidade, String cep) {
        
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.logadouro = logadouro;
        this.numero = numero;
        this.complemento = complemento;
        this.telefone = telefone;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
    }
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    } 

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }  
}
