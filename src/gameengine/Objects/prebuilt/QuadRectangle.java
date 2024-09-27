package gameengine.Objects.prebuilt;

import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.graphics.raytraceing.textures.Metallic;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.Objects.objectmovement.collisions.PolyObject;
import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.paint.Color;

public class QuadRectangle extends PolyObject<Quad> {
    private RayTracingTexture texture;


    /*
     * Construction:
     */

    public QuadRectangle(double x, double y, double z, Vector3D space, RayTracingTexture texture) {
        super(new Vector3D(x, y, z), generateVertices(x, y, z, space));
        setTexture(texture);
    }

    public QuadRectangle(Vector3D position, Vector3D space,
                         RayTracingTexture texture) {
        super(position, generateVertices(position.getX(), position.getY(), position.getZ(), space));
        setTexture(texture);
    }

    public QuadRectangle(double x, double y, double z, Vector3D space, Color color,
                         boolean isLightSource) {
        super(new Vector3D(x, y, z), generateVertices(x, y, z, space)/*, new Mesh<>(new Quad[0]), new InPlane3D()*/);
        setTexture(new Metallic(color, isLightSource ? 1 : 0, 0));
    }

    private static Vector3D[] generateVertices(double x, double y, double z,
                                               Vector3D space) {
        return new Vector3D[] {
                new Vector3D(x, y, z),
                new Vector3D(
                        x + space.getX(),
                        y,
                        z),
                new Vector3D(
                        x + space.getX(),
                        y + space.getY(),
                        z),
                new Vector3D(
                        x,
                        y + space.getY(),
                        z),
                new Vector3D(
                        x,
                        y,
                        z + space.getZ()),
                new Vector3D(
                        x + space.getX(),
                        y,
                        z + space.getZ()),
                new Vector3D(
                        x + space.getX(),
                        y + space.getY(),
                        z + space.getZ()),
                new Vector3D(
                        x,
                        y + space.getY(),
                        z + space.getZ())
        };
    }

    @Override
    protected Quad[] createMesh(Vector3D[] vertices) {
        RayTracingTexture texture = new Metallic(Color.WHITE, 1, 0);

        return new Quad[] {
                // Front face
                new Quad(vertices[0], vertices[1], vertices[2], vertices[3], texture),

                // Right face
                new Quad(vertices[1], vertices[5], vertices[6], vertices[2], texture),

                // Back face
                new Quad(vertices[5], vertices[4], vertices[7], vertices[6], texture),

                // Left face
                new Quad(vertices[4], vertices[0], vertices[3], vertices[7], texture),

                // Top face
                new Quad(vertices[3], vertices[2], vertices[6], vertices[7], texture),

                // Bottom face
                new Quad(vertices[0], vertices[4], vertices[5], vertices[1], texture),
        };
    }


    /*
     * Utilities:
     */

    private Color randomColor() {
        return new Color(Math.random(), Math.random(), Math.random(), 1);
    }

    public RayTracingTexture getTexture() {
        return texture;
    }

    public void setTexture(RayTracingTexture texture) {
        this.texture = texture;
//        int num = 1;
        for (Polygon<? extends RayTraceable> poly : getMesh().getPolygons()) {
//            double col = (double) num / get(Mesh.class).getPolygons().length;
            poly.getRenderable().setTexture(texture);//new Metallic(new Color(col, col, col, 1), texture.isLightSource(), texture.getReflectivity()));
//            num++;
        }
    }
}
