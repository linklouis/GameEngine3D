package gameengine.Objects.prebuilt;

import gameengine.utilities.vectormath.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Mesh<PolyType extends  Polygon<?>> {
    private PolyType[] polygons;
    private Vector3D[] vertices;
    private final PolyType[] validPolyArray;


    /*
     * Construction:
     */

    public Mesh(PolyType[] validPolyArray) {
        this.validPolyArray = validPolyArray;
    }

    public Mesh(PolyType[] validPolyArray, Polygon<?>[] polys, Vector3D[] vertices) { //TODO might not make sense, idr how this works lol
        this.validPolyArray = validPolyArray;
        setPolygons(polys);
        setVertices(vertices);
    }

    public Mesh(PolyType[] mesh, Vector3D[] vertices) {
        this.validPolyArray = mesh;
        setVertices(vertices);
    }


    /*
     * Functionality:
     */

    public static Vector3D[] getVertices(Polygon<?>[] polygons) {
        ArrayList<Vector3D> newVertices = new ArrayList<>();
        for (Polygon<?> poly : polygons) {
            for (Vector3D vertex : poly.getVertices()) {
                boolean alreadyContained = false;
                for (Vector3D other : newVertices) {
                    if (other.equals(vertex)) {
                        alreadyContained = true;
                        break;
                    }
                }

                if (!alreadyContained) {
                    newVertices.add(vertex);
                }
            }
        }

        return newVertices.toArray(new Vector3D[0]);
    }


    /*
     * Utilities:
     */

    public PolyType[] getPolygons() {
        return validPolyArray/*polygons*/;
    }

    public void setPolygons(Polygon<?>[] polygons) {
        // TODO test
        ArrayList<Polygon<?>> newPolys = new ArrayList<>();
        Collections.addAll(newPolys, polygons);
        this.polygons = newPolys.toArray(validPolyArray);
        vertices = getVertices(polygons);
    }

//    public void setPolygons(PolyType[] polygons) {
//        this.polygons = polygons;
//        vertices = getVertices(polygons);
//    }

    public Vector3D[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }

    public void setMesh(Vector3D[] vertices, PolyType[] polygons) {
        this.vertices = vertices;
        this.polygons = polygons;
    }

    @Override
    public String toString() {
        return "Mesh(" + Arrays.toString(getPolygons()) + ")";
    }
}
