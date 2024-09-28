package gameengine.geometry;

import gameengine.graphics.raytraceing.LightRay;
import gameengine.graphics.raytraceing.objectgraphics.TraceableObjectList;
import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.utilities.vectormath.Vector3D;

public class Ray {
    /**
     * The direction the {@code Line} moves.
     */
    private Vector3D direction;
    /**
     * The current position of the {@code Line}.
     */
    protected Vector3D position;

    public Ray(final Vector3D startPosition, final Vector3D direction) {
        this.direction = direction.unitVector();
        this.position = startPosition;
    }

    public Ray(Ray parent) {
        this.direction = parent.getDirection();
        this.position = parent.getPosition();
    }

    /**
     * Finds the first {@link RayTraceable} in {@code objectsInField} that the
     * {@code LightRay} will hit.
     *
     * @param objectsInField The {@link RayTraceable}s that the {@code LightRay} can
     *                       potentially collide with.
     * @return The closest {@link RayTraceable} the {@code LightRay} can collide with.
     */
    public RayTraceable firstCollision(final TraceableObjectList objectsInField) {
        TraceableObjectList.Element element = objectsInField.getHead();

        // No objects, return.
        if (element == null) {
            return null;
        }
        // Set the closest distance to the distance of the first object.
        double closestDist = element.intersectable().distanceToCollide(this,
                getDirection().dotWithSubtracted(position, element.intersectable().getCenter()));

        // While the distance of the closest collision either doesn't exist or is behind the ray,
        // check the next distance.
        // Attempting to get as far into the list as possible without having to start the heavier computations.
        while (Double.isNaN(closestDist) || closestDist < 0) {
            if ((element = element.next()) == null) {
                // List contained no potential colliders
                return null;
            }
            closestDist = element.intersectable().distanceToCollide(this,
                    getDirection().dotWithSubtracted(position, element.intersectable().getCenter()));
        }

        // If the current element is the last one, early exit.
        if (element.isLast()) {
            toDistance(closestDist - 0.01);
            return element.intersectable();
        }
        RayTraceable closest = element.intersectable();
        element = element.next();
        double newDistance;
        Vector3D toCenter;

        // While there are more objects in the scene,
        // if the current object is closer than the closest distance found yet,
        // check the distance it collides at,
        // and if that's also closer, take this object instead.
        while (true) {
            toCenter = position.subtract(element.intersectable().getCenter());

            if (toCenter.magnitude() - element.intersectable().getRange() < closestDist) {
                newDistance = element.intersectable().distanceToCollide(this, closestDist,
                        getDirection().dotProduct(toCenter));
                if (newDistance >= 0 && newDistance < closestDist) {
                    closestDist = newDistance;
                    closest = element.intersectable();
                }
            }

            if (/*element.isLast()*/(element = element.next()) == null) {
                toDistance(closestDist - 0.01);
                return closest;
            }
//            element = element.next();
        }
    }

    /**
     * Updates the {@code Ray}'s direction based on its reflection of off the
     * given {@link RayTraceable}.
     *
     * @param collider The {@link RayTraceable} to reflect off of.
     */
    public void reflect(RayTraceable collider) {
        setDirection(collider.reflection((LightRay) this).direction());
    }

    /**
     * Returns a new {@code Ray} representing the current {@code Ray} after
     * reflecting off of {@code collider}. Assumes the current {@code Ray} is
     * colliding with {@code collider}.
     *
     * @param collider The {@link RayTraceable} to reflect off of.
     * @return A new {@code Ray} representing the current {@code Ray} after
     * reflecting off of {@code collider}.
     */
    public Ray getReflected(RayTraceable collider) {
        return new Ray(position, collider.reflection((LightRay) this).direction());
    }

    public Vector3D pointAtDistance(double distance) {
        return position.addMultiplied(direction, distance);
    }


    /*
     * Utilities:
     */

    public void toDistance(double distance) {
        position = position.addMultiplied(direction, distance);
//        position.x += direction.x * distance;
//        position.y += direction.y * distance;
//        position.z += direction.z * distance;
//        position.magnitude = Double.NaN;
//                Math.sqrt(
//                position.x * position.x
//                        + position.y * position.y
//                        + position.z * position.z);
//        System.out.println(position);
    }

    public Vector3D getDirection() {
        return direction;
    }

    protected void setDirection(Vector3D direction) {
        this.direction = direction.unitVector();
    }

    public Vector3D getPosition() {
        return position;
    }
}
