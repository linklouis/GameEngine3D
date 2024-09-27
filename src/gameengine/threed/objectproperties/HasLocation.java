package gameengine.threed.objectproperties;

import gameengine.vectormath.Vector3D;

public interface HasLocation {

    Vector3D getLocation();

    void setLocation(Vector3D newLocation);

    default double getX() {
        return getLocation().getX();
    }

    default double getY() {
        return getLocation().getY();
    }

    default double getZ() {
        return getLocation().getZ();
    }
}
