package Server.ServerController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerInterfaceController implements Initializable
{
    private List<String> userReservation;

    @FXML
    private ListView reservationListID = new ListView();

    public void getUserReservationList(List<String> userReservation)
    {
        this.userReservation = userReservation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Thread refreshReservationList = new Thread(this::reservationList);
        refreshReservationList.setDaemon(true);
        refreshReservationList.start();
    }

    public void reservationList()
    {
        ArrayList<String> reservationList = new ArrayList<String>();
        while(true)
        {
            Platform.runLater(() -> {
                reservationList.clear();
                for(int i=0; i<8; i++) reservationList.add(i+10 + ":00 - " + (i+11) + ":00              " + userReservation.get(i));
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
}
