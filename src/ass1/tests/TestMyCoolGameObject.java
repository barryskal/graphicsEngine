package ass1.tests;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import ass1.Camera;
import ass1.GameEngine;
import ass1.GameObject;
import ass1.MyCoolGameObject;

/**
 * A simple class to view MyCoolGameObject
 *
 * @author angf
 */
public class TestMyCoolGameObject implements KeyListener{

	private static MyCoolGameObject cgo;
	
	public static void createTestObjects(){
		
		//Should look good when we create using the default constructor
		cgo = new MyCoolGameObject();
		
		// Should not break if we apply transformations for example
		// If we uncommented these lines (or wrote other transformations) 
		// it should not break your object
		
		cgo.translate(-0.2,0.2);
		cgo.rotate(45);
		cgo.scale(0.25);
		
	}
   
    /**
     * A simple test for MyCoolGameObject
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Initialise OpenGL
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        
        // create a GLJPanel to draw on
        GLJPanel panel = new GLJPanel(glcapabilities);

        // Create a camera
        Camera camera = new Camera(GameObject.ROOT);
        
        createTestObjects();
        
        TestMyCoolGameObject cgo = new TestMyCoolGameObject();
        // Add the game engine
        GameEngine engine = new GameEngine(camera);
        panel.addGLEventListener(engine);

        panel.addKeyListener(cgo);
        
        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        // Put it in a window
        JFrame jFrame = new JFrame("Test Shapes");
        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_SPACE:
			if (cgo.isJumping())
				break;
			cgo.startJump();
			
		default:
			break;
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
