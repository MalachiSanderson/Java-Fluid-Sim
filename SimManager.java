//*************************************************
	//Class: SimManager
	//Author: Malachi Sanderson
	//Date Created: 02-25-2020
	//Date Modified: 04-21-2020
	//
	//Purpose: THIS CLASS IS BASICALLY THE MANAGER THAT HANDLES/CENTRALIZES MOST OF THE STUFF
	//THAT THE GUI HAS TO GET/USE (IT STILL HAS TO DIRECTLY USE SOME OTHER STUFF)...
	//	       
	//
	//Attributes:
	//
	//Methods:
	//
	//*******************************************************
public class SimManager 
{
	
	//THIS CLASS IS BASICALLY THE MANAGER THAT HANDLES/CENTRALIZES MOST OF THE STUFF
	//THAT THE GUI HAS TO GET/USE (IT STILL HAS TO DIRECTLY USE SOME OTHER STUFF)...
	//(4-15-20)[TODO]MAKE THIS ACTUALLY FUNCTION LIKE A MANAGER LIKE IN Hound/hare game...

	public SimManager(GridSetup gridName, String fluidGenerator) 
	{
		String generator = fluidGenerator;
		if (generator == "random") 
		{
			gridName.makeRandomFluid();
		}
		else if (generator == "vertical chunk left") 
		{
			gridName.makeLeftVerticalChunkOfFluid();
		}
		else if (generator == "standard horizontal rows")
		{
			gridName.makeSomeRowsOfFluid();
		}
		else if(generator == "clear") 
		{
			gridName.deleteAllTiles();
		}
		else if(generator == "vertical chunk right") 
		{
			gridName.makeRightVerticalChunkOfFluid();
		}
		else if(generator == "empty bottom") 
		{
			gridName.emptyBottomRowOfFluid();
		}
		else if(generator == "bubble") 
		{
			gridName.deleteFluidChunk();
		}
		else if(generator == "packed fluid") 
		{
			gridName.makePackedFluidCell();
		}
		else if(generator == "one solid in center") 
		{
			gridName.makeOneBlockCenter();
		}
		else if(generator == "chunk solid in center") 
		{
			gridName.makeChunkBlockCenter();
		}
		else if(generator == "delete solids") 
		{
			gridName.deleteAllSolids();
		}
		else if(generator == "save fluid")
		{
			gridName.saveGridToFile();
		}
		else if(generator == "delete fluids")
		{
			gridName.deleteAllFluids();
		}
		else if(generator == "one ice center")
		{
			gridName.makeOneIceCubeCenter();
		}
		else if(generator == "chunk ice center")
		{
			gridName.makeChunkICECenter();
		}
		 
	}



	//All steps of sim as a method...

	public void simCycle(GridSetup gridName) 
	{
		//gridName.updateClassesOfEachCell();
		Block.iterateBlock(gridName);
		Fluid.iterateFluid(gridName);
		IceCube.iterateIceCube(gridName);
		
	}



	//************GETTERS AND SETTERS***************


}














