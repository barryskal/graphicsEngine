package ass1;

import java.util.Arrays;

import javax.media.opengl.GL2;

public class LineGameObject extends GameObject 
{
	private static final double EPSILON = 0.001;
	private double[] myLineColour;
	private double[] myPoints;
	
	
	//Create a LineGameObject from (0,0) to (1,0)
	public LineGameObject(GameObject parent, double[] lineColour)
	{
		super(parent);
		myLineColour = lineColour;
		myPoints = new double[4];
		myPoints[0] = 0;
		myPoints[1] = 0;
		myPoints[2] = 1;
		myPoints[3] = 0;
	}

	//Create a LineGameObject from (x1,y1) to (x2,y2)
	public LineGameObject(GameObject parent,  double x1, double y1,double x2, double y2,double[] lineColour)
	{
		super(parent);
		myLineColour = lineColour;
		myPoints = new double[4];
		myPoints[0] = x1;
		myPoints[1] = y1;
		myPoints[2] = x2;
		myPoints[3] = y2;
	}
	
	
	/**
     * Get the points representing the line in the form
     * [x1, y1, x2, y2]
     * 
     * @return an array of doubles representing the line coordinates
     */
    public double[] getPoints() {        
        return myPoints;
    }

    /**
     * Set the points representing the line 
     * 
     * @param points An array of doubles representing the coordinates
     * in the form [x1, y1, x2, y2]
     */
    public void setPoints(double[] points) {
        myPoints = points;
    }

    /**
     * Get the line colour.
     * 
     * @return
     */
    public double[] getLineColour() {
        return myLineColour;
    }

    /**
     * Set the line colour.
     * 
     * Setting the colour to null means the line should not be drawn
     * 
     * @param lineColour
     */
    public void setLineColour(double[] lineColour) {
        myLineColour = lineColour;
    }
    
    
    @Override
    public void drawSelf(GL2 gl) 
    {
    	if (myLineColour == null)
    		return;
    	
    	gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
    	gl.glBegin(GL2.GL_LINES);
    	{
    		gl.glVertex2d(myPoints[0], myPoints[1]);
    		gl.glVertex2d(myPoints[2], myPoints[3]);
    	}
    	gl.glEnd();
    	
    }
    
    @Override
    public boolean collision(double[] point)
    {
    	/*
    	 * Tests whether there is a collision by determining whether there exists
    	 * some value, t, that allows for linear interpolation of this line to 
    	 * generate the given point.
    	 * 
    	 * lerp(P,Q,t) 	= P + t(Q - P)
    	 * 		point	= P + t(Q - P)
    	 * 		tx		= (pointx - Px) / (Qx - Px)
    	 * 		ty		= (pointy - Py) / (Qy - Py)
    	 * 	if tx == ty then collision
    	 */		
    	
    	/*
    	 * Note that we need to convert the points P and Q in to world coordinates
    	 */
    	
    	double localP[] = {myPoints[0], myPoints[1], 1};
    	double localQ[] = {myPoints[2], myPoints[3], 1};
    	
    	double[][] globalTransformationMatrix = getGlobaltransformationMatrix();
    	
    	double[] globalP = MathUtil.multiply(globalTransformationMatrix, localP);
    	double[] globalQ = MathUtil.multiply(globalTransformationMatrix, localQ);
    	
    	double tx = (point[0] - globalP[0]) / (globalQ[0] - globalP[0]);
    	double ty = (point[1] - globalP[1]) / (globalQ[1] - globalP[1]);
    	
    	/* 
    	 * In some cases rounding error can cause a collision to not be
    	 * detected correctly. This formula only works when neither
    	 * tx or ty is zero, as that can cause a false positive 
    	 */
    	if (tx != 0 && ty != 0)
    		return ((tx - ty) / Math.max(Math.abs(tx), Math.abs(ty))) < EPSILON;
    	
    	if (tx == ty)
    		return true;
    	
    	return false;
    	
    }
}
