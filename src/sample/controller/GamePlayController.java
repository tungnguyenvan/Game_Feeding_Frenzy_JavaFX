package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.GameContract;
import sample.SplashContract;
import sample.utils.GameContrants;

public class GamePlayController {


    public static void startSplah(Stage primaryStage, Class clazz, SplashContract.Controller mSplashController, GameContract.View mView) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource("ui/splash.fxml"));
        Pane root = fxmlLoader.load();
        mSplashController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        mSplashController.setView(mView);
        initScreen(primaryStage, scene);
    }

    public static void initViewGamePlay(Stage primaryStage, Class clazz, Pane root, Scene scene, GameContract.Controller mGameController, GameContract.View mView) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource("ui/sample.fxml"));
        Parent parent = fxmlLoader.load();
        root = new Pane();
        root.setId("root");
        mGameController = fxmlLoader.getController();
        root.getChildren().add(parent);
        scene = new Scene(root);
        scene.setCursor(Cursor.NONE);
        mView.startGameSuccess(scene, root, mGameController);
        initScreen(primaryStage, scene);
    }

    public static void initScreen(Stage primaryStage, Scene scene){
        primaryStage.setTitle("Feeding Frenzy");
        scene.getStylesheets().add("sample/style.css");
        primaryStage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        GameContrants.WIDTH = bounds.getWidth();
        GameContrants.HEIGHT = bounds.getHeight();
        primaryStage.show();
    }

    public static void initGameOver(Stage primaryStage, Class clazz, GameContract.View mView) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource("ui/trygame.fxml"));
        Parent parent = fxmlLoader.load();
        TryGameController tryGameController = fxmlLoader.getController();
        tryGameController.initSuperGame(primaryStage, mView);
        Scene scene = new Scene(parent);
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);
        newWindow.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add("sample/style.css");
        scene.setFill(Color.TRANSPARENT);
        newWindow.setScene(scene);
        newWindow.show();
    }
}
