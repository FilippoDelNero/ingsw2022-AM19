package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * A Class for managing waiting while other clients still connecting
 */
public class WaitingStartController implements SceneController {
    /**
     * gui's reference
     */
    private Gui gui;

    public void initialize(){
        loading();
    }

    @FXML
    private Label waitingLabel;

    @FXML
    private ProgressBar progressBar;

    /**
     * Sets gui's reference
     * @param gui id the gui's reference
     */
    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }

    private void loading(){
        //progression bar fill
        new Thread(() -> {
            for(int i = 0; i <=500; i++) {
                final int position = i;
                Platform.runLater(() -> progressBar.setProgress(position / 500.0));
                try{
                    Thread.sleep(100);
                }catch(Exception e){ e.printStackTrace(); }
            }
        }).start();
    }
}
