package sample.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.GameContract;
import sample.model.GameData;

public class TryGameController {
    private Stage primaryStage;
    private GameContract.View mView;

    public void initSuperGame(Stage primaryStage, GameContract.View mView){
        this.primaryStage = primaryStage;
        this.mView = mView;
    }


    public void tryGame(MouseEvent mouseEvent) throws Exception{
        ((Node)mouseEvent.getSource()).getScene().getWindow().hide();
        mView.startGame();
        GameData.revertData();
        System.out.printf(GameData.getPoint() +"");
    }

    public void quitGame(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
