package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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
import sample.model.Sponges;
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
    private Sponges mSponges = new Sponges();

    private List<GameObject> barriers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        initView(primaryStage);
        initObject();
        mPresenter = new GamePresenter(this);
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
    public void initEvent() {
        scene.setOnMouseMoved(event -> {
            if (event.getSceneX() < mPosstionX)
                mGamePlay.rotateNodeToRight(false);
            else if (event.getSceneX() > mPosstionX)
                mGamePlay.rotateNodeToRight(true);
            mGamePlay.setPossition(event.getSceneX(), event.getSceneY());
            mPosstionX = event.getSceneX() + 1;
        });
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mouseMove(mGamePlay.getX(), mGamePlay.getY());
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });
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
        mGamePlay.levelUp(scale);
        GameData.pushLevel();
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
        removeNode(mGameObject);
        GameData.pushPoint();
        mGameController.showLabelPlusScore(mGamePlay.getNode().getTranslateX(), mGamePlay.getNode().getTranslateY());
        if (GameData.getPoint() == GameData.pointLevelSmall
                || GameData.getPoint() == GameData.pointLevelNormal) pushLevel(GameData.getScaleLevel());
    }

    @Override
    public void collisionFail() {
        disableEvent();
        mGamePlay.getNode().setVisible(false);
        mGameController.showDie();
        mGamePlay.setDie();
        mPresenter.retryGamePlay(mGamePlay, this);
    }
}
