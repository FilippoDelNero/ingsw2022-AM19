package it.polimi.ingsw.am19.View.Cli;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli {
    private final PrintStream printer;
    Thread inputThread;

    public Cli() {
        printer = System.out;
    }

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

    public void init() {
        printer.println("######## ########  ##    ##    ###    ##    ## ######## ####  ###### ");
        printer.println("##       ##     ##  ##  ##    ## ##   ###   ##    ##     ##  ##    ## ");
        printer.println("##       ##     ##   ####    ##   ##  ####  ##    ##     ##  ##       ");
        printer.println("######   ########     ##    ##     ## ## ## ##    ##     ##   ######  ");
        printer.println("##       ##   ##      ##    ######### ##  ####    ##     ##        ## ");
        printer.println("##       ##    ##     ##    ##     ## ##   ###    ##     ##  ##    ## ");
        printer.println("######## ##     ##    ##    ##     ## ##    ##    ##    ####  ######  ");
        printer.println("\n");
        printer.println("Welcome!\n\n");
    }

    public int newMatchNumOfPlayers() throws ExecutionException {
        String input;
        printer.println("There are no matches to resume, we will create a new one:");
        do {
            printer.println("How many players? [2, 3]");
            input = readLine();
        } while(!input.equals("2") && !input.equals("3"));
        return Integer.parseInt(input);
    }

    public boolean newMatchIsExpert() throws ExecutionException {
        String input;
        do  {
            printer.println("Do you want to play an expert match? [yes, no]");
            input = readLine();
        } while (!input.equals("yes") && !input.equals("no"));
        return input.equals("yes");
    }

    public String askNickname() throws ExecutionException {
        String input;
        printer.println("Insert a nickname: ");
        input = readLine();
        return input;
    }

    public WizardFamily askWizardFamily (List<WizardFamily> availableWizardFamilies) throws ExecutionException{
        String input;
        WizardFamily wizardFamily = null;
        do {
            printer.println("Choose a Wizard Family from" + availableWizardFamilies);
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

    public TowerColor askTowerColor (List<TowerColor> availableTowerColor) throws ExecutionException{
        String input;
        TowerColor towerColor = null;
        do {
            printer.println("Choose a Tower's color from" + availableTowerColor);
            input=readLine();
            switch (input.toLowerCase()) {
                case "black" -> towerColor = TowerColor.BLACK;
                case "white" -> towerColor = TowerColor.WHITE;
                case "grey" -> towerColor = TowerColor.GREY;
            }
        } while(!availableTowerColor.contains(towerColor));
        return towerColor;
    }

    public void error(String errorMsg) {
        printer.println(errorMsg);
    }

    public void genericPrint(String toPrint) {
        printer.println(toPrint);
    }
}
