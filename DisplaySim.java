//IMPORT STUFF...
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.input.MouseEvent;

//DONE IMPORTING STUFF...

//*************************************************
//Class: DisplaySim
//Author: Malachi Sanderson
//Date Created: 03 5, 2020
//Date Modified: 04-21-2020
//
//Purpose: THIS IS THE GUI CLASS. 
//IT ALSO CONTROLS ALOT OF THE STUFF THAT SIMMANAGER USED TO CONTROL IN V2....
//       
//
//Attributes:
//
//Methods:
//
//*******************************************************
public class DisplaySim extends Application 
{
	//THIS IS THE GUI CLASS. IT ALSO CONTROLS ALOT OF THE STUFF THAT SIMMANAGER USED TO CONTROL IN V2....

	//INCRESE THESE NUMBERS TO CHANGE SIZE OF GRID...
	GridSetup Grid = new GridSetup(40, 40);

	Pane pane = new Pane();
	Timeline timeline = new  Timeline();
	MenuBar menuBar = new MenuBar();
	BorderPane bp = new BorderPane();
	Scene scene = new Scene(bp,750,600);

	//I Optimized the program, allowing it to handle much larger grids by-- 
	//moving where the print statements were in grid setup...


	SimManager simCycle = new SimManager(Grid,"clear");

	int rows = GridSetup.getGridRows();
	int cols = GridSetup.getGridColumns();
	//Creates the grid of rectangles of a size based on the size used to make the matrix in sim manager/grid setup....
	Rectangle rect[][] = new Rectangle[rows][cols];

	//DISPLAY SIM CONSTRUCTOR...
	public DisplaySim() 
	{
		pane.setStyle("-fx-background-color: black");
		//this part just creates a bunch of empty rectangles bound to the pane's dimensions...
		for (int i = 0; i < rows; i++) 
		{
			//(4-20-20)[TODO]MAKE IT SO THAT EACH RECTANGLE IS A BUTTON...
			for(int j = 0; j < cols ; j++) 
			{
				rect[i][j] = new Rectangle();

				rect[i][j].setFill(Color.WHITE);
				//rect[i][j].setStroke(Color.BLACK);
				pane.getChildren().add(rect[i][j]);

				rect[i][j].widthProperty().bind(pane.widthProperty().divide(cols));
				rect[i][j].layoutXProperty().bind(pane.widthProperty().divide(cols).multiply(j));

				rect[i][j].heightProperty().bind(pane.heightProperty().divide(rows));
				rect[i][j].layoutYProperty().bind(pane.heightProperty().divide(rows).multiply(i));
			}
		}

		updateVisual();

		makeButtons();

		timeline.setCycleCount(Timeline.INDEFINITE);

		//Change that value in the duration to change the time between each new key-frame...
		//Poorly optimized so can't go very low (goal was 50 ms)...
		KeyFrame keyframe = new KeyFrame(Duration.millis(100), e->
		{
			simCycle.simCycle(Grid);
			updateVisual();
		});

		timeline.getKeyFrames().add(keyframe);		
	}

	//(4-6-20)[TODO]NEED TO IMPROVE HEIGHT/WIDTH TEXT + MAKE IT'S SIZE/POSITION IS BOUND TO THE SIZE OF THE PANE!!!!!


