package components;

import dge.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    private Vector4f color;
    public SpriteRenderer(Vector4f color) {
        init(color);
    }
    public void init(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }
    @Override
    public void start() {
    }
    @Override
    public void update(float dt) {

    }
}
