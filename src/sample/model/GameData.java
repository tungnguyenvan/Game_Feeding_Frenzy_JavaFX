package sample.model;

public class GameData {
    private static int point = 0;
    private static int level = 1;
    private static int heart = 3;
    public static int maxPoint = 30;
    public static double indicator = 0;

    public static int pointLevelSmall = 10;
    public static int pointLevelNormal = 20;
    public static int pointLevelBig = 30;

    public static void pushPoint(){
        point += 1;
    }
    public static int getPoint(){
        indicator += 0.006;
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


    public static void revertData(){
        level = 1;
        point = 0;
        heart = 3;
    }

    /**
     * trả về scale cho fish
     * @return
     */
    public static int getScaleLevel(){
        int scale = GameObject.SCALE_LEVEL_SMALL;
        if (getPoint() == pointLevelSmall) scale = GameObject.SCALE_LEVEL_NORMAL;
        else if (getPoint() == pointLevelNormal) scale = GameObject.SCALE_LEVEL_BIG;
        return scale;
    }
}
