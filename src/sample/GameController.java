package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import sample.model.GameAnimation;
import sample.model.GameData;

public class GameController implements GameContract.Controller{
    public HBox hboxTop;
    public Label lb_heart;
    public ProgressBar progressGameLevel;
    public Label lb_die;
    public Label lb_plusScore;
    public ImageView img_heart;
    private GameAnimation mGameAnimation;
    public Label lb_levelup;
    public StackPane flowPane;

    public void initialize(){
        mGameAnimation = new GameAnimation();
        Screen screen = Screen.getPrimary();
        flowPane.setMinWidth(screen.getVisualBounds().getWidth());
        flowPane.setMinHeight(screen.getVisualBounds().getHeight());
        hboxTop.setMinWidth(screen.getVisualBounds().getWidth());
        hboxTop.setMinHeight(100);
        img_heart.setImage(new Image("sample/source/heart.png"));
        lb_levelup.setVisible(false);
        lb_die.setVisible(false);
    }

    @Override
    public void showLevelUp() {
        Platform.runLater(() -> {
            lb_levelup.setVisible(true);
            mGameAnimation.showLabel(lb_levelup);
        });
    }

    @Override
    public void hindLevelUp(){
        lb_levelup.setVisible(false);
    }

    @Override
    public void showGameOver() {
        lb_heart.setText("X 0");
        lb_die.setText("GAME OVER");
        lb_die.setVisible(true);
        lb_plusScore.setVisible(false);
    }

    @Override
    public void showGameWin() {
        lb_die.setText("You Win!");
        lb_die.setVisible(true);
        lb_plusScore.setVisible(false);
    }

    @Override
    public void updateScore() {

    }

    @Override
    public void updateHeart() {
        lb_heart.setText("X " + GameData.getHeart());
    }


    @Override
    public void showDie() {
        Platform.runLater(() -> {
            lb_die.setVisible(true);
            mGameAnimation.showLabel(lb_die);
        });
    }

    @Override
    public void showProgressGameLevel() {
        progressGameLevel.setProgress(GameData.getPoint() / GameData.maxPoint);
    }

    @Override
    public void showLabelPlusScore() {
        Platform.runLater(() -> {
            lb_plusScore.setText((int)GameData.getPoint() + "");
//            mGameAnimation.translateScore(lb_plusScore);
            if (GameData.getPoint() > 0)
                progressGameLevel.setProgress(GameData.getPoint() / GameData.maxPoint);
        });
    }
}
