package it.polimi.ingsw.am19.View.Cli;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Client.Client;
import it.polimi.ingsw.am19.Network.Client.Dispatcher;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.View.View;

import java.io.PrintStream;
import java.util.*;

/**
 * class implementing a cli-style view
 */
public class Cli implements View {
    /** used to print out content to the user */
    private final PrintStream printer;

    /** used to read the user's input */
    private final Scanner reader;

    /** cache used to store objects to be displayed on the view */
    private final Cache cache;

    private String nickname;

    private Client myClient;

    private Dispatcher myDispatcher;

    private Message previousMsg;

    /**
     * class constructor
     */
    public Cli() {
        printer = System.out;
        reader = new Scanner(System.in);
        cache = new Cache();
        initView();
        startView();
    }

    /**
     * method to display an introductory splash screen
     */
    private void initView() {
        printer.println("######## ########    ####      ###    ##    ## ######## ##    ##   ######  ");
        printer.println("##       ##     ##    ##      ## ##   ###   ##    ##     ##  ##   ##    ## ");
        printer.println("##       ##     ##    ##     ##   ##  ####  ##    ##       ##    ##        ");
        printer.println("######   ########     ##    ##     ## ## ## ##    ##       ##     ######   ");
        printer.println("##       ##   ##      ##    ######### ##  ####    ##       ##           ## ");
        printer.println("##       ##    ##     ##    ##     ## ##   ###    ##       ##    ##    ##  ");
        printer.println("######## ##     ##   ####   ##     ## ##    ##    ##       ##     ######   ");
        printer.println("\n");
        printer.println("Welcome!\n");
    }

    private void startView() {
        String ipAddress;
        String portNumberString;
        int portNumber = 0;
        boolean isValid = true;

        printer.println("insert an ip address: ");
        ipAddress = reader.nextLine();

        do {
            printer.println("insert a port: ");
            portNumberString = reader.nextLine();
            try {
                portNumber = Integer.parseInt(portNumberString);
            } catch (NumberFormatException e) {
                isValid = false;
            }
        } while(!isValid);


        myClient = new Client(ipAddress, portNumber, this);
        myClient.startPinging();
        myClient.receiveMessage();
    }

    @Override
    public void setMyClient(Client client) {
        this.myClient = client;
    }

    @Override
    public void setDispatcher(Dispatcher dispatcher) {
        this.myDispatcher = dispatcher;
    }

    @Override
    public void setPreviousMsg(Message msg) {
        this.previousMsg = msg;
    }

    public void askLoginFirstPlayer(AskFirstPlayerMessage msg) {
        previousMsg = msg;
        int numOfPlayers;
        boolean isExpert;
        if(!msg.isMatchToResume()) {
            numOfPlayers = newMatchNumOfPlayers();
            isExpert = newMatchIsExpert();
            myClient.sendMessage(new ReplyCreateMatchMessage(numOfPlayers, isExpert));
        }

        else{
            boolean resumingMatch = askResumeMatch();
            if(!resumingMatch) {
                numOfPlayers = newMatchNumOfPlayers();
                isExpert = newMatchIsExpert();
                myClient.sendMessage(new ReplyCreateMatchMessage(numOfPlayers, isExpert));
            } else{
                myClient.sendMessage(new ReplyResumeMatchMessage());
            }
        }
    }

    public void askNicknameFromResumedMatch(AskNicknameOptionsMessage msg){
        this.nickname = askNicknameFromList(new ArrayList<>(msg.getNicknameAvailable()));
        myClient.sendMessage(new ReplyLoginInfoMessage(nickname,null,null));
    }

    /**
     * The method is called when a AskLoginInfoMessage comes in
     * it asks and sends nickname, wizardFamily and towerColor
     * @param msg the AskLoginInfoMessage sent by the server
     */
    public void askLoginInfo(AskLoginInfoMessage msg){
        TowerColor towercolor;
        WizardFamily wizardFamily;
        this.nickname = askNickname();
        towercolor = askTowerColor(msg.getTowerColorsAvailable());
        wizardFamily = askWizardFamily(msg.getWizardFamiliesAvailable());
        myClient.sendMessage(new ReplyLoginInfoMessage(nickname,towercolor,wizardFamily));
    }

