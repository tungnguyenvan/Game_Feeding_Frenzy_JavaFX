package sample.model;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class GameSound {
    public static final String BITE_SOUND = "source/sound/bite.mp3";
    public static final String BUBBLE_SOUND = "sample/source/sound/Bubbles.mp3";
    private static Media mMedia;
    private static MediaPlayer mMediaPlayer;

    public static void biteSound(String url){
        mMedia  = new Media(new File(url).toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.play();
    }
}
