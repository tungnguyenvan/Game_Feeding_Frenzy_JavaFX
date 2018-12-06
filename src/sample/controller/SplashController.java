package sample.controller;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.GameContract;
import sample.SplashContract;
import sample.model.GameAnimation;

public class SplashController implements SplashContract.Controller {
    public VBox img_start;
    public Label lb_start;
    public ImageView img_logo;
    private GameContract.View mView;

    public void initialize(){
        GameAnimation gameAnimation = new GameAnimation();
        gameAnimation.translateLogoSplash(img_logo);
    }

    @Override
    public void setView(GameContract.View mView) {
        this.mView = mView;
    }

    public void playGame(MouseEvent mouseEvent) throws Exception {
        mView.startGame();
    }
}
