package gameengine.threed.graphics.raytraceing.objectgraphics;

import gameengine.threed.geometry.RayIntersectable;
import gameengine.threed.graphics.Renderable;
import gameengine.threed.graphics.raytraceing.LightRay;
import gameengine.threed.graphics.raytraceing.Reflection;
import gameengine.threed.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.vectormath.Vector3D;
import javafx.scene.paint.Color;

public abstract class RayTraceable implements RayIntersectable, Renderable {
    private RayTracingTexture texture = null;


    /*
     * Construction:
     */

    public RayTraceable(RayTracingTexture texture) {
        this.texture = texture;
    }

    /*
     * Functionality:
     */

    public Reflection reflection(LightRay lightRay) {
        return getTexture().reflection(lightRay, surfaceNormal(lightRay));
    }


    /*
     * Utilities:
     */

    public abstract Vector3D[] getVertices();
//
//    RayTracingTexture getTexture();
//
//    void setTexture(RayTracingTexture texture);
//
//    default Color getColor() {
//        return getTexture().getColor();
//    }
//
//    default Vector3D colorVector() {
//        return new Vector3D(getTexture().getColor());
//    }
//    default void setColor(Color color) {
//        getTexture().setColor(color);
//    }

    public RayTracingTexture getTexture() {
        return texture;
    }

    public void setTexture(RayTracingTexture texture) {
        this.texture = texture;
    }

    public Color getColor() {
        return getTexture().getColor();
    }

    public Vector3D colorVector() {
        return new Vector3D(getTexture().getColor());
    }
    public void setColor(Color color) {
        getTexture().setColor(color);
    }
}
