package gameengine.Objects.prebuilt;

import gameengine.Objects.GameObject;
import gameengine.Objects.objectproperties.HasRenderable;
import gameengine.graphics.raytraceing.objectgraphics.SphereGraphics;
import gameengine.graphics.raytraceing.textures.Metallic;
import gameengine.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.utilities.vectormath.Vector3D;
import javafx.scene.paint.Color;

public class Sphere extends GameObject implements HasRenderable<SphereGraphics> {
    private SphereGraphics graphics;
    public Sphere(double x, double y, double z, double r, RayTracingTexture texture) {
        this(new Vector3D(x, y, z), r, texture);
//        get(SphereCollider.class).instantiate(this, r);
//        get(InPlane3D.class).instantiate(this, x, y, z);
//        get(SphereGraphics.class).instantiate(this, r, texture);
//        get(Visual3D.class).instantiate(this, get(SphereGraphics.class));
    }

    public Sphere(Vector3D position, double r, RayTracingTexture texture) {
        super(position);
        setRenderable(new SphereGraphics(r, texture, this::getLocation));
//        get(SphereCollider.class).instantiate(this, r);
//        get(InPlane3D.class).instantiate(this, position);
//        get(SphereGraphics.class).instantiate(this, r, texture);
//        get(Visual3D.class).instantiate(this, get(SphereGraphics.class));
    }

    public Sphere(double x, double y, double z, double r, Color color, boolean isLightSource) {
        this(new Vector3D(x, y, z), r, new Metallic(color, isLightSource ? 1 : 0, 0));
//        get(SphereCollider.class).instantiate(this, r);
//        get(InPlane3D.class).instantiate(this, x, y, z);
//        get(SphereGraphics.class).instantiate(this, r, new Metallic(color, isLightSource ? 1 : 0, 0));
//        get(Visual3D.class).instantiate(this, get(SphereGraphics.class));
    }

    @Override
    public SphereGraphics getRenderable() {
        return graphics;
    }

    @Override
    public void setRenderable(SphereGraphics newRenderable) {
        this.graphics = newRenderable;
    }
}
