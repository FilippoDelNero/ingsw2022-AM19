package it.polimi.ingsw.am19.View;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Client.Cache;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interface to allow the ClientSideController to use indifferently a gui or a cli
 */
public interface View {
    /**
     * setter for the cache containg the view objects
     */
    void setViewCache(Cache viewCache);

    /**
     * method to display an introductory splash screen
     */
    void init();

    /**
     * method used to ask the number of players of a match
     *
     * @return the number of player chosen by the user
     */
    int newMatchNumOfPlayers() throws ExecutionException;

    /**
     * method used to ask the difficulty of a match
     *
     * @return true if the match will be an expert one, false otherwise
     */
    boolean newMatchIsExpert() throws ExecutionException;

    /**
     * method used to ask a nickname to the user
     *
     * @return a String containing the nickname
     */
    String askNickname() throws ExecutionException;

    /**
     * method used to ask a wizard family to the user
     *
     * @param availableWizardFamilies a list containing the yet to be selected wizard families
     * @return the wizard family chosen by the player
     */
    WizardFamily askWizardFamily(List<WizardFamily> availableWizardFamilies) throws ExecutionException;

    /**
     * method used to ask a tower color to the user
     *
     * @param availableTowerColor a list containing the unused tower's color
     * @return the tower's color chosen by the user
     */
    TowerColor askTowerColor(List<TowerColor> availableTowerColor) throws ExecutionException;

    /**
     * method used to display a generic message (error messages as well) to the user
     *
     * @param toPrint the content that needs to be print
     */
    void genericPrint(String toPrint);

    /**
     * method used to print the entire game view
     *
     * @param nickname the nickname of the player who owns the view
     */
    void printView(String nickname);

    HelperCard askHelperCard(List<HelperCard> helperDeck) throws ExecutionException;
}