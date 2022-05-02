package it.polimi.ingsw.am19.Network.Message;

/**
 * Enum for the type of message. It avoids the use of instance of
 */
public enum MessageType {
    //login message
    NEW_MATCH,
    ASK_NUM_PLAYERS,
    NUM_PLAYERS,
    ASK_EXPERT_MATCH,
    MATCH_TYPE,
    ASK_NICKNAME,
    LOGIN_NICKNAME,
    LOGIN_REPLY,
    ASK_TOWER_COLOR,
    LOGIN_TOWER_COLOR,
    ASK_WIZARD_FAMILY,
    LOGIN_WIZARD_FAMILY,

    //resume a match
    ASK_RESUME_MATCH,
    RESUME_MATCH,
    LOGIN_PLAYERS_OPTION,

    //character card message
    PLAY_CHARACTER_CARD,
    REQUEST_COLOR,
    REQUEST_ISLAND,
    REQUEST_COLOR_LIST,
    CHOSEN_COLOR_LIST,

    //planning phase
    PLAYABLE_HELPER_CARD,
    PLAY_HELPER_CARD,

    //action phase part 1
    CHOSEN_COLOR,
    WHERE_MOVE,
    ENTRANCE_TO_ISLAND,
    ENTRANCE_TO_DINING_ROOM,

    //action phase part 2
    HOW_MANY_STEP_MN,
    MN_STEP,

    //action phase part 3
    CHOOSE_CLOUD,
    CHOSEN_CLOUD,

    //end match message
    END_MATCH_MESSAGE,

    //utility message
    ERROR_MESSAGE,
    GENERIC_MESSAGE,

    //ping
    PING_MESSAGE
}
