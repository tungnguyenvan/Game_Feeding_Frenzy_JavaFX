package sample.model;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import sample.GameContract;
import sample.utils.GameContrants;

public class GameAnimation {
    public static final int DURATION_SMALL = 10;
    public static final int DURATION_NORMAL = 15;
    public static final int DURATION_BIG = 20;
    public static final int DURATION_LARGE = 25;
    public static final int DURATION_ROTATE_WAVE = 400;



    /**
     * Lấy kiểu của cá để trả về thời gian bơi hợp lí
     * @param type
     * @return
     */
    private int getDuration(int type){
        int duration = 0;
        switch (type){
            case GameObject.FISH_SMALL:
                duration = DURATION_SMALL;
                break;
            case GameObject.FISH_NORMAL:
                duration = DURATION_NORMAL;
                break;
            case GameObject.FISH_BIG:
                duration = DURATION_BIG;
                break;
            case GameObject.FISH_LARGE:
                duration = DURATION_LARGE;
                break;
        }
        return duration;
    }

    /**
     * di chuyển từ a -> b
     * @param mGameConstract
     * @param gameObject
     * @param isFromRight
     * @param type
     */
    public void translateTransition(GameContract.View mGameConstract, GameObject gameObject, boolean isFromRight, int type){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(getDuration(type)), gameObject.getNode());
        if (isFromRight){
            translateTransition.setFromX(GameContrants.WIDTH + 100);
            translateTransition.setToX(-100);
        }else {
            translateTransition.setFromX(-100);
            translateTransition.setToX(GameContrants.WIDTH + 100);
        }
        translateTransition.play();
        translateTransition.setOnFinished((event -> mGameConstract.removeNode(gameObject)));
    }

    /**
     * Animation vẫy đuôi
     * @param node
     */
    public void wave(Node node){
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(DURATION_ROTATE_WAVE), node);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(15);
        rotateTransition.setToAngle(-15);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();
    }

    /**
     * hiệu ứng chữ Levelup
     * @param node
     */
    public void showLabel(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(1);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500), node);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setFromZ(0);
        scaleTransition.setToX(1);
        scaleTransition.setToZ(1);
        scaleTransition.setToY(1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1500), node);
        translateTransition.setFromY(500);
        translateTransition.setToY(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setCycleCount(1);

        fadeTransition.play();
        scaleTransition.play();
        translateTransition.play();
        translateTransition.setOnFinished((event) -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                hindLabel(node);
            }
        });
    }

    /**
     *
     * @param node
     * @param mView
     */
    public void animationInitGamePlay(Node node, GameContract.View mView){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), node);
        node.setVisible(true);
        translateTransition.setFromY(100);
        translateTransition.setToY(500);
        translateTransition.setAutoReverse(false);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.setOnFinished(event -> mView.initEvent());
    }

    /**
     * Ẩn label levelup khi animation hoạt động thành công
     */
    public void hindLabel(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setAutoReverse(false);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> node.setVisible(false));
    }

    public void translateScore(Node node,double fromX, double fromY, double toX, double toY){
        node.setVisible(true);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), node);
        translateTransition.setFromX(fromX);
        translateTransition.setFromY(fromY);
        translateTransition.setToX(toX);
        translateTransition.setToY(toY);
        translateTransition.setAutoReverse(false);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        translateTransition.setOnFinished(event -> hindLabel(node));
    }
}
