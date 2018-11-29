package sample;

import javafx.scene.layout.Pane;
import sample.model.GameObject;

import java.awt.*;
import java.util.List;

public interface GameContract {
    interface View{
        /**
         * init event onMouseMoved
         */
        void initEvent() throws AWTException;

        /**
         * Xóa object để giảm lag
         * @param mGameObject
         */
        void removeNode(GameObject mGameObject);

        /**
         * xử lí va chạm thành công, là cá lớn nuốt cá bé
         * @param mGameObject
         */
        void collisionSuccess(GameObject mGameObject);

        /**
         * xử lí va chạm thất bại, bị cá lớn nuốt
         */
        void collisionFail();
    }

    interface Presenter{
        /**
         * init GamePlay
         * @param gamePlay
         * @param mView
         */
        void retryGamePlay(GameObject gamePlay, View mView);

        /**
         * xóa object bẳng Presenter sử dụng thread
         * @param mGameObject
         * @param pane
         * @param barriers
         */
        void removeGameObject(GameObject mGameObject, Pane pane, List<GameObject> barriers);

        /**
         * xóa game play
         * @param gamePlay
         * @param mPane
         */
        void removeGamePlay(GameObject gamePlay, Pane mPane);

        /**
         * Tạo mới cá
         * @param mPane
         * @param barriers
         */
        void initGameBarrier(Pane mPane, List<GameObject> barriers);

        /**
         * Kiểm tra va chạm
         * @param gamePlay
         * @param gameCollision
         */
        void checkCollision(GameObject gamePlay, GameObject gameCollision);
    }

    interface Controller{
        /**
         * cho label level up hiển thị lên
         */
        void showLevelUp();

        /**
         * ẩn label level up
         */
        void hindLevelUp();

        /**
         * update điểm hiển thị trên màn hình
         */
        void updateScore();

        /**
         * cho label die hien thi
         */
        void showDie();

        /**
         * Show lable +1 score
         */
        void showLabelPlusScore();
    }
}
