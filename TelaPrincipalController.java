package projetointegrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TelaPrincipalController implements Initializable {

    @FXML
    private BorderPane container;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void carregarTelaCadastro(ActionEvent event) throws IOException {
        App.setRoot("TelaCadastro", container);
        label1.setText(" ");
        label2.setText(" ");
        label3.setText(" ");
    }

    @FXML
    private void carregarTelaPesquisar(ActionEvent event) throws IOException {
        App.setRoot("TelaPesquisar", container);
        label1.setText(" ");
        label2.setText(" ");
        label3.setText(" ");
    }

    @FXML
    private void carregarTelaRelatorio(ActionEvent event) throws IOException {
        App.setRoot("TelaRelatorio", container);
        label1.setText(" ");
        label2.setText(" ");
        label3.setText(" ");
    }   

    @FXML
    private void carregarTelaProduto(ActionEvent event) throws IOException {
        App.setRoot("TelaProduto", container);
        label1.setText(" ");
        label2.setText(" ");
        label3.setText(" ");
    }

    @FXML
    private void carregarTelaVendas(ActionEvent event) throws IOException {
        App.setRoot("TelaVenda", container);
        label1.setText(" ");
        label2.setText(" ");
        label3.setText(" ");
    }
}
