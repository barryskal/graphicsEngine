package ass1;

public class MyCoolGameObject extends GameObject {

	// The time it takes to complete a jump in ms
	private static final double JUMP_TIME = 0.50;
	
	private PolygonalGameObject myBody;
	private PolygonalGameObject myTurret;
	private LineGameObject myCannon;
	private CircularGameObject myWheel1;
	private CircularGameObject myWheel2;
	private double jumpHeight;
	private double currentHeight;
	private boolean jumping;
	private boolean falling;
	private double positionBeforeJump;
	
	public MyCoolGameObject()
	{
		super(GameObject.ROOT);
		createTank();
		jumpHeight = 0.5;
		currentHeight = 0;
		jumping = false;
		falling = false;
	}
	
	public MyCoolGameObject(GameObject parent) 
	{
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	
	public void startJump()
	{
		jumping = true;
		positionBeforeJump = getPosition()[1];
	}
	
	public boolean isJumping()
	{
		return jumping;
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

	public double getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(double currentHeight) {
		this.currentHeight = currentHeight;
	}
	
	/**
	 * What the override method does in this case
	 * is update the vertical position of the tank
	 * if it is in the middle of a jump. Otherwise
	 * it does noting 
	 */
	@Override
	public void update(double dt) 
	{
		System.out.println("time: " + dt);
        if (!jumping)
        	return;
        
        /* 
         * The percentage of distance moved will be equal to 
         * p = dt / JUMP_TIME
         * 
         * that is a distance of 
         * d = p * (jumpHeight * 2)
         * currentHeight += d
         * 
         * If the current height is greater than jumpHeight then 
         * start the trip down, or if it is < 0, set the currentHeight to
         * 0
         * 
         */
        
        double p = dt / JUMP_TIME;
        double d = p * (jumpHeight * 2);
        
        if (!falling)
        {
        	currentHeight += d;
        
	        if (currentHeight > jumpHeight)
	        {
	        	double distanceDown = currentHeight - jumpHeight;
	        	currentHeight = jumpHeight - distanceDown;
	        	falling = true;
	        }
        }
        else
        {
        	currentHeight -= d;
        	
        	if (currentHeight <= 0)
        	{
        		currentHeight = 0;
        		jumping = false;
        		falling = false;
        	}
        }
        
        // Update y position of the object
        double currentPos[] = getPosition();
        setPosition(currentPos[0], positionBeforeJump + currentHeight);
        
    }
	
	

}
