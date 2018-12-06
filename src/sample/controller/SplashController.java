package sample.controller;

import javafx.scene.input.MouseEvent;
import sample.GameContract;
import sample.SplashContract;

public class SplashController implements SplashContract.Controller {
    private GameContract.View mView;

    @Override
    public void setView(GameContract.View mView) {
        this.mView = mView;
    }

    public void playGame(MouseEvent mouseEvent) throws Exception {
        mView.startGame();
    }
}
