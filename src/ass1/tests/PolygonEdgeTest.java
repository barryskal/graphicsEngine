package ass1.tests;

import ass1.GameObject;
import ass1.PolygonalGameObject;

public class PolygonEdgeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[] points = {0, 0, 0, 1, 1, 1, 1, 0};
		PolygonalGameObject polygon = new PolygonalGameObject(GameObject.ROOT, points, null, null);
		
		polygon.printEdgeList();
			
	}

}
