package gameengine.Objects.prebuilt;

import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.graphics.raytraceing.textures.Metallic;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.Objects.objectmovement.collisions.PolyObject;
import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.paint.Color;

public class RectPlane extends PolyObject<Tri> {
    private RayTracingTexture texture;
    public RectPlane(Vector3D position, Vector3D displacement,
                     RayTracingTexture texture) {
        super(position, generateVertices(position, displacement)/*, new Mesh<>(new Tri[0]), new InPlane3D()*/);

        setTexture(texture);
    }

    public RectPlane(double x, double y, double z,
                     Vector3D displacement, RayTracingTexture texture) {
        this(new Vector3D(x, y, z), displacement, texture);
    }

    private static Vector3D[] generateVertices(Vector3D position,
                                               Vector3D displacement) {
        return new Vector3D[] {
                position,
                new Vector3D(
                        position.getX() + displacement.getX(),
                        position.getY()
                                + (displacement.getZ() != 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ()),
                new Vector3D(
                        position.getX() + displacement.getX(),
                        position.getY() + displacement.getY(),
                        position.getZ() + displacement.getZ()),
                new Vector3D(
                        position.getX(),
                        position.getY()
                                + (displacement.getZ() == 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ() + displacement.getZ()),
        };
    }

    @Override
    protected Tri[] createMesh(Vector3D[] vertices) {
        RayTracingTexture texture = new Metallic(Color.WHITE, 1, 0);

        return new Tri[]{
                new Tri(vertices[0], vertices[1], vertices[2], texture),
                new Tri(vertices[0], vertices[2], vertices[3], texture)
        };
    }

    private Color randomColor() {
        return new Color(Math.random(), Math.random(), Math.random(), 1);
    }

    public RayTracingTexture getTexture() {
        return texture;
    }

    public void setTexture(RayTracingTexture texture) {
        this.texture = texture;
        for (Polygon<? extends RayTraceable> poly : getMesh().getPolygons()) {
            poly.getRenderable().setTexture(texture);
        }
    }
}
