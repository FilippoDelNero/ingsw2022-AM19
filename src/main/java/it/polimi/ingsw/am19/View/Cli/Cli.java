package it.polimi.ingsw.am19.View.Cli;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import it.polimi.ingsw.am19.View.View;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * class implementing a cli-style view
 */
public class Cli implements View {
    /** used to print out content to the user */
    private final PrintStream printer;

    /** single thread used to asynchronously read user input*/
    Thread inputThread; //TODO forse dovrebbe essere privato e final o forse solo un campo di readLine?

    /** cache used to store objects to be displayed on the view*/
    private Cache cache;

    /**
     * class constructor
     */
    public Cli() {
        printer = System.out;
    }

    /**
     * method used to read a line written by the user
     * @return a String containing the line written by the user
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new StdInReader());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    /**
     * setter for the viewCache
     * @param viewCache the cache this view will pull data from
     */
    @Override
    public void setViewCache(Cache viewCache) {
        this.cache = viewCache;
    }

    /**
     * method to display an introductory splash screen
     */
    @Override
    public void init() {
        printer.println("######## ########  ##    ##    ###    ##    ## ######## ####  ###### ");
        printer.println("##       ##     ##  ##  ##    ## ##   ###   ##    ##     ##  ##    ## ");
        printer.println("##       ##     ##   ####    ##   ##  ####  ##    ##     ##  ##       ");
        printer.println("######   ########     ##    ##     ## ## ## ##    ##     ##   ######  ");
        printer.println("##       ##   ##      ##    ######### ##  ####    ##     ##        ## ");
        printer.println("##       ##    ##     ##    ##     ## ##   ###    ##     ##  ##    ## ");
        printer.println("######## ##     ##    ##    ##     ## ##    ##    ##    ####  ######  ");
        printer.println("\n");
        printer.println("Welcome!\n");
    }

    /**
     * method used to ask the number of players of a match
     * @return the number of player chosen by the user
     */
    @Override
    public int newMatchNumOfPlayers() throws ExecutionException {
        String input;
        printer.println("There are no matches to resume, we will create a new one:");
        do {
            printer.println("How many players? [2, 3]");
            input = readLine();
        } while(!input.equals("2") && !input.equals("3"));
        return Integer.parseInt(input);
    }

    /**
     * method used to ask the difficulty of a match
     * @return true if the match will be an expert one, false otherwise
     */
    @Override
    public boolean newMatchIsExpert() throws ExecutionException {
        String input;
        do  {
            printer.println("Do you want to play an expert match? [yes, no]");
            input = readLine();
        } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));
        return input.equalsIgnoreCase("yes");
    }

    /**
     * method used to ask a nickname to the user
     * @return a String containing the nickname
     */
    @Override
    public String askNickname() throws ExecutionException {
        String input;
        printer.println("Insert a nickname: ");
        input = readLine();
        return input;
    }

    /**
     * method used to ask a wizard family to the user
     * @param availableWizardFamilies a list containing the yet to be selected wizard families
     * @return the wizard family chosen by the player
     */
    @Override
    public WizardFamily askWizardFamily (List<WizardFamily> availableWizardFamilies) throws ExecutionException{
        String input;
        WizardFamily wizardFamily = null;
        do {
            printer.println("Choose a Wizard Family from " + availableWizardFamilies);
            input=readLine();
            switch (input.toLowerCase()) {
                case "warrior" -> wizardFamily=WizardFamily.WARRIOR;
                case "shaman" -> wizardFamily=WizardFamily.SHAMAN;
                case "king" -> wizardFamily=WizardFamily.KING;
                case "witch" -> wizardFamily=WizardFamily.WITCH;
            }
        } while(!availableWizardFamilies.contains(wizardFamily));
        return wizardFamily;
    }

    /**
     * method used to ask a tower color to the user
     * @param availableTowerColor a list containing the unused tower's color
     * @return the tower's color chosen by the user
     */
    @Override
    public TowerColor askTowerColor (List<TowerColor> availableTowerColor) throws ExecutionException{
        String input;
        TowerColor towerColor = null;
        do {
            printer.println("Choose a Tower's color from " + availableTowerColor);
            input = readLine();
            switch (input.toLowerCase()) {
                case "black" -> towerColor = TowerColor.BLACK;
                case "white" -> towerColor = TowerColor.WHITE;
                case "grey" -> towerColor = TowerColor.GREY;
            }
        } while(!availableTowerColor.contains(towerColor));
        return towerColor;
    }

    @Override
    public String askEntranceMove() throws ExecutionException {
        String input;
        printer.println("Which color do you want to move and where? [e.g. RED island 1]");
        input = readLine();
        return input.toLowerCase();
    }

    /**
     * method used to display a generic message (error messages as well) to the user
     * @param toPrint the content that needs to be print
     */
    @Override
    public void genericPrint(String toPrint) {
        printer.println(toPrint);
    }

    /**
     * method used to print the entire game view
     * @param nickname the nickname of the player who owns the view
     */
    @Override
    public void PrintView(String nickname) {
        String s = "";
        printer.flush(); //TODO NON FUNZIONA

        if(cache.getCharacterCards() != null) {
            printer.println("Character cards: ");
            for(Character c : cache.getCharacterCards()) {
                s = s.concat(c.toString());
                s = s.concat("  ");
            }
            printer.println(s + '\n');
        }

        if(cache.getClouds() != null) {
            printer.println("The Clouds: ");
            for(Map<PieceColor, Integer> m : cache.getClouds())
                printer.println(m.toString());
        }

        if(cache.getGameBoards() != null) {
            printer.println("\nEach Player's GameBoard: ");
            for(ReducedGameBoard rgb : cache.getGameBoards())
                printer.println(rgb.toString()); //TODO I WOULD LIKE TO PRINT FIRST THE GAMEBOARD OF THE PLAYER OWNING THIS VIEW
        }

        if(cache.getIslands() != null) {
            printer.println("\nThe Majestic Archipelago of Eryantis: ");
            for(ReducedIsland isle : cache.getIslands())
                printer.println(isle.toString());
        }
    }
}
