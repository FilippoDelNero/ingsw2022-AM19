package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;

public interface SceneController {
    void setGui(Gui gui);

    void showGenericMsg(GenericMessage msg);
}
