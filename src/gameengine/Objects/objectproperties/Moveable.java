package gameengine.Objects.objectproperties;

import gameengine.utilities.vectormath.Vector3D;

public interface Moveable extends HasLocation {
    void setLocation(Vector3D newLocation);

    default void move(Vector3D translation) {
        setLocation(getLocation().add(translation));
    }
}
