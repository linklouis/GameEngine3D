package gameengine.graphics.raytraceing.postprocesses;

import javafx.scene.image.WritableImage;

public interface PostProcess {
    WritableImage process(WritableImage image);
}
