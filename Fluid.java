//*************************************************
	//Class: Fluid
	//Author: Malachi Sanderson
	//Date Created: 03-15-2020
	//Date Modified: 04-03-2020
	//
	//Purpose: THIS CLASS IS THE CLASS FOR FLUIDS THAT MOVE ACCORDING 
	//TO THE RULES DEFINED IN GRIDSETUP. 
	//
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class Fluid extends GenericTiles
{

//Attributes...


//Methods...

	//Constructor...
	public Fluid(double cap) 
	{
		setTypeName(getFluidName());
		setCapacity(cap);
	}

	//Updates all fluid on matrix...
	public static void iterateFluid(GridSetup gridName)
	{
		gridName.oneGravityTurn('f');
		gridName.oneGravityTurn('t');
		gridName.onePressureVerticalTurn('f');
		gridName.onePressureRightTurn('f');
		gridName.onePressureLeftTurn('f');
		gridName.onePressureRightTurn('f');
		gridName.onePressureLeftTurn('t');
	}

	public static String getFluidName()
	{
		return "fluid";
	}

//Setters and Getters...


}