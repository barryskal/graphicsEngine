package ass1;

import javax.media.opengl.GL2;

public class LineGameObject extends GameObject 
{
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
}
