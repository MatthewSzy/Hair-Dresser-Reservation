package Client;

import Client.ClientReceiveThread.ClientReceiveThread;
import Client.ClientController.ClientInterfaceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientMain extends Application
{
    private static List<String> reservationCheck = Collections.synchronizedList(new ArrayList<String>());
    private static Socket clientSocket;

    public static void main(String[] args)
    {
        try
        {
            clientSocket = new Socket("localhost", 5100);
            Thread clientReceiveThread = new ClientReceiveThread(reservationCheck, clientSocket);
            clientReceiveThread.start();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/ClientInterface/ClientInterface.fxml"));
        AnchorPane anchorPane = loader.load();
        primaryStage.setTitle("Client");

        ClientInterfaceController sceneControl = loader.getController();
        sceneControl.getReservationCheck(reservationCheck);
        sceneControl.getClientSocket(clientSocket);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
