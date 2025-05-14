package gameengine.graphics.raytraceing.objectgraphics;

import gameengine.geometry.Ray;
import gameengine.Objects.objectproperties.Renderable;
import gameengine.graphics.raytraceing.LightRay;
import gameengine.graphics.raytraceing.Reflection;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.paint.Color;

public abstract class RayTraceable implements Renderable {
    private RayTracingTexture texture;

    public RayTraceable(RayTracingTexture texture) {
        this.texture = texture;
    }


    /*
     * Functionality:
     */

    public Reflection reflection(LightRay lightRay) {
        return getTexture().reflection(lightRay, surfaceNormal(lightRay));
    }

    /**
     * Finds the first intersection a lightRay will have with the
     * {@code Collider3D}.
     *
     * @param ray               The lightRay to find a collision with.
     * @param curSmallestDist   The largest distance the output is looking for.
     *                          Can be used for optimization by counting out a
     *                          {@code Collider3D} early.
     * @param amountInDirection
     * @return -1 if never enters range or if collision is behind start.
     * Otherwise, the distance to first hit
     */
    public abstract double distanceToCollide(Ray ray, double curSmallestDist, double amountInDirection);

    public abstract double getRange();

    public abstract Vector3D getCenter();

    /**
     * Returns the normal vector of the intersectable object facing towards the
     * {@code VectorLine}.
     *
     * @param perspective
     * @return
     */
    public abstract Vector3D surfaceNormal(Ray perspective);

    public double distanceToCollide(Ray ray, double curSmallestDist) {
        return distanceToCollide(ray, curSmallestDist,
                ray.getDirection().dotWithSubtracted(ray.getPosition(), getCenter()));
    }

    public double closestDistTo(Vector3D point) {
        return getCenter().distance(point) - getRange();
    }


    /*
     * Utilities:
     */

    public abstract Vector3D[] getVertices();

    public RayTracingTexture getTexture() {
        return texture;
    }

    public void setTexture(RayTracingTexture texture) {
        this.texture = texture;
    }

    public Color getColor() {
        return getTexture().getColor();
    }

    public void setColor(Color color) {
        getTexture().setColor(color);
    }
}
