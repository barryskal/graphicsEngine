package ass1;

import javax.media.opengl.GL2;

/**
 * The camera is a GameObject that can be moved, rotated and scaled like any other.
 * 
 * TODO: You need to implment the setView() and reshape() methods.
 *       The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class Camera extends GameObject {

    private float[] myBackground;

    public Camera(GameObject parent) {
        super(parent);

        myBackground = new float[4];
    }

    public Camera() {
        this(GameObject.ROOT);
    }
    
    public float[] getBackground() {
        return myBackground;
    }

    public void setBackground(float[] background) {
        myBackground = background;
    }

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
   
    
    public void setView(GL2 gl) {
        
        // TODO 1. clear the view to the background colour
        
        // TODO 2. set the view matrix to account for the camera's position         
    }

    public void reshape(GL2 gl, int x, int y, int width, int height) {
        // TODO  1. match the projection aspect ratio to the viewport
        // to avoid stretching
        
    }
}
