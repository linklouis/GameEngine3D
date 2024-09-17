package gameengine.threed.geometry;

import gameengine.vectormath.Vector4D;

public interface VectorLineIntersectable {
    boolean willCollide(VectorLine3D line);

    Vector4D getCenter();
}
