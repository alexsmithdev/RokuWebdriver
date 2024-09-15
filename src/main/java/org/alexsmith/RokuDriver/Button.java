package org.alexsmith.RokuDriver;

public enum Button {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right"),
    OK("ok"),
    BACK("back"),
    PLAY_PAUSE("play/pause"),
    HOME("home"),
    FF("fast forward"),
    REW("rewind");

    public final String action;

    private Button(String action) {
        this.action = action;
    }
}

