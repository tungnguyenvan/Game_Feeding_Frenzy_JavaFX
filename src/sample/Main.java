package sample;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.controller.GamePlayController;
import sample.model.GameData;
import sample.model.GameObject;
import sample.model.GameSound;
import sample.model.Sponges;
import sample.utils.GameContrants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application implements GameContract.View {
    private Stage primaryStage;
    private Pane root;
    private Scene scene;
    private GameContract.Presenter mPresenter;
    private GameContract.Controller mGameController;
    private SplashContract.Controller mSplashController;

    private double mPosstionX = 0;

    private GameObject mGamePlay = new GameObject();
    private AnimationTimer mTimer;
    private long time = 0;
    private GameSound mGameSound = new GameSound();

    private List<GameObject> barriers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        mPresenter = new GamePresenter(this);
        mGameSound.gameWaitingSound();
        GamePlayController.startSplah(primaryStage, this.getClass(), mSplashController, this);
    }

    private void checkCollision(){
        for (GameObject mGameObject : barriers){
            mPresenter.checkCollision(mGamePlay, mGameObject);
        }
    }

    private void onUpdate(){
        mTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mGameController.updateScore();
                checkCollision();
                if (time == 100){
                    updateGame();
                    time = 0;
                }
                time++;
            }
        };
        mTimer.start();
    }


    /**
     * truyền các object như mGamePlay và các con cá khác vào
     */
    private void initObject(){
        mGameSound.bubbleSound();
        root.getChildren().add(mGamePlay.gamePlay(this));
    }

    /**
     * init event khi chuột di chuyển thì thực hiện hành động cho các chạy theo
     */
    @Override
    public void initEvent() throws AWTException {
        scene.setOnMouseMoved(event -> {
            if (event.getSceneX() < mPosstionX)
                mGamePlay.rotateNodeToRight(false);
            else if (event.getSceneX() > mPosstionX)
                mGamePlay.rotateNodeToRight(true);
            mGamePlay.setPossition(event.getSceneX(), event.getSceneY());
            mPosstionX = event.getSceneX() + 1;
        });
    }



    /**
     * Mỗi khi AnimationTimer chạy thì sẽ gọi
     */
    private void updateGame(){
        initBarrier();
    }


    /**
     * Tạo vật cản
     */
    private void initBarrier(){
        mPresenter.initGameBarrier(root, barriers);
    }


    /**
     * Level up
     */
    private void pushLevel(int scale){
        mGameController.showLevelUp();
        GameData.pushLevel();
        mGamePlay.levelUp(scale);
    }

    private void finishGame(){

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void removeNode(GameObject mGameObject) {
        mGameObject.setDie();
        mPresenter.removeGameObject(mGameObject, root, barriers);
    }

    @Override
    public void collisionSuccess(GameObject mGameObject) {
        mGameSound.biteSound();
        removeNode(mGameObject);
        GameData.pushPoint();
        mGameController.showLabelPlusScore();
        if (GameData.getPoint() == GameData.maxPoint) finishGame();
        if (GameData.getPoint() == GameData.pointLevelSmall
                || GameData.getPoint() == GameData.pointLevelNormal) pushLevel(GameData.getScaleLevel());
    }

    @Override
    public void collisionFail() {
        mGameSound.biteSound();
        mGameSound.bubbleSound();
        GameData.subHeart();
        mGamePlay.getNode().setVisible(false);
        mGameController.showDie();
        mGamePlay.setDie();
        mPresenter.retryGamePlay(mGamePlay, this);
    }

    @Override
    public void startGame() throws Exception {
        GamePlayController.initViewGamePlay(primaryStage, this.getClass(), root, scene, mGameController, this);
    }

    @Override
    public void startGameSuccess(Scene scene, Pane root, GameContract.Controller mController) throws Exception {
        this.scene = scene;
        this.root = root;
        this.mGameController = mController;
        initObject();
        initEvent();
        onUpdate();
    }
}
