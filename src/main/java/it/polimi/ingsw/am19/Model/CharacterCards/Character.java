package it.polimi.ingsw.am19.Model.CharacterCards;

/**
 * Enum for the 12 characters card, with price and a description of the effect that they cause
 */
public enum Character {
    MONK(1,"Take 1 student from this card and place it on an Island of your choice. Then, draw a new Student from the Bag and place it on this card.",true,true,false),
    FARMER(2, "During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.",false,false,false),
    HERALD(3,"Choose an Island and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends hre movement will also be resolved.",false, true,false),
    MAGIC_MAILMAN(1,"You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.", false, false, false),
    GRANNY(2,"Place a No Entry tile on an Island of your choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card DO NOT calculate influence on that island, or place any Towers.",false,true,false),
    CENTAUR(3,"When resolving a Conquering on an Island, Towers do not count towards influence.",false,false,false),
    JESTER(1,"You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.",false,false,true),
    KNIGHT(2,"During the influence calculation this turn, you count as having 2 more influence.",false,false,false),
    MUSHROOM_HUNTER(3,"Choose a color of Student; during the influence calculation this turn, that color adds no influence.",true,false,true),
    MINSTREL(1,"You may exchange up to 2 Students between your Entrance and your Dining Room.",false,false,true),
    PRINCESS(2,"Take 1 Student from this card and place it in your Dining Room. Then, draw a new Student from the Bag and place it on this card.",true,false,false),
    THIEF(3,"Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag. If any player has fewer than 3 Students of that type, return as many Students as they have.",true,false,false);

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequiringPieceColor() {
        return requiringPieceColor;
    }

    public boolean isRequiringIsland() {
        return requiringIsland;
    }

    public boolean isRequiringPieceColorList() {
        return requiringPieceColorList;
    }

    private final int price;
    private final String description;
    private final boolean requiringPieceColor;
    private final boolean requiringIsland;
    private final boolean requiringPieceColorList;

    Character(int price, String description, boolean requirePieceColor, boolean requireIsland, boolean requirePieceColorList){
        this.price=price;
        this.description=description;
        this.requiringPieceColor=requirePieceColor;
        this.requiringIsland=requireIsland;
        this.requiringPieceColorList=requirePieceColorList;
    }
}

