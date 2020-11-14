package Client.ClientController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientInterfaceController implements Initializable
{
    private List<String> reservationCheck;
    private List<String> reservationYour = new ArrayList<String>();
    private Socket clientSocket;
    private String selectedTime;
    private String yourReservation;

    @FXML
    private ListView reservationListID = new ListView();

    @FXML
    private ListView reservationList2ID = new ListView();

    @FXML
    private MenuButton menuButtonID = new MenuButton();

    @FXML
    private TextField textFieldNameID = new TextField();

    @FXML
    private TextField textFieldLastnameID = new TextField();

    public void getReservationCheck(List<String> reservationCheck)
    {
        this.reservationCheck = reservationCheck;
    }
    public void getClientSocket(Socket clientSocket) { this.clientSocket = clientSocket; }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Thread refreshReservationCheck = new Thread(this::refreshReservationCheck);
        refreshReservationCheck.setDaemon(true);
        refreshReservationCheck.start();

        Thread refreshReservationYour = new Thread(this::refreshReservationYour);
        refreshReservationYour.setDaemon(true);
        refreshReservationYour.start();

        Thread getReservation = new Thread(this::getReservation);
        getReservation.setDaemon(true);
        getReservation.start();
    }

    public void refreshReservationCheck()
    {
        ArrayList<String> reservationList = new ArrayList<String>();
        while(true)
        {
            Platform.runLater(() -> {
                reservationList.clear();
                for(int i=0; i<8; i++) reservationList.add(i+10 + ":00 - " + (i+11) + ":00             " + reservationCheck.get(i));
                ObservableList<String> uList = FXCollections.observableArrayList(reservationList);
                reservationListID.setItems(uList);
            });

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void refreshReservationYour()
    {
        ArrayList<String> reservationList2 = new ArrayList<String>();
        while(true)
        {
            Platform.runLater(() -> {
                    reservationList2.clear();
                    for(int i=0; i<reservationYour.size(); i++) reservationList2.add(reservationYour.get(i));
                    ObservableList<String> uList = FXCollections.observableArrayList(reservationList2);
                    reservationList2ID.setItems(uList);
            });

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void getReservation()
    {
        while(true)
        {
            Platform.runLater(() -> {
                reservationList2ID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
                {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                    {
                        if (newValue != null)
                        {
                            yourReservation = newValue;
                        }
                    }
                });
            });
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void sendReservation(MouseEvent mouseEvent) throws IOException
    {
        if(!textFieldNameID.getText().trim().isEmpty() && !textFieldLastnameID.getText().trim().isEmpty() && selectedTime != null && !reservationCheck.get(Integer.parseInt(selectedTime.substring(0, 2)) - 10).equals("ZAJETY TERMIN"))
        {
            DataOutputStream dataSend = new DataOutputStream(clientSocket.getOutputStream());
            dataSend.writeUTF("addRes");
            dataSend.flush();
            dataSend.writeInt(Integer.parseInt(selectedTime.substring(0, 2)) - 10);
            dataSend.flush();
            dataSend.writeUTF(textFieldNameID.getText() + " " + textFieldLastnameID.getText());
            dataSend.flush();
            reservationYour.add(selectedTime + " " + textFieldNameID.getText() + " " + textFieldLastnameID.getText());
            textFieldNameID.clear();
            textFieldLastnameID.clear();
        }
    }

    public void selectTime(ActionEvent actionEvent)
    {
        menuButtonID.setText(((MenuItem)actionEvent.getSource()).getText());
        selectedTime = ((MenuItem) actionEvent.getSource()).getText();
    }

    public void deleteReservationID(MouseEvent mouseEvent) throws IOException
    {
        if(yourReservation != null)
        {
            DataOutputStream dataSend = new DataOutputStream(clientSocket.getOutputStream());
            dataSend.writeUTF("delRes");
            dataSend.flush();
            dataSend.writeUTF(yourReservation);
            for(int i=0; i<reservationYour.size(); i++)
            {
                if(reservationYour.get(i).equals(yourReservation))
                {
                    reservationCheck.remove(i);
                    reservationCheck.add(i, "WOLNY TERMIN");
                    reservationYour.remove(i);
                }
            }
        }
    }
}
