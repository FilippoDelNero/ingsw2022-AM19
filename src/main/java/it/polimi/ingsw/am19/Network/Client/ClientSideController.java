package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import it.polimi.ingsw.am19.View.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * client-side controller used to react to incoming messages
 */
public class ClientSideController {
    /** reference to the client, necessary to be able to send back replies to the server */
    private final Client myClient;

    /** reference to the view, used to communicate with the user */
    private final View view;

    /** cache used to store objects to be displayed on the view */
    private final Cache cache;

    /** nickname chosen by the user, used to sign outgoing messages */
    private String nickname;

    /** a references to the second to last message, used to recover from errors */
    private Message previousMsg;

    /**
     * class constructor, it also perform the view.init() method
     * @param client the client that this controller is associated with
     * @param view the view this controller has to work with
     */
    public ClientSideController(Client client, View view) {
        myClient = client;
        this.view = view;
        cache = new Cache();
        view.setViewCache(cache);
        view.init();
    }

    /**
     * the method called by the client to pass a message that's to be worked on
     * @param msg the message sent by the server
     */
    public void communicate(Message msg) {
        MessageType type = msg.getMessageType();
        //save the previously sent message to recover from an error
        if(!(type == MessageType.ERROR_MESSAGE))
            previousMsg = msg;
        switch (type) {
            case ASK_LOGIN_FIRST_PLAYER -> askLoginFirstPlayer((AskFirstPlayerMessage) msg);
            case ASK_LOGIN_INFO -> askLoginInfo((AskLoginInfoMessage) msg);
            case ERROR_MESSAGE -> error((ErrorMessage) msg);
            case GENERIC_MESSAGE -> generic((GenericMessage) msg);
            case UPDATE_CLOUDS -> updateCloud((UpdateCloudMessage) msg);
            case UPDATE_GAMEBOARDS -> updateGameBoards((UpdateGameBoardsMessage) msg);
            case UPDATE_ISLANDS -> updateIslands((UpdateIslandsMessage) msg);
            case UPDATE_CARDS -> updateCards((UpdateCardsMessage) msg);
            case ENTRANCE_MOVE -> askEntranceMove();
            case HOW_MANY_STEP_MN -> askMotherNatureStep((AskMotherNatureStepMessage)msg);
            case CHOOSE_CLOUD -> askCloud((AskCloudMessage) msg);
            case PLAYABLE_HELPER_CARD -> showHelperOptions((AskHelperCardMessage)msg);
        }
    }

