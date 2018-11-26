package sample.model;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import sample.utils.GameContrants;

import java.util.Random;

public class Sponges{
    private static PhongMaterial phongMaterial;
    private static Random random;

    public Node Sponges(){
        Sphere sphere = new Sphere();
        if (phongMaterial == null) phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.web("#b5bfcc6e"));
        if (random == null) random = new Random();
        sphere.setTranslateY(200);
        sphere.setTranslateX(200);
        return sphere;
    }
}
