package projetointegrador;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LAPTOP
 */
public class TelaRelatorioController implements Initializable {

    @FXML
    private TableColumn<LinhaTabelaRelatorio, Integer> colunaID;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, String> colunaCliente;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, Integer> colunaCodigo;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, String> colunaProduto;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, Double> colunaValor;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, Integer> colunaIDPedido;
    @FXML
    private TableView<LinhaTabelaRelatorio> TableRelatorio;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, Integer> colunaQuantidade;
    @FXML
    private DatePicker firstDate;
    @FXML
    private DatePicker lastDate;
    @FXML
    private TableColumn<LinhaTabelaRelatorio, LocalDate> colunaData;
    @FXML
    private TextField valorPeriodo;
    
    private double total = 0;
    private Date first1;
    private Date last2;


  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaID.setCellValueFactory(new PropertyValueFactory("id"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colunaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colunaProduto.setCellValueFactory(new PropertyValueFactory("produto"));
        colunaValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaIDPedido.setCellValueFactory(new PropertyValueFactory("idPedido"));
        colunaData.setCellValueFactory(new PropertyValueFactory("dataPedido"));
        
    }    

    @FXML
    private void emitir(ActionEvent event) {
        
        long dias = DAYS.between(firstDate.getValue(), lastDate.getValue());
        
        
        
        if(firstDate.getValue() == null && lastDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos obrigatórios");
            alert.setContentText("Preencher intervalo!");
            
            alert.showAndWait();
        }
        else if(dias < 0 || dias > 31){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de intervalo");
            alert.setContentText("Selecione um intervalo de 30 dias");
            
            alert.showAndWait();
        }
        else{
            TableRelatorio.getItems().clear();

            String sql = "select ordem.id_cliente, cliente.nome, ordem_pedido.id_produto, produto.nome_produto, ordem_pedido.quantidade, ordem_pedido.valor, ordem.id, ordem.dataPedido from ordem inner join cliente on ordem.id_cliente = cliente.id inner join ordem_pedido on ordem.id = ordem_pedido.id_ordem inner join produto on produto.id = ordem_pedido.id_produto where dataPedido > ? and dataPedido < ?";

            try(PreparedStatement ps = DB.connect().prepareStatement(sql)){


                Date first = Date.valueOf(firstDate.getValue());
                Date last = Date.valueOf(lastDate.getValue());

                if(first1 != first && last2 != last){
                    total = 0;
                }
                else{
                    first1 = first;
                    last2 = last;
                }

                ps.setDate(1, first);
                ps.setDate(2, last);


                ResultSet rs = ps.executeQuery();

                    while(rs.next()){
                        int id = rs.getInt("id_cliente");
                        String cliente = rs.getString("nome");
                        int idProduto = rs.getInt("id_produto");
                        String produto = rs.getString("nome_produto");
                        int quantidade = rs.getInt("quantidade");
                        Double valor = rs.getDouble("valor");
                        int idPedido = rs.getInt("id");
                        Date datapedido = rs.getDate("dataPedido");

                        LinhaTabelaRelatorio linha = new LinhaTabelaRelatorio(id, cliente, idProduto, produto, quantidade, valor, idPedido, datapedido.toLocalDate() );
                        TableRelatorio.getItems().add(linha);

                    }
                    periodoValor();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void periodoValor(){
        for(int i = 0; i < TableRelatorio.getItems().size(); i++){
                 total = total + TableRelatorio.getItems().get(i).getValor();
                 valorPeriodo.setText(String.valueOf(total));
        }
    }
}    
