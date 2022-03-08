package projetointegrador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class App extends Application {

    private static Scene scene;
  
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("TelaPrincipal"), 650, 500);
        stage.setScene(scene);
        stage.setTitle("Loja Games");
        stage.setMinWidth(660);
        stage.setMinHeight(510);
        stage.setMaxWidth(1500);
        stage.setMaxHeight(1200);
        stage.getIcons().add(new Image("/projetointegrador/imagens/game.png"));
        stage.show();
    }

    static void setRoot(String fxml, BorderPane container) throws IOException {
        container.setCenter(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
