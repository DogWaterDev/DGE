package dge;

public class LevelScene extends Scene{
    public LevelScene() {
        System.out.println("Inside level scene");
        Window.get().r = 0.145f;
        Window.get().g = 0.678f;
        Window.get().b = 0.22f;
    }

    @Override
    public void update(float dt) {

    }
}
