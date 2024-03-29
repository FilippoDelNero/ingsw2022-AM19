package it.polimi.ingsw.am19.Network.Message;

/**
 * Enum for the type of message. It avoids the use of 'instance of'
 */
public enum MessageType {
    //login message
    ASK_LOGIN_FIRST_PLAYER,
    REPLY_CREATE_MATCH,
    ASK_NUM_PLAYERS,
    NUM_PLAYERS,
    ASK_EXPERT_MATCH,
    MATCH_TYPE,
    ASK_LOGIN_INFO,
    REPLY_LOGIN_INFO,

    //resume a match
    ASK_RESUME_MATCH,
    RESUME_MATCH,
    LOGIN_PLAYERS_OPTION,

    //character card message
    ASK_CHARACTER_CARD,
    PLAY_CHARACTER_CARD,
    ASK_CHARACTER_PARAMETER,
    REPLY_CHARACTER_PARAMETER,

    //planning phase
    PLAYABLE_HELPER_CARD,
    PLAY_HELPER_CARD,

    //action phase part 1
    ENTRANCE_MOVE,
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
    WAIT_MESSAGE,
    START_ACTION_MESSAGE,

    //ping
    PING_MESSAGE,

    //Update-view related
    UPDATE_CLOUDS,
    UPDATE_GAMEBOARDS,
    UPDATE_ISLANDS,
    UPDATE_CARDS
}
