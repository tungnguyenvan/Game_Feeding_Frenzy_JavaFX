package sample.model;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import sample.GameContract;
import sample.utils.GameContrants;

import java.util.*;

public class GameObject {
    public static final int FISH_SMALL = 1;
    public static final int FISH_NORMAL = 2;
    public static final int FISH_BIG = 3;
    public static final int FISH_LARGE = 4;

    public static final int SCALE_SMALL = 150;
    public static final int SCALE_NORMAL = 250;
    public static final int SCALE_BIG = 350;
    public static final int SCALE_LARGE = 450;

    public static final int SCALE_LEVEL_SMALL = 100;
    public static final int SCALE_LEVEL_NORMAL = 150;
    public static final int SCALE_LEVEL_BIG = 200;

    private List<Color> colors = new ArrayList<>();
    private Node node = null;
    private Rotate rotate;
    private int type;
    private GameAnimation mGameAnimation;
    private static Random random;
    private boolean survive = true;

    /**
     * GamePlay
     * @return
     */
    public Node gamePlay(GameContract.View mView){
        TdsModelImporter model = new TdsModelImporter();
        model.read("src/sample/source/Angel0.3ds");
        Node[] arr = model.getImport();
        Map<String, PhongMaterial> phongMaterialMap = model.getNamedMaterials();
        Iterator<String> it = phongMaterialMap.keySet().iterator();
        String url = "sample/source/AngelT.bmp";
        while (it.hasNext()) {
            String key = it.next();
            phongMaterialMap.get(key).setDiffuseMap(new Image(url));
            phongMaterialMap.get(key).setSpecularMap(new Image(url));
        }
        node = arr[0];

        int gameScale = GameData.getScaleLevel();
        node.setScaleX(gameScale);
        node.setScaleY(gameScale);
        node.setScaleZ(gameScale);
        node.setTranslateX(500);

        rotate = new Rotate();
        node.getTransforms().add(rotate);
        mGameAnimation = new GameAnimation();
        mGameAnimation.wave(node);
        model.close();
        mGameAnimation.animationInitGamePlay(node, mView);
        return node;
    }

    /**
     * Tạo vật cản
     * @param type
     * @param gameConstract
     * @return
     */
    public Node gameBarrier(int type, GameContract.View gameConstract){
        this.type = type;
        colors.add(Color.GRAY); colors.add(Color.AQUA); colors.add(Color.RED); colors.add(Color.AQUAMARINE);
        TdsModelImporter model = new TdsModelImporter();
        model.read("src/sample/source/BrownTrout0.3ds");
        Node[] arr = model.getImport();
        Map<String, PhongMaterial> phongMaterialMap = model.getNamedMaterials();
        Iterator<String> it = phongMaterialMap.keySet().iterator();
        String url = "sample/source/BrownTT.bmp";

        while (it.hasNext()) {
            String key = it.next();
            phongMaterialMap.get(key).setDiffuseMap(new Image(url));
            phongMaterialMap.get(key).setDiffuseColor(colors.get(type-1));
            phongMaterialMap.get(key).setSpecularColor(colors.get(type-1));
            phongMaterialMap.get(key).setSpecularMap(new Image(url));
        }
        node = arr[0];
        rotate = new Rotate();
        node.getTransforms().add(rotate);
        model.close();

        if (random == null) random = new Random();
        int y = 100 + random.nextInt((int) GameContrants.HEIGHT - 100);
        node.setTranslateY(y);
        mGameAnimation = new GameAnimation();
        mGameAnimation.wave(node);
        boolean isFromRight = random.nextBoolean();
        if (isFromRight) rotateNodeToRight(false);
        else rotateNodeToRight(true);
        mGameAnimation.translateTransition(gameConstract,this, isFromRight, type);

        node.setScaleX(scaleFish(type));
        node.setScaleY(scaleFish(type));
        node.setScaleZ(scaleFish(type));
        return node;
    }

    /**
     * scale node, Level up
     * @param mscale
     */
    public void levelUp(int mscale){
        node.setScaleX(mscale);
        node.setScaleY(mscale);
        node.setScaleZ(mscale);
    }

    /**
     * xét scale cho vật cản
     * @param type
     * @return
     */
    private int scaleFish(int type){
        int scale = 0;
        switch (type){
            case FISH_SMALL :
                scale = SCALE_SMALL;
                break;
            case FISH_NORMAL :
                scale = SCALE_NORMAL;
                break;
            case FISH_BIG :
                scale = SCALE_BIG;
                break;
            case FISH_LARGE :
                scale = SCALE_LARGE;
                break;
        }
        return scale;
    }

    public Node getNode(){
        return node;
    }

    /**
     * Hàm xóa node khi
     * @param toRight
     */
    public void rotateNodeToRight(boolean toRight){
        if (toRight) rotate.setAngle(180);
        else rotate.setAngle(0);
    }

    /**
     * Hàm xét vị trí của node khi di chuyển chuột
     * @param x
     * @param y
     */
    public void setPossition(double x, double y){
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    /**
     * Lấy vị trí x
     * @return
     */
    public int getX(){
        return (int) node.getTranslateX();
    }

    /**
     * Lấy vị trí y
     * @return
     */
    public int getY(){
        return (int) node.getTranslateY();
    }

    /**
     * lấy type của cá
     * @return
     */
    public int getType(){
        return type;
    }

    /**
     * lấy trạng thái "Sống sót"
     * @return
     */
    public boolean isSuvive(){
        return survive;
    }

    public void setSurvive(){
        this.survive = true;
    }

    public void setDie() {
        this.survive = false;
    }
}
