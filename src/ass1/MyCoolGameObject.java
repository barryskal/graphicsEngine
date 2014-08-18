package ass1;

public class MyCoolGameObject extends GameObject {

	private PolygonalGameObject myBody;
	private PolygonalGameObject myTurret;
	private LineGameObject myCannon;
	private CircularGameObject myWheel1;
	private CircularGameObject myWheel2;
	
	public MyCoolGameObject()
	{
		super(GameObject.ROOT);
		createTank();
	}
	
	public MyCoolGameObject(GameObject parent) 
	{
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	private void createTank()
	{
		double bodyPoints [] = {0.5, 0.125, 0.5, -0.125, -0.5, -0.125, -0.5, 0.125};
		// Green fill
		double fillColour [] = {15.0 / 255.0, 138.0 / 255.0, 7.0 / 255.0, 1.0};
		// Black line
		double lineColour [] = {1, 1, 1, 1};
		myBody = new PolygonalGameObject(this, bodyPoints, fillColour, lineColour);
		
		double turretPoints [] = {0.0, 0.0, 0.125, 0.25, 0.375, 0.25, 0.5, 0.0};
		myTurret = new PolygonalGameObject(myBody, turretPoints, fillColour, lineColour);
		myTurret.setPosition(-0.25, 0.125);
		
		myCannon = new LineGameObject(myTurret,0,0,0.25,0,lineColour);
		myCannon.setPosition((0.5 - 0.125 / 2), (0.25 / 2));
		
		double radius = 0.05;
		myWheel1 = new CircularGameObject(myBody, radius, null, lineColour);
		myWheel1.setPosition((-0.5 + radius), (-0.125 - radius));
		
		myWheel2 = new CircularGameObject(myBody, radius, null, lineColour);
		myWheel2.setPosition((0.5 - radius), (-0.125 - radius));
		
		
		
		
	}
	
	

}
