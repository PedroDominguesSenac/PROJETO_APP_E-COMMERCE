package projetointegrador;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TelaCadastroClienteController implements Initializable {

    @FXML
    private TextField editNome;
    @FXML
    private TextField editCpf;
    @FXML
    private TextField editTelefone;
    @FXML
    private TextField editEmail;
    @FXML
    private ComboBox<String> editGenero;
    @FXML
    private ComboBox<String> editEstadoCivil;
    @FXML
    private DatePicker editData;
    @FXML
    private TextField editLogadouro;
    @FXML
    private TextField editNumero;
    @FXML
    private TextField editComplemento;
    @FXML
    private TextField editCep;
    @FXML
    private ComboBox<String> editEstado;
    @FXML
    private TextField editCidade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        editGenero.getItems().add("Masculino"); 
        editGenero.getItems().add("Feminino"); 
        editGenero.getItems().add("Não declarar");
        
        editEstadoCivil.getItems().add("Solteiro(a)"); 
        editEstadoCivil.getItems().add("Casado(a)"); 
        editEstadoCivil.getItems().add("Separado(a)");
        editEstadoCivil.getItems().add("Divorciado(a)"); 
        editEstadoCivil.getItems().add("Viúvo(a)");
        
        editEstado.getItems().add("Acre (AC)");
        editEstado.getItems().add("Alagoas (AL)");
        editEstado.getItems().add("Amapá (AP)");
        editEstado.getItems().add("Amazonas (AM)");
        editEstado.getItems().add("Bahia (BA)");
        editEstado.getItems().add("Ceará (CE)");
        editEstado.getItems().add("Distrito Federal (DF)");
        editEstado.getItems().add("Espírito Santo (ES)");
        editEstado.getItems().add("Goiás (GO)");
        editEstado.getItems().add("Maranhão (MA)");
        editEstado.getItems().add("Mato Grosso (MT)");
        editEstado.getItems().add("Mato Grosso do Sul (MS)");
        editEstado.getItems().add("Minas Gerais (MG)");
        editEstado.getItems().add("Pará (PA)");
        editEstado.getItems().add("Paraíba (PB)");
        editEstado.getItems().add("Paraná (PR)");
        editEstado.getItems().add("Pernambuco (PE)");
        editEstado.getItems().add("Piauí (PI)");
        editEstado.getItems().add("Rio de Janeiro (RJ)");
        editEstado.getItems().add("Rio Grande do Norte (RN)");
        editEstado.getItems().add("Rio Grande do Sul (RS)");
        editEstado.getItems().add("Rondônia (RO)");
        editEstado.getItems().add("Roraima (RR)");
        editEstado.getItems().add("Santa Catarina (SC)");
        editEstado.getItems().add("São Paulo (SP)");
        editEstado.getItems().add("Sergipe (SE)");
        editEstado.getItems().add("Tocantins (TO)");
    }   
    
    @FXML
    private void salvar(ActionEvent event) {
        
        if(editNome.getText().isBlank() && editCpf.getText().isBlank() && editTelefone.getText().isBlank() && editEmail.getText().isBlank()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campos obrigatórios");
            alert.setContentText("Preencher campo de nome, cpf, telefone e e-mail!");
            
            alert.showAndWait();
        }
        else{
        String sql = "INSERT INTO cliente(nome, cpf, email, telefone, genero, estado_civil, data_nascimento,"
                + " logadouro, numero, complemento, cep, estado, cidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ps.setString(1, editNome.getText());
            ps.setString(2, editCpf.getText());
            ps.setString(3, editEmail.getText());
            ps.setString(4, editTelefone.getText());
            ps.setString(5, editGenero.getValue());
            ps.setString(6, editEstadoCivil.getValue());
            ps.setDate(7, Date.valueOf(editData.getValue()));
            ps.setString(8, editLogadouro.getText());
            ps.setString(9, editNumero.getText());
            ps.setString(10, editComplemento.getText());
            ps.setString(11, editCep.getText());
            ps.setString(12, editEstado.getValue());
            ps.setString(13, editCidade.getText());
            
            ps.execute();
            
            editNome.clear();
            editCpf.clear();
            editEmail.clear();
            editTelefone.clear();
            editLogadouro.clear();
            editNumero.clear();
            editComplemento.clear();
            editCep.clear();
            editCidade.clear();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        }
    }  
}
