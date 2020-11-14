package Server.ServerReservationSender;

import Server.ServerReservationSender.Class.SendReservationClass;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerSingleReservationSender extends Thread
{
    final private List<String> userReservation;
    final private Socket clientSocket;

    public ServerSingleReservationSender (List<String> userReservation, Socket clientSocket)
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
            sendReservation.sendSingleReservation();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