    /**
     * The method called when a AskFirstPlayerMessage comes in
     * if the are match to resume those will be shown otherwise it will be asked the number of player
     * and the difficulty of the new match that will be created
     * @param msg the AskFirstPlayerMessage sent by the server
     */
    private void askLoginFirstPlayer(AskFirstPlayerMessage msg) {
        int numOfPlayers;
        boolean isExpert;
        if(msg.getMatchToResume() == null) {
            try {
                numOfPlayers = view.newMatchNumOfPlayers();
                isExpert = view.newMatchIsExpert();
                myClient.sendMessage(new ReplyCreateMatchMessage(numOfPlayers, isExpert));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //TODO manca cosa fare in caso di else
    }

    /**
     * The method is called when a AskLoginInfoMessage comes in
     * it asks and sends nickname, wizardFamily and towerColor
     * @param msg the AskLoginInfoMessage sent by the server
     */
    private void askLoginInfo(AskLoginInfoMessage msg){
        TowerColor towercolor;
        WizardFamily wizardFamily;
        try {
            this.nickname = view.askNickname();
            towercolor = view.askTowerColor(msg.getTowerColorsAvailable());
            wizardFamily = view.askWizardFamily(msg.getWizardFamiliesAvailable());
            myClient.sendMessage(new ReplyLoginInfoMessage(nickname,towercolor,wizardFamily));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method is called when a AskMotherNatureStepMessage comes in
     * it ask and send the num of step
     * @param msg the AskMotherNatureStep sent by server
     */
    private void askMotherNatureStep(AskMotherNatureStepMessage msg){
        int step;
        try {
            step= view.askMotherNatureStep();
            myClient.sendMessage(new ReplyMotherNatureStepMessage(nickname,step));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method is called when a AskCloudMessage comes in
     * it ask and send the num of cloud chosen
     * @param msg the AskCloudMessage sent by server
     */
    private void askCloud(AskCloudMessage msg){
        int cloudChosen;
        try {
            cloudChosen= view.askCloud(msg.getCloudAvailable());
            myClient.sendMessage(new ReplyCloudMessage(nickname, cloudChosen));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void askEntranceMove() {
        String input;
        int islandNum;
        PieceColor color = null;

        String[] colorsString = {"red", "green", "blue", "yellow", "pink"};
        PieceColor[] colorsPC = {PieceColor.RED, PieceColor.GREEN, PieceColor.BLUE, PieceColor.YELLOW, PieceColor.PINK};
        Map<String, PieceColor> colors = new HashMap<>();
        for(int i = 0; i < 5; i++) {
            colors.put(colorsString[i], colorsPC[i]);
        }

        try {
            input = view.askEntranceMove();
            for(String s : colors.keySet()) {
                if(input.contains(s))
                    color = colors.get(s);
            }
            if (color == null)
                communicate(previousMsg);
            else if(input.contains("island") || input.contains(" i ") || input.contains("isle")) {
                input = input.replaceAll("[^0-9]+", " ");
                islandNum = Integer.parseInt(input.trim());
                myClient.sendMessage(new ReplyEntranceToIslandMessage(nickname, islandNum, color));
            }
            else if(input.contains("dining") || input.contains("room") || input.contains(" d "))
                myClient.sendMessage(new ReplyEntranceToDiningRoomMessage(nickname, color));
            else
                communicate(previousMsg);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to update the clouds on the cache
     * @param msg the UpdateCloudMessage sent by the server
     */
    private void updateCloud(UpdateCloudMessage msg) {
        cache.setClouds(msg.getClouds());
    }

    /**
     * method to update the gameBoards on the cache
     * @param msg the UpdateGameBoardsMessage sent by the server
     */
    private void updateGameBoards(UpdateGameBoardsMessage msg) {
        List<ReducedGameBoard> list = cache.getGameBoards();
        if(list != null) {
            list.removeAll(msg.getList());
            list.addAll(msg.getList());
        }
        else {
            list = new ArrayList<>(msg.getList());
        }
        cache.setGameBoards(list);
    }

    /**
     * method to update the Islands on the cache
     * @param msg the UpdateIslandsMessage sent by the server
     */
    private void updateIslands(UpdateIslandsMessage msg) { //TODO RIVEDERE COME MANTENERE L'ORDINE ANCHE NEL CASO IN CUI VENGA SOSTITUITA UNA SOLA ISOLA
        List<ReducedIsland> list = cache.getIslands();
        if(list != null) {
            list.removeAll(msg.getList());
            list.addAll(msg.getList());
        }
        else {
            list = new ArrayList<>(msg.getList());
        }
        cache.setIslands(list);
        view.printView(nickname); //TODO SICURAMENTE QUESTO METODO NON VA QUA
    }

    /**
     * method to update the Cards, both Helper and Character, on the cache
     * @param msg the UpdateCardsMessage sent by the server
     */
    private void updateCards(UpdateCardsMessage msg) {
            cache.setCharacterCards(msg.getCharacterCardList());
    }

    /**
     * Method used to display a genericMessage coming from the server
     * @param msg the GenericMessage sent by the server
     */
    private void generic(GenericMessage msg) {
        view.genericPrint(msg.toString());
    }

    /**
     * Method used to display an errorMessage and to recover to the last message by simulating the arrival
     * of the message of which the answer caused the error
     * @param msg the ErrorMessage sent by the server
     */
    private void error(ErrorMessage msg) {
        view.genericPrint(msg.toString());
        communicate(previousMsg);
    }

    private void showHelperOptions(AskHelperCardMessage msg){
        List<HelperCard> cardOptions =  msg.getPlayableHelperCard();
        HelperCard helperCard = null;
        try {
            helperCard = view.askHelperCard(cardOptions);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        myClient.sendMessage(new ReplyHelperCardMessage(nickname,helperCard));
    }
}
