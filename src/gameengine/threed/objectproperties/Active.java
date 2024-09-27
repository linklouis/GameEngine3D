package gameengine.threed.objectproperties;

import gameengine.vectormath.Vector3D;

public interface Active extends Moveable {
    Vector3D getVelocity();

    void setVelocity(Vector3D newVelocity);

    default void changeVelocity(Vector3D acceleration) {
        setVelocity(getVelocity().add(acceleration));
    }
}
