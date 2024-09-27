package gameengine.threed.graphics;

import gameengine.skeletons.DirectionalGameObject;
import gameengine.skeletons.GameObject;
import gameengine.threed.objectproperties.Moveable;
import gameengine.threed.graphics.raytraceing.postprocesses.PostProcess;
import gameengine.vectormath.Vector2D;
import gameengine.vectormath.Vector3D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A skeleton for a Camera that renders a 3D scene from a certain perspective.
 *
 * @author Louis Link
 * @since 1.0
 */
public abstract class Camera<VisualType extends Renderable> extends DirectionalGameObject implements Moveable {
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

    private final Class<? extends VisualType> visualType;



    /*
     * Construction:
     */

    public Camera(Vector3D location, Vector3D direction, final Vector2D imageDimensions,
                  double fieldOfViewDegrees, Class<? extends VisualType> visualType) {
        this(location, direction, (int) imageDimensions.getX(), (int) imageDimensions.getY(), fieldOfViewDegrees, visualType);
    }

    public Camera(double x, double y, double z, Vector3D direction, Vector2D imageDimensions,
                  double fieldOfViewDegrees, Class<? extends VisualType> visualType) {
        this(new Vector3D(x, y, z), direction, imageDimensions, fieldOfViewDegrees, visualType);
    }

    public Camera(Vector3D location, Vector3D direction, int imageWidth, int imageHeight,
                  double fieldOfViewDegrees, Class<? extends VisualType> visualType) {
        super(location, direction.unitVector());
        this.visualType = visualType;

        image = new WritableImage(imageWidth, imageHeight);
        setFieldOfViewDegrees(fieldOfViewDegrees);
    }

    public Camera(double x, double y, double z, Vector3D direction, int imageWidth, int imageHeight,
                  double fieldOfViewDegrees, Class<? extends VisualType> visualType) {
        this(new Vector3D(x, y, z), direction.unitVector(), imageWidth, imageHeight, fieldOfViewDegrees, visualType);
    }


    /*
     * Functionality:
     */

    /**
     * Renders a new frame if it has been requested and applies
     * post-processing, otherwise returns.
     *
     * @param renderableObjects The {@link HasRenderable} objects to be rendered in
     *                          the scene.
     * @return True if a new frame has been rendered, otherwise false.
     */
    public boolean update(Collection<VisualType> renderableObjects) {
        if (updateNeeded) {
            renderImage(renderableObjects);
//            renderWithProcessing(renderableObjects);
            updateNeeded = false;
            return true;
        }
        return false;
    }

    public void displayOn(ImageView view) {
        view.setImage(getImage());
    }

    public void renderWithProcessing(Collection<VisualType> renderableObjects) {
        image = applyPostProcessing(renderImage(renderableObjects));
    }

    public abstract WritableImage renderImage(Collection<VisualType> renderableObjects);

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

//    protected List<? extends VisualType> getValidObjects(final Collection<GameObject> visuals) {
//        return visuals.stream()
//                .filter(object -> object instanceof VisualType)
//                .map(object -> object.getFromParent(visualType))
//                .sorted(Comparator.comparingDouble(obj ->
//                    -getLocation().distance(obj.getCenter()))).toList();
//    }

//    public boolean canRender(final Visual3D visual) {
//        return visual.getParent().containsModifier(visualType);
//    }

//    public Optional<VisualType> getRenderableFrom(final Visual3D visual) {
//        if (canRender(visual)) {
//            return Optional.of(visual.getFromParent(visualType));
//        }
//        return Optional.empty();
//    }


    /*
     * Utilities:
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

    protected void setFieldOfView(double fieldOfViewDegrees) {
        this.fieldOfViewDegrees = fieldOfViewDegrees;
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

    public void setImage(WritableImage image) {
        this.image = image;
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

//    public Class<? extends Modifier> getVisualType() {
//        return visualType;
//    }
}
