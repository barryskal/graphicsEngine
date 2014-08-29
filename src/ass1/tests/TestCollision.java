package ass1.tests;

import junit.framework.TestCase;

import org.junit.Test;

import ass1.CircularGameObject;
import ass1.GameObject;
import ass1.LineGameObject;
import ass1.MathUtil;
import ass1.PolygonalGameObject;

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
	
	@Test
	public void testPolygonCollision1()
	{
		double white[] = {1,1,1,1};
		double points[] = {0,0,0,1,1,1,1,0};
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, points, null, white);
		
		double collisionPoint[] = {2, 1};
		
		assertFalse(obj.collision(collisionPoint));
	}
	
	@Test
	public void testPolygonCollision2()
	{
		double white[] = {1,1,1,1};
		double points[] = {0,0,0,1,1,1,1,0};
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, points, null, white);
		
		double collisionPoint[] = {0.5, 0.5};
		
		assertTrue(obj.collision(collisionPoint));
	}
	
	@Test
	public void testPolygonCollision3()
	{
		/*
		 * A point that was outside the polygon as per test 1 should now be 
		 * inside if the polygon is scaled.
		 */
		double white[] = {1,1,1,1};
		double points[] = {0,0,0,1,1,1,1,0};
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, points, null, white);
		
		obj.setScale(2);
		double collisionPoint[] = {2, 1};
		
		assertTrue(obj.collision(collisionPoint));
	}
	
	@Test
	public void testPolygonCollision4()
	{
		/*
		 * A point that was outside the polygon as per test 1 should now be 
		 * inside if the polygon is moved.
		 */
		double white[] = {1,1,1,1};
		double points[] = {0,0,0,1,1,1,1,0};
		GameObject obj = new PolygonalGameObject(GameObject.ROOT, points, null, white);
		
		
		obj.setPosition(1, 0.5);;
		double collisionPoint[] = {2, 1};
		
		assertTrue(obj.collision(collisionPoint));
	}
	
	@Test
	public void testCircleCollision1()
	{
		GameObject obj = new CircularGameObject(GameObject.ROOT, 1, null, null);
		
		double collisionPoint[] = {0.5, 0};
		
		assertTrue(obj.collision(collisionPoint));
	}
	
	@Test
	public void testCircleCollision2()
	{
		GameObject obj = new CircularGameObject(GameObject.ROOT, 1, null, null);
		
		double collisionPoint[] = {1, 1};
		
		assertFalse(obj.collision(collisionPoint));
		
		obj.setPosition(1, 1);
		
		assertTrue(obj.collision(collisionPoint));
	}
}
