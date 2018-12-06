package sample.model;


import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class GameSound {
    private static final String BITE_SOUND = "src/sample/source/sound/bite.mp3";
    private static final String BUBBLE_SOUND = "src/sample/source/sound/Bubbles.mp3";
    private static final String WAITING_SOUND = "src/sample/source/sound/waiting.mp3";
    private Media mMedia;
    private MediaPlayer mMediaPlayer;


    public void biteSound(){
        mMedia = new Media(new File(BITE_SOUND).toURI().toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.play();
    }

    public void bubbleSound(){
        mMedia = new Media(new File(BUBBLE_SOUND).toURI().toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.play();
    }

    public void gameWaitingSound(){
        Media mMedia = new Media(new File(WAITING_SOUND).toURI().toString());
        MediaPlayer mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mMediaPlayer.setOnReady(() -> mMediaPlayer.play());
    }

    public static void gamePauseSound(){

    }

    public static void gamePlaySound(){

    }
}
