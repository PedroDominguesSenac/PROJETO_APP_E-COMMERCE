package projetointegrador;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaPesquisarController implements Initializable {
   
    @FXML
    private TableView<LinhaTabelaCadastro> tableCadastro;
    @FXML
    private TableColumn<LinhaTabelaCadastro, Integer> colunaId;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaNome;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaCpf;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaEmail;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaLogadouro;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaNumero;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaComplemento;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaTelefone;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaEstado;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaCidade;
    @FXML
    private TableColumn<LinhaTabelaCadastro, String> colunaCep;
    
    @FXML
    private TextField editPesquisar;
    @FXML
    private TextField editCpf;
    @FXML
    private TextField editNome;
    @FXML
    private TextField editEmail;
    @FXML
    private TextField editTelefone;
    @FXML
    private TextField editLogadouro;
    @FXML
    private TextField editNumero;
    @FXML
    private TextField editComplemento;
    @FXML
    private TextField editEstado;
    @FXML
    private TextField editCidade;
    @FXML
    private TextField editCep;
    
    private Integer idEdicao = null;
    private boolean estaEditando = false;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colunaLogadouro.setCellValueFactory(new PropertyValueFactory("logadouro"));
        colunaNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        colunaComplemento.setCellValueFactory(new PropertyValueFactory("complemento"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        colunaEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colunaCep.setCellValueFactory(new PropertyValueFactory("cep"));   
    }    

    @FXML
    private void atualizarTabela(ActionEvent event){
        if(editPesquisar.getText().isBlank()){
            listar();   
        }
        else{
            pesquisar();
        }
        
    }  

  
    @FXML
    private void editar(ActionEvent event) {
        LinhaTabelaCadastro linha = tableCadastro.getSelectionModel().getSelectedItem();
        if(linha == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Seleção obrigatória");
            alert.setContentText("Selecinar linha!");
            
            alert.showAndWait();
        }
        if (linha != null) {
            int id = linha.getId();
            
            String sql = "SELECT  * FROM cliente WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    editNome.setText(rs.getString("nome"));
                    editCpf.setText(rs.getString("cpf"));
                    editEmail.setText(rs.getString("email"));
                    editLogadouro.setText(rs.getString("logadouro"));
                    editNumero.setText(rs.getString("numero"));
                    editComplemento.setText(rs.getString("complemento"));
                    editTelefone.setText(rs.getString("telefone"));
                    editEstado.setText(rs.getString("estado"));
                    editCidade.setText(rs.getString("cidade"));
                    editCep.setText(rs.getString("cep"));
                    idEdicao = id;
                    estaEditando = true;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }   
    }

    @FXML
    private void excluir(ActionEvent event) {
        LinhaTabelaCadastro linha = tableCadastro.getSelectionModel().getSelectedItem();
        
        if (linha != null) {
            int id = linha.getId();
            
            String sql = "DELETE FROM cliente WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setInt(1, id);
                
                ps.execute();
            }
            catch(Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de exclusão");
                alert.setContentText("Não é possível apagar esse cliente pois ele ja realizou uma compra");
            
                alert.showAndWait();
                
            }
        }   
    }

    @FXML
    private void salvar(ActionEvent event) {
        if (!estaEditando) {
            String sql = "INSERT INTO cliente(nome, cpf, email, logadouro, numero, complemento,"
                + " telefone, estado, cidade, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setString(1, editNome.getText());
                ps.setString(2, editCpf.getText());
                ps.setString(3, editEmail.getText());
                ps.setString(4, editLogadouro.getText());
                ps.setString(5, editNumero.getText());
                ps.setString(6, editComplemento.getText());
                ps.setString(7, editTelefone.getText());
                ps.setString(8, editEstado.getText());
                ps.setString(9, editCidade.getText());
                ps.setString(10, editCep.getText());
            
                ps.execute();
            }
            catch(Exception e) {
            e.printStackTrace();
            }
        }
        else {
            String sql = "UPDATE cliente SET nome = ?, cpf = ?, email = ?, logadouro = ?, numero = ?, "
                    + "complemento = ?, telefone = ?, estado = ?, cidade = ?, cep = ? WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setString(1, editNome.getText());
                ps.setString(2, editCpf.getText());
                ps.setString(3, editEmail.getText());
                ps.setString(4, editLogadouro.getText());
                ps.setString(5, editNumero.getText());
                ps.setString(6, editComplemento.getText());
                ps.setString(7, editTelefone.getText());
                ps.setString(8, editEstado.getText());
                ps.setString(9, editCidade.getText());
                ps.setString(10, editCep.getText());
                ps.setInt(11, idEdicao);
                
                ps.execute();
                
                estaEditando = false;
                idEdicao = null;
                
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        editNome.clear();
        editCpf.clear();
        editEmail.clear();
        editLogadouro.clear();
        editNumero.clear();
        editComplemento.clear();
        editTelefone.clear();
        editEstado.clear();
        editCidade.clear();
        editCep.clear();
    }
    
    private void listar() {
        tableCadastro.getItems().clear();
     
        String sql = "SELECT * FROM cliente";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                String logadouro = rs.getString("logadouro");
                String numero = rs.getString("numero");
                String complemento = rs.getString("complemento");
                String telefone = rs.getString("telefone");
                String estado = rs.getString("estado");
                String cidade = rs.getString("cidade");
                String cep = rs.getString("cep");
                
                LinhaTabelaCadastro linha = new LinhaTabelaCadastro(id, nome, cpf, email, logadouro, 
                        numero, complemento, telefone, estado, cidade, cep);
                tableCadastro.getItems().add(linha);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void pesquisar() {
        tableCadastro.getItems().clear();
        
        String sql = "SELECT * FROM cliente WHERE nome like ? OR cpf like ?";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ps.setString(1, editPesquisar.getText());
            ps.setString(2, editPesquisar.getText()); 
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                
                LinhaTabelaCadastro linha = new LinhaTabelaCadastro(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), rs.getString("logadouro"), rs.getString("numero"), rs.getString("complemento"), rs.getString("telefone"), rs.getString("estado"), rs.getString("cidade"), rs.getString("cep"));
                
                tableCadastro.getItems().add(linha);                
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltarTelaCadastro(ActionEvent event) {
    }
}