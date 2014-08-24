package ass1.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ass1.GameObject;
import ass1.LineGameObject;
import ass1.MathUtil;

public class TestCollision extends TestCase 
{

	@Test
    public void testLineCollision0() {
		double white[] = {1,1,1,1};
		GameObject obj = new LineGameObject(GameObject.ROOT, 1, 1, 3, 3, white);
        
		double collisionPoint[] = {1, 3};
		
		assertFalse(obj.collision(collisionPoint));

    }
	
	@Test
    public void testLineCollision1() {
		double white[] = {1,1,1,1};
		GameObject obj = new LineGameObject(GameObject.ROOT, 1, 1, 3, 3, white);
        
		double collisionPoint[] = {2, 2};
		
		assertTrue(obj.collision(collisionPoint));

    }
	
	@Test
    public void testLineCollision2() {
		double white[] = {1,1,1,1};
		GameObject obj = new LineGameObject(GameObject.ROOT, 0, 0, 3, 0, white);
        
		//obj.translate(0, 1);
		obj.rotate(45);
		//MathUtil.printMatrix(obj.getGlobaltransformationMatrix());
		
		double collisionPoint[] = {1, 1};
		
		assertTrue(obj.collision(collisionPoint));

    }
}
