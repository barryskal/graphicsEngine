package ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.media.opengl.GL2;

/**
 * A game object that has a polygonal shape.
 * 
 * This class extend GameObject to draw polygonal shapes.
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class PolygonalGameObject extends GameObject {

    private double[] myPoints;
    private double[] myFillColour;
    private double[] myLineColour;

    /**
     * Create a polygonal game object and add it to the scene tree
     * 
     * The polygon is specified as a list of doubles in the form:
     * 
     * [ x0, y0, x1, y1, x2, y2, ... ]
     * 
     * The line and fill colours can possibly be null, in which case that part of the object
     * should not be drawn.
     *
     * @param parent The parent in the scene tree
     * @param points A list of points defining the polygon
     * @param fillColour The fill colour in [r, g, b, a] form
     * @param lineColour The outlien colour in [r, g, b, a] form
     */
    public PolygonalGameObject(GameObject parent, double points[],
            double[] fillColour, double[] lineColour) {
        super(parent);

        myPoints = points;
        myFillColour = fillColour;
        myLineColour = lineColour;
    }

    /**
     * Get the polygon
     * 
     * @return
     */
    public double[] getPoints() {        
        return myPoints;
    }

    /**
     * Set the polygon
     * 
     * @param points
     */
    public void setPoints(double[] points) {
        myPoints = points;
    }

    /**
     * Get the fill colour
     * 
     * @return
     */
    public double[] getFillColour() {
        return myFillColour;
    }

    /**
     * Set the fill colour.
     * 
     * Setting the colour to null means the object should not be filled.
     * 
     * @param fillColour The fill colour in [r, g, b, a] form 
     */
    public void setFillColour(double[] fillColour) {
        myFillColour = fillColour;
    }

    /**
     * Get the outline colour.
     * 
     * @return
     */
    public double[] getLineColour() {
        return myLineColour;
    }

    /**
     * Set the outline colour.
     * 
     * Setting the colour to null means the outline should not be drawn
     * 
     * @param lineColour
     */
    public void setLineColour(double[] lineColour) {
        myLineColour = lineColour;
    }

    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: Draw the polygon
     * 
     * if the fill colour is non-null, fill the polygon with this colour
     * if the line colour is non-null, draw the outline with this colour
     * 
     * @see ass1.spec.GameObject#drawSelf(javax.media.opengl.GL2)
     */
    @Override
    public void drawSelf(GL2 gl) {
    	// If both fill and line colour are null, there's no need to draw anything
    	if (myFillColour == null && myLineColour == null) 
    		return;
    	
    	// draw the filled polygon first
    	if (myFillColour != null)
    	{
    		gl.glColor4d(myFillColour[0], myFillColour[1], myFillColour[2], myFillColour[3]);
    		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    		drawPolygon(gl);
    	}
    	
    	// draw the outline of the polygon
    	if (myLineColour != null)
    	{
    		gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
    		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
    		
    		/* If the fill has already been drawn, we need to offset the line so that
    		 * it is rastersized properly
    		 */
    		if (myFillColour != null)
    		{
    			gl.glEnable(GL2.GL_POLYGON_OFFSET_LINE);
    			gl.glPolygonOffset(-2.0f, -2.0f);
    		}
    		
    		drawPolygon(gl);
    		// Change the polygon mode back to avoid issues
    		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    	}
    	
    }
    
    private void drawPolygon(GL2 gl)
    {
    	gl.glBegin(GL2.GL_POLYGON);
		{
			for (int i = 0; i < myPoints.length; i += 2)
			{
				gl.glVertex2d(myPoints[i], myPoints[i + 1]);
			}	
		}
		gl.glEnd();
    }
    		
    
    private List<PolygonEdge> getEdgeList()
    {
    	ArrayList<PolygonEdge> edgeList = new ArrayList<PolygonEdge>();
    	
    	for (int i = 0; i < myPoints.length; i += 2)
    	{
    		double[] upperVertex = Arrays.copyOfRange(myPoints, i, i + 2);
    		double[] lowerVertex = Arrays.copyOfRange(myPoints, (i + 2), (i + 4));
    		PolygonEdge newEdge = new PolygonEdge(upperVertex, lowerVertex);
    		edgeList.add(newEdge);
    	}
    	
    	return edgeList;
    }
    
    public void printEdgeList()
    {
    	for (PolygonEdge edge : getEdgeList())
    	{
    		edge.printVertices();
    	}
    }
    
    private class PolygonEdge
    {
    	private double[] upperVertex;
    	private double[] lowerVertex;
    	
    	public PolygonEdge(double[] newUpperVertex, double[] newLowerVertex)
    	{
    		upperVertex = newUpperVertex;
    		lowerVertex = newLowerVertex;
    	}
    	
    	public void printVertices()
    	{
    		System.out.printf("Upper Vertex: (%.3f, %.3f) lower vertex: (%.3f, %.3f) %n", upperVertex[0], upperVertex[1], lowerVertex[0], lowerVertex[1]);
    	}
    }
    
    @Override
    public boolean collision(double[] point)
    {
    	
    	
    	return false;
    }


}
