package Server;

import Server.ServerController.ServerInterfaceController;
import Server.ServerMainThread.ServerMainThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerMain extends Application
{
    private static List<String> userReservation = Collections.synchronizedList(new ArrayList<String>());
    private static List<Socket> onlineUser = Collections.synchronizedList(new ArrayList<Socket>());

    public static void main(String[] args)
    {
        try
        {
            for(int i=0; i<8; i++) userReservation.add("WOLNY TERMIN");
            ServerSocket serverSocket = new ServerSocket(5100);
            Thread serverMainThread = new ServerMainThread(userReservation, onlineUser, serverSocket);
            serverMainThread.start();
            launch(args);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Server/ServerInterface/ServerInterface.fxml"));
        AnchorPane anchorPane = loader.load();
        primaryStage.setTitle("Server");

        ServerInterfaceController sceneControl = loader.getController();
        sceneControl.getUserReservationList(userReservation);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
