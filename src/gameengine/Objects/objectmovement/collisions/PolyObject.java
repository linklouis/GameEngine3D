package gameengine.Objects.objectmovement.collisions;

import gameengine.drivers.GameDriver;
import gameengine.Objects.GameObject;
import gameengine.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.Objects.prebuilt.Mesh;
import gameengine.Objects.prebuilt.Polygon;
import gameengine.utilities.vectormath.Vector3D;

public abstract class PolyObject<PolyType extends Polygon<? extends RayTraceable>> extends GameObject {
    private Mesh<PolyType> mesh;

    public PolyObject(Vector3D location, Mesh<PolyType> mesh) {
        super(location);
        this.mesh = mesh;
//        try {
//            get(Mesh.class).instantiate(this, mesh.getPolygons(), mesh.getVertices());
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    public PolyObject(Vector3D location, Vector3D[] vertices) {
        super(location);
        this.mesh = new Mesh(createMesh(vertices), vertices);
//        try {
//            get(Mesh.class).instantiate(this, createMesh(vertices), vertices);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void initiate(GameDriver driver) {
        for (Polygon poly : mesh.getPolygons()) {
            driver.newObject(poly);
        }
    }

    protected abstract PolyType[] createMesh(Vector3D[] vertices);

    public Mesh<PolyType> getMesh() {
        return mesh;
    }
}
