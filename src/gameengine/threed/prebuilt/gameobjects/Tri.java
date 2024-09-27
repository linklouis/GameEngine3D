package gameengine.threed.prebuilt.gameobjects;

import gameengine.threed.graphics.HasRenderable;
import gameengine.threed.graphics.raytraceing.objectgraphics.TriGraphics;
import gameengine.threed.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.vectormath.Vector3D;

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
