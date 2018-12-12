package sample;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.model.GameData;
import sample.model.GameObject;
import sample.model.GameSound;
import sample.utils.GameContrants;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application implements GameContract.View {
    private Pane root;
    private Scene scene;
    private GameContract.Presenter mPresenter;
    private GameContract.Controller mGameController;

    private double mPosstionX = 0;

    private GameObject mGamePlay = new GameObject();
    private AnimationTimer mTimer;
    private long time = 0;

    private List<GameObject> barriers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        initView(primaryStage);
        initObject();
        initEvent();
        mPresenter = new GamePresenter(this);
        mTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mGameController.updateScore();
                mGameController.updateHeart();
                mGameController.showProgressGameLevel();
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

    private void checkCollision(){
        for (GameObject mGameObject : barriers){
            mPresenter.checkCollision(mGamePlay, mGameObject);
        }

    }

    /**
     * Tạo mới View và chưa làm gì
     * @param primaryStage
     */
    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui/sample.fxml"));
        Parent parent = fxmlLoader.load();
        root = new Pane();
        root.setId("root");
        mGameController = fxmlLoader.getController();
        root.getChildren().add(parent);
        primaryStage.setTitle("Feeding Frenzy");
        scene = new Scene(root);
        scene.getStylesheets().add("sample/style.css");
        scene.setCursor(Cursor.NONE);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        GameContrants.WIDTH = bounds.getWidth();
        GameContrants.HEIGHT = bounds.getHeight();
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * truyền các object như mGamePlay và các con cá khác vào
     */
    private void initObject(){
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
//        new Robot().mouseMove(500, 500);
    }

    /**
     * disable event khi game play die
     */
    private void disableEvent(){
        scene.setOnMouseMoved(null);
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
        mGameController.showGameWin();
        mTimer.stop();
        scene.setCursor(Cursor.OPEN_HAND);
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
        GameSound.biteSound();
        removeNode(mGameObject);
        GameData.pushPoint();
        mGameController.showLabelPlusScore();
        if (GameData.getPoint() == GameData.maxPoint) finishGame();
        if (GameData.getPoint() == GameData.pointLevelSmall
                || GameData.getPoint() == GameData.pointLevelNormal) pushLevel(GameData.getScaleLevel());
    }

    @Override
    public void collisionFail() {
        GameSound.biteSound();
        GameData.subHeart();
        mGamePlay.getNode().setVisible(false);
        mGamePlay.setDie();

        if (GameData.getHeart() == 0) { // hết mạng
            mGameController.showGameOver();
            mTimer.stop();
            scene.setCursor(Cursor.OPEN_HAND);
        } else {
            GameSound.bubbleSound();
            mPresenter.retryGamePlay(mGamePlay, this);
        }
    }


}
