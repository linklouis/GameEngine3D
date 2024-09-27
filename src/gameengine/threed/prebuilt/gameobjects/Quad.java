package gameengine.threed.prebuilt.gameobjects;

import gameengine.threed.graphics.HasRenderable;
import gameengine.threed.graphics.raytraceing.objectgraphics.QuadGraphics;
import gameengine.threed.graphics.raytraceing.textures.RayTracingTexture;
import gameengine.vectormath.Vector2D;
import gameengine.vectormath.Vector3D;

public class Quad extends Polygon<QuadGraphics> implements HasRenderable<QuadGraphics> {

    public Quad(Vector3D point1, Vector3D point2, Vector3D point3, Vector3D point4, RayTracingTexture texture) {
        super(point1.add(point1).add(point3).add(point4).scalarDivide(4), new QuadGraphics(new Vector3D[] { point1, point2, point3, point4 }, texture));
    }

    public Quad(Vector3D position, Vector2D size, Vector3D normal, RayTracingTexture texture) {
        super(position, new QuadGraphics(generateVertices(position, size, normal), texture));
    }


    private static Vector3D[] generateVertices(Vector3D position, Vector2D size, Vector3D normal) {
        // Thx to Chat-GPT!
        // Calculate the right and up vectors based on the normal
        Vector3D rightVector = normal.crossProduct(Vector3D.J);
        if (rightVector.magnitude() < 1e-6) {
            // If rightVector is too small, use a fallback vector (e.g., Vector3D.I)
            rightVector = Vector3D.I;
        } else {
            rightVector = rightVector.unitVector();
        }

        Vector3D upVector = normal.crossProduct(rightVector);
        if (upVector.magnitude() < 1e-6) {
            // If upVector is too small, use a fallback vector (e.g., Vector3D.K)
            upVector = Vector3D.K;
        } else {
            upVector = upVector.unitVector();
        }

        // Adjust width and height vectors according to the orientation
        Vector3D halfWidth = rightVector.scalarMultiply(size.getX() / 2.0);
        Vector3D halfHeight = upVector.scalarMultiply(size.getY() / 2.0);

        // Calculate the vertices based on the position, half-width, and half-height
        Vector3D vertex1 = position.add(halfWidth).add(halfHeight);
        Vector3D vertex2 = position.subtract(halfWidth).add(halfHeight);
        Vector3D vertex3 = position.subtract(halfWidth).subtract(halfHeight);
        Vector3D vertex4 = position.add(halfWidth).subtract(halfHeight);

        return new Vector3D[]{vertex1, vertex2, vertex3, vertex4};
    }


//    private static Vector3D[] generateVertices(Vector3D position,
//                                               Vector2D size, Vector3D normal) {
//        // Calculate the right and up vectors based on the normal
//        Vector3D rightVector = normal.crossProduct(Vector3D.J).unitVector();
//        Vector3D upVector = normal.crossProduct(rightVector).unitVector();
//
//        // Adjust width and height vectors according to the orientation
//        Vector3D width = rightVector.scalarMultiply(size.getX());
//        Vector3D height = upVector.scalarMultiply(size.getY());
//
//        Vector3D vertex1 = position.add(width).add(height);
//        Vector3D vertex2 = position.subtract(width).add(height);
//        Vector3D vertex3 = position.subtract(width).subtract(height);
//        Vector3D vertex4 = position.add(width).subtract(height);
//
//        return new Vector3D[]{vertex1, vertex2, vertex3, vertex4};
//    }

//    private static Vector3D[] generateVertices(Vector3D position,
//                                               Vector3D displacement1, Vector3D displacement2) {
//        Vector3D vertex2 = new Vector3D(
//                position.getX() + displacement1.getX(),
//                position.getY()
//                        + (displacement2.getZ() != 0 ?
//                        displacement1.getY()
//                        : 0),
//                position.getZ());
//        Vector3D vertex3 = new Vector3D(
//                position.getX() + displacement1.getX() + displacement2.getX(),
//                position.getY() + displacement1.getY() + displacement2.getY(),
//                position.getZ() + displacement2.getZ());
//        Vector3D vertex4 = new Vector3D(
//                position.getX() + displacement2.getX(),
//                position.getY()
//                        + (displacement2.getZ() == 0 ?
//                        displacement1.getY()
//                        : 0),
//                position.getZ() + displacement2.getZ());
//
//        return new Vector3D[]{position, vertex2, vertex3, vertex4};
//    }

    private static Vector3D[] generateVertices(Vector3D position,
                                               Vector3D displacement) {
        return new Vector3D[] {
                position,
                new Vector3D(
                        position.getX() + displacement.getX(),
                        position.getY()
                                + (displacement.getZ() != 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ()),
                new Vector3D(
                        position.getX() + displacement.getX(),
                        position.getY() + displacement.getY(),
                        position.getZ() + displacement.getZ()),
                new Vector3D(
                        position.getX(),
                        position.getY()
                                + (displacement.getZ() == 0 ?
                                displacement.getY()
                                : 0),
                        position.getZ() + displacement.getZ()),
        };
    }

    @Override
    public Vector3D[] getVertices() {
        return getRenderable().getVertices();
    }

    @Override
    public String toString() {
        return "Quad: " + getRenderable().toString();
    }
}
