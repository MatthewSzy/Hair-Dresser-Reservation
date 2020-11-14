package Server.ServerMainThread;

import Server.ServerMainThread.Class.StartClientThreadClass;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerMainThread extends Thread
{
    final private List<String> userReservation;
    final private ServerSocket serverSocket;
    final private List<Socket> onlineUser;

    public ServerMainThread(List<String> userReservation, List<Socket> onlineUser, ServerSocket serverSocket)
    {
        this.userReservation = userReservation;
        this.onlineUser = onlineUser;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                onlineUser.add(clientSocket);
                StartClientThreadClass clientThread = new StartClientThreadClass(userReservation, onlineUser, clientSocket);
                clientThread.startReservationSender();
                clientThread.startServerReceiveThread();
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
