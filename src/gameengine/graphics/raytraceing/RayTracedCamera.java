package gameengine.graphics.raytraceing;

import gameengine.Objects.DirectionalGameObject;
import gameengine.Objects.objectproperties.HasRenderable;
import gameengine.Objects.objectproperties.Moveable;
import gameengine.graphics.Camera;
import gameengine.graphics.raytraceing.objectgraphics.RayIntersectableList;
import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.graphics.raytraceing.postprocesses.PostProcess;
import gameengine.utilities.vectormath.Vector2D;
import gameengine.utilities.vectormath.Vector3D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A game {@link Camera} that renders a 3D scene using ray tracing
 *
 * @author Louis Link
 * @since 1.0
 */
public class RayTracedCamera extends DirectionalGameObject implements Moveable {
    /**
     * The {@link WritableImage} the {@code Camera} will render the image on.
     */
    private WritableImage image;
    /**
     * The number of degrees in the horizontal field of view.
     */
    private double fieldOfViewDegrees;
    /**
     * A flag indicating that the scene has changed, and the {@code Camera}
     * needs to render a new frame.
     */
    private boolean updateNeeded = true;
    /**
     * A {@link List} of all the {@link PostProcess}es that the {@code Camera}
     * applies to the rendered image.
     */
    private final List<PostProcess> postProcesses = new ArrayList<>();

    /**
     * The number of rays averaged to find each pixel's color.
     */
    private int raysPerPixel;
    /**
     * Whether the {@code Camera} should utilize multithreading when rendering
     * images. Doing so can speed up rendering.
     */
    private boolean multiThreaded;
    /**
     * The maximum number of times a light ray can bounce before it is declared
     * to never hit a light.
     */
    private final int maxBounces;
    /**
     * The time in nanoseconds it took to render the last frame.
     */
    private long renderTime;
    /**
     * The target size of the tiles for the image to be broken into for
     * increased threaded performance.
     * <p>The actual tile size may end up being different than the set target.
     */
    private int targetTileSize;
    private Vector2D tileDimensions;
    protected final IntBuffer buffer;
    private double scaleX;



    /*
     * Construction:
     */

    /**
     * Constructs a new {@code RayTracedCamera} with a location given by a
     * {@link Vector3D} and a specified field of view given in degrees.
     *
     * @param location The location of the {@code Camera} in space.
     * @param direction a {@link Vector3D} representing the amount the
     *                  {@code Camera} is facing in each direction.
     * @param imageDimensions The resolution to render the image at.
     * @param maxBounces The maximum number of times a light ray can bounce
     *                   before it is declared to never hit a light.
     * @param raysPerPixel The number of rays to be averaged to find each
     *                     pixel's color.
     * @param fieldOfViewDegrees The number of degrees in the horizontal field
     *                           of view. Default intersectable: 60.0
     */
    public RayTracedCamera(Vector3D location, Vector3D direction, Vector2D imageDimensions,
                           int maxBounces, int raysPerPixel, double fieldOfViewDegrees) {
        super(location, direction);
        image = new WritableImage((int) imageDimensions.getX(), (int) imageDimensions.getY());
        setFieldOfViewDegrees(fieldOfViewDegrees);

        buffer = IntBuffer.allocate(getWidth() * getHeight() * Integer.BYTES);
        this.maxBounces = maxBounces;
        this.raysPerPixel = raysPerPixel;
        setTargetTileSize(15 / raysPerPixel);
        updateScaleX();
    }

    /**
     * Constructs a new {@code RayTracedCamera} with a location given with
     * coordinates and a default field of view of 60 degrees.
     *
     * @param location The location of the {@code Camera} in space.
     * @param direction a {@link Vector3D} representing the amount the
     *                  {@code Camera} is facing in each direction.
     * @param imageDimensions The resolution to render the image at.
     * @param maxBounces The maximum number of times a light ray can bounce
     *                   before it is declared to never hit a light.
     * @param raysPerPixel The number of rays to be averaged to find each
     *                     pixel's color.
     */
    public RayTracedCamera(Vector3D location, Vector3D direction,
                           Vector2D imageDimensions, int maxBounces, int raysPerPixel) {
        this(location, direction, imageDimensions, maxBounces, raysPerPixel, 60.0);
    }

