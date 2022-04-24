package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.ExceedingStudentsPerColorException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalIslandException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class ThreePlayersMatch extends AbstractMatch {
    /**
     * Builds a new Match for 3 player
     */
    public ThreePlayersMatch() {
        super(3);
    }

    /**
     * Initializes all the game component for a new match to start
     */
    @Override
    public void initializeMatch() {
        Bag bag = getBag();

        refillBag(bag, 2);

        //initialize the islands and put students on them
        initializeIslandManager();

        refillBag(bag,24);

        //creating two clouds with no students on them. each cloud could store up to three students
        initializeClouds();

        initializeGameBoards(bag);
    }

    private void refillBag(Bag bag, int num) {
        for (PieceColor color: PieceColor.values()) {
            try {
                bag.refillWith(color, num);
            } catch (ExceedingStudentsPerColorException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeIslandManager() {
        Random randomGenerator = new Random();
        IslandManager islandManager = getIslandManager();
        ListIterator<Island> iterator = islandManager.getIterator();
        MotherNature motherNature = getMotherNature();
        Island initialIsland = null;
        Island oppositeToMotherNature = null;
        int initialMotherNaturePosition = randomGenerator.nextInt(0,12);
        Bag bag = getBag();

        motherNature.setIslandManager(islandManager);

        //find the island corresponding to the initial positioning
        for(int i = 0; i <= initialMotherNaturePosition; i++) {
            initialIsland = iterator.next();
        }
        try {
            motherNature.setCurrPosition(initialIsland);
        } catch (IllegalIslandException e) {
            e.printStackTrace();
        }
        //find the island opposite to motherNature
        for(int i = 0; i < 6; i++) {
            oppositeToMotherNature = iterator.next();
        }
        //put a student to every island except for initialIsland and oppositeToMN
        for(Island island : islandManager.getIslands()) {
            if(island != initialIsland && island != oppositeToMotherNature) {
                if(!bag.isEmpty()){
                    PieceColor color = bag.drawStudent();
                    island.addStudent(color);
                }
            }
        }
    }

    private void initializeClouds() {
        for (int i = 0; i < 3; i++) {
            getClouds().add(new Cloud(4));
        }
    }

    private void initializeGameBoards(Bag bag){
        for (Player player: getPlanningPhaseOrder()){
            GameBoard board = new GameBoard(player,6,getProfessorManager(),9);
            for (int i = 0; i < 9; i++){
                if(!bag.isEmpty()){
                    try {
                        PieceColor color = bag.drawStudent();
                        board.addStudent(color);
                    } catch (TooManyStudentsException e) {
                        e.printStackTrace();
                    }
                }
            }
            getGameBoards().put(player,board);
        }
        Map<Player, GameBoard> gameBoards = getGameBoards();
        getProfessorManager().setGameboards(gameBoards);
    }
}

