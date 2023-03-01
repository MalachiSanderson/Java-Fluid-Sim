//*************************************************
	//Class: Block
	//Author: Malachi Sanderson
	//Date Created: 04-19-2020
	//Date Modified: 04-20-2020
	//
	//Purpose: THIS CLASS IS THE CLASS FOR THE ICE CUBE TYPE OF OBJECT. 
	//WHICH DISPLACES WATER LIKE ITS BROTHER OF THE BLOCK TYPE
	//BUT IT FLOATS AND MELTS OVER TIME.
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class IceCube extends GenericSolid 
{

//Attributes...	
	


//Methods...	
	
	//CONSTRUCTOR.......
	public IceCube()
	{
		setTypeName(getIceCubeName());
	}
	
	public static String getIceCubeName()
	{
		return "ice cube";
	}

	//ITERATE ICE CUBE...
	public static void iterateIceCube(GridSetup gridName)
	{
		gridName.oneMeltTurn('t');
		gridName.iceSinkIfNoWater();
		//gridName.newIceSinkMethod();
		gridName.iceFloatOneTurn('t');	
	}
	
	//[TODO]MELT ICE CUBE...
	public void melt()
	{
		//[TODO]
	}





//Setters and Getters...
	


}