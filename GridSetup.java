import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

//*************************************************
//Class: GridSetup
//Author: Malachi Sanderson
//Date Created: 02-20-2020
//Date Modified: 04-21-2020
//
//Purpose://THIS CLASS GENERATES A MATRIX OF TILES. 
//IT WILL ALSO BE WHAT CONTROLS THE MOTION OF TILES WITHIN THE MATRIX...
//	       
//
//Attributes:
//
//Methods:
//
//*******************************************************
public class GridSetup 
{
	//THIS CLASS GENERATES A MATRIX OF TILES. 
	//IT WILL ALSO BE WHAT CONTROLS THE MOTION OF TILES WITHIN THE MATRIX...


	//---------------------------------------------------------	
	//-------------------ATTRIBUTES----------------------------
	//---------------------------------------------------------	

	private GenericTiles[][] Matrix;
	private static int gridRows;
	private static int gridColumns;



	//---------------------------------------------------------	
	//-----------------------METHODS--------------------------
	//---------------------------------------------------------	


	//(4-16-20)[TODO]FIGURE OUT WHY BLOCKS SEEM TO DELETE FLUID WHEN PLACING THEM AND REMOVING THEM FROM GRID...***[IMPORTANT]***


	//CONSTRUCTOR....................
	public GridSetup( int gridRows, int gridColumns) 
	{
		setGridRows(gridRows);
		setGridColumns(gridColumns);
		Matrix = new GenericTiles[gridRows][gridColumns];
		for (int i = 0; i < Matrix.length; i++) 
		{
			for (int j = 0; j < Matrix[i].length; j++) 
			{
				Matrix[i][j]  = new GenericTiles();	

			}
		}
	}



	//-------------------METHODS FOR GENERATING FLUID-------------------
	//******************************************************************


