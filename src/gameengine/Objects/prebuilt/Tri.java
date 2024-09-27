package gameengine.Objects.prebuilt;

import gameengine.Objects.objectproperties.HasRenderable;
import gameengine.graphics.raytraceing.objectgraphics.TriGraphics;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.utilities.vectormath.Vector3D;

public class Tri extends Polygon<TriGraphics> implements HasRenderable<TriGraphics> {
    public Tri(Vector3D point1, Vector3D point2, Vector3D point3, RayTracingTexture texture) {
        super(point1, new TriGraphics(new Vector3D[] { point1, point2, point3 }, texture));
    }

    @Override
    public Vector3D[] getVertices() {
        return getRenderable().getVertices();
    }

    @Override
    public String toString() {
        return "Tri: " + getRenderable().toString();
    }
}