    /**
     * Constructs a new {@code RayTracedCamera} with a location given with
     * coordinates and a specified field of view given in degrees.
     *
     * @param x The location of the {@code Camera} on the x-axis.
     * @param y The location of the {@code Camera} on the y-axis.
     * @param z The location of the {@code Camera} on the z-axis.
     * @param direction a {@link Vector3D} representing the amount the
     *                  {@code Camera} is facing in each direction.
     * @param imageDimensions The resolution to render the image at.
     * @param maxBounces The maximum number of times a light ray can bounce
     *                   before it is declared to never hit a light.
     * @param raysPerPixel The number of rays to be averaged to find each
     *                     pixel's color.
     * @param fieldOfViewDegrees The number of degrees in the horizontal field
     *                           of view. Default intersectable: 60.0
     */
    public RayTracedCamera(double x, double y, double z, Vector3D direction, Vector2D imageDimensions,
                           int maxBounces, int raysPerPixel, double fieldOfViewDegrees) {
        this(new Vector3D(x, y, z), direction, imageDimensions, maxBounces, raysPerPixel, fieldOfViewDegrees);
    }

    /**
     * Constructs a new {@code RayTracedCamera} with a location given with
     * coordinates and a default field of view of 60 degrees.
     *
     * @param x The location of the {@code Camera} on the x-axis.
     * @param y The location of the {@code Camera} on the y-axis.
     * @param z The location of the {@code Camera} on the z-axis.
     * @param direction a {@link Vector3D} representing the amount the
     *                  {@code Camera} is facing in each direction.
     * @param imageDimensions The resolution to render the image at.
     * @param maxBounces The maximum number of times a light ray can bounce
     *                   before it is declared to never hit a light.
     * @param raysPerPixel The number of rays to be averaged to find each
     *                     pixel's color.
     */
    public RayTracedCamera(double x, double y, double z, Vector3D direction,
                           Vector2D imageDimensions, int maxBounces, final int raysPerPixel) {
        this(new Vector3D(x, y, z), direction, imageDimensions, maxBounces, raysPerPixel, 60.0);
    }


    /*
     * Functionality:
     */

    private RayIntersectableList objects;

    public void updateObjects(final Collection<RayTraceable> renderableObjects) {
        objects = new RayIntersectableList(
                renderableObjects.stream().sorted(Comparator.comparingDouble((RayTraceable obj) -> obj.closestDistTo(getLocation()))).toArray(RayTraceable[]::new), getLocation());
    }

    /**
     * Render a ray-traced image of the known objects.
     */
    public void renderImage() {
        long startTime = System.nanoTime();
        renderThreaded(getImage().getPixelWriter(), objects);
        renderTime = System.nanoTime() - startTime;

//        System.out.println("Execution Time: "
//                + TimeFormatter.format(renderTime));
//        System.out.println("Execution Time: "
//                + TimeConversionFactor.MILLISECOND.convert(renderTime)
//                + " milliseconds");
//        System.out.println("rendered");

//        try {
//            saveToFile("RayTraced_0_1_("
//                    + getWidth() + "," + getHeight() + ")_"
//                    + "rays" + raysPerPixel + "_bounces"
//                    + maxBounces + ".png");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.exit(0);

    }

    private void renderThreaded(final PixelWriter writer,
                                final RayIntersectableList objects) {
        IntStream.range(0, getWidth() * getHeight()).parallel().forEach(pixelIndex -> renderPixel(pixelIndex, objects));
        updateImage(writer);
    }


    protected void renderPixel(int pixelIndex, RayIntersectableList objects) {
        buffer.put(
                pixelIndex,
                calculatePixelColorInt(rayTo(pixelIndex), objects)
        );
    }


    protected void updateImage(PixelWriter writer) {
        writer.setPixels(0, 0, getWidth(), getHeight(), PixelFormat.getIntArgbInstance(), buffer, getWidth());
    }

    protected LightRay rayTo(final double pixelIndex) {
        return new LightRay(
                getLocation(),
                // Thx to ChatGPT:
                getDirection().transformToNewCoordinates(
                        (pixelIndex % getWidth() * 2 / getWidth() - 1) * scaleX,
                        (getHeight() - pixelIndex / getWidth() * 2) * scaleX / getWidth()));
    }


    /*
     * LightRay Tracing:
     */

    /**
     * @return The average color of each ray to measure based on
     * {@link #raysPerPixel}.
     */
    protected int calculatePixelColorInt(final LightRay startRay, final RayIntersectableList objectsInField) {
        RayTraceable firstCollision = (RayTraceable) startRay.firstCollision(objectsInField);

        if (firstCollision == null) {
            return 0;
        }
        if (firstCollision.getTexture().getColor().getBrightness() < 0.1
                || firstCollision.getTexture().isLightSource()) {
            return startRay.getReflected(firstCollision).getIncomingLight().oneInt();
        }

        Vector3D averageColor = new Vector3D(0);
        for (int i = 0; i < raysPerPixel; i++) {
            averageColor.addMutable(
                            startRay.getReflected(firstCollision).getColor(
                            maxBounces, objectsInField, 2));
        }

        return averageColor.scalarDivide(raysPerPixel).oneInt();
    }
    
    
    /*
     * Camera Functionality
     */

