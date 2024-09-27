package gameengine.threed.drivers;

import gameengine.skeletons.GameDriver;
import gameengine.skeletons.GameObject;
import gameengine.threed.graphics.HasRenderable;
import gameengine.threed.graphics.Renderable;
import gameengine.threed.graphics.raytraceing.objectgraphics.RayTraceable;
import gameengine.threed.prebuilt.objectmovement.physics.PhysicsEngine3D;
import gameengine.threed.prebuilt.objectmovement.physics.PhysicsObject3D;

public abstract class GameDriver3D extends GameDriver<GraphicsDriver3D<RayTraceable>, PhysicsEngine3D> {
    /*
     * Construction:
     */

    public GameDriver3D(GraphicsDriver3D<RayTraceable> graphicsDriver, PhysicsEngine3D physicsEngine) {
        super("New 3D Game", graphicsDriver, physicsEngine);
    }

    public GameDriver3D(String name, GraphicsDriver3D<RayTraceable> graphicsDriver, PhysicsEngine3D physicsEngine) {
        super(name, graphicsDriver, physicsEngine);
    }

    /*
     * Utilities:
     */

    @Override
    public void newObject(GameObject object) {
//        if (object instanceof PhysicsObject3D)) {
//            getPhysicsEngine().add(object);
//        }
//        if (object.containsModifier(Visual3D.class)) {
        if (object instanceof HasRenderable<?>) {
            getGraphicsDriver().add(((HasRenderable<? extends RayTraceable>) object).getRenderable());
        }

        getObjects().add(object);
    }
}
