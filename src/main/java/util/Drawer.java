package util;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class Drawer {

    public Drawer(){
        glClearColor( 0.0f, 0.0f, 0.0f, 0.0f ); //clear background screen to black

        //Clear information from last draw
        glClear( GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW); //Switch to the drawing perspective
        glLoadIdentity(); //Reset the drawing perspective
    }
    public static void drawTriamgle(long window, float[] first, float[] second, float[] third) {
        glBegin(GL_TRIANGLES); //Begin quadrilateral coordinates
        glVertex3f(first[0], first[1], first[2]);
        glVertex3f(-1.0f, 1.5f, -5.0f);
        glVertex3f(-1.5f, 0.5f, -5.0f);
        glEnd();
    }
}
