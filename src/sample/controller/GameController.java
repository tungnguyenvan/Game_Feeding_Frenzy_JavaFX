package sample.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.GameContract;
import sample.model.GameAnimation;
import sample.model.GameData;

public class GameController implements GameContract.Controller{
    public HBox hboxTop;
    public Label lb_heart;
    public ProgressBar progressGameLevel;
    public Label lb_die;
    public Label lb_plusScore;
    public Label lb_gameover;
    public Label lb_finish;
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
        lb_gameover.setVisible(false);
        lb_finish.setVisible(false);
    }

    @Override
    public void showLevelUp() {
        Platform.runLater(() -> {
            lb_levelup.setVisible(true);
            mGameAnimation.showLabel(lb_levelup, true);
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
            mGameAnimation.showLabel(lb_die, true);
            lb_heart.setText(GameData.getHeart() + "X");
        });
    }

    @Override
    public void showLabelPlusScore() {
        Platform.runLater(() -> {
            lb_plusScore.setText(String.valueOf(GameData.getPoint()));
            mGameAnimation.translateScore(lb_plusScore);
            if (GameData.getPoint() > 0) {;
                progressGameLevel.setProgress(GameData.indicator);
            }
        });
    }

    @Override
    public void showGameOver() {
        lb_heart.setText("0X");
        lb_gameover.setVisible(true);
        Platform.runLater(() ->
            mGameAnimation.showLabel(lb_gameover, false));
    }

    @Override
    public void showFinish(Stage primaryStage, Class clazz, GameContract.View mView) throws Exception{
        lb_finish.setVisible(true);
        Platform.runLater(() -> mGameAnimation.showLabel(lb_finish, false));
        GamePlayController.initGameOver(primaryStage, clazz, mView);
    }
}
