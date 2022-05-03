package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.List;

/**
 * Reply with the parameter of the activateEffect of a CharacterCard
 */
public class ReplyCharacterParameterMessage extends Message {
    private final PieceColor color;
    private final int island;
    private final List<PieceColor> colorList;
    private ClientManager clientManager = null;

    public ReplyCharacterParameterMessage(String nickname, PieceColor color, int island, List<PieceColor> colorList) {
        super(nickname, MessageType.REPLY_CHARACTER_PARAMETER);
        this.color = color;
        this.island = island;
        this.colorList = colorList;
    }

    public PieceColor getColor() {
        return color;
    }

    public int getIsland() {
        return island;
    }

    public List<PieceColor> getColorList() {
        return colorList;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public String toString() {
        return "ReplyCharacterParameterMessage{" +
                "Nickname=" + getNickname() +
                "color=" + color +
                ", island=" + island +
                ", colorList=" + colorList +
                ", clientManager=" + clientManager +
                '}';
    }
}
