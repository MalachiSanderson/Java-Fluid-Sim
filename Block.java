//*************************************************
	//Class: Block
	//Author: Malachi Sanderson
	//Date Created: 03-25-2020
	//Date Modified: 04-15-2020
	//
	//Purpose: THIS CLASS IS THE CLASS FOR THE BLOCK TYPE OF OBJECT. 
	//WHICH SINKS AND DISPLACES FLUID.
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class Block extends GenericSolid 
{

	//Attributes...	
	private static final double blockCapacityIdentity = 9999.0040901;


	//Methods...	

	public static void iterateBlock(GridSetup gridName) 
	{
		gridName.oneSinkTurn('t');
	}

	//CONSTRUCTOR.......
	public Block() 
	{
		//[TODO]
		setCapacity(getBlockCapacityIdentity());
		setTypeName(getBlockName());
	}

	public static String getBlockName()
	{
		return "block";
	}



	//Setters and Getters...
	public static double getBlockCapacityIdentity() 
	{
		return blockCapacityIdentity;
	}


}