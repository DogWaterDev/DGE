package dge;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{
    private boolean changingScene = false;
    private float changeTime = 2.0f;
    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {
        if (!(dt == 0)) {
            System.out.println((1.0f / dt) + "FPS");
        }
        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }
        if (changingScene && changeTime > 0) {
            changeTime -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;

        } else if (changingScene) {
            Window.changeScene(1);
            
        }
    }

}
