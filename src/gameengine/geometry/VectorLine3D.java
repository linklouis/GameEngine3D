package gameengine.geometry;

import gameengine.utilities.vectormath.Vector3D;

public class VectorLine3D {
    /**
     * The direction the {@code Line} moves.
     */
    private Vector3D direction;
    /**
     * The current position of the {@code Line}.
     */
    protected Vector3D position;

    public VectorLine3D(final Vector3D startPosition, final Vector3D direction) {
        this.direction = direction.unitVector();
        this.position = startPosition;
    }


    /*
     * Functionality:
     */

    public Vector3D pointAtDistance(double distance) {
        return position.addMultiplied(direction, distance);
    }

    public void toDistance(double distance) {
        position = position.addMultiplied(direction, distance);
//        position.x += direction.x * distance;
//        position.y += direction.y * distance;
//        position.z += direction.z * distance;
//        position.magnitude = Double.NaN;
//                Math.sqrt(
//                position.x * position.x
//                        + position.y * position.y
//                        + position.z * position.z);
//        System.out.println(position);
    }


    /*
     * Utilities:
     */

    public Vector3D getDirection() {
        return direction;
    }

    protected void setDirection(Vector3D direction) {
        this.direction = direction.unitVector();
    }

    public Vector3D getPosition() {
        return position;
    }
}
