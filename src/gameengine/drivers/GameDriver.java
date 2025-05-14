package gameengine.drivers;

import gameengine.Objects.GameObject;
import gameengine.Objects.objectproperties.HasRenderable;
import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class GameDriver extends Application {
    private final List<GameObject> objects = new ArrayList<>();
    private final GraphicsDriver graphicsDriver;
    private final String name;

//    private final long[] frameTimes = new long[100];
//    private int frameTimeIndex = 0 ;
//    private double frameRate = 60;


    /*
     * Construction:
     */

    public GameDriver(GraphicsDriver graphicsDriver) {
        this.graphicsDriver = graphicsDriver;
        name = "New Game";
    }

    public GameDriver(String name, GraphicsDriver graphicsDriver) {
        this.graphicsDriver = graphicsDriver;
        this.name = name;
    }

    public abstract void initialize();

    @Override
    public void start(Stage stage) {
        stage.setTitle(name);
        initialize();
        graphicsDriver.initialize(stage);

        AnimationTimer animationTimer = setupAnimationTimer();
        animationTimer.start();
    }


    /*
     * Functionality:
     */

    public abstract void updateGame();

    protected AnimationTimer setupAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
//                updateFrameRateCounter(now);

                updateGame();
                graphicsDriver.updateGraphics();
            }
        };
    }

//    private void updateFrameRateCounter(long now) {
//        long oldFrameTime = frameTimes[frameTimeIndex];
//        frameTimes[frameTimeIndex] = now;
//        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
//        long elapsedNanos = now - oldFrameTime;
//        long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
//        frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
//        if (frameRate < 1 || frameRate > 500) {
//            frameRate = 1_000_000_000.0 / (now - frameTimes[(frameTimeIndex == 0 ? frameTimes.length : frameTimeIndex) - 1]);
//        }
//    }

    public void newObject(GameObject object) {
        if (object instanceof HasRenderable<?>) {
            getGraphicsDriver().add(((HasRenderable<RayTraceable>) object).getRenderable());
        }
        getObjects().add(object);
    }

    public void forEach(Consumer<GameObject> function) {
        objects.forEach(function);
    }


    /*
     * Utilities:
     */

    public List<GameObject> getObjects() {
        return objects;
    }

    public GraphicsDriver getGraphicsDriver() {
        return graphicsDriver;
    }

    public String getName() {
        return name;
    }

//    public double getFrameRate() {
//        return frameRate;
//    }
}
