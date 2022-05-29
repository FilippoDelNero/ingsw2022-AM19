package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;

public interface SceneController {
    /**
     * Sets gui's reference
     * @param gui id the gui's reference
     */
    void setGui(Gui gui);

    /**
     * It shows a generic message content in the current scene
     * @param msg the message that wraps what needs to be displayed
     */
    void showGenericMsg(GenericMessage msg);
}
