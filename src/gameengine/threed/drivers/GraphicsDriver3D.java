package gameengine.threed.drivers;

import gameengine.skeletons.GraphicsDriver;
import gameengine.threed.graphics.Camera;
import gameengine.threed.graphics.HasRenderable;
import gameengine.threed.graphics.Renderable;
import gameengine.threed.graphics.raytraceing.objectgraphics.RayTraceable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GraphicsDriver3D<RenderType extends Renderable> extends GraphicsDriver<RenderType> {
    private final List<RenderType> visualObjects = new ArrayList<>();
    private final ImageView view;
    private Camera<RenderType> camera;


    /*
     * Construction:
     */

    public GraphicsDriver3D(int width, int height, Camera<RenderType> camera) {
        super(width, height);
        this.camera = camera;
        view = new ImageView(camera.getImage());
    }

    public GraphicsDriver3D(int width, int height, Color bgColor, Camera<RenderType> camera) {
        super(width, height, bgColor);
        this.camera = camera;
        view = new ImageView(camera.getImage());
    }

    @Override
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

    @Override
    public void updateGraphics() {
        camera.update(visualObjects);
    }


    /*
     * Utilities:
     */

    public void add(RenderType newObject) {
        visualObjects.add(newObject);
//        if (camera.canRender(newObject)) {
//            cameraObjects.add(newObject);
//        }
//        cameraObjects = camera.getValidObjects(visualObjects);
    }

    public RenderType get(int index) {
        return visualObjects.get(index);
    }

    public void forEach(Consumer<RenderType> function) {
        visualObjects.forEach(function);
    }

    public Camera<RenderType> getCamera() {
        return camera;
    }

    public void setCamera(Camera<RenderType> camera) {
        this.camera = camera;
    }

    public List<RenderType> getVisualObjects() {
        return visualObjects;
    }

//    public List<RenderType> getCameraObjects() {
//        return cameraObjects;
//    }
}
