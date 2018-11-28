package sample;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import sample.model.GameAnimation;
import sample.model.GameData;
import sample.model.GameObject;

import java.util.List;
import java.util.Random;

public class GamePresenter implements GameContract.Presenter{
    private GameContract.View mView;
    private Random mRandom;

    public GamePresenter(GameContract.View mView) {
        this.mView = mView;
        mRandom = new Random();
    }

    @Override
    public void retryGamePlay(GameObject gamePlay, GameContract.View mView){
        Platform.runLater(() -> {
            GameAnimation gameAnimation = new GameAnimation();
            gameAnimation.animationInitGamePlay(gamePlay.getNode(), mView);
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    gamePlay.setSurvive();
                }
            });
            thread.start();
        });
    }

    @Override
    public void removeGameObject(GameObject mGameObject, Pane pane, List<GameObject> barriers) {
        Platform.runLater(() -> {
            pane.getChildren().remove(mGameObject.getNode());
            barriers.remove(mGameObject.getNode());
        });
    }

    @Override
    public void removeGamePlay(GameObject gamePlay, Pane mPane){
        if (gamePlay.isSuvive()) {
            Platform.runLater(() -> {
                mPane.getChildren().remove(gamePlay.getNode());
                gamePlay.setDie();
            });
        }
    }

    @Override
    public void initGameBarrier(Pane mPane, List<GameObject> barriers) {
        Platform.runLater(() -> {
            GameObject mGameObject = new GameObject();
            mPane.getChildren().add(mGameObject.gameBarrier(1 + mRandom.nextInt((GameObject.FISH_LARGE - 1)), mView));
            barriers.add(mGameObject);
        });
    }

    @Override
    public void checkCollision(GameObject gamePlay, GameObject gameCollision) {
        if (gameCollision.isSuvive()) {
            Platform.runLater(() -> {
                if (isCollision(gamePlay, gameCollision)) {
                    if (GameData.getLevel() >= gameCollision.getType() && gamePlay.isSuvive())
                        handleCollisionSuccess(gameCollision);
                    else handleCollisionFail(gamePlay);
                }
            });
        }
    }

    private boolean isCollision(GameObject gamePlay, GameObject gameCollision){
        return gamePlay.getNode().getBoundsInParent().getMaxX() >= gameCollision.getNode().getBoundsInParent().getMinX() + 5 &&
                gamePlay.getNode().getBoundsInParent().getMinX() <= gameCollision.getNode().getBoundsInParent().getMaxX() - 5 &&
                gamePlay.getNode().getBoundsInParent().getMaxY() >= gameCollision.getNode().getBoundsInParent().getMinY() + 10 &&
                gamePlay.getNode().getBoundsInParent().getMinY() <= gameCollision.getNode().getBoundsInParent().getMaxY() - 10;
    }

    private void handleCollisionSuccess(GameObject gameObject){
        mView.collisionSuccess(gameObject);
    }

    private void handleCollisionFail(GameObject gamePlay){
        if (gamePlay.isSuvive()) mView.collisionFail();
    }
}
