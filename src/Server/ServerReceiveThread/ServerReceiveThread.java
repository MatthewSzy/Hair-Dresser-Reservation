package Server.ServerReceiveThread;

import Server.ServerMainThread.Class.StartClientThreadClass;
import Server.ServerReceiveThread.Class.ReceiveDataClass;
import Server.ServerReservationSender.ServerReservationSender;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerReceiveThread extends Thread
{
    final private List<String> userReservation;
    final private Socket clientSocket;
    final private List<Socket> onlineUser;


    public ServerReceiveThread(List<String> userReservation, List<Socket> onlineUser, Socket clientSocket)
    {
        this.userReservation = userReservation;
        this.clientSocket = clientSocket;
        this.onlineUser = onlineUser;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                ReceiveDataClass receiveData = new ReceiveDataClass(userReservation, clientSocket);
                receiveData.receiveReservation();

                StartClientThreadClass clientThread = new StartClientThreadClass();
                for(Socket clientSocket : onlineUser) clientThread.startReservationSender(userReservation, clientSocket);
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
