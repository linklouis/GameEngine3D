package gameengine.Objects.objectmovement.collisions;

import gameengine.Objects.GameObject;
import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Collider3D<ColliderType extends Collider3D<ColliderType>> extends GameObject {
    /*
     * Construction:
     */

    public Collider3D(Vector3D location) {
        super(location);
    }

    public Collider3D(double x, double y, double z) {
        super(new Vector3D(x, y, z));
    }


    /*
     * Functionality:
     */

    public boolean inRange(Collider3D<?> collider) {
        return
                (
                        (maxY() > collider.minY() && minY() < collider.maxY()) ||
                                (collider.maxY() > minY() && collider.minY() < maxY())
                ) && (
                        (maxX() > collider.minX() && minX() < collider.maxX()) ||
                                (collider.maxX() > minX() && collider.minX() < maxX())
                ) && (
                        (maxZ() > collider.minZ() && minZ() < collider.maxZ()) ||
                                (collider.maxZ() > minZ() && collider.minZ() < maxZ())
                );
    }


    /*
     * Abstract Methods:
     */

//    /**
//     * Finds the first intersection a ray will have with the
//     * {@code Collider3D}.
//     *
//     * @param ray The ray to find a collision with.
//     * @param curSmallestDist The largest distance the output is looking for.
//     *                        Can be used for optimization by counting out a
//     *                        {@code Collider3D} early.
//     * @return -1 if never enters range or if collision is behind start.
//     * Otherwise, the distance to first hit
//     */
//    public abstract double distanceToCollide(LightRay ray, double curSmallestDist);

    public abstract boolean isColliding(ColliderType coll);

    public abstract boolean contains(Vector3D point);

    public abstract double minX();

    public abstract double minY();

    public abstract double minZ();

    public abstract double maxX();

    public abstract double maxY();

    public abstract double maxZ();

    public abstract Vector3D getCenter();

    public abstract Class<ColliderType> getColliderClass();

    public abstract void paint(GraphicsContext gc, Color color);


    /*
     * Utilities:
     */

    public double centerX() {
        return getCenter().getX();
    }

    public double centerY() {
        return getCenter().getY();
    }

    public double centerZ() {
        return getCenter().getZ();
    }

    public double getWidth() {
        return maxX() - minX();
    }

    public double getHeight() {
        return maxY() - minY();
    }

    public double getLength() {
        return maxZ() - minZ();
    }
}
