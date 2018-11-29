package sample.model;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class GameSound {
    private static final String BITE_SOUND = "src/sample/source/sound/bite.mp3";
    private static final String BUBBLE_SOUND = "src/sample/source/sound/Bubbles.mp3";
    private static Media mMedia;
    private static MediaPlayer mMediaPlayer;

    private static void play(){
        mMediaPlayer.setAutoPlay(true);
        mMediaPlayer.setVolume(1);
        mMediaPlayer.play();
    }

    public static void biteSound(){
        mMedia = new Media(new File(BITE_SOUND).toURI().toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        play();
    }

    public static void bubbleSound(){
        mMedia = new Media(new File(BUBBLE_SOUND).toURI().toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        play();
    }

    public static void gameWaitingSound(){

    }
}
