package gameengine.drivers;

import gameengine.graphics.Camera;
import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GraphicsDriver {
    public final int WIDTH, HEIGHT;
    public Color background = Color.BLACK;
    private Pane root;
    private Scene scene;

    private final List<RayTraceable> visualObjects = new ArrayList<>();
    private final ImageView view;
    private Camera<RayTraceable> camera;


    /*
     * Construction:
     */

    public GraphicsDriver(int width, int height, Camera<RayTraceable> camera) {
        WIDTH = width;
        HEIGHT = height;
        this.camera = camera;
        view = new ImageView(camera.getImage());
    }

    public GraphicsDriver(int width, int height, Color bgColor, Camera<RayTraceable> camera) {
        WIDTH = width;
        HEIGHT = height;
        background = bgColor;
        this.camera = camera;
        view = new ImageView(camera.getImage());
    }

    public void initialize(Stage stage) {
        setRoot(new VBox());
        getRoot().setPrefSize(camera.getWidth(), camera.getHeight());
        setScene(new Scene(getRoot(), Color.BLACK));
        getRoot().getChildren().add(view);

        stage.setScene(getScene());
        stage.show();
    }


    /*
     * Functionality:
     */

    public void updateGraphics() {
        camera.update(visualObjects);
    }


    /*
     * Utilities:
     */

    public void add(RayTraceable newObject) {
        visualObjects.add(newObject);
        camera.updateObjects(getVisualObjects());
//        if (camera.canRender(newObject)) {
//            cameraObjects.add(newObject);
//        }
//        cameraObjects = camera.getValidObjects(visualObjects);
    }

    public RayTraceable get(int index) {
        return visualObjects.get(index);
    }

    public void forEach(Consumer<RayTraceable> function) {
        visualObjects.forEach(function);
    }

    public Camera<RayTraceable> getCamera() {
        return camera;
    }

    public void setCamera(Camera<RayTraceable> camera) {
        this.camera = camera;
    }

    public List<RayTraceable> getVisualObjects() {
        return visualObjects;
    }

    public Pane getRoot() {
        return root;
    }

    protected void setRoot(Pane root) {
        this.root = root;
    }

    public Scene getScene() {
        return scene;
    }

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

//    public List<RenderType> getCameraObjects() {
//        return cameraObjects;
//    }
}
