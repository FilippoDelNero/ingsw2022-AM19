package it.polimi.ingsw.am19.Model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.CharacterCards.*;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Utilities.CoinManager;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

/**
 * Class for wrapping an AbstractMatch to make it support additional expert match rules
 */
public class ExpertMatchDecorator extends MatchDecorator{
    /**
     * A list of current available characterCards
     */
    private List<AbstractCharacterCard> characterCards;

    private final CoinManager coinManager;
    /**
     * Builds a new ExpertMatchDecorator that wraps the match passed as argument
     * @param match is the match to wrap
     */
    public ExpertMatchDecorator(AbstractMatch match) {
        super(match);
        this.coinManager = new CoinManager();
        this.characterCards = new ArrayList<>();
        this.characterCards.add(new StudentToIslandCard(wrappedMatch));
        this.characterCards.add(new TakeProfessorCard(wrappedMatch));
        this.characterCards.add(new ExtraInfluenceCard(wrappedMatch));
        this.characterCards.add(new MotherNaturePlusTwoCard(wrappedMatch));
        this.characterCards.add(new NoEntryTileCard(wrappedMatch));
        this.characterCards.add(new NoTowersInfluenceCard(wrappedMatch));
        this.characterCards.add(new ThreeStudentToEntryCard(wrappedMatch));
        this.characterCards.add(new PlusTwoInfluenceCard(wrappedMatch));
        this.characterCards.add(new NoColorInfluenceCard(wrappedMatch));
        this.characterCards.add(new StudentToHallCard(wrappedMatch));
        this.characterCards.add(new ThreeToBagCard(wrappedMatch));
        this.characterCards.add(new EntranceToDiningRoomCard(wrappedMatch));
    }

    private void drawCharacterCards(){
        Random randomGenerator = new Random();
        int firstCardIndex = randomGenerator.nextInt(0,12);

        int secondCardIndex = randomGenerator.nextInt(0,12);
        while (secondCardIndex == firstCardIndex){
            secondCardIndex = randomGenerator.nextInt(0,12);
        }

        int thirdCardIndex = randomGenerator.nextInt(0,12);
        while (thirdCardIndex == firstCardIndex || thirdCardIndex == secondCardIndex){
            thirdCardIndex = randomGenerator.nextInt(0,12);
        }

        List<AbstractCharacterCard> chosenCards = new ArrayList<>();
        chosenCards.add(characterCards.get(firstCardIndex));
        chosenCards.add(characterCards.get(secondCardIndex));
        chosenCards.add(characterCards.get(thirdCardIndex));

        this.characterCards = chosenCards;
    }

    private void activateInitialActions(){
        for (AbstractCharacterCard card: characterCards){
            card.initialAction();
        }
    }

    /**
     * Calls the initializeMatch() of the wrapped AbstractMatch and draws 3 CharacterCards, activating their initial action. It also supplies the Players with a coin
     */
    @Override
    public void initializeMatch(){
        wrappedMatch.initializeMatch();
        drawCharacterCards();
        activateInitialActions();

        for (Player player: wrappedMatch.getPlanningPhaseOrder()) {
            player.setCoinManager(coinManager);
            player.addCoins(1);
        }
    }

    /**
     * Lets the current Player play a CharacterCard, if he can afford it
     * @param card is the CharacterCard to be played
     * @param color is the PieceColor needed by the card to activate its effect. It is null if not needed
     * @param island is the Island needed by the card to activate its effect. It is null if not needed
     * @throws InsufficientCoinException when trying to make the current player spend more coins than possessed
     */
    //TODO rivedere come richiedere parametri della activateEffect()
    public void playCard(AbstractCharacterCard card,PieceColor color,Island island, List<PieceColor> extraColors) throws InsufficientCoinException {
        Player currPlayer = wrappedMatch.getCurrPlayer();
        int cardPrice = card.getPrice();
        try {
            currPlayer.removeCoins(cardPrice);
        } catch (InsufficientCoinException e) {
            throw new InsufficientCoinException(e.getMessage(),e.getCause());
        }
        card.activateEffect(island,color,extraColors);
    }

    public List<AbstractCharacterCard> getCharacterCards() {
        return characterCards;
    }

    public CoinManager getCoinManager() {
        return coinManager;
    }
}
