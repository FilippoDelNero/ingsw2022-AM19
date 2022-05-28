package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.View;

public class Dispatcher {

    /** the view of this client */
    private final View view;

    /** the cache of this client*/
    private final Cache cache;

    /**
     * class constructor
     * @param view the view chosen by the user
     */
    public Dispatcher(View view) {
        this.view = view;
        this.cache = new Cache();
        view.setCache(cache);
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
            case GENERIC_MESSAGE -> view.generic((GenericMessage) msg);

            case UPDATE_CLOUDS -> updateCloud((UpdateCloudsMessage) msg);
            case UPDATE_GAMEBOARDS -> updateGameBoards((UpdateGameBoardsMessage) msg);
            case UPDATE_ISLANDS -> updateIslands((UpdateIslandsMessage) msg);
            case UPDATE_CARDS -> updateCards((UpdateCardsMessage) msg);
        }
    }

    /**
     * method to update the clouds on the cache
     * @param msg the UpdateCloudMessage sent by the server
     */
    public void updateCloud(UpdateCloudsMessage msg) {
        cache.setClouds(msg.getClouds());
    }

    /**
     * method to update the gameBoards on the cache
     * @param msg the UpdateGameBoardsMessage sent by the server
     */
    public void updateGameBoards(UpdateGameBoardsMessage msg) {
        cache.setGameBoards(msg.getList());
    }

    /**
     * method to update the Islands on the cache
     * @param msg the UpdateIslandsMessage sent by the server
     */
    public void updateIslands(UpdateIslandsMessage msg) {
        cache.setIslands(msg.getList());
    }

    //TODO IS IT USED?
    /**
     * method to update the Cards, both Helper and Character, on the cache
     * @param msg the UpdateCardsMessage sent by the server
     */
    public void updateCards(UpdateCardsMessage msg) {
        cache.setCharacterCards(msg.getCharacterCardList());
    }
}
