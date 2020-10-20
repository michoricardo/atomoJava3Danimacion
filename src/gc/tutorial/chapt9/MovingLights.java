package gc.tutorial.chapt9;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
 
import javafx.fxml.FXMLLoader;

public class MovingLights extends Application {

  private static final float WIDTH = 1400;
  private static final float HEIGHT = 1000;

  private double anchorX, anchorY;
  private double anchorAngleX = 0;
  private double anchorAngleY = 0;
  private final DoubleProperty angleX = new SimpleDoubleProperty(0);
  private final DoubleProperty angleY = new SimpleDoubleProperty(0);

  @Override
  public void start(Stage primaryStage) {
    
	Sphere atomo = prepareAtom();
    SmartGroup group = new SmartGroup();
    group.getChildren().add(atomo);
    group.getChildren().addAll(prepareLightSource());

    Camera camera = new PerspectiveCamera(true);
    camera.setNearClip(1);
    camera.setFarClip(1000);
    //camera.setFarClip(500);
    //camera.translateZProperty().set(-200);
    camera.translateZProperty().set(-400); //

    Scene scene = new Scene(group, WIDTH, HEIGHT);
    scene.setFill(Color.SILVER);
    scene.setCamera(camera);

    group.translateXProperty().set(0);
    group.translateYProperty().set(0);
    group.translateZProperty().set(0);

    initMouseControl(group, scene, primaryStage);

    primaryStage.setTitle("Ricardo García");
    primaryStage.setScene(scene);
    primaryStage.show();

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        pointLight.setRotate(pointLight.getRotate() + 1);
        pointLight2.setRotate(pointLight.getRotate() + 1);
        pointLight3.setRotate(pointLight.getRotate() + 1);
        pointLight4.setRotate(pointLight.getRotate() + 1);
        pointLight5.setRotate(pointLight.getRotate() + 1);
        pointLight6.setRotate(pointLight.getRotate() + 1);
      }
    };
    timer.start();
  }

  private final PointLight pointLight = new PointLight();
  private final PointLight pointLight2 = new PointLight();
  private final PointLight pointLight3 = new PointLight();
  private final PointLight pointLight4 = new PointLight();
  private final PointLight pointLight5 = new PointLight();
  private final PointLight pointLight6 = new PointLight();

  private Node[] prepareLightSource() {
    pointLight.setColor(Color.RED);
    pointLight.getTransforms().add(new Translate(0, -40, 50));
    pointLight.setRotationAxis(Rotate.X_AXIS);
    
    pointLight2.setColor(Color.YELLOW);
    pointLight2.getTransforms().add(new Translate(-40, -50, 0));
    pointLight2.setRotationAxis(Rotate.Z_AXIS);
    
    pointLight3.setColor(Color.GREEN);
    pointLight3.getTransforms().add(new Translate(-40, 0, -50));
    pointLight3.setRotationAxis(Rotate.Y_AXIS);
    
    pointLight4.setColor(Color.ORANGE);
    pointLight4.getTransforms().add(new Translate(0, 40, -50));
    pointLight4.setRotationAxis(Rotate.X_AXIS);
    
    pointLight5.setColor(Color.PINK);
    pointLight5.getTransforms().add(new Translate(40, 0, -50));
    pointLight5.setRotationAxis(Rotate.Y_AXIS);
    
    pointLight6.setColor(Color.PINK);
    pointLight6.getTransforms().add(new Translate(40, -50, 0));
    pointLight6.setRotationAxis(Rotate.Y_AXIS);
    
    Sphere sphere = new Sphere(4);
    sphere.getTransforms().setAll(pointLight.getTransforms());
    sphere.rotateProperty().bind(pointLight.rotateProperty());
    sphere.rotationAxisProperty().bind(pointLight.rotationAxisProperty());
    
    Sphere sphere2 = new Sphere(4);
    sphere2.getTransforms().setAll(pointLight2.getTransforms());
    sphere2.rotateProperty().bind(pointLight2.rotateProperty());
    sphere2.rotationAxisProperty().bind(pointLight2.rotationAxisProperty());

    Sphere sphere3 = new Sphere(4);
    sphere3.getTransforms().setAll(pointLight3.getTransforms());
    sphere3.rotateProperty().bind(pointLight3.rotateProperty());
    sphere3.rotationAxisProperty().bind(pointLight3.rotationAxisProperty());
    
    Sphere sphere4 = new Sphere(4);
    sphere4.getTransforms().setAll(pointLight4.getTransforms());
    sphere4.rotateProperty().bind(pointLight4.rotateProperty());
    sphere4.rotationAxisProperty().bind(pointLight4.rotationAxisProperty());
    
    Sphere sphere5 = new Sphere(4);
    sphere5.getTransforms().setAll(pointLight5.getTransforms());
    sphere5.rotateProperty().bind(pointLight5.rotateProperty());
    sphere5.rotationAxisProperty().bind(pointLight5.rotationAxisProperty());
    
    Sphere sphere6 = new Sphere(4);
    sphere6.getTransforms().setAll(pointLight6.getTransforms());
    sphere6.rotateProperty().bind(pointLight6.rotateProperty());
    sphere6.rotationAxisProperty().bind(pointLight6.rotationAxisProperty());
    //return new Node[]{pointLight, sphere};
    return new Node[]{pointLight,pointLight2,pointLight3,pointLight4,pointLight5,pointLight6 ,sphere,sphere2,sphere3,sphere4,sphere5,sphere6};
  }

  	private Sphere prepareAtom() {
    PhongMaterial material = new PhongMaterial();
    material.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/wood.jpg")));


    Sphere atomo= new Sphere(20);
    atomo.setMaterial(material);
    return atomo;
  }

  private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
    Rotate xRotate;
    Rotate yRotate;
    group.getTransforms().addAll(
        xRotate = new Rotate(0, Rotate.X_AXIS),
        yRotate = new Rotate(0, Rotate.Y_AXIS)
    );
    xRotate.angleProperty().bind(angleX);
    yRotate.angleProperty().bind(angleY);

    scene.setOnMousePressed(event -> {
      anchorX = event.getSceneX();
      anchorY = event.getSceneY();
      anchorAngleX = angleX.get();
      anchorAngleY = angleY.get();
    });

    scene.setOnMouseDragged(event -> {
      angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
      angleY.set(anchorAngleY + anchorX - event.getSceneX());
    });

    stage.addEventHandler(ScrollEvent.SCROLL, event -> {
      double delta = event.getDeltaY();
      group.translateZProperty().set(group.getTranslateZ() + delta);
    });
  }

  public static void main(String[] args) {
    launch(args);
  }

  class SmartGroup extends Group {

    Rotate r;
    Transform t = new Rotate();

    void rotateByX(int ang) {
      r = new Rotate(ang, Rotate.X_AXIS);
      t = t.createConcatenation(r);
      this.getTransforms().clear();
      this.getTransforms().addAll(t);
    }

    void rotateByY(int ang) {
      r = new Rotate(ang, Rotate.Y_AXIS);
      t = t.createConcatenation(r);
      this.getTransforms().clear();
      this.getTransforms().addAll(t);
    }
  }
}