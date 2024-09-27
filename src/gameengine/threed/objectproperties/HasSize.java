package gameengine.threed.objectproperties;

import gameengine.vectormath.Vector3D;

public interface HasSize {
    Vector3D getSize();

    Vector3D setSize(Vector3D newSize);

    default void changeSize(Vector3D change) {
        setSize(getSize().add(change));
    }

    default void scale(double value) {
        setSize(getSize().scalarMultiply(value));
    }
}
