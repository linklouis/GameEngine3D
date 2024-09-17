package gameengine.threed.graphics.raytraceing;

import gameengine.vectormath.Vector4D;

public record Reflection(Vector4D direction, Vector4D color) {
    public Reflection {
        direction.unitVectorMutable();
    }
}
