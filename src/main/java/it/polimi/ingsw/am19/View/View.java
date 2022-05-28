package it.polimi.ingsw.am19.View;

import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Client.Client;
import it.polimi.ingsw.am19.Network.Client.Dispatcher;
import it.polimi.ingsw.am19.Network.Message.*;

/**
 * Interface to allow the ClientSideController to use indifferently a gui or a cli
 */
public interface View {
    /**
     * setter for the cache attribute
     * @param cache the cache this view will pull data from
     */
    void setCache(Cache cache);

    /**
     * setter for the client parameter
     * @param client the client this view needs to refer to to send messages
     */
    void setMyClient(Client client);

    /**
     * setter for the dispatcher parameter
     * @param dispatcher the dispatcher this view needs to refer to
     */
    void setDispatcher(Dispatcher dispatcher);

    /**
     * setter for the previous message attribute
     * @param msg the message that just arrived
     */
    void setPreviousMsg(Message msg);

    /**
     * This method is called to ask match info to the first connecting player
     * @param msg the AskFirstPlayerMessage sent by the server
     */
    void askLoginFirstPlayer(AskFirstPlayerMessage msg);

    /**
     * This method is called to ask a nickname to the user when resuming a saved match
     * @param msg the AskNicknameOptionsMessage sent by the server
     */
    void askNicknameFromResumedMatch(AskNicknameOptionsMessage msg);

    /**
     * This method is called when a AskLoginInfoMessage comes in
     * it asks and sends nickname, wizardFamily and towerColor
     * @param msg the AskLoginInfoMessage sent by the server
     */
    void askLoginInfo(AskLoginInfoMessage msg);

    /**
     * shows the available HelperCards to the user and the sends he/she's answer to the server
     * @param msg the message sent by the server containing the availableHelperCards
     */
    void showHelperOptions(AskHelperCardMessage msg);

    /**
     * Method used to ask the player where she/he wants to move which student's color
     * it also checks that a valid color and a valid destination are passed, but no checks are made on the island number
     */
    void askEntranceMove(AskEntranceMoveMessage msg);

    /**
     * The method is called when a AskMotherNatureStepMessage comes in
     * it ask and send the num of step
     */
    void askMotherNatureStep();

    /**
     * The method is called when a AskCloudMessage comes in
     * it ask and send the num of cloud chosen
     * @param msg the AskCloudMessage sent by server
     */
    void askCloud(AskCloudMessage msg);

    /**
     * Method used to ask the user if and which characterCards they want to play
     * @param msg the AskPlayCharacterCardMessage sent by server containing the options to present to the user
     */
    void askPlayCharacter(AskPlayCharacterCardMessage msg);

    /**
     * method used to ask the user the parameters of the character card that they played
     * @param msg the AskCharacterParameterMessage sent by server containing which parameter the played card will need
     */
    void askCharacterCardParameters(AskCharacterParameterMessage msg);

    /**
     * Method used to display the winner and close the client connection
     * @param msg the EndMatchMessage sent by the server
     */
    void endMatch(EndMatchMessage msg);

    /**
     * Method used to display a genericMessage coming from the server
     * @param msg the GenericMessage sent by the server
     */
    void generic(GenericMessage msg);

    /**
     * Method used to display an errorMessage and to recover to the last message by simulating the arrival
     * of the message of which the answer caused the error
     * @param msg the ErrorMessage sent by the server
     */
    void error(ErrorMessage msg);
}