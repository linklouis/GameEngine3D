package gameengine.threed.graphics.raytraceing.textures;

import gameengine.threed.graphics.raytraceing.LightRay;
import gameengine.threed.graphics.raytraceing.Reflection;
import gameengine.vectormath.Vector4D;
import javafx.scene.paint.Color;

public abstract class RayTracingTexture {
    private Color color;
    private double emissionStrength;
    private Vector4D emissionColor;
    private Vector4D emission;

    public RayTracingTexture(Color color, double emissionStrength, Vector4D emissionColor) {
        this.color = color;
        this.emissionStrength = emissionStrength;
        this.emissionColor = new Vector4D(emissionColor);
        calculateEmission();
    }

    public RayTracingTexture(Color color, double emissionStrength, Color emissionColor) {
        this.color = color;
        this.emissionStrength = emissionStrength;
        this.emissionColor = new Vector4D(emissionColor);
        calculateEmission();
    }

    public abstract Reflection reflection(final LightRay lightRayDirection,
                                          final Vector4D surfaceNormal);

    public Vector4D scatterRay(Vector4D surfaceNormal) {
        Vector4D reflection = Vector4D.random(-1, 1);
//        reflection =;
        return reflection.add(surfaceNormal.unitVector()).unitVector();
//        if (reflection.dotProduct(surfaceNormal) > 0) {
//            reflection = reflection.scalarMultiply(-1);
//        }
//        return reflection;
    }

    public Vector4D reflectRay(final Vector4D rayDirection,
                               final Vector4D surfaceNormal) {
        return rayDirection.reflectFromNormal(surfaceNormal);
//          return rayDirection.subtract(
//                surfaceNormal.scalarMultiply(
//                        2 * rayDirection.dotProduct(surfaceNormal)));
                              /*  / Math.pow(surfaceNormal.magnitude(), 2)*/
    }

    protected Vector4D defaultColorUpdate(Vector4D rayColor) {
        return rayColor.multiplyAcross(colorVector());
    }


    /*
     * Utilities:
     */

    public Color getColor() {
        return color;
    }

    public Vector4D colorVector() {
        return new Vector4D(color);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getEmissionStrength() {
        return emissionStrength;
    }

    public void setEmissionStrength(double emissionStrength) {
        this.emissionStrength = emissionStrength;
        calculateEmission();
    }

    public Vector4D getEmissionColor() {
        return emissionColor;
    }

    public void setEmissionColor(Vector4D emissionColor) {
        this.emissionColor = emissionColor;
        calculateEmission();
    }

    public Vector4D getEmission() {
        return emission;
    }

    protected void calculateEmission() {
        emission = emissionColor.scalarMultiply(emissionStrength);
    }

    public boolean isLightSource() {
        return emissionStrength > 0;
    }

    @Override
    public String toString() {
        return "RayTracingTexture: " + color;
//                + ", " + isLightSource();
    }
}
