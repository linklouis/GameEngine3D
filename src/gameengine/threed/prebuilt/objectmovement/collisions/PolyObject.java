package gameengine.threed.prebuilt.objectmovement.collisions;

import gameengine.skeletons.GameObject;
import gameengine.threed.drivers.GameDriver3D;
import gameengine.threed.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.threed.prebuilt.gameobjects.Mesh;
import gameengine.threed.prebuilt.gameobjects.Polygon;
import gameengine.vectormath.Vector3D;

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

    public void initiate(GameDriver3D driver) {
        for (Polygon poly : mesh.getPolygons()) {
            driver.newObject(poly);
        }
    }

    protected abstract PolyType[] createMesh(Vector3D[] vertices);

    public Mesh<PolyType> getMesh() {
        return mesh;
    }
}
