package gameengine.threed.geometry;

import gameengine.vectormath.Vector4D;

public class VectorLine3D {
    /**
     * The direction the {@code Line} moves.
     */
    private Vector4D direction;
    /**
     * The current position of the {@code Line}.
     */
    protected Vector4D position;

    public VectorLine3D(final Vector4D startPosition, final Vector4D direction) {
        this.direction = direction.unitVector();
        this.position = startPosition;
    }


    /*
     * Functionality:
     */

    public Vector4D pointAtDistance(double distance) {
        return position.addMultiplied(direction, distance);
    }

    public void toDistance(double distance) {
        position = position.addMultiplied(direction, distance);
    }


    /*
     * Utilities:
     */

    public Vector4D getDirection() {
        return direction;
    }

    protected void setDirection(Vector4D direction) {
        this.direction = direction.unitVector();
    }

    public Vector4D getPosition() {
        return position;
    }
}
