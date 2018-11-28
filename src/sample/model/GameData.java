package sample.model;

public class GameData {
    private static int point = 0;
    private static int level = 1;
    private static int heart = 3;
    public static int MAX = 30;

    public static int pointLevelSmall = 10;
    public static int pointLevelNormal = 20;
    public static int pointLevelBig = 30;

    public static void pushPoint(){
        point += 1;
    }
    public static int getPoint(){
        return point;
    }

    public static void pushLevel(){
        level += 1;
    }
    public static int getLevel(){
        return level;
    }

    public static void subHeart(){
        heart -= 1;
    }
    public static int getHeart() {
        return heart;
    }

    /**
     * trả về scale cho fish
     * @return
     */
    public static int getScaleLevel(){
        int scale = GameObject.SCALE_SMALL;
        if (getPoint() == pointLevelSmall) scale = GameObject.SCALE_LEVEL_NORMAL;
        else if (getPoint() == pointLevelNormal) scale = GameObject.SCALE_BIG;
        return scale;
    }
}
