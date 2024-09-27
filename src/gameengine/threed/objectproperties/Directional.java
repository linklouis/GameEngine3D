package gameengine.threed.objectproperties;

import gameengine.vectormath.Vector3D;

public interface Directional {
    Vector3D getDirection();

    void setDirection(Vector3D newDirection);

    default void changeDirection(Vector3D change) {
        setDirection(getDirection().add(change));
    }
}