    /**
     * shows the available HelperCards to the user and the sends he/she's answer to the server
     * @param msg the message sent by the server containing the availableHelperCards
     */
    public void showHelperOptions(AskHelperCardMessage msg){
        List<HelperCard> cardOptions =  msg.getPlayableHelperCard();
        HelperCard helperCard;
        printView();
        helperCard = askHelperCard(cardOptions);
        myClient.sendMessage(new ReplyHelperCardMessage(nickname,helperCard));
    }

    /**
     * Method used to ask the player where she/he wants to move which student's color
     * it also checks that a valid color and a valid destination are passed, but no checks are made on the island number
     */
    public void askEntranceMove(AskEntranceMoveMessage msg) {
        String input;
        int islandNum;
        PieceColor color = null;

        String[] colorsString = {"red", "green", "blue", "yellow", "pink"};
        PieceColor[] colorsPC = {PieceColor.RED, PieceColor.GREEN, PieceColor.BLUE, PieceColor.YELLOW, PieceColor.PINK};
        Map<String, PieceColor> colors = new HashMap<>();
        for(int i = 0; i < 5; i++) {
            colors.put(colorsString[i], colorsPC[i]);
        }

        printView();
        input = askEntranceMove(msg.getMovesLeft());
        for(String s : colors.keySet()) {
            if(input.contains(s))
                color = colors.get(s);
        }
        if (color == null)
            myDispatcher.dispatch(previousMsg);
        else if(input.contains("island") || input.contains(" i") || input.contains("isle")) {
            input = input.replaceAll("[^0-9]+", " ");
            try {
                islandNum = (Integer.parseInt(input.trim())) - 1;
            } catch (NumberFormatException e) {
                myDispatcher.dispatch(previousMsg);
                return;
            }
            if(islandNum < 0 || islandNum >= cache.getIslands().size()) {
                myDispatcher.dispatch(previousMsg);
                return;
            }
            myClient.sendMessage(new ReplyEntranceToIslandMessage(nickname, islandNum, color));
        }
        else if(input.contains("dining") || input.contains("room") || input.contains(" d"))
            myClient.sendMessage(new ReplyEntranceToDiningRoomMessage(nickname, color));
        else
            myDispatcher.dispatch(previousMsg);
    }

    /**
     * The method is called when a AskMotherNatureStepMessage comes in
     * it ask and send the num of step
     */
    public void askMotherNatureStep() {
        int step;
        printView();
        step = askMotherNatureSteps();
        myClient.sendMessage(new ReplyMotherNatureStepMessage(nickname, step));
    }

    /**
     * The method is called when a AskCloudMessage comes in
     * it ask and send the num of cloud chosen
     * @param msg the AskCloudMessage sent by server
     */
    public void askCloud(AskCloudMessage msg){
        int cloudChosen;
        printView();
        cloudChosen= askCloud(msg.getCloudAvailable());
        myClient.sendMessage(new ReplyCloudMessage(nickname, cloudChosen));
    }

    /**
     * Method used to ask the user if and which characterCards they want to play
     * @param msg the AskPlayCharacterCardMessage sent by server containing the options to present to the user
     */
    public void askPlayCharacter(AskPlayCharacterCardMessage msg){
        printView();
        Character chosenCardEnum = askPlayCharacter(msg.getAvailableCharacterCards());
        myClient.sendMessage(new ReplyPlayCharacterCardMessage(nickname, chosenCardEnum));
    }

    /**
     * method used to ask the user the parameters of the character card that they played
     * @param msg the AskCharacterParameterMessage sent by server containing which parameter the played card will need
     */
    public void askCharacterCardParameters(AskCharacterParameterMessage msg) {
        PieceColor color = null;
        Integer islandIndex = null;
        List<PieceColor> pieceColorList = null;
        if(msg.isRequireColor())
            color = askCharacterCardParamPieceColor();
        if(msg.isRequireIsland())
            islandIndex = askCharacterCardParamIsland();
        if(msg.isRequireColorList())
            pieceColorList = askCharacterCardParamList();
        myClient.sendMessage(new ReplyCharacterParameterMessage(nickname, color, islandIndex, pieceColorList));
    }

