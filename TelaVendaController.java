
package projetointegrador;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaVendaController implements Initializable {

    @FXML
    private TextField inputCodprod;
    @FXML
    private TextField inputQuantidade;
    @FXML
    private TextField inputCPF;
    @FXML
    private TableColumn<LinhaTabelaVendas, Integer> colunaCodigo;
    @FXML
    private TableColumn<LinhaTabelaVendas, String> colunaNome;
    @FXML
    private TableColumn<LinhaTabelaVendas, String> colunaFabricante;
    @FXML
    private TableColumn<LinhaTabelaVendas, Double> colunaPreco;
    @FXML
    private TableColumn<LinhaTabelaVendas, String> colunaCategoria;
    @FXML
    private TableColumn<LinhaTabelaVendas, Integer> colunaQuantidade;
    @FXML
    private TableColumn<LinhaTabelaVendas, Double> colunaTotal;
    @FXML
    private TextField inputDinheiro;
    @FXML
    private TableView<LinhaTabelaVendas> TableVendas;
    
    private int idCliente;
    @FXML
    private Label nomeCliente;
    @FXML
    private DatePicker dataCompra;
    @FXML
    private TextField valorFinal;
    
    private double total2 = 0;
    @FXML
    private ComboBox<String> pagBox;
    @FXML
    private TextField troco;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colunaFabricante.setCellValueFactory(new PropertyValueFactory("fabricante"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory("total"));
        
        pagBox.getItems().add("Cartão de Crédito");
        pagBox.getItems().add("Cartão de Débito");
        pagBox.getItems().add("Dinheiro");
        
    }    

    @FXML
    private void adicionar(ActionEvent event) {
//        TableVendas.getItems().clear();

        if(inputCodprod.getText().isBlank() && inputCPF.getText().isBlank() && inputQuantidade.getText().isBlank() ){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campos Obrigatórios");
            alert.setContentText("Campo de quantidade, código do produto e cpf do cliente são obrigatórios!");
            alert.showAndWait();
        }
        else{
        
            String sql = "SELECT * FROM produto WHERE id like ?";

            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(inputCodprod.getText()));

                int quantidade = Integer.parseInt(inputQuantidade.getText());

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int codigo = rs.getInt("id");
                    String nome = rs.getString("nome_produto");
                    String fabricante = rs.getString("fabricante");
                    Double preco = rs.getDouble("preco");
                    String categoria = rs.getString("categoria");
                    int estoque = rs.getInt("estoque");
                    double total = quantidade * preco;

                    int validacao = estoque - quantidade;
                    int sobra = quantidade - estoque;
                    int resto = quantidade - sobra;
                    
                    if(validacao <= 0 || estoque == 0){
                         Alert alert = new Alert(AlertType.ERROR);
                         if(estoque == 0){
                             alert.setTitle("Falta de estoque");
                        alert.setContentText("Sem estoque!");

                        alert.showAndWait();
                         }
                         else{
                        alert.setTitle("Falta de estoque");
                        alert.setContentText("Apenas " + resto + " unidades restando!");
                        alert.showAndWait();
                         }
                    }
                    else{
                    LinhaTabelaVendas linha = new LinhaTabelaVendas(codigo, nome, fabricante, preco, categoria, quantidade, total );
                    TableVendas.getItems().add(linha);     
                    total2 = total2 + total;
                    valorFinal.setText(String.valueOf(total2));
                    }
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
            inputCodprod.clear();
            inputQuantidade.clear();
        }
    }
    

    @FXML
    private void selecionarCliente(ActionEvent event) {
        
        if(inputCPF.getText().isBlank()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campo obrigatório");
            alert.setContentText("Campo do cpf do cliente obrigatório");
            alert.showAndWait();
        }
        else{
        
        String sql = "SELECT TOP 1 * FROM cliente WHERE cpf = ?";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ps.setString(1, inputCPF.getText());


            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int codigo = rs.getInt("id");
                String nome = rs.getString("nome");
                nomeCliente.setText(nome);
                idCliente = codigo;
            }   
        }
        catch(Exception e){
            e.printStackTrace();
        }
        }
    }

    @FXML
    private void confirmarCompra(ActionEvent event) {
        
        if(inputCodprod.getText().isBlank() && inputCPF.getText().isBlank() && inputQuantidade.getText().isBlank() ){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campos Obrigatórios");
            alert.setContentText("Campo de quantidade, código do produto e cpf do cliente são obrigatórios!");
            alert.showAndWait();
        }
        else{
        String sql = "INSERT INTO ordem (id_cliente, dataPedido) VALUES (?, ?) ";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, idCliente);
            ps.setDate(2, Date.valueOf(dataCompra.getValue()));
            
            
            ps.execute();
            
            ResultSet rsKeys = ps.getGeneratedKeys();
            
            if(rsKeys.next()){
                int idOrdem = rsKeys.getByte(1);
                registrarCompras(idOrdem);
                
            }
            
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
        
        double troco1 = Double.parseDouble(inputDinheiro.getText()) - Double.parseDouble(valorFinal.getText());
        troco.setText(String.valueOf(troco1));
        
        inputCPF.clear();
        inputCodprod.clear();
        inputQuantidade.clear();
        valorFinal.clear();
        TableVendas.getItems().clear();
        nomeCliente.setText("Nome cliente");
        idCliente = 0;
        }
    }
    
    private void registrarCompras(int idOrdem){
        for(int i = 0; i < TableVendas.getItems().size(); i++){
            String sql = "INSERT INTO ordem_pedido (id_produto, id_ordem, quantidade, valor) VALUES (?, ?, ?, ?)";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)){
                ps.setInt(1, TableVendas.getItems().get(i).getCodigo());
                ps.setInt(2, idOrdem);
                ps.setInt(3, TableVendas.getItems().get(i).getQuantidade());
                ps.setDouble(4, TableVendas.getItems().get(i).getTotal());
                
                ps.execute();
            }
        
            catch(Exception e){
                e.printStackTrace();
            }
            
            String sql2 = "UPDATE produto SET estoque = estoque - ? WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql2)){
                ps.setInt(1, TableVendas.getItems().get(i).getQuantidade());
                ps.setInt(2, TableVendas.getItems().get(i).getCodigo());
                
                ps.execute();
            }
        
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
    }

    @FXML
    private void cancelarCompra(ActionEvent event) {
        TableVendas.getItems().clear();
        inputCPF.clear();
        inputQuantidade.clear();
        inputCodprod.clear();
        idCliente = 0;
        nomeCliente.setText("........");
        valorFinal.clear();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Compra cancelada");
        alert.setContentText("Sua compra foi cancelada");
        
        alert.showAndWait();
    }
    
    
    
}
