package sample.model;


import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class GameSound {
    private static final String BITE_SOUND = "src/sample/source/sound/bite.mp3";
    private static final String BUBBLE_SOUND = "src/sample/source/sound/Bubbles.mp3";
    private static final String WAITING_SOUND = "src/sample/source/sound/waiting.mp3";
    private static final String WIN_SOUND = "src/sample/source/sound/sound_win.mp3";
    private static final String OVER_SOUND = "src/sample/source/sound/sound_over.mp3";

    private Media mMedia;
    private MediaPlayer mMediaPlayer;


    public void biteSound(){
        playSount(BITE_SOUND);
    }

    public void bubbleSound(){
        playSount(BUBBLE_SOUND);
    }

    public void gameWaitingSound(){
        Media mMedia = new Media(new File(WAITING_SOUND).toURI().toString());
        MediaPlayer mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mMediaPlayer.setOnReady(() -> mMediaPlayer.play());
    }

    public void gameWin(){
        playSount(WIN_SOUND);
    }

    public void gameOver(){
        playSount(OVER_SOUND);
    }

    private void playSount(String path){
        mMedia = new Media(new File(path).toURI().toString());
        mMediaPlayer = new MediaPlayer(mMedia);
        mMediaPlayer.play();
    }
}