    /**
     * Method used to display the winner and close the client connection
     * @param msg the EndMatchMessage sent by the server
     */
    public void endMatch(EndMatchMessage msg) {
        if(msg.getWinners() != null)
            genericPrint("The match has ended, the winner is: " + msg.getWinners().toString());
        else
            genericPrint("We are sorry, the match will interrupted due to a fatal error occurring");
        myClient.disconnect();
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

    /**
     * method to update the Cards, both Helper and Character, on the cache
     * @param msg the UpdateCardsMessage sent by the server
     */
    public void updateCards(UpdateCardsMessage msg) {
        cache.setCharacterCards(msg.getCharacterCardList());
    }

    /**
     * Method used to display a genericMessage coming from the server
     * @param msg the GenericMessage sent by the server
     */
    public void generic(GenericMessage msg) {
        genericPrint(msg.toString());
    }

    /**
     * Method used to display an errorMessage and to recover to the last message by simulating the arrival
     * of the message of which the answer caused the error
     * @param msg the ErrorMessage sent by the server
     */
    public void error(ErrorMessage msg) {
        genericPrint("\033[31;1;4m" + msg.toString() + "\033[0m");
        myDispatcher.dispatch(previousMsg);
    }

    /**
     * method used to ask the player if they want to resume a saved match
     * @return true if the player wants to resume a saved match, false otherwise
     */
    private boolean askResumeMatch() {
        String input;
        do  {
            printer.println("Do you want to resume the previous match? [yes, no]");
            input = reader.nextLine();
        } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));
        return input.equalsIgnoreCase("yes");
    }

    /**
     * method used to ask the number of players of a match
     * @return the number of player chosen by the user
     */
    private int newMatchNumOfPlayers() {
        String input;
        printer.println("There are no matches to resume, we will create a new one:");
        do {
            printer.println("How many players? [2, 3]");
            input = reader.nextLine();
        } while(!input.equals("2") && !input.equals("3"));
        return Integer.parseInt(input);
    }

    /**
     * method used to ask the difficulty of a match
     * @return true if the match will be an expert one, false otherwise
     */
    private boolean newMatchIsExpert() {
        String input;
        do  {
            printer.println("Do you want to play an expert match? [yes, no]");
            input = reader.nextLine();
        } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));
        return input.equalsIgnoreCase("yes");
    }

    /**
     * method used to ask a nickname to the user
     * @return a String containing the nickname
     */
    private String askNickname() {
        String input;
        printer.println("Insert a nickname: ");
        input = reader.nextLine();
        return input;
    }

    private String askNicknameFromList(List<String> nicknameAvailable) {
        String input;
        do{
            printer.println("Who are you?" + nicknameAvailable + '\n');
            input= reader.nextLine();
        } while (!nicknameAvailable.contains(input));
        return input;
    }

    /**
     * method used to ask a wizard family to the user
     * @param availableWizardFamilies a list containing the yet to be selected wizard families
     * @return the wizard family chosen by the player
     */
    private WizardFamily askWizardFamily (List<WizardFamily> availableWizardFamilies) {
        String input;
        WizardFamily wizardFamily = null;
        do {
            printer.println("Choose a Wizard Family from " + availableWizardFamilies);
            input = reader.nextLine();
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
    private TowerColor askTowerColor (List<TowerColor> availableTowerColor) {
        String input;
        TowerColor towerColor = null;
        do {
            printer.println("Choose a Tower's color from " + availableTowerColor);
            input = reader.nextLine();
            switch (input.toLowerCase()) {
                case "black" -> towerColor = TowerColor.BLACK;
                case "white" -> towerColor = TowerColor.WHITE;
                case "grey" -> towerColor = TowerColor.GREY;
            }
        } while(!availableTowerColor.contains(towerColor));
        return towerColor;
    }
    private HelperCard askHelperCard(List<HelperCard> cardOptions) {
        int chosenCardIndex;
        List<Integer> availableIndexes = cardOptions.stream()
                .map(HelperCard::getNextRoundOrder)
                .toList();
        do{
            printer.println("Please choose a card number between those available in your deck:");
            for (HelperCard card : cardOptions)
                printer.println(card);
            String index = reader.nextLine();
            try {
                chosenCardIndex = Integer.parseInt(index);
            } catch (NumberFormatException e) {
                chosenCardIndex = 13; //there is no card with that index, the card will always be asked again if no number is provided
            }

        }while(!availableIndexes.contains(chosenCardIndex));

        for(HelperCard card : cardOptions)
            if(card.getNextRoundOrder() == chosenCardIndex)
                return card;
        return null; //If it happens there's a bug
    }

    /**
     * method used to ask the user which student's color wants to move and where
     * @return the input of the player
     */
    private String askEntranceMove(int movesLeft) {
        String input;
        printer.println("You need to move " + movesLeft + " more students. Which color do you want to move and where? [e.g. RED island 1]");
        input = reader.nextLine();
        return input.toLowerCase();
    }

    /**
     * Method used to ask the step that Mother Nature have to do in clockwise
     * @return the num of step
     */
    private int askMotherNatureSteps() {
        String input;
        int step=0;
        do {
            printer.println("How many step Mother Nature have to do in clockwise?");
            input = reader.nextLine();
            switch (input) {
                case "1" -> step=1;
                case "2" -> step=2;
                case "3" -> step=3;
                case "4" -> step=4;
                case "5" -> step=5;
                case "6" -> step=6;
                case "7" -> step=7;
            }
        } while(step<=0);
        return step;
    }

    /**
     * Method used to ask a cloud to take
     * @param cloudAvailable the array with the num of cloud still available to take
     * @return the index of cloud to take
     */
    private int askCloud(List<Integer> cloudAvailable) {
        int cloudChosen;
        String input;
        do {
            //server sends the list of available clouds starting from index 0, but we need to display it starting from 1
            printer.println("Choose a cloud from " + cloudAvailable.stream()
                    .map(index -> index + 1).toList());
            input = reader.nextLine();
            try {
                cloudChosen=Integer.parseInt(input) - 1; // we send back the chosen index according to server index management
            } catch (NumberFormatException e) {
                cloudChosen = 12; //it will never be contained, so it will be asked again
            }
        } while(!cloudAvailable.contains(cloudChosen));
        return cloudChosen; //Arrays on the server side are from 0, to the user we present data starting from 1
    }

    /**
     * Method used to ask the user if and which characterCards they want to play
     * @param characterOptions the character cards present in this expert match
     * @return the character card chosen by the user or null if they chose not to play a card
     */
    private Character askPlayCharacter(List<AbstractCharacterCard> characterOptions) {
        boolean validInput;
        Character chosenCardEnum = null;

        printer.println("Options:");
        for (AbstractCharacterCard card : characterOptions) {
            printer.println(card.getId() + ": price: " + card.getPrice() + ", description: " + card.getDescription());
            if(card.getStudents() != null)
                printer.println(card.getStudents());
        }

        do {
            validInput = true;
            printer.println("Do you want to play any character card?[e.g. NO or MONK]");
            String input = reader.nextLine().toLowerCase();
            switch(input) {
                case "monk" -> chosenCardEnum = Character.MONK;
                case "farmer" -> chosenCardEnum = Character.FARMER;
                case "herald" -> chosenCardEnum = Character.HERALD;
                case "mailman", "magic", "magic mailman"-> chosenCardEnum = Character.MAGIC_MAILMAN;
                case "granny" -> chosenCardEnum = Character.GRANNY;
                case "centaur" -> chosenCardEnum = Character.CENTAUR;
                case "jester" -> chosenCardEnum = Character.JESTER;
                case "knight" -> chosenCardEnum = Character.KNIGHT;
                case "mushroom", "hunter", "mushroom hunter"-> chosenCardEnum = Character.MUSHROOM_HUNTER;
                case "minstrel" -> chosenCardEnum = Character.MINSTREL;
                case "princess" -> chosenCardEnum = Character.PRINCESS;
                case "thief" -> chosenCardEnum = Character.THIEF;
                case "no" -> {}
                default -> validInput = false;
            }
        } while(!validInput);
        return chosenCardEnum;
    }

    /**
     * Method used to ask the user a color for a Character Card
     * @return the chosen PieceColor
     */
    private PieceColor askCharacterCardParamPieceColor() {
        PieceColor color;
        String input;
        do {
            printer.println("write the color of the student");
            input = reader.nextLine().toLowerCase();
            color = convertToColor(input);
        } while(color == null);
        return color;
    }

    /**
     * Method used to ask the user an index of an island for a Character Card
     * @return the chosen index
     */
    private int askCharacterCardParamIsland(){
        int islandIndex;
        do {
            printer.println("write the index of the island: ");
            try {
                islandIndex = Integer.parseInt(reader.nextLine()) - 1;
            } catch (NumberFormatException e) {
                islandIndex = 13; //there is no island with that index, the card will always be asked again if no number is provided
            }
        } while(islandIndex < 0 || islandIndex >= cache.getIslands().size());
        return islandIndex;
    }

    /**
     * Method used to ask the user a list of PieceColor for a Character Card
     * @return the list of PieceColor
     */
    private List<PieceColor> askCharacterCardParamList() {
        List<PieceColor> pieceColorList = new ArrayList<>();
        PieceColor color1;
        PieceColor color2;
        String input;
        do {
            printer.println("write the first color's student you want to move:");
            input = reader.nextLine().toLowerCase();
            color1 = convertToColor(input);
            if(color1 != null) {
                pieceColorList.add(color1);
                printer.println("write the second color's student you want to move:");
                input = reader.nextLine().toLowerCase();
                color2 = convertToColor(input);
                if(color2 != null)
                    pieceColorList.add(color2);
                else {
                    pieceColorList.remove(color1);
                    printer.println("retry writing both color");
                }
            }
        } while (!input.equals("stop"));
        return pieceColorList;
    }

    /**
     * method used to display a generic message (error messages as well) to the user
     * @param toPrint the content that needs to be print
     */
    private void genericPrint(String toPrint) {
        printer.println(toPrint);
    }

    /**
     * method used to print the entire game view
     */
    private void printView() {
        printer.flush(); //TODO NON FUNZIONA

        if(cache.getCharacterCards() != null) {
            String s = "";
            printer.println("Character cards: ");
            for(Character c : cache.getCharacterCards()) {
                s = s.concat(c.toString());
                s = s.concat("  ");
            }
            printer.println(s + '\n');
        }

        if(cache.getClouds() != null) {
            printer.println("The Clouds: ");
            for(Map<PieceColor, Integer> m : cache.getClouds()) {
                int index = cache.getClouds().lastIndexOf(m) + 1;
                String s = "cloud #" + index + ": ";
                for(PieceColor p : PieceColor.values()) {
                    if(m.get(p) != 0)
                        s = s.concat(p + "x" + m.get(p) + " ");
                }
                printer.println(s + '\n');
            }
        }

        if(cache.getGameBoards() != null) {
            printer.println("Each Player's GameBoard: ");
            for(ReducedGameBoard rgb : cache.getGameBoards())
                printer.println(rgb.toString() + '\n'); //TODO I WOULD LIKE TO PRINT FIRST THE GAMEBOARD OF THE PLAYER OWNING THIS VIEW
        }

        if(cache.getIslands() != null) {
            printer.println("The Archipelago: ");
            for(int i = 0; i < cache.getIslands().size(); i++)
                printer.println("island #" + (i+1) + ": " + cache.getIslands().get(i).toString());
        }
        printer.println('\n');
    }

    /**
     * utility method used to convert a string in a PieceColor
     * @param input the string written by the user
     * @return the PieceColor corresponding to the user's input
     */
    private PieceColor convertToColor(String input) {
        PieceColor color;
        switch (input) {
            case "red" -> color = PieceColor.RED;
            case "green" -> color = PieceColor.GREEN;
            case "blue" -> color = PieceColor.BLUE;
            case "yellow" -> color = PieceColor.YELLOW;
            case "pink" -> color = PieceColor.PINK;
            default -> color = null;
        }
        return color;
    }
}