	//Updates class of a cell method...
	public void updateClassesOfEachCell(boolean t)
	{
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				double cellsCapacity = Matrix[row][column].getCapacity();
				String name =  Matrix[row][column].getTypeName();
				if ( name != "block" && name != "ice cube")
				{
					if (cellsCapacity == 0)
					{
						Matrix[row][column] = new GenericTiles();
						Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());
					}
					else if (cellsCapacity > 0)
					{
						Matrix[row][column] = new Fluid(cellsCapacity);
						Matrix[row][column].setTypeName(Fluid.getFluidName());
					}
				}
				else if (cellsCapacity == Block.getBlockCapacityIdentity())
				{
					Matrix[row][column] = new Block();
					Matrix[row][column].setTypeName(Block.getBlockName());
					Matrix[row][column].setCapacity(Block.getBlockCapacityIdentity());
				}
				else if (name == "ice cube")
				{
					Matrix[row][column] = new IceCube();
					Matrix[row][column].setTypeName(IceCube.getIceCubeName());
					Matrix[row][column].setCapacity(cellsCapacity);
				}
			}
		}
		if (t = true)
		{
			//printMatrix();
		}
	}

	//MAKES TWO ROWS OF FLUID.........
	public void makeSomeRowsOfFluid() 
	{
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if( ! isItASolid(Matrix[row][column]))
				{
					//Change which part is commented if want water to fall instead...

					if(row > Matrix.length*0.8) 
					{
						Matrix[row][column].setCapacity(135);
					}
					/*
						if(!(row >= Matrix.length*0.2)) 
						{
							Matrix[row][column].setCapacity(135);
						}
					 */
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//MAKES VERTICAL BLOCK OF FLUID ON LEFT END OF MATRIX........
	public void makeLeftVerticalChunkOfFluid() 
	{
		for (int row = 0; row < Matrix.length*0.4; row++) 
		{
			for (int column = 0; column < Matrix[row].length*0.5; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					//Change the number and put ! in front if want to have water fall instead...
					if(  (column < ((Matrix.length)*0.30 )) )
					{
						Matrix[row][column].setCapacity(135); 
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//MAKES VERTICAL BLOCK OF FLUID ON RIGHT END OF MATRIX........
	public void makeRightVerticalChunkOfFluid() 
	{
		for (int row = 0; row < Matrix.length*0.4; row++) 
		{
			for (int column = (int) (Matrix[row].length*0.5); column < Matrix[row].length; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					//Change the number and put ! in front if want to have water fall instead...
					if( (column >= ((Matrix.length)*0.7 )) )
					{
						Matrix[row][column].setCapacity(135); 
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//********(2-27-20)[TODO]FUNCTION THAT ADDS WATER TO A SET POSITION (THEN GET THIS TO WORK AS A BUTTON ON GUI)...

	//*******(4-16-20)[TODO]IMPROVED AIR BUBBLE AND DROP ONE BLOCK THAT LETS YOU--
	//--SELECT WHERE IT'LL BE (MAYBE ADJUSTABLE VIA ARROW KEYS WITH OUTLINED AREA OF ACTION DISPLAYED AS A CYAN RECTANGLE)...

	//SUBTRACTS SMALL CHUNK OF WATER FROM BOTTOM MID..........
	public void deleteFluidChunk()
	{
		for (int row = (int) (Matrix.length*0.7); row < Matrix.length*0.9; row++) 
		{
			for (int column =  (int) (Matrix[row].length*0.4); column < Matrix[row].length*0.7; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					Matrix[row][column] = new GenericTiles();
					Matrix[row][column].setCapacity(0);
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//FILLS ONE CELL ON TOP WITH A BUNCH OF FLUID........
	public void makePackedFluidCell()
	{
		boolean makeOne = true;
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column =  (int) (Matrix[row].length*0.4); column < Matrix[row].length*0.5; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					if(makeOne) 
					{
						if (row == Matrix.length-1 && column ==  (int) (Matrix[row].length*0.4) )
						{
							Matrix[row][column].setCapacity((GenericTiles.getMAX()*Matrix.length*Matrix[row].length)*0.1);
							makeOne = false;
						}
						else if(Matrix[row][column-1].getCapacity() != 0)
						{
							Matrix[row][column].setCapacity((GenericTiles.getMAX()*Matrix.length*Matrix[row].length)*0.1);
							makeOne = false;
						}
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//MAKES RANDOM TILESET............
	public void makeRandomFluid() 
	{
		Random rn = new Random();
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					double capacityOfACell = Matrix[row][column].getCapacity();
					if (capacityOfACell == 0) 
					{
						//Matrix[row][column] = new Fluid(rn.nextInt(125));
						Matrix[row][column].setCapacity(rn.nextInt(125));
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//EMPTIES MATRIX OF ONLY FLUIDS...
	public void deleteAllFluids()
	{
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column =  0; column < Matrix[row].length; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					Matrix[row][column] = new GenericTiles();
					Matrix[row][column].setCapacity(0);
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//EMPTIES MATRIX COMPLETELY(NOT JUST FLUID)........
	public void deleteAllTiles() 
	{
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				Matrix[row][column] = new GenericTiles();
				Matrix[row][column].setCapacity(0);
			}
		}
		updateClassesOfEachCell(true);
	}

	//DELETES ANY SOLIDS IN MATRIX..........
	public void deleteAllSolids() 
	{
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if(isItASolid(Matrix[row][column]))
				{
					Matrix[row][column] = new GenericTiles();
					Matrix[row][column].setCapacity(0);
				}
			}
		}
		updateClassesOfEachCell(true);
	}


	//EMPTIES BOTTOM ROW OF MATRIX........
	public void emptyBottomRowOfFluid() 
	{
		for (int row = Matrix.length-1; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) )
				{
					Matrix[row][column].setCapacity(0);
				}
			}
		}
		updateClassesOfEachCell(true);
	}

	//*****(LONG TERM) MAKE A FUNCTION THAT ADDS FORCE VECTORS (DOWNWARDS)********
	//DO THIS BY DOING W = MG AND ADDING EACH CELL'S MG TO THE MG OF THE CELL UNDER IT
	//this would allow for buoyancy and for pressure to increase proportionally to depth.
	//as a cell has more force vectors being added to it, increase it's maxCapacity...
	//(so it could comfortably hold greater amounts of water without vertical pressure spitting it up)...



	//-------------------------METHODS FOR GENERATING SOLIDS------------------------------
	//************************************************************************************

	//(4-20-20)[TODO]IMPROVE THIS TO BE MORE DIRECTLY CONTROLLABLE...
	//Makes a single "block" in the center of grid...
	public void makeOneBlockCenter() 
	{
		boolean makeOne = true;
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column =  (int) (Matrix[row].length*0.4); column < Matrix[row].length*0.5; column++) 
			{
				if(makeOne) 
				{
					//make one on the bottom row...
					if (row == Matrix.length-1 && column ==  (int) (Matrix[row].length*0.4) )
					{
						//makeNewBlock(Matrix[row][column]);
						Matrix[row][column].setTypeName(Block.getBlockName());
						Matrix[row][column].setCapacity(Block.getBlockCapacityIdentity());
						makeOne = false;
					}
					//...if you can't make on top layer of water...
					else if(Matrix[row+1][column].getTypeName().equals(Fluid.getFluidName()))
					{
						//makeNewBlock(Matrix[row][column]);
						Matrix[row][column].setTypeName(Block.getBlockName());
						Matrix[row][column].setCapacity(Block.getBlockCapacityIdentity());
						makeOne = false;
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//Makes a single "block" in the center of grid...
	public void makeOneIceCubeCenter() 
	{
		boolean makeOne = true;
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column =  (int) (Matrix[row].length*0.4); column < Matrix[row].length*0.5; column++) 
			{
				if(makeOne) 
				{
					//make one on the bottom row...
					if (row == Matrix.length-1 && column ==  (int) (Matrix[row].length*0.4) )
					{
						Matrix[row][column].setTypeName(IceCube.getIceCubeName());
						Matrix[row][column].setCapacity(500);
						makeOne = false;
					}
					//...if you can't make on top layer of water...
					else if(Matrix[row+1][column].getTypeName().equals(Fluid.getFluidName()))
					{
						Matrix[row][column].setTypeName(IceCube.getIceCubeName());
						Matrix[row][column].setCapacity(500);
						makeOne = false;
					}
				}
			}
		}
		updateClassesOfEachCell(true);
		//printMatrix();
	}

	//Makes a chunk of solids in center of grid...
	public void makeChunkBlockCenter() 
	{
		//Makes a square of blocks in top center of grid --
		//with length and width of (10-100)% the length and width of the matrix...
		GenericSolid.getBlockWidth();
		GenericSolid.getBlockHeight();
		GenericSolid.makeASolidOfSetSize(Matrix, Block.getBlockName(), Block.getBlockCapacityIdentity());
		updateClassesOfEachCell(true);
	}


	public void makeChunkICECenter() 
	{
		//Makes a square of blocks in top center of grid --
		//with length and width of (10-100)% the length and width of the matrix...
		GenericSolid.getBlockWidth();
		GenericSolid.getBlockHeight();
		GenericSolid.makeASolidOfSetSize(Matrix, IceCube.getIceCubeName(), 500);
		updateClassesOfEachCell(true);
	}

	//----------------------------END OF GENERATING THINGS METHODS------------------------
	//************************************************************************************



	//----------------------------START OF UTILITY---------------------------------------
	//***********************************************************************************

	//MATRIX PRINTER................
	public void printMatrix() 
	{
		System.out.println("\n");
		for (int row = 0; row < Matrix.length; row++) 
		{
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if(Matrix[row][column].getTypeName().equals(GenericTiles.getGenericTilesName())  )
				{
					System.out.printf("  *  ");
				}	
				else if(! isItASolid( Matrix[row][column]) )
				{
					System.out.printf("%4.0f ",Matrix[row][column].getCapacity());
					//System.out.printf(Matrix[row][column].getTypeName());
				}
				else 
				{
					System.out.printf(" |B| ");
					//System.out.printf(Matrix[row][column].getTypeName());
				}
			}
			System.out.println();
		}

	}

	public static boolean isItASolid(GenericTiles tiles)
	{
		boolean checkSolidIdentifier = false;
		try
		{
			if(tiles.getTypeName().equals("block") || tiles.getTypeName().equals("ice cube"))
			{
				checkSolidIdentifier = true;
			}
			else 
			{
				checkSolidIdentifier = false;
			}
			return checkSolidIdentifier;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return checkSolidIdentifier;
		}
	}



	//********(3-28-20)[TODO]FIGURE OUT WHY THESE METHODS DONT WORK BEC THEY'D HELP CLEAN UP A LOT...
	public void makeNewBlock(GenericTiles tiles)
	{
		tiles = new Block();
		tiles.setTypeName(Block.getBlockName());
		tiles.setCapacity(Block.getBlockCapacityIdentity() );
	}
	public void makeNewFluid(Fluid fluids)
	{
		double currentCapacity = fluids.getCapacity();
		//fluids = new Fluid(currentCapacity);
		fluids.setTypeName(Fluid.getFluidName());
		fluids.setCapacity(currentCapacity);
	}
	public void makeNewGenericTiles(GenericTiles tiles)
	{
		tiles.setCapacity(0);
		tiles = new GenericTiles();
		tiles.setTypeName("generic tiles");
	}

	//----------------------------END OF UTILITY---------------------------------------
	//***********************************************************************************


	//------------NORMAL MOVE METHODS FOR FLUIDS IN MATRIX-------------
	//******************************************************************

	//(4-21-20)[TODO]REDUCE THE TIMECOMPLEXITY OF ALL THESE FOR LOOPS BY TRYING TO TRUNKADE SOME OF THESE FOR LOOPS INTO EACHOTHER
	//[IE: try to see if can make one master-for loop for these 4 fluid methods that does only one for loop that applies each force at once...]  

	//GRAVITY.......................
	public void oneGravityTurn(char t) 
	{
		for (int row = 0; row < Matrix.length - 1; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if ( ! isItASolid(Matrix[row][column]) && (! isItASolid(Matrix[row+1][column]))  )
				{
					// gravity
					if((Matrix[row+1][column].getCapacity() + Matrix[row][column].getCapacity()) <= GenericTiles.getMAX()) 
					{
						Matrix[row+1][column].addCapacity(Matrix[row][column].getCapacity());
						Matrix[row][column].setCapacity(0);
					}
					else if (Matrix[row+1][column].getCapacity() < GenericTiles.getMAX()) 
					{
						double originalMatrix = (Matrix[row][column].getCapacity() - (GenericTiles.getMAX() - Matrix[row+1][column].getCapacity()));
						Matrix[row+1][column].addCapacity((GenericTiles.getMAX()-Matrix[row+1][column].getCapacity()));
						Matrix[row][column].setCapacity(originalMatrix); 						
					}
					else 
					{
						// Do nothing
					}
				}
				//update visual (change num value to increase or decrease time between updates)
				//try {Thread.sleep(timer);} catch (InterruptedException e) {}
			}
		}
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}

	//VERTICAL PRESSURE...............
	public void onePressureVerticalTurn(char t) 
	{
		for (int row = Matrix.length - 1; row > 0; row--) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if( (! isItASolid(Matrix[row][column])  && (! isItASolid(Matrix[row-1][column])) ) )
				{
					//Pressure...
					if(Matrix[row][column].getCapacity() > GenericTiles.getMAX()) 
					{
						Matrix[row-1][column].setCapacity(Matrix[row-1][column].getCapacity() + (Matrix[row][column].getCapacity() - GenericTiles.getMAX())); 
						Matrix[row][column].setCapacity(Matrix[row][column].getCapacity() - (Matrix[row][column].getCapacity()-GenericTiles.getMAX())); 
					}
					else 
					{
						//Do nothing...
					}
					//Update visual (Change num to alter time it takes for pressure to update visual)...
					//try {Thread.sleep(timer);} catch (InterruptedException e) {}
				}
			}
		}
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}


	//RIGHT PRESSURE..................
	public void onePressureRightTurn(char t) 
	{
		for (int row = 0; row < Matrix.length; row++) 
		{ 
			for (int column = Matrix[row].length-1 ; column > 0; column--) 
			{
				if( ! isItASolid(Matrix[row][column]) && (! isItASolid(Matrix[row][column-1])) )
				{
					//********NEED TO GET THIS TO PUSH SOME EXCESS WATER TO THE RIGHT SO THE LEFT PRESSURE CAN COUNTERACT IT AND PUSH THE EXCESS BACK TO LEFT...****************
					// pressure
					if(Matrix[row][column].getCapacity() > Matrix[row][column-1].getCapacity() ) 
					{
						double matrixOriginal= (double) ((Matrix[row][column].getCapacity() + Matrix[row][column-1].getCapacity())/2);
						Matrix[row][column-1].setCapacity(((Matrix[row][column].getCapacity() + Matrix[row][column-1].getCapacity())/2));
						Matrix[row][column].setCapacity(matrixOriginal);
					}
					else 
					{
						//do nothing
					}
					//update visual (Change num to alter time it takes for pressure to update visual)...
					//try {Thread.sleep(timer);} catch (InterruptedException e) {}
				}
			}
		}
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}


	//LEFT PRESSURE.....................
	public void onePressureLeftTurn(char t) 
	{
		for (int row = 0; row < Matrix.length; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length-1; column++) 
			{
				if( ! isItASolid(Matrix[row][column]) && (! isItASolid(Matrix[row][column+1])) )
				{
					//********NEED TO GET THIS TO PUSH SOME EXCESS WATER TO THE LEFT SO THE LEFT PRESSURE CAN COUNTERACT IT AND PUSH THE EXCESS BACK TO LEFT...***************
					if(Matrix[row][column].getCapacity() > Matrix[row][column+1].getCapacity() ) 
					{
						double tileOriginal= (double) ((Matrix[row][column].getCapacity() + Matrix[row][column+1].getCapacity())/2);
						Matrix[row][column+1].setCapacity(((Matrix[row][column].getCapacity() + Matrix[row][column+1].getCapacity())/2));
						Matrix[row][column].setCapacity(tileOriginal);
					}
					else 
					{
						//do nothing
					}
					//update visual (Change num to alter time it takes for pressure to update visual)...
					//try {Thread.sleep(timer);} catch (InterruptedException e) {}
				}
			}
		}
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}


	//------------NORMAL MOVE METHODS FOR SOLIDS IN MATRIX-------------
	//******************************************************************


	//SINK..............
	public void oneSinkTurn(char t) 
	{
		sinkTogetherMethod();
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}

	//MELT ICE CUBE...
	public void oneMeltTurn(char t) 
	{

		//[TODO]...
		for (int row = 0; row < Matrix.length; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if (Matrix[row][column].getTypeName().equals(IceCube.getIceCubeName()))
				{
					//printMatrix();
					if(Matrix[row][column].getCapacity() < 20)
					{
						Matrix[row][column].setTypeName(Fluid.getFluidName());
					}
					else
					{
						try
						{
							moveMeltedIce(row,column);
						}
						catch (Exception e)
						{
							e.printStackTrace();
							try
							{
								Matrix[row][column-1].addCapacity(Matrix[row][column].getCapacity()*0.1);
							}
							catch(Exception e1)
							{
								Matrix[row][column+1].addCapacity(Matrix[row][column].getCapacity()*0.1);
							}
						}
					}
				}
			}
		}
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
		//System.out.println("IT MADE IT TO END OF ICE CUBE MELT");
		//printMatrix();
	}

	//FLOAT ICE CUBE...
	public void iceFloatOneTurn(char t) 
	{
		for (int row = 1; row < Matrix.length; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				if (Matrix[row][column].getTypeName().equals(IceCube.getIceCubeName()))
				{
					double iceCapacity = Matrix[row][column].getCapacity();
					boolean didSomethingFloat = false;
					try
					{
						if( (Matrix[row-1][column].getCapacity() >= 100) && !isItASolid(Matrix[row-1][column]) ) //if the cell above u is greater than or equal to 100 and not a solid...
						{

							Random rn = new Random();
							int diceRoll = rn.nextInt(2);
							if (diceRoll == 1) 
							{
								//if there is a cell to the displacing fluid's left put it there...
								try 
								{
									Matrix[row-1][column-1].addCapacity( Matrix[row-1][column].getCapacity() );
									Matrix[row-1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row-1][column] = new IceCube();
									Matrix[row-1][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row-1][column].setCapacity(iceCapacity);
									didSomethingFloat = true;

								}
								catch(Exception e)
								{
									if((! isItASolid(Matrix[row-1][column])) && (! isItASolid(Matrix[row-1][column+1])))
									{
										//...otherwise put it to the cell to the right if there is a cell there...
										Matrix[row-1][column+1].addCapacity( Matrix[row-1][column].getCapacity() );
										Matrix[row-1][column].setCapacity( 0 );
										Matrix[row][column].setCapacity(0);
										Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

										Matrix[row-1][column] = new IceCube();
										Matrix[row-1][column].setTypeName(IceCube.getIceCubeName());
										Matrix[row-1][column].setCapacity(iceCapacity);
										didSomethingFloat = true;
										//e.printStackTrace();
									}
								}
							}
							else 
							{
								try 
								{
									Matrix[row-1][column+1].addCapacity( Matrix[row-1][column].getCapacity() );
									Matrix[row-1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row-1][column] = new IceCube();
									Matrix[row-1][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row-1][column].setCapacity(iceCapacity);
									didSomethingFloat = true;
								}
								catch(Exception e)
								{
									if((! isItASolid(Matrix[row-1][column])) && (! isItASolid(Matrix[row-1][column-1])))
									{
										Matrix[row-1][column-1].addCapacity( Matrix[row-1][column].getCapacity() );
										Matrix[row-1][column].setCapacity( 0 );
										Matrix[row][column].setCapacity(0);
										Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

										Matrix[row-1][column] = new IceCube();
										Matrix[row-1][column].setTypeName(IceCube.getIceCubeName());
										Matrix[row-1][column].setCapacity(iceCapacity);
										didSomethingFloat = true;
										//e.printStackTrace();
									}
								}
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					if(didSomethingFloat)
					{
						//[TODO]May need to be reviewed/improved to work for the floating
						for (int x = 0; x < (GenericSolid.getBlockActualHeight(Matrix)-1); x++)
						{
							if((row+x) <=( Matrix.length-1))
							{
								//Check if the cell under you was an ice cube...
								if (Matrix[row+x][column].getTypeName().equals(IceCube.getIceCubeName()))
								{ 
									double tempCap = Matrix[row+x][column].getCapacity();
									Matrix[row+x][column].setCapacity(0);
									Matrix[row+x][column] = new GenericTiles();
									Matrix[row+x][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row+(x-1)][column] = new IceCube();
									Matrix[row+(x-1)][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row+(x-1)][column].setCapacity(tempCap);
									//System.out.println("Row: " + (row - x)+ "\tNext is: " + (row+1-x));
								}
							}
							else 
							{
								//System.out.println("*******************************");
							}
						}
					}
					didSomethingFloat = false;

				}
			}

		}


		//[TODO]AFTER THIS WORKS MOVE THE PRINT MATRIX TO AFTER THE FOR LOOP...
		//printMatrix();
		if (t=='t')
		{
			updateClassesOfEachCell(true);
		}
		else 
		{
			updateClassesOfEachCell(false);
		}
	}

	//ICE DROP IF NO WATER UNDER IT...
	public void iceSinkIfNoWater()
	{

		//ONLY DROP IF BELOW THE ICECUBE IS A GENERIC TILE/FLUID LESS THAN 100...
		for (int row = 0; row < Matrix.length; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				try //if this "try" fails, it's because there wasn't a cell below the specified cell... (the cell was at bottom)
				{
					//if you are an Ice cube and the cell under u is not a solid...
					if (Matrix[row][column].getTypeName().equals(IceCube.getIceCubeName()) && !isItASolid(Matrix[row+1][column])) 
					{
						boolean hasItSunkYet = false;
						if( (row - 1) >= 0 ) //if there is a row above u...
						{
							//System.out.println("Made it to part that just checked if there's a row above it.");
							if( (Matrix[row-1][column].getCapacity() == 0) ) //if the cell above you equals 0...
							{
								if (!isItASolid(Matrix[row-1][column])) //if above you is not a solid...
								{
									iceSinkMethodCondenser(row, column); //do the other calculations...
									hasItSunkYet = true;
								}
								else
								{
									hasItSunkYet = true;
								}

								//System.out.println("Made it to the end of if the cell above = 0 or is solid.");
							}
							else if(Matrix[row+1][column].getCapacity() >= 50)
							{
								//System.out.println("Because the cell above it had more than 0 or wasn't solid, did not sink.");
								hasItSunkYet = true;
							}
							//basically: I only want the ice cube to consider sinking if the cell above it is a solid or is empty.
						}

						if(!hasItSunkYet)  //if you haven't sunk yet because the cell above you wasn't compatible...
						{
							//System.out.println("Made it to the second if.");
							iceSinkMethodCondenser(row, column); //do the other calculations...
						}
						//System.out.println("Made it to the end of Ice Sink Method");
					}
				}
				catch(Exception e)
				{
					//e.printStackTrace();
					//do nothing...

				}
			}
		}
	}

	//Method to help shorten the ice sinking method...
	public void iceSinkMethodCondenser(int row, int column)
	{
		boolean hasItSunkYet = false;
		double iceCapacity = Matrix[row][column].getCapacity();

		if( (column - 1) >= 0 ) //if there is a column to the left of u...
		{
			//System.out.println("\tPASS 1");
			boolean isNOTSolidBottomLEFT =  ( !isItASolid(Matrix[row+1][column-1]) );
			//check if the bottom left is a solid and if not, move water there and set sunk = true...
			if( isNOTSolidBottomLEFT && !hasItSunkYet)  //if the cell under and to the left of you isn't a solid...
			{
				//System.out.println("\tPASS 1.B");
				//move all the capacity of the cell under u to that bottom left cell, then move yourself to the cell under u...
				Matrix[row+1][column-1].addCapacity( Matrix[row+1][column].getCapacity() );
				Matrix[row+1][column].setCapacity( 0 );
				Matrix[row][column].setCapacity(0);
				Matrix[row][column] = new GenericTiles();
				Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

				Matrix[row+1][column] = new IceCube();
				Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
				Matrix[row+1][column].setCapacity(iceCapacity);
				hasItSunkYet = true;
			}
		}

		if ((column + 1) <= (Matrix.length - 1) && !hasItSunkYet ) //if there is a column to the right of u and u haven't sunk yet...
		{
			//System.out.println("\tPASS 2");
			boolean isNOTSolidBottomRIGHT =  ( !isItASolid(Matrix[row+1][column+1]) );
			if ( isNOTSolidBottomRIGHT ) //if the cell under and to the right of you isn't a solid...
			{
				//System.out.println("\tPASS 2.B");
				//move all the capacity of the cell under u to that bottom right cell, then move yourself to the cell under u...
				Matrix[row+1][column+1].addCapacity( Matrix[row+1][column].getCapacity() );
				Matrix[row+1][column].setCapacity( 0 );
				Matrix[row][column].setCapacity(0);
				Matrix[row][column] = new GenericTiles();
				Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

				Matrix[row+1][column] = new IceCube();
				Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
				Matrix[row+1][column].setCapacity(iceCapacity);
				hasItSunkYet = true;
			}
		}



		else if ( ( Matrix[row+1][column].getCapacity() < 100) && !hasItSunkYet) //If the cell below you is fluid that is less than 100...
		{
			//System.out.println("\tPASS 3");
			//add the fluid there to yourself and move to it's position (essentially absorb the extra water to melt out later)...
			double combinedCapacity = Matrix[row+1][column].getCapacity() + iceCapacity ;
			Matrix[row+1][column].setCapacity(0);
			Matrix[row][column].setCapacity(0);
			Matrix[row][column] = new GenericTiles();
			Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

			Matrix[row+1][column] = new IceCube();
			Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
			Matrix[row+1][column].setCapacity(combinedCapacity);
			hasItSunkYet = true;

		}
	}



	//(4-21-20)[TODO][THIS IS UNUSED]BETTER ICE FALLING METHOD THAT WORKS WITH BLOCKS LIKE IN BLOCK SINK METHOD...
	public void newIceSinkMethod()
	{
		for (int row = 0; row < Matrix.length - 1; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				//if its a block...
				if (Matrix[row][column].getTypeName().equals(IceCube.getIceCubeName()) )
				{
					double iceCapacity = Matrix[row][column].getCapacity();
					boolean didSomethingSink = false;

					//if the cell below the block is not a solid...
					if( (! isItASolid(Matrix[row+1][column])) ) 
					{
						if( /*Matrix[row-1][column].getTypeName().equals(GenericTiles.getGenericTilesName()) || */ Matrix[row-1][column].getTypeName().equals(Fluid.getFluidName()) )
						{

						}
						else 
						{
							Random rn = new Random();
							int diceRoll = rn.nextInt(2);
							if (diceRoll == 1) 
							{
								//if there is a cell to the displacing fluid's left put it there...
								try 
								{
									Matrix[row+1][column-1].addCapacity( Matrix[row+1][column].getCapacity() );
									Matrix[row+1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row+1][column] = new IceCube();
									Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row+1][column].setCapacity(iceCapacity);
									didSomethingSink = true;

								}
								catch(Exception e)
								{
									if((! isItASolid(Matrix[row+1][column])) && (! isItASolid(Matrix[row+1][column+1])))
									{
										//...otherwise put it to the cell to the right if there is a cell there...
										Matrix[row+1][column+1].addCapacity( Matrix[row+1][column].getCapacity() );
										Matrix[row+1][column].setCapacity( 0 );
										Matrix[row][column].setCapacity(0);
										Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

										Matrix[row+1][column] = new IceCube();
										Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
										Matrix[row+1][column].setCapacity(iceCapacity);
										didSomethingSink = true;
										e.printStackTrace();
									}
								}
							}
							else 
							{
								try 
								{
									Matrix[row+1][column+1].addCapacity( Matrix[row+1][column].getCapacity() );
									Matrix[row+1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row+1][column] = new IceCube();
									Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row+1][column].setCapacity(iceCapacity);
									didSomethingSink = true;
								}
								catch(Exception e)
								{
									if((! isItASolid(Matrix[row+1][column])) && (! isItASolid(Matrix[row+1][column-1])))
									{
										Matrix[row+1][column-1].addCapacity( Matrix[row+1][column].getCapacity() );
										Matrix[row+1][column].setCapacity( 0 );
										Matrix[row][column].setCapacity(0);
										Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

										Matrix[row+1][column] = new IceCube();
										Matrix[row+1][column].setTypeName(IceCube.getIceCubeName());
										Matrix[row+1][column].setCapacity(iceCapacity);
										didSomethingSink = true;
										e.printStackTrace();
									}
								}
							}
						}
					}
					if(didSomethingSink)
					{
						//(4-5-20)[TODO]IMPROVE THIS PART SO IT PROPERLY DROPS THE WHOLE BLOCK AS ONE...
						for (int x = 0; x < (GenericSolid.getBlockActualHeight(Matrix)-1); x++)
						{
							if((row-x) > 0)
							{
								//Check if there was a block above the one that sunk...
								if (Matrix[row-x][column].getTypeName().equals(Block.getBlockName()))
								{
									Matrix[row-x][column].setCapacity(0);
									Matrix[row-x][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row-(x-1)][column] = new IceCube();
									Matrix[row-(x-1)][column].setTypeName(IceCube.getIceCubeName());
									Matrix[row-(x-1)][column].setCapacity(iceCapacity);
									//System.out.println("Row: " + (row - x)+ "\tNext is: " + (row+1-x));
								}
							}
							else 
							{
								//System.out.println("*******************************");
							}
						}
					}
					didSomethingSink = false;

				}
			}
		}
	}

	//METHOD THAT WORKS WITH SINK AND MAKES IT SO THAT BLOCKS STICK TOGETHER...
	//...by checking if a block moves down it brings the block above it with it and into the cell it just left...
	
	
	
	//(4-15-20)[TODO]Condense this sink together method...
	//SINK AND STICK TOGETHER...
	public void sinkTogetherMethod()
	{

		for (int row = 0; row < Matrix.length - 1; row++) 
		{ 
			for (int column = 0; column < Matrix[row].length; column++) 
			{
				//if its a block...
				if (Matrix[row][column].getTypeName().equals(Block.getBlockName()) )
				{
					boolean didSomethingSink = false;
					//if the cell below the block is not a solid...
					if( (! isItASolid(Matrix[row+1][column])) ) 
					{
						Random rn = new Random();
						int diceRoll = rn.nextInt(2);
						if (diceRoll == 1) 
						{
							//if there is a cell to the displacing fluid's left put it there...
							try 
							{
								Matrix[row+1][column-1].addCapacity( Matrix[row+1][column].getCapacity() );
								Matrix[row+1][column].setCapacity( 0 );
								Matrix[row][column].setCapacity(0);
								Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

								Matrix[row+1][column] = new Block();
								Matrix[row+1][column].setTypeName(Block.getBlockName());
								Matrix[row+1][column].setCapacity(Block.getBlockCapacityIdentity());
								didSomethingSink = true;

							}
							catch(Exception e)
							{
								if((! isItASolid(Matrix[row+1][column])) && (! isItASolid(Matrix[row+1][column+1])))
								{
									//...otherwise put it to the cell to the right if there is a cell there...
									Matrix[row+1][column+1].addCapacity( Matrix[row+1][column].getCapacity() );
									Matrix[row+1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row+1][column] = new Block();
									Matrix[row+1][column].setTypeName(Block.getBlockName());
									Matrix[row+1][column].setCapacity(Block.getBlockCapacityIdentity());
									didSomethingSink = true;
									e.printStackTrace();
								}
							}
						}
						else 
						{
							try 
							{
								Matrix[row+1][column+1].addCapacity( Matrix[row+1][column].getCapacity() );
								Matrix[row+1][column].setCapacity( 0 );
								Matrix[row][column].setCapacity(0);
								Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

								Matrix[row+1][column] = new Block();
								Matrix[row+1][column].setTypeName(Block.getBlockName());
								Matrix[row+1][column].setCapacity(Block.getBlockCapacityIdentity());
								didSomethingSink = true;
							}
							catch(Exception e)
							{
								if((! isItASolid(Matrix[row+1][column])) && (! isItASolid(Matrix[row+1][column-1])))
								{
									Matrix[row+1][column-1].addCapacity( Matrix[row+1][column].getCapacity() );
									Matrix[row+1][column].setCapacity( 0 );
									Matrix[row][column].setCapacity(0);
									Matrix[row][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row+1][column] = new Block();
									Matrix[row+1][column].setTypeName(Block.getBlockName());
									Matrix[row+1][column].setCapacity(Block.getBlockCapacityIdentity());
									didSomethingSink = true;
									e.printStackTrace();
								}
							}
						}
					}
					if(didSomethingSink)
					{
						//(4-5-20)[TODO]IMPROVE THIS PART SO IT PROPERLY DROPS THE WHOLE BLOCK AS ONE...
						for (int x = 0; x < (GenericSolid.getBlockActualHeight(Matrix)-1); x++)
						{
							if((row-x) > 0)
							{
								//Check if there was a block above the one that sunk...
								if (Matrix[row-x][column].getTypeName().equals(Block.getBlockName()))
								{
									Matrix[row-x][column].setCapacity(0);
									Matrix[row-x][column].setTypeName(GenericTiles.getGenericTilesName());

									Matrix[row-(x-1)][column] = new Block();
									Matrix[row-(x-1)][column].setTypeName(Block.getBlockName());
									Matrix[row-(x-1)][column].setCapacity(Block.getBlockCapacityIdentity());
									//System.out.println("Row: " + (row - x)+ "\tNext is: " + (row+1-x));
								}
							}
							else 
							{
								//System.out.println("*******************************");
							}
						}
					}
					didSomethingSink = false;

				}
			}
		}
	}




	//[TODO][REMOVE WHEN DONE TESTING]...
	public void showActualBlockHeight()
	{
		System.out.println("ACTUAL BLOCK HEIGHT: " + GenericSolid.getBlockActualHeight(Matrix));
	}

	//HELPS TELL MELTED ICE WHERE TO GO...
	public void moveMeltedIce(int row,int column) 
	{
		double tempCap = Matrix[row][column].getCapacity();
		Matrix[row][column].setCapacity(tempCap*0.9);
		if( (row - 1) >= 0 ) //if there is a row above u...
		{
			if(Matrix[row-1][column].getTypeName().equals(IceCube.getIceCubeName()))
			{
				try
				{
					Matrix[row][column-1].addCapacity(tempCap*0.1);
				}
				catch(Exception e)
				{
					Matrix[row][column+1].addCapacity(tempCap*0.1);
				}
			}
			else
			{
				Matrix[row-1][column].addCapacity(tempCap*0.1);
			}
		}

	}


	//--------------------------------FILE I\O STUFF START-------------------------------------
	//*****************************************************************************************

	private File saveSlot = new File("SaveSlot_Preset.csv");

	//SAVES CURRENT GRID TO SAVE SLOT FILE...
	public void saveGridToFile()
	{
		try 
		{		
			FileWriter fw = new FileWriter(saveSlot);
			for (int i = 0; i < Matrix.length; i++)
			{
				for(int j = 0; j< Matrix[i].length; j++)
				{
					if(!isItASolid(Matrix[i][j]))
					{
						double fluidThere = Matrix[i][j].getCapacity();
						//System.out.print(fluidThere);
						//System.out.print("\t");
						fw.write(String.valueOf(fluidThere) + ",");
					}
					else if(Matrix[i][j].getTypeName().equals(Block.getBlockName()))
					{
						fw.write("|B|" + ",");
					}
					else if (Matrix[i][j].getTypeName().equals(IceCube.getIceCubeName()))
					{
						fw.write("|C|" + ",");
					}

				}
				//System.out.print("\n");
				fw.write(System.getProperty( "line.separator" ));
			}
			fw.close();
		} 

		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//(4-16-20)[TODO]IMPROVE THIS SO THAT IF A FILE ISN'T SAME SIZE AS GRID, IT AUTOMATICALLY SCALES TO MATCH GRIDSIZE... [TODO] [IMPORTANT TO FIGURE OUT!]
	//LOADS A CERTIAN FILE...
	public boolean loadGridFromFile(String fileName)
	{
		boolean misMatchFileSize = false;
		File fluidPresetFile = new File(fileName);
		try 
		{
			//int lineNumber = calcNumberOfLinesInFile(fileName);
			FileReader fr = new FileReader(fluidPresetFile);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] lineColumns;
			/*
			if (Matrix[0].length != lineNumber)
			{
				int ratioOfLengths = (int) (Matrix[0].length/lineNumber);


				for (int i = 0; i < Matrix.length; i++)
				{
					line = br.readLine();
					lineColumns = line.split(",");
					for(int j = 0; j< Matrix[i].length; j++)
					{
						for(int p = 1; p < ratioOfLengths; p++)
						{
							try
							{
								if (Matrix[i][j].getCapacity() == 0)
								{
									double capacity = Double.parseDouble(lineColumns[j]);
									Matrix[i][j+(p-1)] = new Fluid(capacity);
									Matrix[i][j+(p-1)].setCapacity(capacity);
								}

								//System.out.print(fluidThere);
							}
							catch (Exception e) 
							{
								Matrix[i+(p-1)][j] = new Block();
							}
						}
					}
				}
			}
			else 
			{
			 */
			try
			{

				for (int i = 0; i < Matrix.length; i++)
				{
					line = br.readLine();
					lineColumns = line.split(",");
					//System.out.println("\n\n\n"+line);
					for(int j = 0; j< Matrix[i].length; j++)
					{
						try
						{
							if (Matrix[i][j].getCapacity() == 0)
							{
								double capacity = Double.parseDouble(lineColumns[j]);
								//System.out.print(capacity);
								//System.out.println("\t");
								Matrix[i][j] = new Fluid(capacity);
								Matrix[i][j].setCapacity(capacity);
							}

							//System.out.print(fluidThere);
						}
						catch (Exception e) 
						{
							if (lineColumns[j].equals( "|B|"))
							{
								//e.printStackTrace();
								//System.out.println("\n\n[ERROR READING FILE]");
								//System.out.print(lineColumns[j]);
								//System.out.println("\t");
								Matrix[i][j] = new Block();
							}
							if (lineColumns[j].equals( "|C|"))
							{
								Matrix[i][j] = new IceCube();
								Matrix[i][j].setCapacity(500);
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				misMatchFileSize = true;
			}
			//}

			fr.close();

			//System.out.println("\n");
		} 

		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return misMatchFileSize;
	}


	//METHOD THAT CALCULATES THE NUMBER OF LINES IN A .CSV FILE...
	public int calcNumberOfLinesInFile(String fileName)
	{
		int lineNumber = 0;
		File fluidPresetFile = new File(fileName);
		try 
		{
			FileReader fr = new FileReader(fluidPresetFile);
			BufferedReader br = new BufferedReader(fr);
			@SuppressWarnings("unused")
			String line;
			//String[] lineColumns;

			try
			{
				while( ( line = br.readLine() ) != null    )
				{
					lineNumber += 1;
				}
				//System.out.println("LINE LENGTH: "+lineNumber + "\tMatrix height: "+ Matrix[0].length);
				fr.close();
			}
			catch (Exception e) 
			{
				//System.out.println("[ERROR IN COUNTING LINES OF FILE METHOD]");
				//e.printStackTrace();
			}

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return lineNumber;	
	}


	//--------------------------------FILE I\O STUFF END-------------------------------------
	//***************************************************************************************

	//---------------------------------------------------------	
	//---------------GETTERS AND SETTERS----------------------
	//---------------------------------------------------------	



	public GenericTiles[][] getMatrix() 
	{
		return Matrix;
	}

	public static int getGridRows() 
	{
		return gridRows;
	}


	public static int getGridColumns() 
	{
		return gridColumns;
	}


	public static void setGridColumns(int gridColumns) 
	{
		GridSetup.gridColumns = gridColumns;
	}


	public static void setGridRows(int gridRows) 
	{
		GridSetup.gridRows = gridRows;
	}





}