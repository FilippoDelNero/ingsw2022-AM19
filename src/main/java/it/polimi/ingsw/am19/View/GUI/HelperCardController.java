package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Network.Message.ReplyHelperCardMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class HelperCardController implements SceneController{
    private Gui gui;
    private List<HelperCard> helperCardList;

    public void setHelperCardList(List<HelperCard> helperCardList) {
        this.helperCardList = helperCardList;
        List<Integer> availableCard = new ArrayList<>();
        for(HelperCard hc: helperCardList)
            availableCard.add(hc.getNextRoundOrder());
        for(int i = 1; i<11; i++){
            if(!availableCard.contains(i))
                setInvisible(getImage(i));
        }
    }

    @FXML
    private ImageView helperCard1;

    @FXML
    private ImageView helperCard10;

    @FXML
    private ImageView helperCard2;

    @FXML
    private ImageView helperCard3;

    @FXML
    private ImageView helperCard4;

    @FXML
    private ImageView helperCard5;

    @FXML
    private ImageView helperCard6;

    @FXML
    private ImageView helperCard7;

    @FXML
    private ImageView helperCard8;

    @FXML
    private ImageView helperCard9;

    @FXML
    void initialize(){
    }

    @FXML
    void useHelperCard1(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,1,1)));
    }

    @FXML
    void useHelperCard10(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,10,5)));
    }

    @FXML
    void useHelperCard2(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,2,1)));
    }

    @FXML
    void useHelperCard3(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,3,2)));
    }

    @FXML
    void useHelperCard4(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,4,2)));
    }

    @FXML
    void useHelperCard5(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,5,3)));
    }

    @FXML
    void useHelperCard6(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,6,3)));
    }

    @FXML
    void useHelperCard7(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,7,4)));
    }

    @FXML
    void useHelperCard8(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,8,4)));
    }

    @FXML
    void useHelperCard9(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyHelperCardMessage(gui.getNickname(),new HelperCard(null,9,5)));
    }

    @FXML
    void onMouseEnteredEvent(MouseEvent event) {
        event.getPickResult().getIntersectedNode().setCursor(Cursor.HAND);
    }

    @FXML
    void onMouseExitedEvent(MouseEvent event) {

    }

    private ImageView getImage (int maxRoundOrder){
        switch (maxRoundOrder){
            case 1 -> {
                return helperCard1;
            }
            case 2 -> {
                return helperCard2;
            }
            case 3 -> {
                return helperCard3;
            }
            case 4 -> {
                return helperCard4;
            }
            case 5 -> {
                return helperCard5;
            }
            case 6 -> {
                return helperCard6;
            }
            case 7 -> {
                return helperCard7;
            }
            case 8 -> {
                return helperCard8;
            }
            case 9 -> {
                return helperCard9;
            }
            case 10 -> {
                return helperCard10;
            }
        }
        return helperCard1;
    }

    public void setInvisible(ImageView image){
        image.setOpacity(0.50);
        image.setDisable(true);
    }

    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }
}