    /**
     * Renders a new frame if it has been requested and applies
     * post-processing, otherwise returns.
     */
    public void update() {
        if (updateNeeded) {
            renderImage();
//            renderWithProcessing();
            updateNeeded = false;
        }
    }

    public void displayOn(ImageView view) {
        view.setImage(getImage());
    }

    public void requestUpdate() {
        updateNeeded = true;
    }

    /**
     * Saves {@link #image} to a .png file with the given path.
     *
     * @param fileName The file path to save the image to.
     * @throws IOException
     */
    public void saveToFile(String fileName) throws IOException {
        saveToFile(fileName, "png");
    }

    /**
     * Saves {@link #image} to a file of the given image format and file path.
     *
     * @param fileName The file path to save the image to.
     * @param format The image format to use to save the file.
     * @throws IOException
     */
    public void saveToFile(String fileName, String format) throws IOException {
        File file = new File(fileName);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, file);
    }

    /**
     * Applies the {@code Camera}'s post-processing routine to the given
     * {@link WritableImage}.
     *
     * @param image The {@link WritableImage} to apply the post-processing to.
     * @return The {@link WritableImage} with the {@code Camera}'s
     * post-processing routine preformed on it.
     */
    public WritableImage applyPostProcessing(WritableImage image) {
        WritableImage currentImage = image;
        for (PostProcess process : postProcesses) {
            currentImage = process.process(currentImage);
        }
        return currentImage;
    }


    /*
     * Utilities:
     */
    
    protected void setFieldOfView(final double fieldOfViewDegrees) {
        this.fieldOfViewDegrees = fieldOfViewDegrees;
        updateScaleX();
    }

    public int getRaysPerPixel() {
        return raysPerPixel;
    }

    public void setRaysPerPixel(final int newRaysPerPixel) {
        raysPerPixel = newRaysPerPixel;
    }

    public boolean isMultiThreaded() {
        return multiThreaded;
    }

    public void setMultiThreaded(boolean newMultiThreaded) {
        multiThreaded = newMultiThreaded;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public long getRenderTime() {
        return renderTime;
    }

    public int getTargetTileSize() {
        return targetTileSize;
    }

    public void setTargetTileSize(int targetTileSize) {
        this.targetTileSize = targetTileSize;
        tileDimensions = new Vector2D(
                findClosestFactor(getWidth(), targetTileSize),
                findClosestFactor(getHeight(), targetTileSize));
    }

    public Vector2D getTileDimensions() {
        return tileDimensions;
    }

    public void setImage(WritableImage image) {
        this.image = image;
        tileDimensions = new Vector2D(
                findClosestFactor(getWidth(), targetTileSize),
                findClosestFactor(getHeight(), targetTileSize));
        updateScaleX();
    }

    private static int findClosestFactor(int imageSize, int targetTileSize) {
        int closestFactor = 0;
        int minDifference = Integer.MAX_VALUE;

        for (int i = 1; i <= imageSize; i++) {
            if (imageSize % i == 0) {
                int difference = Math.abs(targetTileSize - i);
                if (difference < minDifference) {
                    minDifference = difference;
                    closestFactor = i;
                }
            }
        }

        return closestFactor;
    }

    public void updateScaleX() {
        scaleX = Math.tan(Math.toRadians(getFieldOfViewDegrees() / 2.0));
    }
    
    /*
     * Camera Utilities
     */

    public boolean needsUpdate() {
        return updateNeeded;
    }

    public double getFieldOfViewDegrees() {
        return fieldOfViewDegrees;
    }

    public double getFieldOfViewRadians() {
        return Math.toRadians(fieldOfViewDegrees);
    }

    public void setFieldOfViewDegrees(double fieldOfViewDegrees) {
        setFieldOfView(fieldOfViewDegrees);
    }

    public void setFieldOfViewRadians(double fieldOfViewRadians) {
        setFieldOfView(Math.toDegrees(fieldOfViewRadians));
    }

    public WritableImage getImage() {
        return image;
    }

    public int getWidth() {
        return (int) getImage().getWidth();
    }

    public int getHeight() {
        return (int) getImage().getHeight();
    }

    public double getAspectRatio() {
        return image.getHeight() / image.getWidth();
    }

    public List<PostProcess> getPostProcesses() {
        return postProcesses;
    }

    public void newPostProcess(PostProcess process) {
        getPostProcesses().add(process);
    }
}


