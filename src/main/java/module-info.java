module it.polimi.ingsw.am19 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens it.polimi.ingsw.am19 to javafx.fxml;
    exports it.polimi.ingsw.am19;
    exports it.polimi.ingsw.am19.Utilities;
    opens it.polimi.ingsw.am19.Utilities to javafx.fxml;
    exports it.polimi.ingsw.am19.Network.Server;
    opens it.polimi.ingsw.am19.Network.Server to javafx.fxml;

    exports it.polimi.ingsw.am19.Network.Message;
    exports it.polimi.ingsw.am19.Network.ReducedObjects;
    exports it.polimi.ingsw.am19.Model.BoardManagement;
    exports it.polimi.ingsw.am19.Model.Utilities;
    exports it.polimi.ingsw.am19.Model.CharacterCards;
    exports it.polimi.ingsw.am19.Model.InfluenceStrategies;
    exports it.polimi.ingsw.am19.Model.Exceptions;
    exports it.polimi.ingsw.am19.View.GUI;
    opens it.polimi.ingsw.am19.View.GUI to javafx.fxml;
}