package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskWhereMove when the player want to move the student on an Island
 * Contains the num of the Island chosen
 */
public class ReplyEntranceToIslandMessage extends Message {
    private final int island;

    public ReplyEntranceToIslandMessage(String nickname, int island) {
        super(nickname, MessageType.ENTRANCE_TO_ISLAND);
        this.island = island;
    }

    @Override
    public String toString() {
        return "ReplyEntranceToIslandMessage{" +
                "nickname="+ getNickname() +
                "island=" + island +
                '}';
    }
}
