package gameengine.Objects.objectmovement.collisions;

import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SphereCollider extends Collider3D<SphereCollider> {
    private double radius;
    
    public SphereCollider(Vector3D location, double radius) {
        super(location);
        this.radius = radius;
    }


    /*
     * Functionality:
     */

    @Override
    public boolean isColliding(SphereCollider coll) {
        return coll.getCenter()
                .subtract(getCenter())
                .magnitude() > getRadius() + coll.getRadius();
    }

    @Override
    public boolean contains(Vector3D point) {
        return Math.abs(point.subtract(getCenter()).magnitude()) <= getRadius();
    }

    @Override
    public double minX() {
        return getCenter().getX() - getRadius();
    }

    @Override
    public double minY() {
        return getCenter().getY() - getRadius();
    }

    @Override
    public double minZ() {
        return getCenter().getZ() - getRadius();
    }

    @Override
    public double maxX() {
        return getCenter().getX() + getRadius();
    }

    @Override
    public double maxY() {
        return getCenter().getY() + getRadius();
    }

    @Override
    public double maxZ() {
        return getCenter().getZ() + getRadius();
    }

    @Override
    public Vector3D getCenter() {
        return getLocation();
    }

    @Override
    public Class<SphereCollider> getColliderClass() {
        return SphereCollider.class;
    }

    @Override
    public void paint(GraphicsContext gc, Color color) {
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
