package gameengine.graphics.raytraceing;

import gameengine.utilities.vectormath.Vector3D;

public record Reflection(Vector3D direction, Vector3D color) {
    public Reflection {
        direction.unitVectorMutable();
    }
}
