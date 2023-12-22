package dge;

import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{
    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position         // color
            // NORMALIZED DEVICE COORDINATES
            /*
            0.5f, -0.5f,  0.0f,   1.0f, 0.0f, 0.0f, 1.0f, // bottom right 0
            -0.5f, 0.5f,  0.0f,   0.0f, 1.0f, 0.0f, 1.0f, // top left 1
            0.5f, 0.5f,   0.0f,   0.0f, 0.0f, 1.0f, 1.0f, // top right 2
            -0.5f, -0.5f, 0.0f,   1.0f, 1.0f, 0.0f, 1.0f // bottom left 3*/
             0.8f, -0.6f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // bottom right 0
             -0.8f, 0.4f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // top left 1
              0.8f, 0.4f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // top right 2
            -0.8f, -0.6f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // bottom left 3
              0.4f, 0.6f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // top right (kinda) 4
             -0.4f, 0.6f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, // top left (kinda) 5

    };

    // IMPORTANT: must be in counter-clockwise order
    /*
    x1     x2


    x3     x0
     */
    private int[] elementArray = {
            /*2, 1, 0, // top right triangle
            0, 1, 3*/
            2, 3, 0,
            4, 3, 2,
            4, 5, 3,
            5, 1, 3

    };


    private boolean changingScene = false;
    private float changeTime = 2.0f;

    private int vaoID, vboID, eboID;
    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void init() {
        //Compile and link shaders

        // first load and compile vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // pass the shader source to GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);
        // Check for errors in compilation process
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'default.glsl'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false: "";
        }

        // first load and compile fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // pass the shader source to GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);
        // Check for errors in compilation process
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'default.glsl'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false: "";
        }

        // Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking erros
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'default.glsl'\n\tLinking shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false: "";

        }

        // GENERATE VAO, VBO, EBO buffer objects and send them to the GPU

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer, GL_STATIC_DRAW);
        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize * floatSizeBytes));
        glEnableVertexAttribArray(1);


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
            Window.get().a = 0;

        } else if (changingScene) {
            Window.changeScene(1);
            
        }
        // Bind shader program
        glUseProgram(shaderProgram);
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        glUseProgram(0);
    }

}
