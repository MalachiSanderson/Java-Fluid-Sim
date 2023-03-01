//*************************************************
	//Class: GenericSolid
	//Author: Malachi Sanderson
	//Date Created: 03-18-2020
	//Date Modified: 04-05-2020
	//
	//Purpose: THIS CLASS IS THE GENERIC PARENT CLASS FOR ALL SOLIDS. 
	//
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class GenericSolid extends GenericTiles 
{ 

	//Attributes...	
	//[TODO]CHANGE THESE NAMES BEC BLOCK IS THE NAME OF A SPECIFIC TYPE OF SOLID...
	private static double blockWidth = 2;
	private static double blockHeight = 2;
	//private double solidCapacityIdentity;
	
	//(4-16-20)[TODO]MAKE A STATIC BLOCK SUBCLASS THAT IS BASICALLY JUST A BLOCK THAT DOESN'T FALL DUE TO GRAVITY LIKE NORMAL
	//this will be useful if I wanted to make cool scenes like a mountain with a hollow core/cave...
	
//Methods...	

	//MAKE A SOLID OF CERTAIN SIZE.....
	public static void makeASolidOfSetSize(GenericTiles[][] Matrix, String typeName, double capacity)
	{
		double leftBound = (100 - (getBlockWidth()*10))/2;
		double rightBound = leftBound + (getBlockWidth()*10);
		for (int row = 0; row < getBlockActualHeight(Matrix); row++) 
		{
			for (int column =  (int) (Matrix[row].length*(leftBound*0.01)); column < Matrix[row].length*(rightBound*0.01); column++) 
			{
				Matrix[row][column].setTypeName(typeName);
				Matrix[row][column].setCapacity(capacity); 
			}
		}
		
	}
	
	//Returns Actual height of solid based on size of grid and inputed size...
	public static double getBlockActualHeight(GenericTiles[][] Matrix)
	{
		return Matrix[0].length*(getBlockHeight()*0.1);
	}

	//Returns Actual width of solid based on size of grid and inputed size...
	public static double getBlockActualWidth(GenericTiles[][] Matrix)
	{
		return Matrix.length*(getBlockWidth()*0.1);
	}

//Setters and Getters...

	public static double getBlockWidth() 
	{
		return blockWidth;
	}

	public static double getBlockHeight() 
	{
		return blockHeight;
	}


	public static void setBlockWidth(double blockWidth) {
		GenericSolid.blockWidth = blockWidth;
	}

	public static void setBlockHeight(double blockHeight) {
		GenericSolid.blockHeight = blockHeight;
	}



}