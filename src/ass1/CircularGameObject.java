package ass1;

public class CircularGameObject extends PolygonalGameObject 
{
	private double myRadius;
	private static final int numVertices = 32;
	
	//Create a CircularGameObject with centre 0,0 and radius 1
	public CircularGameObject(GameObject parent, double[] fillColour, double[] lineColour)
	{
		super(parent, null, fillColour, lineColour);
		myRadius = 1;
		setPoints(getPointsFromRadius());
	}

	//Create a CircularGameObject with centre 0,0 and a given radius
	public CircularGameObject(GameObject parent, double radius,double[] fillColour,double[] lineColour)
	{
		super(parent, null, fillColour, lineColour);
		myRadius = radius;
		setPoints(getPointsFromRadius());
	}
	public void setRadius(double radius)
	{
		myRadius = radius;
	}
	
	public double getRadius()
	{
		return myRadius;
	}
	
	private double[] getPointsFromRadius()
	{
		double[] points = new double[numVertices * 2];
        
		double angle = 0;
		double angleIncrement = 2*Math.PI/numVertices;

		for (int i = 0; i < points.length; i += 2)
		{
			angle = (i / 2) * angleIncrement;
			points[i] = myRadius * Math.cos(angle);
			points[i + 1] = myRadius * Math.sin(angle);	
		}
		
		return points;
	}
	
	@Override
	public boolean collision(double[] point)
	{
		/*
		 * This is a lot simpler than the polygon collision testing.
		 * First the given point is converted in to the local coordinate system 
		 * then the distance is calculated between the point and the
		 * center of the circle. If that distance is <= radius
		 * then that is a collision		 * 
		 */
		
		double[] localPoint = convertPointToLocalCoordinates(point);
		/*
		 * Note that the centre point is always 0, 0 for a circle.
		 * So calculating the distance between the two points 
		 * is as aimple as just taking the given collision point
		 * coordinates
		 */
		
		double distance = Math.sqrt((localPoint[0] * localPoint[0]) + (localPoint[1] * localPoint[1]));
		
		return distance <= myRadius;
		
	}
}
