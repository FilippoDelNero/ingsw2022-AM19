package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.View;

public class Dispatcher {

    /** the view of this client */
    private final View view;

    /**
     * class constructor
     * @param view the view chosen by the user
     */
    public Dispatcher(View view) {
        this.view = view;
        view.setDispatcher(this);
    }

    /**
     * the method called by the client to pass a message that's to be worked on
     * @param msg the message sent by the server
     */
    public void dispatch(Message msg) {
        MessageType type = msg.getMessageType();

        if(type != MessageType.ERROR_MESSAGE)
            view.setPreviousMsg(msg);

        switch (type) {
            case ASK_LOGIN_FIRST_PLAYER -> view.askLoginFirstPlayer((AskFirstPlayerMessage) msg);
            case LOGIN_PLAYERS_OPTION -> view.askNicknameFromResumedMatch((AskNicknameOptionsMessage)msg);
            case ASK_LOGIN_INFO -> view.askLoginInfo((AskLoginInfoMessage) msg);
            case PLAYABLE_HELPER_CARD -> view.showHelperOptions((AskHelperCardMessage) msg);
            case ENTRANCE_MOVE -> view.askEntranceMove((AskEntranceMoveMessage) msg);
            case HOW_MANY_STEP_MN -> view.askMotherNatureStep();
            case CHOOSE_CLOUD -> view.askCloud((AskCloudMessage) msg);
            case END_MATCH_MESSAGE -> view.endMatch((EndMatchMessage) msg);
            case ASK_CHARACTER_CARD -> view.askPlayCharacter((AskPlayCharacterCardMessage) msg);
            case ASK_CHARACTER_PARAMETER -> view.askCharacterCardParameters((AskCharacterParameterMessage) msg);

            case ERROR_MESSAGE -> view.error((ErrorMessage) msg);
            case GENERIC_MESSAGE, WAIT_MESSAGE, START_ACTION_MESSAGE -> view.generic((GenericMessage) msg);

            case UPDATE_CLOUDS -> view.updateCloud((UpdateCloudsMessage) msg);
            case UPDATE_GAMEBOARDS -> view.updateGameBoards((UpdateGameBoardsMessage) msg);
            case UPDATE_ISLANDS -> view.updateIslands((UpdateIslandsMessage) msg);
            case UPDATE_CARDS -> view.updateCards((UpdateCardsMessage) msg);
        }
    }
}
