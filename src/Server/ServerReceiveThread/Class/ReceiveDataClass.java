package Server.ServerReceiveThread.Class;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ReceiveDataClass
{
    final private List<String> userReservation;
    final private Socket clientSocket;

    public ReceiveDataClass(List<String> userReservation, Socket clientSocket)
    {
        this.userReservation = userReservation;
        this.clientSocket = clientSocket;
    }

    public void receiveReservation() throws IOException{ receive(); }
    private void receive() throws IOException
    {
        DataInputStream dataReceive = new DataInputStream(clientSocket.getInputStream());
        String type = dataReceive.readUTF();
        if(type.equals("addRes"))
        {
            int value = dataReceive.readInt();
            String personalData = dataReceive.readUTF();
            userReservation.remove(value);
            userReservation.add(value, personalData);
        }
        else if(type.equals("delRes"))
        {
            String del = dataReceive.readUTF();
            userReservation.remove(Integer.parseInt(del.substring(0, 2)) - 10);
            userReservation.add(Integer.parseInt(del.substring(0, 2)) - 10, "WOLNY TERMIN");
        }
    }
}
