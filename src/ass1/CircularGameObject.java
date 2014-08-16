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
}
