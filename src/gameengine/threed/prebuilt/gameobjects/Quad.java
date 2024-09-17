package gameengine.threed.prebuilt.gameobjects;

import gameengine.skeletons.Modifier;
import gameengine.threed.graphics.Visual3D;
import gameengine.threed.graphics.raytraceing.objectgraphics.QuadGraphics;
import gameengine.threed.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.vectormath.Vector2D;
import gameengine.vectormath.Vector4D;

import java.util.ArrayList;
import java.util.List;

public class Quad extends Polygon  {
    public Quad(Vector4D point1, Vector4D point2, Vector4D point3, Vector4D point4, RayTracingTexture texture) {
        super(new QuadGraphics());
        get(QuadGraphics.class).instantiate(this, new Vector4D[] { point1, point2, point3, point4 }, texture);
    }

    public Quad(Vector4D position, Vector2D size, Vector4D normal, RayTracingTexture texture) {
        super(new QuadGraphics());
        get(QuadGraphics.class).instantiate(this, generateVertices(position, size, normal), texture);
    }


    @Override
    public List<Class<? extends Modifier>> getDependencies() {
        return new ArrayList<>() {
            {
                add(QuadGraphics.class);
                add(Visual3D.class);
            }
        };
    }

    private static Vector4D[] generateVertices(Vector4D position, Vector2D size, Vector4D normal) {
        // Thx to Chat-GPT!
        // Calculate the right and up vectors based on the normal
        Vector4D rightVector = normal.crossProduct(Vector4D.J);
        if (rightVector.magnitude() < 1e-6) {
            // If rightVector is too small, use a fallback vector (e.g., Vector4D.I)
            rightVector = Vector4D.I;
        } else {
            rightVector = rightVector.unitVector();
        }

        Vector4D upVector = normal.crossProduct(rightVector);
        if (upVector.magnitude() < 1e-6) {
            // If upVector is too small, use a fallback vector (e.g., Vector4D.K)
            upVector = Vector4D.K;
        } else {
            upVector = upVector.unitVector();
        }

        // Adjust width and height vectors according to the orientation
        Vector4D halfWidth = rightVector.scalarMultiply(size.getX() / 2.0);
        Vector4D halfHeight = upVector.scalarMultiply(size.getY() / 2.0);

        // Calculate the vertices based on the position, half-width, and half-height
        Vector4D vertex1 = position.add(halfWidth).add(halfHeight);
        Vector4D vertex2 = position.subtract(halfWidth).add(halfHeight);
        Vector4D vertex3 = position.subtract(halfWidth).subtract(halfHeight);
        Vector4D vertex4 = position.add(halfWidth).subtract(halfHeight);

        return new Vector4D[]{vertex1, vertex2, vertex3, vertex4};
    }


//    private static Vector4D[] generateVertices(Vector4D position,
//                                               Vector2D size, Vector4D normal) {
//        // Calculate the right and up vectors based on the normal
//        Vector4D rightVector = normal.crossProduct(Vector4D.J).unitVector();
//        Vector4D upVector = normal.crossProduct(rightVector).unitVector();
//
//        // Adjust width and height vectors according to the orientation
//        Vector4D width = rightVector.scalarMultiply(size.getX());
//        Vector4D height = upVector.scalarMultiply(size.getY());
//
//        Vector4D vertex1 = position.add(width).add(height);
//        Vector4D vertex2 = position.subtract(width).add(height);
//        Vector4D vertex3 = position.subtract(width).subtract(height);
//        Vector4D vertex4 = position.add(width).subtract(height);
//
//        return new Vector4D[]{vertex1, vertex2, vertex3, vertex4};
//    }

//    private static Vector4D[] generateVertices(Vector4D position,
//                                               Vector4D displacement1, Vector4D displacement2) {
//        Vector4D vertex2 = new Vector4D(
//                position.getX() + displacement1.getX(),
//                position.getY()
//                        + (displacement2.getZ() != 0 ?
//                        displacement1.getY()
//                        : 0),
//                position.getZ());
//        Vector4D vertex3 = new Vector4D(
//                position.getX() + displacement1.getX() + displacement2.getX(),
//                position.getY() + displacement1.getY() + displacement2.getY(),
//                position.getZ() + displacement2.getZ());
//        Vector4D vertex4 = new Vector4D(
//                position.getX() + displacement2.getX(),
//                position.getY()
//                        + (displacement2.getZ() == 0 ?
//                        displacement1.getY()
//                        : 0),
//                position.getZ() + displacement2.getZ());
//
//        return new Vector4D[]{position, vertex2, vertex3, vertex4};
//    }

    private static Vector4D[] generateVertices(Vector4D position,
                                               Vector4D displacement) {
        return new Vector4D[] {
                position,
                new Vector4D(
                        position.getX() + displacement.getX(),
                        position.getY()
                                + (displacement.getZ() != 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ()),
                new Vector4D(
                        position.getX() + displacement.getX(),
                        position.getY() + displacement.getY(),
                        position.getZ() + displacement.getZ()),
                new Vector4D(
                        position.getX(),
                        position.getY()
                                + (displacement.getZ() == 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ() + displacement.getZ()),
        };
    }

    @Override
    public Vector4D[] getVertices() {
        return get(QuadGraphics.class).getVertices();
    }

    @Override
    public String toString() {
        return "Quad: " + get(QuadGraphics.class).toString();
    }
}
