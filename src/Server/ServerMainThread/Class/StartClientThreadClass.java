package Server.ServerMainThread.Class;

import Server.ServerReceiveThread.ServerReceiveThread;
import Server.ServerReservationSender.ServerReservationSender;
import Server.ServerReservationSender.ServerSingleReservationSender;

import java.net.Socket;
import java.util.List;

public class StartClientThreadClass
{
    final private List<String> userReservation;
    final private Socket clientSocket;
    final private List<Socket> onlineUser;

    public StartClientThreadClass()
    {
        this.userReservation = null;
        this.clientSocket = null;
        this.onlineUser = null;
    }

    public StartClientThreadClass(List<String> userReservation, List<Socket> onlineUser, Socket clientSocket)
    {
        this.userReservation = userReservation;
        this.onlineUser = onlineUser;
        this.clientSocket = clientSocket;
    }

    public void startReservationSender(){ reservationSender(); }
    private void reservationSender()
    {
        Thread serverReservationSender = new ServerReservationSender(userReservation, clientSocket);
        serverReservationSender.start();
    }

    public void startReservationSender(List<String> userReservation, Socket clientSocket) { reservationSender(userReservation, clientSocket); }
    private void reservationSender(List<String> userReservation, Socket clientSocket)
    {
        Thread serverSingleReservationSender = new ServerSingleReservationSender(userReservation, clientSocket);
        serverSingleReservationSender.start();
    }

    public void startServerReceiveThread(){ serverReceive(); }
    private void serverReceive()
    {
        Thread serverReceiveThread = new ServerReceiveThread(userReservation, onlineUser, clientSocket);
        serverReceiveThread.start();
    }
}
