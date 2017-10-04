package ca.bcit.cst.rongyi;

import ca.bcit.cst.rongyi.ChatServer.ClientHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The GUI for server side.
 * 
 * @author Rongyi Chen
 * @version 0.2
 */
public class ServerApp extends Application {

    private ChatServer server = new ChatServer();
    private ListView<ClientHandler> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        Thread serverThread = new Thread() {
            public void run() {
                server.start(6666);
            }
        };
        serverThread.start();

        Button stopBtn = new Button("Stop Server");
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                server.stop();
                primaryStage.hide();
            }
        });
        
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                refreshList();
            }
        });
        
        listView = new ListView<ClientHandler>();
        
        GridPane gridpane = new GridPane();
        gridpane.add(refreshBtn, 0, 0);
        gridpane.add(stopBtn, 1, 0);
        gridpane.add(listView, 0, 1);
        
        Group root = new Group(gridpane);

        Scene scene = new Scene(root);
        primaryStage.setTitle("ChatServer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void refreshList() {
        ObservableList<ClientHandler> clients = FXCollections.observableArrayList(server.clientSockets);
        listView.setItems(clients);
    }

    /**
     * Drives the program.
     * 
     * @param args
     *            unused.
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

}
