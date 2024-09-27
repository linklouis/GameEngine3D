package gameengine.threed.prebuilt.gameobjects;

import gameengine.skeletons.GameObject;
import gameengine.threed.graphics.HasRenderable;
import gameengine.threed.graphics.Renderable;
import gameengine.vectormath.Vector3D;

public abstract class Polygon<GraphicsType extends Renderable> extends GameObject implements HasRenderable<GraphicsType> {
    private GraphicsType graphics;

    public Polygon(double x, double y, double z, GraphicsType graphics) {
        super(x, y, z);
        this.graphics  = graphics;
    }

    public Polygon(Vector3D location, GraphicsType graphics) {
        super(location);
        this.graphics = graphics;
    }
//    public Polygon(final RayTraceable graphics, Modifier... modifiers) {
//        super(new ArrayList<>() {
//            {
//                add(new Visual3D());
//                add(graphics);
//                Collections.addAll(this, modifiers);
//            }
//        });
//        get(Visual3D.class).instantiate(this, graphics);
//    }

    public abstract Vector3D[] getVertices();

    @Override
    public GraphicsType getRenderable() {
        return graphics;
    }

    @Override
    public void setRenderable(GraphicsType newRenderable) {
        this.graphics = newRenderable;
    }

//    @Override
//    public String toString() {
//        return "Polygon: " + get(RayTraceable.class).toString();
//    }
}
