package Server.ServerReservationSender;

import Server.ServerReservationSender.Class.SendReservationClass;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerReservationSender extends Thread
{
    final private List<String> userReservation;
    final private Socket clientSocket;

    public ServerReservationSender(List<String> userReservation, Socket clientSocket)
    {
        this.userReservation = userReservation;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        try
        {
            SendReservationClass sendReservation = new SendReservationClass(userReservation, clientSocket);
            sendReservation.sendReservation();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
