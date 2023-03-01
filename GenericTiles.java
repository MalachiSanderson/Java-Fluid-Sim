//*************************************************
	//Class: GenericTiles
	//Author: Malachi Sanderson
	//Date Created: 02-20-2020
	//Date Modified: 04-03-2020
	//
	//Purpose: THIS CLASS IS THE PARENT CLASS FOR ALL TILES (FLUIDS/SOLIDS) 
	//AND WHEN AN OBJECT IS MADE OF THIS CLASS IT IS BASICALLY JUST REPRESENTATIVE 
	//OF AIR/EMPTY SPACE.
	//
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class GenericTiles 
{
	//THIS CLASS IS THE GENERIC PARENT CLASS THAT FLUIDS AND SOLIDS ARE DERIVED FROM...
	//It also kind of serves as a empty cell/air...
	//....ATTRIBUTES....
	private double capacity;  //Capacity is actually like a measure of DENSITY in that area.
	private static final double MAX = 100;
	private String typeName;


	//....METHODS....

	public static String getGenericTilesName()
	{
		return "generic tiles";
	}

	public void addCapacity(double c) 
	{
		capacity+=c;
	}

	//....GETTERS AND SETTERS....
	
	public double getCapacity() 
	{
		return capacity;
	}
	public void setCapacity(double capacity) 
	{
		this.capacity = capacity;
	}

	public static double getMAX() 
	{
		return MAX;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}