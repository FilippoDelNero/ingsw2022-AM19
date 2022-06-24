package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.ExceedingStudentsPerColorException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalIslandException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class TwoPlayersMatch extends AbstractMatch {
    /**
     * Builds a new Match for 2 player
     */
    public TwoPlayersMatch() {
        super(2);
    }

    /**
     * Initializes all the game component for a new match to start
     * The match reference is passed to those classes that could potentially determine the end of the match or the start of the final round
     * In this way they can notify the occurrence of these conditions
     */
    @Override
    public void initializeMatch() {
        Bag bag = getBag();

        bag.removeAll();
        refillBag(bag, 2);

        //initialize the islands and put students on them
        initializeIslandManager();
        islandManager.addObserver(this);

        refillBag(bag,24);
        bag.addObserver(this);

        //creating two clouds with no students on them. each cloud could store up to three students
        initializeClouds();
        for (Cloud c : clouds)
            c.addObserver(this);

        initializeGameBoards(bag);
        for (Player player: getGameBoards().keySet()) {
            player.addObserver(this);
            getGameBoards().get(player).addObserver(this);
        }

        motherNature.addObserver(this);
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
        for (int i = 0; i < 2; i++) {
            getClouds().add(new Cloud(3));
        }
    }

    private void initializeGameBoards(Bag bag){
        for (Player player: getPlanningPhaseOrder()){
            GameBoard board = new GameBoard(player,8,getProfessorManager(),7);
            for (int i = 0; i < 7; i++){
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

