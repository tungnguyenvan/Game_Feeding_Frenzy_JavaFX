package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private GameAnimation mGameAnimation;
    public Label lb_levelup;
    public StackPane flowPane;

    public void initialize(){
        mGameAnimation = new GameAnimation();
        Screen screen = Screen.getPrimary();
        flowPane.setMinWidth(screen.getVisualBounds().getWidth());
        flowPane.setMinHeight(screen.getVisualBounds().getHeight());
        lb_heart.setText(GameData.getHeart() + "X");
        hboxTop.setMinWidth(screen.getVisualBounds().getWidth());
        hboxTop.setMinHeight(100);
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

    public void updateScore(){

    }

    @Override
    public void showDie() {
        Platform.runLater(() -> {
            lb_die.setVisible(true);
            mGameAnimation.showLabel(lb_die);
            lb_heart.setText(GameData.getHeart() + "X");
        });
    }

    @Override
    public void showLabelPlusScore() {
        Platform.runLater(() -> {
            lb_plusScore.setText(String.valueOf(GameData.getPoint()));
            mGameAnimation.translateScore(lb_plusScore);
            if (GameData.getPoint() > 0)
                progressGameLevel.setProgress(GameData.getPoint() / GameData.maxPoint);
        });
    }
}
