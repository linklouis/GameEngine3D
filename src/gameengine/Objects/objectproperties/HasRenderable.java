package gameengine.Objects.objectproperties;

import gameengine.Objects.objectproperties.Renderable;

public interface HasRenderable<RenderableType extends Renderable> {
    RenderableType getRenderable();

    void setRenderable(RenderableType newRenderable);
}
