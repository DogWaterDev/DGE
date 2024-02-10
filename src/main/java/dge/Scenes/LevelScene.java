package dge.Scenes;

import dge.Listeners.KeyListener;
import dge.Window;

import java.awt.event.KeyEvent;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;


public class LevelScene extends Scene{
    private boolean changingScene = false;
    private float changeTime = 1.0f;
    public LevelScene() {
        System.out.println("Inside level scene");
        Window.get().r = 0.145f;
        Window.get().g = 0.678f;
        Window.get().b = 0.22f;
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
            Window.changeScene(0);


        }
    }

    public static void drawCube() {
        int vertices[] =
                {
                        -1, -1, -1,   -1, -1,  1,   -1,  1,  1,   -1,  1, -1,
                        1, -1, -1,    1, -1,  1,    1,  1,  1,    1,  1, -1,
                        -1, -1, -1,   -1, -1,  1,    1, -1,  1,    1, -1, -1,
                        -1,  1, -1,   -1,  1,  1,    1,  1,  1,    1,  1, -1,
                        -1, -1, -1,   -1,  1, -1,    1,  1, -1,    1, -1, -1,
                        -1, -1,  1,   -1,  1,  1,    1,  1,  1,    1, -1,  1
                };
        int colors[] =
                {
                        0, 0, 0,   0, 0, 1,   0, 1, 1,   0, 1, 0,
                        1, 0, 0,   1, 0, 1,   1, 1, 1,   1, 1, 0,
                        0, 0, 0,   0, 0, 1,   1, 0, 1,   1, 0, 0,
                        0, 1, 0,   0, 1, 1,   1, 1, 1,   1, 1, 0,
                        0, 0, 0,   0, 1, 0,   1, 1, 0,   1, 0, 0,
                        0, 0, 1,   0, 1, 1,   1, 1, 1,   1, 0, 1
                };
        float alpha = 0f;
        // attempt to rotate the cube
        glRotatef(alpha, 0, 1, 0);

        // enable color and vertices
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glVertexPointer(3, GL_FLOAT, 0, IntBuffer.wrap(vertices));
        glColorPointer(3, GL_FLOAT, 0, IntBuffer.wrap(colors));

        // send data: 24 vertices
        glDrawArrays(GL_QUADS, 0, 24);

        // memory cleanup
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        alpha += 1;
    }
}
