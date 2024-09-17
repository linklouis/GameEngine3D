package gameengine.threed.prebuilt.gameobjects;

import gameengine.skeletons.Modifier;
import gameengine.utilities.ArgumentContext;
import gameengine.utilities.ModifierInstantiateParameter;
import gameengine.vectormath.Vector4D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mesh<PolyType extends  Polygon> extends Modifier {
    private PolyType[] polygons;
    private Vector4D[] vertices;
    private final PolyType[] validPolyArray;


    /*
     * Construction:
     */

    public Mesh(PolyType[] validPolyArray) {
        super();
        this.validPolyArray = validPolyArray;
    }

    @Override
    public List<Class<? extends Modifier>> getDependencies() {
        return new ArrayList<>();
    }

    @Override
    public ArgumentContext[] getArgumentContexts() {
        return new ArgumentContext[] {
                new ArgumentContext(
                        new ModifierInstantiateParameter<>(
                                "Polygons", Polygon[].class,
                                this::setPolygons),
                        new ModifierInstantiateParameter<>(
                                "Vertices", Vector4D[].class,
                                this::setVertices)
                )
        };
    }


    /*
     * Functionality:
     */

    public static Vector4D[] getVertices(Polygon[] polygons) {
        ArrayList<Vector4D> newVertices = new ArrayList<>();
        for (Polygon poly : polygons) {
            for (Vector4D vertex : poly.getVertices()) {
                boolean alreadyContained = false;
                for (Vector4D other : newVertices) {
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

        return newVertices.toArray(new Vector4D[0]);
    }


    /*
     * Utilities:
     */

    public PolyType[] getPolygons() {
        return polygons;
    }

    public void setPolygons(Polygon[] polygons) {
        // TODO test
        ArrayList<Polygon> newPolys = new ArrayList<>();
        Collections.addAll(newPolys, polygons);
        this.polygons = newPolys.toArray(validPolyArray);
        vertices = getVertices(polygons);
    }

//    public void setPolygons(PolyType[] polygons) {
//        this.polygons = polygons;
//        vertices = getVertices(polygons);
//    }

    public Vector4D[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector4D[] vertices) {
        this.vertices = vertices;
    }

    public void setMesh(Vector4D[] vertices, PolyType[] polygons) {
        this.vertices = vertices;
        this.polygons = polygons;
    }
}
