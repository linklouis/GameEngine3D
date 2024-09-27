package gameengine.threed.objectproperties;

import gameengine.vectormath.Vector3D;

public interface Moveable extends HasLocation {
    void setLocation(Vector3D newLocation);

    default void move(Vector3D translation) {
        setLocation(getLocation().add(translation));
    }
}