	//MAKES THE BUTTONS FOR LOADING THE FILES...
	public void makeSaveAndLoadButtons()
	{
		
		//[TODO]MAKE IT SO SAVE IS ON F6 AND LOAD IS ON F8
		Menu SALMenu = new Menu("Save/Load");
		SALMenu.setStyle("-fx-border-style: solid");
		//*************************************************
		MenuItem clearAllBtn = new MenuItem("Clear all");
		MenuItem saveFluidBtn = new MenuItem("Save (Overwrites last Save)");
		MenuItem loadSavedSlotBtn = new MenuItem("Load: SAVED Scenario");
		MenuItem loadScenarioOneBtn = new MenuItem("Load: Scenario 1(weird w thing)");
		MenuItem loadScenarioTwoBtn = new MenuItem("Load: Scenario 2 (rain)");
		MenuItem loadScenarioThreeBtn = new MenuItem("Load: Scenario 3 (rain on mountain)");
		MenuItem loadScenarioFourBtn = new MenuItem("Load: Scenario 4 (wide block pancake)");
		Text loadErrorTxt = new Text();

		menuBar.getMenus().add(SALMenu);

		//*********************************************
		SALMenu.getItems().add(clearAllBtn);
		SALMenu.getItems().add(saveFluidBtn);
		SALMenu.getItems().add(loadSavedSlotBtn);
		SALMenu.getItems().add(loadScenarioOneBtn);
		SALMenu.getItems().add(loadScenarioTwoBtn);
		SALMenu.getItems().add(loadScenarioThreeBtn);
		SALMenu.getItems().add(loadScenarioFourBtn);

		loadErrorTxt.setText("[LOAD ERROR. GRID SIZE IS INCOMPATIBLE (Default is 20*20) ] ");
		loadErrorTxt.setFill(Color.RED);
		loadErrorTxt.setLayoutX(scene.getWidth()*0.5);
		loadErrorTxt.setLayoutY(scene.getHeight()*0.5);
		pane.getChildren().add(loadErrorTxt);
		loadErrorTxt.setVisible(false);

		//***********************************************
		clearAllBtn.setOnAction(e-> 
		{
			remakeGridForGUI(Grid,"clear");
			updateVisual();
		});

		saveFluidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid,"save fluid");
			updateVisual();
		});

		loadSavedSlotBtn.setOnAction(e->
		{
			if(Grid.loadGridFromFile("SaveSlot_Preset.csv"))
			{
				loadErrorTxt.setVisible(true);
			}
			else 
			{
				loadErrorTxt.setVisible(false);
			}
			updateVisual();
		});


		loadScenarioOneBtn.setOnAction(e->
		{
			if(Grid.loadGridFromFile("W_Preset.csv"))
			{
				loadErrorTxt.setVisible(true);
			}
			else 
			{
				loadErrorTxt.setVisible(false);
			}
			updateVisual();
		});

		loadScenarioTwoBtn.setOnAction(e->
		{
			if(Grid.loadGridFromFile("rain_Preset.csv"))
			{
				loadErrorTxt.setVisible(true);
			}
			else 
			{
				loadErrorTxt.setVisible(false);
			}
			updateVisual();

		});

		loadScenarioThreeBtn.setOnAction(e->
		{
			if(Grid.loadGridFromFile("MountainRain_Preset.csv"))
			{
				loadErrorTxt.setVisible(true);
			}
			else 
			{
				loadErrorTxt.setVisible(false);
			}
			updateVisual();

		});

		loadScenarioFourBtn.setOnAction(e->
		{
			if(Grid.loadGridFromFile("blockPancake_Preset.csv"))
			{
				loadErrorTxt.setVisible(true);
			}
			else 
			{
				loadErrorTxt.setVisible(false);
			}
			updateVisual();

		});


	}

	//THIS IS JUST ALL OF THE BUTTONS MADE IN ONE METHOD...
	public void makeButtons()
	{
		//-----------------------MAKE MENUS/BUTTONS START----------------------------
		//*******************************************************************
		Menu gridOptions = new Menu("Grid Options.");
		Menu playMenu = new Menu("Play Timeline [ENTER KEY]");
		Menu stopMenu = new Menu("Stop Timeline [CONTROL KEY]");
		Menu bubblesMenu = new Menu("BUBBLE!");
		Menu solidsMenu = new Menu("Solid Options");

		gridOptions.setStyle("-fx-border-style: solid");
		playMenu.setStyle("-fx-border-style: solid");
		stopMenu.setStyle("-fx-border-style: solid");
		bubblesMenu.setStyle("-fx-border-style: solid");
		solidsMenu.setStyle("-fx-border-style: solid");

		//***************************
		MenuItem playBtn = new MenuItem("START ANIMATION [ENTER]");
		MenuItem readyToStartBtn = new MenuItem("Do You Want To Start Timeline Again?");
		MenuItem oneTurnBtn = new MenuItem("take one turn");
		MenuItem generateRandomFluidBtn = new MenuItem("Generate Random Fluid [R KEY]");
		MenuItem generateRightVerticalChunkOfFluidBtn = new MenuItem("Generate A Vertical Chunk of Fluid (RIGHT)");
		MenuItem generateLeftVerticalChunkOfFluidBtn = new MenuItem("Generate A Vertical Chunk of Fluid (LEFT)");
		MenuItem generateHorizontalRowsOfFluidBtn = new MenuItem("Generate A Few Rows of Fluid");
		MenuItem emptyBottomRowBtn = new MenuItem("Empty Bottom Row");
		MenuItem clearAllBtn = new MenuItem("Clear all [BACKSPACE KEY]");
		MenuItem deleteFluidsBtn = new MenuItem("Remove all FLUIDS");
		

		
		MenuItem packedFluidBtn = new MenuItem("Make Hyper-dense Fluid");
		MenuItem airBubbleBtn = new MenuItem("Make Air Bubble [SPACEBAR]");
		//MenuItem fiveAirBubbleBtn = new MenuItem("Make Five Air Bubbles[non--functional]");	//[TODO]


		MenuItem addOneCenterSolidBtn = new MenuItem("Place A Single Solid In The Center.");
		MenuItem addBlockCenterBtn = new MenuItem("Drop A Block!");
		MenuItem addOneCenterICEBtn = new MenuItem("Place A Single Piece of Ice In The Center.");
		MenuItem addIceCubeChunkCenterBtn = new MenuItem("Drop An Ice Block! (Warning: ice is still in testing)");
		MenuItem inputSolidHeightBtn = new MenuItem("Manually Input Block Height (1-10)");
		MenuItem inputSolidWidthtBtn = new MenuItem("Manually Input Block Width (1-10)");
		MenuItem deleteSolidsBtn = new MenuItem("Remove All SOLIDS.");


		Button submitBlockHeightBtn = new Button ("Submit");
		TextField inputBlockHeightTxt = new TextField ();
		Button submitBlockWidthBtn = new Button ("Submit");
		TextField inputBlockWidthTxt = new TextField ();
		Button allKyPrs = new Button();

		HBox hb = new HBox();


		//-----------------------MAKE MENUS/BUTTONS END----------------------------
		//*************************************************************************



		//-----------------------CREATE ORDER OF BUTTONS/MENUS START-------------------
		//*****************************************************************************
		menuBar.getMenus().add(gridOptions);
		menuBar.getMenus().add(bubblesMenu);
		menuBar.getMenus().add(solidsMenu);
		makeSaveAndLoadButtons();
		menuBar.getMenus().add(playMenu);
		menuBar.getMenus().add(stopMenu);

		playMenu.getItems().add(playBtn);

		stopMenu.getItems().add(readyToStartBtn);

		gridOptions.getItems().add(oneTurnBtn);
		gridOptions.getItems().add(generateRandomFluidBtn);
		gridOptions.getItems().add(generateRightVerticalChunkOfFluidBtn);
		gridOptions.getItems().add(generateLeftVerticalChunkOfFluidBtn);
		gridOptions.getItems().add(generateHorizontalRowsOfFluidBtn);
		//gridOptions.getItems().add(emptyBottomRowBtn); //[DO NOT DELETE]removed because don't use it often + don't want the clutter in GUI. Is functional!!! 
		gridOptions.getItems().add(deleteFluidsBtn);
		gridOptions.getItems().add(clearAllBtn);



		bubblesMenu.getItems().add(packedFluidBtn);
		bubblesMenu.getItems().add(airBubbleBtn);

		solidsMenu.getItems().add(addOneCenterSolidBtn);
		solidsMenu.getItems().add(addBlockCenterBtn);
		solidsMenu.getItems().add(addOneCenterICEBtn);
		solidsMenu.getItems().add(addIceCubeChunkCenterBtn); 		//[TODO]BEFORE THIS CAN BE IMPLEMENTED THE SINK METHODS FOR ICE SHOULD BE IMPROVED...
		solidsMenu.getItems().add(inputSolidWidthtBtn);
		solidsMenu.getItems().add(inputSolidHeightBtn);
		solidsMenu.getItems().add(deleteSolidsBtn);

		
		
		pane.getChildren().addAll(hb,allKyPrs);
		hb.setSpacing(10);

		//makes the button that you trigger with key presses, invisible and out the the visible space
		allKyPrs.setStyle("-fx-background-color: transparent");
		allKyPrs.setLayoutX(20*Math.pow(10, 5));
		//airBubbleKyPrs.setStyle("-fx-background-color: transparent");
		//airBubbleKyPrs.setLayoutX(10*Math.pow(10, 5));

		Text errorMessageTxt = new Text("[ERROR: INPUT A NUMBER FROM 1 TO 10]");
		errorMessageTxt.setFill(Color.RED);

		//(4-6-20)[TODO]NEED TO IMPROVE THIS TEXT + MAKE IT'S SIZE/POSITION IS BOUND TO THE SIZE OF THE PANE!!!!!
		Text printHeightTxt = new Text("Default Generated Block Height (ratio out of 10): " + GenericSolid.getBlockHeight());
		printHeightTxt.setFill(Color.LIMEGREEN);
		printHeightTxt.setLayoutX(200);
		printHeightTxt.setLayoutY(25);

		Text printWidthTxt = new Text("Default Generated Block Width (ratio out of 10): " + GenericSolid.getBlockWidth() );
		printWidthTxt.setFill(Color.LIMEGREEN);
		printWidthTxt.setLayoutX(200);
		printWidthTxt.setLayoutY(50);

		pane.getChildren().addAll(printHeightTxt, printWidthTxt);

		playBtn.setStyle("-fx-border-style: solid");
		readyToStartBtn.setStyle("-fx-border-style: solid");


		//-----------------------CREATE ORDER OF BUTTONS/MENUS END-------------------
		//*****************************************************************************

		//PLAY BUTTON...
		playBtn.setOnAction( e -> 
		{
			//STARTS TIMELINE THEN HIDES PLAY MENU ONCE CLICKED
			//play.hide();
			playMenu.setDisable(true);
			timeline.play();
			playMenu.setStyle("-fx-border-style: dotted");
			if (stopMenu.isDisable()) 
			{
				stopMenu.setDisable(false);
				playMenu.hide();
			}
		});

		//STOP MENU...
		stopMenu.addEventHandler(Menu.ON_SHOWN, e->
		{
			//on showing the stop menu, stop the time line.
			timeline.stop();
		});

		//ALLOW ANIMATION TO START AGAIN...
		readyToStartBtn.setOnAction(e-> 
		{
			timeline.stop();
			stopMenu.setDisable(true);
			playMenu.setStyle("-fx-border-style: solid");
			playMenu.setDisable(false);
		});


		//TAKES ONE TURN...
		oneTurnBtn.setOnAction(e->
		{
			simCycle.simCycle(Grid);
			updateVisual();
		});

		//FILLS GRID WITH RANDOMIZED FLUID CELLS
		generateRandomFluidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid,"random");
			updateVisual();
		});

		//SPAWNS A CHUNK OF FLUID IN TOP RIGHT...
		generateRightVerticalChunkOfFluidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid,"vertical chunk right");
			updateVisual();
		});

		//SPAWNS A CHUNK OF FLUID IN TOP LEFT...
		generateLeftVerticalChunkOfFluidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid,"vertical chunk left");
			updateVisual();
		});

		//SPAWNS SOME ROWS OF FLUID...
		generateHorizontalRowsOfFluidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid,"standard horizontal rows");
			updateVisual();
		});

		//EMPTY BOTTOM ROWS...
		emptyBottomRowBtn.setOnAction(e-> 
		{
			remakeGridForGUI(Grid,"empty bottom");
			updateVisual();
		});

		//CLEARS ALL FLUID...
		deleteFluidsBtn.setOnAction(e-> 
		{
			remakeGridForGUI(Grid,"delete fluids");
			updateVisual();
		});

		//CLEARS ENTIRE GRID...
		clearAllBtn.setOnAction(e-> 
		{
			remakeGridForGUI(Grid,"clear");
			updateVisual();
		});


		//SPAWNS A HYPER-DENSE FLUID CELL...
		packedFluidBtn.setOnAction( e -> 
		{
			remakeGridForGUI(Grid,"packed fluid");
			updateVisual();
		});

		//SPAWNS AIR BUBBLE IN LOWER MID CENTER...
		airBubbleBtn.setOnAction( e -> 
		{
			remakeGridForGUI(Grid,"bubble");
			updateVisual();
		});

		//**********(3-20-20)[TODO]MAKE BUTTON THAT RUNS AIR BUBBLE 5 TIMES ONE AFTER ANOTHER...
		//gridOptions.getItems().add(fiveAirBubbleBtn);
		/*
		fiveAirBubbleBtn.setOnAction(e->
		{
			//[TODO]
			for (int i = 0; i < 5; i++)
			{
				remakeGridForGUI(Grid,"bubble");
				simCycle.simCycle(Grid);
				updateVisual();

			}
		});

		 */

		//**********(3-20-20)[TODO]MAKE IT SO CAN JUST CLICK RMB AND ACTIVATE BUBBLE...
		/*
		MouseEvent fiveAirBubbleBtn = new MouseEvent();
		gridOptions.getItems().add(fiveAirBubbleBtn);
		fiveAirBubbles.addEventHandler(MouseEvent.MOUSE_PRESSED.getButton(),(e->
		{
				remakeGridForGUI(Grid,"bubble");
				updateVisual();
		});
		 */

		//-----------------------SOLIDS BUTTONS START----------------------------
		//***********************************************************************

		//MAKES ONE SOLID BLOCK CELL...
		addOneCenterSolidBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid, "one solid in center");
			updateVisual();
		});

		//MAKES ONE BIG BLOCK IN CENTER...
		addBlockCenterBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid, "chunk solid in center");
			updateVisual();
		});

		//MAKES A CHUNK OF ICE IN CENTER...
		addIceCubeChunkCenterBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid, "chunk ice center");
			updateVisual();
		});
		
		
		//DELETES ALL SOLIDS...
		deleteSolidsBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid, "delete solids");
			updateVisual();
		});
		
		//MAKES A SINGLE CELL OF ICE...
		addOneCenterICEBtn.setOnAction(e->
		{
			remakeGridForGUI(Grid, "one ice center");
			updateVisual();
		});


		//***********************STUFF FOR INPUTTING SOLID SIZES*************************

		//BUTTON THAT SHOWS TEXT FIELD FOR SOLID HEIGHT...
		inputSolidHeightBtn.setOnAction(e->
		{
			hb.getChildren().clear();
			hb.getChildren().addAll(inputBlockHeightTxt,submitBlockHeightBtn);
		});

		//SUBMIT WHAT YOU INPUTTED IN TEXT FIELD...
		submitBlockHeightBtn.setOnAction(e -> 
		{
			//Button turns red if you input something not accepted and 
			//empties and hides itself after submission.
			try 
			{
				double inputtedValue = Double.parseDouble(inputBlockHeightTxt.getText());
				if(inputtedValue <= 10 && inputtedValue >= 1 )
				{
					GenericSolid.setBlockHeight(inputtedValue);
					inputBlockHeightTxt.clear();
					//System.out.println("Block Height: " + GenericSolid.getBlockHeight());
					hb.getChildren().clear();
					submitBlockHeightBtn.setStyle("-fx-background-color: white");
					printHeightTxt.setText("Newly Generated Block Height: " + GenericSolid.getBlockHeight());
					printHeightTxt.setFill(Color.LIMEGREEN);
					printHeightTxt.setLayoutX(250);
					printHeightTxt.setLayoutY(25);
				}
				else
				{
					//System.out.println("\n\n\t[ERROR: INPUT A NUMBER FROM 1 TO 10]");
					inputBlockHeightTxt.clear();
					submitBlockHeightBtn.setStyle("-fx-background-color: red");
					hb.getChildren().add(errorMessageTxt);
					//(4-6-20)[TODO]MAKE THE BUTTON FLASH RED AND WHITE FOR A SECOND...
					//submitBlockHeightBtn.setStyle("-fx-background-color: white");
				}
			}
			catch(Exception e1)
			{
				//System.out.println("\n\n\t[ERROR: INPUT A NUMBER FROM 1 TO 10]");
				inputBlockHeightTxt.clear();
				submitBlockHeightBtn.setStyle("-fx-background-color: red");
				hb.getChildren().add(errorMessageTxt);
			}
		});

		//BUTTON THAT SHOWS TEXT FIELD FOR SOLID WIDTH...
		inputSolidWidthtBtn.setOnAction(e->
		{
			hb.getChildren().clear();
			hb.getChildren().addAll(inputBlockWidthTxt,submitBlockWidthBtn);
		});

		//SUBMIT WHAT YOU INPUTTED IN TEXT FIELD...
		submitBlockWidthBtn.setOnAction(e -> 
		{
			//Button turns red if you input something not accepted and 
			//empties and hides itself after submission.
			try 
			{
				double inputtedValue = Double.parseDouble(inputBlockWidthTxt.getText());
				if(inputtedValue <= 10 && inputtedValue >= 1 )
				{
					GenericSolid.setBlockWidth(inputtedValue);
					inputBlockWidthTxt.clear();
					//System.out.println("Block Width: " + GenericSolid.getBlockWidth());
					hb.getChildren().clear();
					submitBlockWidthBtn.setStyle("-fx-background-color: white");
					printWidthTxt.setText("Newly Generated Block Width: " + GenericSolid.getBlockWidth());
					printWidthTxt.setFill(Color.LIMEGREEN);
					printWidthTxt.setLayoutX(250);
					printWidthTxt.setLayoutY(50);
				}
				else
				{
					//System.out.println("\n\n\t[ERROR: INPUT A NUMBER FROM 1 TO 10]");
					inputBlockWidthTxt.clear();
					submitBlockWidthBtn.setStyle("-fx-background-color: red");
					hb.getChildren().add(errorMessageTxt);
				}
			}
			catch(Exception e1)
			{
				//System.out.println("\n\n\t[ERROR: INPUT A NUMBER FROM 1 TO 10]");
				inputBlockWidthTxt.clear();
				submitBlockWidthBtn.setStyle("-fx-background-color: red");
				hb.getChildren().add(errorMessageTxt);
			}
		});

		//-----------------------SOLIDS BUTTONS END----------------------------
		//***********************************************************************


		//-----------------------BUTTONS TURNED TO KEYSTROKES-------------------
		//**********************************************************************
		//(4-7-20)[TODO]SET MORE BUTTONS TO FIRE ON KEY PRESS...
	
		allKyPrs.setOnKeyPressed(event -> 
		{
			if (event.getCode().equals(KeyCode.BACK_SPACE))
			{
				clearAllBtn.fire();
			}
			if (event.getCode().equals(KeyCode.SPACE))
			{
				airBubbleBtn.fire();
			}
			if (event.getCode().equals(KeyCode.ENTER))
			{
				playBtn.fire();
			}
			if (event.getCode().equals(KeyCode.CONTROL))
			{
				readyToStartBtn.fire();
			}
			if (event.getCode().equals(KeyCode.R))
			{
				generateRandomFluidBtn.fire();
			}
		});
		
	}

	//THIS RESETS SIM/REGENERATES A SCENARIO (ALLOWS COMBINING FLUID GENERATORS)...
	public void remakeGridForGUI(GridSetup Grid, String typeOfGrid) 
	{
		new SimManager(Grid,typeOfGrid);
		//updateVisual();
	}

	//THIS UPDATES THE VISUALS...
	private void updateVisual() 
	{
		Grid.updateClassesOfEachCell(false);
		for (int i = 0; i < rows; i++) 
		{
			for(int j = 0; j < cols ; j++) 
			{
				double level = Grid.getMatrix()[i][j].getCapacity()/GenericTiles.getMAX();
				if (level>1) 
				{
					level = 1;
				}
				//This updates the classes of each and if they are GenericTiles (cap = 0) colors it black.

				if (Grid.getMatrix()[i][j].getTypeName().equals(GenericTiles.getGenericTilesName())) 
				{
					//****CHANGE THIS COLOR IF YOU WANT TO DISTINGUISH THE COLOR OF NON-FLUID CELLS....
					rect[i][j].setFill(Color.BLACK);
					//This part prints out any non-Fluid.java (GenericTiles) cells...
					//System.out.println("Cell ID: " + i +","+ j + "\n\n\nCLASS NAME: " + Grid.getMatrix()[i][j].getClass().getCanonicalName() );
				}
				else if (Grid.getMatrix()[i][j].getTypeName().equals(Fluid.getFluidName()))
				{
					rect[i][j].setFill(Color.rgb(0, 0, 150,level));
				}
				else if (Grid.getMatrix()[i][j].getTypeName().equals(Block.getBlockName()))
				{
					rect[i][j].setFill(Color.rgb(150, 0, 0,level));
				}
				else if (Grid.getMatrix()[i][j].getTypeName().equals(IceCube.getIceCubeName()))
				{
					rect[i][j].setFill(Color.rgb(0,255, 255,level));
				}
			}
		}
	}


	//START...
	@Override
	public void start(Stage stage) 
	{
		bp.setCenter(pane);
		bp.setTop(menuBar);

		stage.setScene(scene);
		stage.setTitle("Fluid Sim");
		stage.show();
	}


	//MAIN...
	public static void main(String[] args) 
	{
		launch(args);
	}



}













