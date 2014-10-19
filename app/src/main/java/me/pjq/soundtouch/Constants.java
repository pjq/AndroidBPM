package me.pjq.soundtouch;

/**
 * Created by pengjianqing on 8/7/14.
 */
public class Constants {
    public static final String ACTION_TAKE_PICTURE = "me.pjq.TAKE_PICTURE";
    public static final String ACTION_TAKE_VIDEO = "me.pjq.TAKE_VIDEO";
    public static final String ACTION_STOP_TAKE_VIDEO = "me.pjq.STOP_TAKE_VIDEO";
    public static final String ACTION_STOP_TAKE_PICTURE = "me.pjq.STOP_TAKE_PICTURE";
    public static final String COMMAND = "command";
    public static final int COMMAND_TAKE_VIDEO = 1;
    public static final int COMMAND_TAKE_PICTURE = COMMAND_TAKE_VIDEO + 1;
    public static final int COMMAND_STOP_TAKE_PICTURE = COMMAND_TAKE_PICTURE + 1;
    public static final int COMMAND_STOP_TAKE_VIDEO = COMMAND_STOP_TAKE_PICTURE + 1;
    public static final int COMMAND_STOP_SERVICE = COMMAND_STOP_TAKE_VIDEO + 1;

}
