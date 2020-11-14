package Client.ClientReceiveThread;

import Client.ClientReceiveThread.Class.ClientReceiveClass;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientReceiveThread extends Thread
{
    final private List<String> reservationCheck;
    final private Socket clientSocket;

    public ClientReceiveThread(List<String> reservationCheck, Socket clientSocket)
    {
        this.reservationCheck = reservationCheck;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        try
        {
            ClientReceiveClass receiveClass = new ClientReceiveClass(reservationCheck, clientSocket);
            receiveClass.receiveReservationList();

            while(true)
            {
                receiveClass.receiveSingleReservation();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }



    }
}
