package gameengine.threed.graphics;

public interface HasRenderable<RenderableType extends Renderable> {
    RenderableType getRenderable();

    void setRenderable(RenderableType newRenderable);
}
