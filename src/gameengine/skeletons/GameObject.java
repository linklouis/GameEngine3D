package gameengine.skeletons;

import gameengine.threed.objectproperties.Directional;
import gameengine.threed.objectproperties.HasLocation;
import gameengine.vectormath.Vector3D;

public abstract class GameObject implements HasLocation {
    private Vector3D location;

    public GameObject(double x, double y, double z) {
        this(new Vector3D(x, y, z));
    }

    public GameObject(Vector3D location) {
        this.location = location;
    }

    @Override
    public void setLocation(Vector3D newLocation) {
        this.location = newLocation;
    }

    @Override
    public Vector3D getLocation() {
        return location;
    }

    @Override
    public double getX() {
        return getLocation().getX();
    }

    @Override
    public double getY() {
        return getLocation().getY();
    }

    @Override
    public double getZ() {
        return getLocation().getZ();
    }

}
