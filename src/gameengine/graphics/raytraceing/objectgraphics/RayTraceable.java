package gameengine.graphics.raytraceing.objectgraphics;

import gameengine.geometry.RayIntersectable;
import gameengine.Objects.objectproperties.Renderable;
import gameengine.graphics.raytraceing.LightRay;
import gameengine.graphics.raytraceing.Reflection;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.utilities.vectormath.Vector3D;
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
