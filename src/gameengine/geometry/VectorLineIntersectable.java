package gameengine.geometry;

import gameengine.utilities.vectormath.Vector3D;

public interface VectorLineIntersectable {
    boolean willCollide(VectorLine3D line);

    Vector3D getCenter();
}
