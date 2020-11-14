package Client.ClientReceiveThread.Class;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientReceiveClass
{
    final private List<String> reservationCheck;
    final private Socket clientSocket;

    public ClientReceiveClass (List<String> reservationCheck, Socket clientSocket)
    {
        this.reservationCheck = reservationCheck;
        this.clientSocket = clientSocket;
    }

    public void receiveReservationList() throws IOException { receiveReservation(); }
    private void receiveReservation() throws IOException
    {
        DataInputStream dataReceive = new DataInputStream(clientSocket.getInputStream());
        for(int i=0; i<8; i++)
        {
            boolean check = dataReceive.readBoolean();
            if(check == false) reservationCheck.add("WOLNY TERMIN");
            else reservationCheck.add("ZAJETY TERMIN");
        }
    }

    public void receiveSingleReservation() throws IOException { receiveSingle(); }
    private void receiveSingle() throws IOException
    {
        DataInputStream dataReceive = new DataInputStream(clientSocket.getInputStream());
        int value = dataReceive.readInt();
        reservationCheck.remove(value);
        reservationCheck.add(value, "ZAJETY TERMIN");
    }
}
