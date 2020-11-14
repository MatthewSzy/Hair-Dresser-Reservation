package Server.ServerReservationSender.Class;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class SendReservationClass
{
    final private List<String> userReservation;
    final private Socket clientSocket;

    public SendReservationClass(List<String> userReservation, Socket clientSocket)
    {
        this.userReservation = userReservation;
        this.clientSocket = clientSocket;
    }

    public void sendReservation() throws IOException { send(); }
    private void send() throws IOException
    {
        DataOutputStream dataSend = new DataOutputStream(clientSocket.getOutputStream());
        for(int i=0; i<8; i++)
        {
            if(userReservation.get(i).equals("WOLNY TERMIN")) dataSend.writeBoolean(false);
            else dataSend.writeBoolean(true);
            dataSend.flush();
        }
    }

    public void sendSingleReservation() throws IOException { sendSingle(); }
    private void sendSingle() throws IOException
    {
        DataOutputStream dataSend = new DataOutputStream(clientSocket.getOutputStream());
        for(int i=0; i<8; i++)
        {
            if(!userReservation.get(i).equals("WOLNY TERMIN"))
            {
                dataSend.writeInt(i);
                dataSend.flush();
            }
        }
    }
}
