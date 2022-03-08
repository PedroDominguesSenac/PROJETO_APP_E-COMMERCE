package projetointegrador;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class TelaProdutoController implements Initializable {

    @FXML
    private TableView<LinhaTabelaProdutos> TableProduto;
    @FXML
    private TableColumn<LinhaTabelaProdutos, Integer> ColunaID;
    @FXML
    private TableColumn<LinhaTabelaProdutos, String> ColunaNome;
    @FXML
    private TableColumn<LinhaTabelaProdutos, Double> ColunaPreco;
    @FXML
    private TableColumn<LinhaTabelaProdutos, String> ColunaFabricante;
    @FXML
    private TextField namepTextField;
    @FXML
    private TextField precoTextField;
    @FXML
    private TextField fabTextField;
    @FXML
    private TextField idTextField;
    
    Integer IdEdicao = null;
    
    @FXML
    private TextField catTextField;
    @FXML
    private TextField estoqueTextField;
    @FXML
    private TableColumn<LinhaTabelaProdutos, Integer> ColunaEstoque;
    @FXML
    private TableColumn<LinhaTabelaProdutos, String> ColunaCategoria;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ColunaID.setCellValueFactory(new PropertyValueFactory("id"));
        ColunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        ColunaPreco.setCellValueFactory(new PropertyValueFactory("preco"));
        ColunaFabricante.setCellValueFactory(new PropertyValueFactory("fabricante"));
        ColunaEstoque.setCellValueFactory(new PropertyValueFactory("estoque"));
        ColunaCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        
    }
   

    @FXML
    private void cadastrar(ActionEvent event) {
        
        if(namepTextField.getText().isBlank() && estoqueTextField.getText().isBlank() && precoTextField.getText().isBlank() && fabTextField.getText().isBlank() && catTextField.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos obrigatórios");
            alert.setContentText("Preencher campos!");
            
            alert.showAndWait();
        }
        
        String sql = "INSERT INTO produto (nome_produto, preco, estoque, fabricante, categoria) VALUES (?,?,?,?,?)";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ps.setString(1, namepTextField.getText());
            ps.setDouble(2, Double.parseDouble(precoTextField.getText()) );
            ps.setInt(3, Integer.parseInt(estoqueTextField.getText()) );
            ps.setString(4, fabTextField.getText());
            ps.setString(5, catTextField.getText());
            
            ps.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        namepTextField.clear();
        precoTextField.clear();
        fabTextField.clear();
        catTextField.clear();
        
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        if(idTextField.getText().isBlank()){
            atualizarTabela();
        }
        else{
            buscar();
        }
        
    }

    @FXML
    private void deletar(ActionEvent event) {
        LinhaTabelaProdutos linha = TableProduto.getSelectionModel().getSelectedItem();
        if(linha != null){
            int id = linha.getId();
            
            String sql = "DELETE FROM produto WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setInt(1, id);
                
                ps.execute();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void atualizarTabela(){
        TableProduto.getItems().clear();
        
        String sql = "SELECT * FROM produto";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome_produto");
                double preco = rs.getDouble("preco");
                String fabricante = rs.getString("fabricante");
                String categoria = rs.getString("categoria");
                int estoque = rs.getInt("estoque");
                
                LinhaTabelaProdutos linha = new LinhaTabelaProdutos(id, nome, preco, fabricante, estoque, categoria);
                TableProduto.getItems().add(linha);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        LinhaTabelaProdutos linha = TableProduto.getSelectionModel().getSelectedItem();
         if(linha == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Seleção obrigatória");
            alert.setContentText("Selecinar linha!");
            
            alert.showAndWait();
         }
        if(linha != null){
            int id = linha.getId();
            
            String sql = "SELECT * FROM produto WHERE id = ?";
            
            try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    namepTextField.setText(rs.getString("nome_produto"));
                    precoTextField.setText(String.valueOf(rs.getDouble("preco")));
                    fabTextField.setText(rs.getString("fabricante"));
                    catTextField.setText(rs.getString("categoria"));
                    estoqueTextField.setText(String.valueOf(rs.getInt("estoque")));
                    IdEdicao = id;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void salvar(ActionEvent event) {
        String sql = "UPDATE produto SET nome_produto = ?, preco = ?, estoque = ?, fabricante = ?, categoria = ? WHERE id = ?";
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
              ps.setString(1, namepTextField.getText());
              ps.setDouble(2, Double.parseDouble(precoTextField.getText()));
              ps.setInt(3, Integer.parseInt(estoqueTextField.getText()));
              ps.setString(4, fabTextField.getText());
              ps.setString(5, catTextField.getText());
              ps.setInt(6, IdEdicao);
              
              ps.execute();
                
                
            }
            catch(Exception e){
                e.printStackTrace();
            }
        
    }
    
    private void buscar(){
        TableProduto.getItems().clear();
        
        String sql = "SELECT * FROM produto WHERE nome_produto like ?";
        
        try(PreparedStatement ps = DB.connect().prepareStatement(sql)) {
                ps.setString(1, idTextField.getText());
                
                
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome_produto");
                    String fabricante = rs.getString("fabricante");
                    String categoria = rs.getString("categoria");
                    int estoque = rs.getInt("estoque");
                
                    LinhaTabelaProdutos linha = new LinhaTabelaProdutos(id, nome, rs.getDouble("preco"), fabricante, estoque, categoria);
                    TableProduto.getItems().add(linha);
                }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }    
    
}
