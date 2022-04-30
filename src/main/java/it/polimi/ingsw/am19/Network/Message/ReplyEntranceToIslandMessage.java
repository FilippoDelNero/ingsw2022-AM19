package it.polimi.ingsw.am19.Network.Message;

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
