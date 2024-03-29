package ass1;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;


/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class GameObject {

    // the list of all GameObjects in the scene tree
    public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();
    
    // the root of the scene tree
    public final static GameObject ROOT = new GameObject();
    
    // the links in the scene tree
    private GameObject myParent;
    private List<GameObject> myChildren;

    // the local transformation
    //myRotation should be normalised to the range (-180..180)
    private double myRotation;
    private double myScale;
    private double[] myTranslation;
    
    // is this part of the tree showing?
    private boolean amShowing;

    /**
     * Special private constructor for creating the root node. Do not use otherwise.
     */
    private GameObject() {
        myParent = null;
        myChildren = new ArrayList<GameObject>();

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        amShowing = true;
        
        ALL_OBJECTS.add(this);
    }

    /**
     * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
     *  
     * New objects are created at the same location, orientation and scale as the parent.
     *
     * @param parent
     */
    public GameObject(GameObject parent) {
        myParent = parent;
        myChildren = new ArrayList<GameObject>();

        parent.myChildren.add(this);

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        // initially showing
        amShowing = true;

        ALL_OBJECTS.add(this);
    }

    /**
     * Remove an object and all its children from the scene tree.
     */
    public void destroy() {
        for (GameObject child : myChildren) {
            child.destroy();
        }
        
        myParent.myChildren.remove(this);
        ALL_OBJECTS.remove(this);
    }

    /**
     * Get the parent of this game object
     * 
     * @return
     */
    public GameObject getParent() {
        return myParent;
    }

    /**
     * Get the children of this object
     * 
     * @return
     */
    public List<GameObject> getChildren() {
        return myChildren;
    }

    /**
     * Get the local rotation (in degrees)
     * 
     * @return
     */
    public double getRotation() {
        return myRotation;
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(double rotation) {
        myRotation = MathUtil.normaliseAngle(rotation);
    }

    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(double angle) {
        myRotation += angle;
        myRotation = MathUtil.normaliseAngle(myRotation);
    }

    /**
     * Get the local scale
     * 
     * @return
     */
    public double getScale() {
        return myScale;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     */
    public void setScale(double scale) {
        myScale = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(double factor) {
        myScale *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     */
    public double[] getPosition() {
        double[] t = new double[2];
        t[0] = myTranslation[0];
        t[1] = myTranslation[1];

        return t;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        myTranslation[0] = x;
        myTranslation[1] = y;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(double dx, double dy) {
        myTranslation[0] += dx;
        myTranslation[1] += dy;
    }

    /**
     * Test if the object is visible
     * 
     * @return
     */
    public boolean isShowing() {
        return amShowing;
    }

    /**
     * Set the showing flag to make the object visible (true) or invisible (false).
     * This flag should also apply to all descendents of this object.
     * 
     * @param showing
     */
    public void show(boolean showing) {
        amShowing = showing;
    }

    /**
     * Update the object. This method is called once per frame. 
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param dt The amount of time since the last update (in seconds)
     */
    public void update(double dt) {
        // do nothing
    }

    /**
     * Draw the object (but not any descendants)
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param gl
     */
    public void drawSelf(GL2 gl) {
        // do nothing
    }

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * TODO: Complete this method
     * 
     * @param gl
     */
    public void draw(GL2 gl) {
        
        // don't draw if it is not showing
        if (!amShowing) {
            return;
        }

        gl.glPushMatrix();
        gl.glTranslated(myTranslation[0], myTranslation[1], 0);
        gl.glRotated(myRotation, 0, 0, 1);
        gl.glScaled(myScale, myScale, myScale);
        drawSelf(gl);
        
        for (GameObject currentChild : myChildren)
        	currentChild.draw(gl);
        
        gl.glPopMatrix();
    }

    private double[][] getLocalTransformationMatrix()
    {
    	double[][] translate = MathUtil.translationMatrix(myTranslation);
    	double[][] rotate = MathUtil.rotationMatrix(myRotation);
    	double[][] scale = MathUtil.scaleMatrix(myScale);
    	
    	return MathUtil.multiply(MathUtil.multiply(translate, rotate), scale);
    }
    
    public double[][] getGlobaltransformationMatrix()
    {
    	double[][] localTransformationMatrix = getLocalTransformationMatrix();
    	
    	// If this is the root node, we just need to return the local transformation matrix
    	if (myParent == null)
    		return localTransformationMatrix;
    	
    	// Otherwise, multiply teh transformation matrices together to get the new global one. 
    	double[][] parentTransformationMatrix = myParent.getGlobaltransformationMatrix();
    	
        return MathUtil.multiply(parentTransformationMatrix, getLocalTransformationMatrix());
    }
    
    /**
     * Compute the object's position in world coordinates
     * 
     * TODO: Write this method
     * 
     * @return a point in world coordinats in [x,y] form
     */
    public double[] getGlobalPosition() 
    {
    	
        return getPositionFromTransformationMatrix(getGlobaltransformationMatrix());
        
    }

	private double[] getPositionFromTransformationMatrix(double[][] transformationMatrix) 
	{
		double[] p = new double[2];
              
        for (int i = 0; i < 2; i++)
        	p[i] = transformationMatrix[i][2];
        
        return p;
	}

    /**
     * Compute the object's rotation in the global coordinate frame
     * 
     * TODO: Write this method
     * 
     * @return the global rotation of the object (in degrees) and 
     * normalized to the range (-180, 180) degrees. 
     */
    public double getGlobalRotation() 
    {
    	
    	return getRotationFromTransformationMatrix(getGlobaltransformationMatrix(), getGlobalScale());
    	  
    }

	private double getRotationFromTransformationMatrix(double[][] transformationMatrix, double scale) 
	{
		double i1 = transformationMatrix[0][0];
    	double i2 = transformationMatrix[1][0];
    	
    	// Get the unit vector for i (i.e. eliminate scale)
    	i1 /= scale;
    	i2 /= scale;
    	
    	double angle = Math.toDegrees(Math.acos(i1));
        
    	
    	
    	if (i1 >= 0 && i2 >= 0) // in first quadrant
    		return angle;
    	else if (i1 < 0 && i2 >= 0) // in second quadrant
    		return angle;
    	else if (i1 >= 0 && i2 < 0) // fourth quadrant
    		return angle * -1;
    	else
    		return angle * -1; // third quadrant
	}

    /**
     * Compute the object's scale in global terms
     * 
     * TODO: Write this method
     * 
     * @return the global scale of the object 
     */
    public double getGlobalScale() 
    {
        if (myParent == null)
        	return myScale;
        
        return myScale * myParent.getGlobalScale();
    	
    }

	private double getScaleFromTransformationMatrix(double[][] transformationMatrix) 
	{
		double sum = 0;
    	
    	for (int i = 0; i < 3; i++)
    		sum += transformationMatrix[i][0] * transformationMatrix[i][0];
    	
    	
    	return Math.sqrt(sum);
	}

    /**
     * Change the parent of a game object.
     * 
     * TODO: add code so that the object does not change its global position, rotation or scale
     * when it is reparented. 
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
        // get global coordinate transformation
    	// get inverse matrix for parent
    	
    	double[][] globalTransformationMatrix = getGlobaltransformationMatrix();
    	double globalScale = getGlobalScale();
        myParent.myChildren.remove(this);
        myParent = parent;
        
        double[][] inverseTransformationMatrix = myParent.getInverseTransformationMatrix();
        //MathUtil.printMatrix(inverseTransformationMatrix);
        double[][] newLocalTransformationMatrix = MathUtil.multiply(inverseTransformationMatrix, globalTransformationMatrix);
        double[] newPosition = getPositionFromTransformationMatrix(newLocalTransformationMatrix);
        setPosition(newPosition[0], newPosition[1]);
        
        setScale(globalScale / myParent.getGlobalScale());

        setRotation(getRotationFromTransformationMatrix(newLocalTransformationMatrix, myScale));
        
        
        myParent.myChildren.add(this);
        
    }
    
    /**
     * Generates the inverse global transformation matrix for this object
     * @return a 3 x 3 matrix representing the inverse transformation
     * matrix
     */
    public double[][] getInverseTransformationMatrix()
    {
    	double[][] inverseScale = MathUtil.scaleMatrix(1 / getGlobalScale());
    	//System.out.println("scale");
    	//MathUtil.printMatrix(inverseScale);
    	double[][] inverseRotation = MathUtil.rotationMatrix(getGlobalRotation() * -1);
    	//System.out.println("roation");
    	//MathUtil.printMatrix(inverseRotation);
    	double[] globalTranslation = getGlobalPosition();
    	globalTranslation[0] *= -1;
    	globalTranslation[1] *= -1;
    	
    	double[][] inversetranslation = MathUtil.translationMatrix(globalTranslation);
    	//System.out.println("translation");
    	//MathUtil.printMatrix(inversetranslation);
    	
    	return MathUtil.multiply(MathUtil.multiply(inverseScale, inverseRotation), inversetranslation);
    }
    
    /**
     * This tests whether the given point is colliding with the GameObject. 
     * By default this method returns false, but it should be overridden in 
     * each of the individual shape classes.
     * @param point The point used for collision testing
     * @return true is the given point collides with this object, false otherwise.
     */
    public boolean collision(double[] point)
    {
    	return false;
    }
    
    
    /**
     * Converts a given point in global coordinates to the local coordinate system
     * @param globalPoints The point in global coordinates
     * @return The same point, but specified in the local coordiante system
     */
    public double[] convertPointToLocalCoordinates(double[] globalPoints)
    {
    	double point[] = {globalPoints[0], globalPoints[1], 1};
    	
    	return MathUtil.multiply(getInverseTransformationMatrix(), point);
    }
    

}
