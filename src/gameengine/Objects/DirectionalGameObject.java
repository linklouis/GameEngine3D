package gameengine.Objects;

import gameengine.Objects.objectproperties.Directional;
import gameengine.utilities.vectormath.Vector3D;

public class DirectionalGameObject extends GameObject implements Directional {
    private Vector3D direction;

    public DirectionalGameObject(Vector3D location, Vector3D direction) {
        super(location);
        this.direction = direction.normalize();
    }

    public DirectionalGameObject(double x, double y, double z, Vector3D direction) {
        this(new Vector3D(x, y, z), direction.normalize());
    }

    @Override
    public void setDirection(Vector3D newDirection) {
        this.direction = newDirection.normalize();
    }

    @Override
    public Vector3D getDirection() {
        return direction;
    }
}
