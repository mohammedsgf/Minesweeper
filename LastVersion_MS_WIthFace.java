import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.security.SecureRandom;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class LastVersion_MS_WIthFace extends Application{

    final int SEC=60;
    final int BOMBS_NUMBER=20;//here to change bombs number
    int nums[][]=new int[15][15];
    
    boolean[][] Booms=new boolean[15][15];
    boolean opened[][]=new boolean[15][15];
    
    Label NumCfBoms=new Label();
    
    TextField stateField=new TextField();
    
    
    Button[][] arrayButton=new Button[15][15];
    Button playButton=new Button("Play Again!!");
    Button bts = new Button("START");
    Button bthp = new Button("How to paly");
    Button bta = new Button("About");
    Button btex = new Button("Exit");
    boolean isStart=false;
    int h1=0;
	int h0=0;
	int m1=0;
	int m0=0;
	int s1=0;
	int s0=0;
	
	String Hours=h1+""+h0;
	String minutes=m1+""+m0;
	String sec=s1+""+s0;
    @Override
    public void start(Stage stage) {
    	Label timer=new Label(Hours+":"+minutes+":"+sec);
    	timer.setFont(javafx.scene.text.Font.font(20));
    	paint(stage,timer); // to pain the game grids
    	//-------------------------------------------------------------------------------------------------------------
    	spawnbomb(BOMBS_NUMBER);// spawn the bombs
    	//----------------------------------------------------------------------------------------------------------------
    	bulidstate();// find the around bombs of each grid
    	//------------------------------------------------------------------------------------------------------------
    	 EventHandler<ActionEvent>  ev=e -> {
			 s1++;
			 if(s1==10) {
				 s1=0;
				 s0++; 
				 
			 }
			 if(sec.equals("60")) {
				 s1=0;
				 s0=0;
				 m1++;
				 if(m1==10) {
					 m0++;
					 m1=0;
				 }
				 if(minutes.equals("60")) {
					 m1=0;
					 m0=0;
					 h1++;
					 if(h1==10) {
						 h0++;
						 h1=0;
					 }
				 }
					 
			 }
			 sec=s0+""+s1;
			 minutes=m0+""+m1;
			 Hours=h0+""+h1;
			 timer.setText(Hours+":"+minutes+":"+sec);
		 };
    	      
    	      
			Timeline animation=new Timeline(new KeyFrame(Duration.seconds(1),ev));

    	    	    animation.setCycleCount(Timeline.INDEFINITE);                
    	    	    animation.play(); // Start animation
    	    	    
    	//------------------------------------------------------------------------------------------------------------
    	for(int row=0;row<15;row++) 
    		for(int culm=0;culm<15;culm++) {
    			int index_row=row;
    			int index_culm=culm;
    			arrayButton[row][culm].setOnMouseClicked(e ->{

    				if(e.getButton()==MouseButton.SECONDARY) {
    					if(arrayButton[index_row][index_culm].getText().equals("F")) {
    						arrayButton[index_row][index_culm].setText("");
    					}
    					else {
    						arrayButton[index_row][index_culm].setText("F");
    						arrayButton[index_row][index_culm].setStyle(" -fx-text-fill: #ff0000");
    					}//for the flag
    				}
    				else {
    					CheckBooms(index_row,index_culm); if(stateField.getText().equals("Game Over") || stateField.getText().equals("Win") ){
        	    	    	animation.stop();	
        	    	    }} });
    		} // Handling
    	//-----------------------------------------------------------------------------------------------------------
    	playButton.setOnAction(e ->{ PlayAgian();isStart=false; animation.stop(); h1=0;
    	 h0=0;
    	 m1=0;
    	 m0=0;
    	 s1=0;
    	 s0=0;; start(stage);  });
    	// Play again button Handling

    	//-------------------------------------------------------------------------------------------------------------
    }
		public static void main(String[] args) { 
			launch(args);
			}
		public void PlayAgian() {
			for(int row=0;row<15;row++) 
				for(int culm=0;culm<15;culm++) {
					Booms[row][culm]=false;
					nums[row][culm]=0;
					opened[row][culm]=false;
				}
			stateField.setText("");
		}
		
		public void paint(Stage stage,Label timer) {
			BorderPane borderPane=new BorderPane();
			HBox topPane=new HBox();
			NumCfBoms.setPrefSize(90, 20);
			NumCfBoms.setText(BOMBS_NUMBER+" Bombs");
			
			NumCfBoms.setTextFill(Color.BLACK);
			
			NumCfBoms.setFont(javafx.scene.text.Font.font(20));
			NumCfBoms.setAlignment(Pos.CENTER);
			
			playButton.setPrefSize(100, 20);
			topPane.setPadding(new Insets(5,0,1,1));
			topPane.getChildren().addAll(playButton,timer,NumCfBoms,stateField);
			topPane.setSpacing(20);
			borderPane.setTop(topPane);
    
			GridPane gridPane=new GridPane();
			for(int row=0;row<15;row++) 
				for(int culm=0;culm<15;culm++) {
					arrayButton[row][culm]=new Button();
					arrayButton[row][culm].setPrefSize(32, 20);
					gridPane.add(arrayButton[row][culm], culm, row);
				}
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setPadding(new Insets(5,15,5,5));
			borderPane.setCenter(gridPane);
    
			BorderPane MainPane =new BorderPane();
			HBox HB = new HBox(15);
			VBox VB = new VBox(10);
			VB.setAlignment(Pos.CENTER);
			VB.getChildren().addAll(bts,bthp,bta,btex);
			bts.setPrefSize(100, 10);
			bthp.setPrefSize(100, 10);
			bta.setPrefSize(70, 10);
			VB.setSpacing(10);
			MainPane.setCenter(VB);
			ImageView image=new ImageView("file:///D:/%D8%B3%D8%B7%D8%AD%20%D8%A7%D9%84%D9%85%D9%83%D8%AA%D8%A8/Java%20Project/Logo2.png");
			HBox im=new HBox();
			im.getChildren().add(image);
			MainPane.setTop(im);
			Scene s = new Scene(MainPane, 600, 670);
			
			s.windowProperty().addListener(OV -> {
				for(int row=0;row<15;row++) 
					for(int culm=0;culm<15;culm++) {
						
						arrayButton[row][culm].setPrefSize(s.getWidth(), s.getHeight());
					}
				im.setAlignment(Pos.CENTER);
				topPane.setAlignment(Pos.CENTER);
				
				});//Binding the Buttons
			
			stage.setTitle("Minesweeper Game");
			stage.setScene(s);
			stage.show();
			//(Ali section) EventHandler for Start, How To Play, About and exit 
			Alert About = new Alert(Alert.AlertType.INFORMATION, "Minesweeper is a single-player puzzle video"
					+"game.The objective of the game is to clear a rectangular board containing hidden \"mines\" "
					+ "or bombs without detonating any of them, with help from clues about the number of neighboring mines"
					+ " in each field",ButtonType.OK);
			Alert Hint = new Alert(Alert.AlertType.INFORMATION, "At the beginning of the game the screen"
					+ " will be all gray squares and the start will be random where we will click on any box"
					+ " and if a large number of squares have been turned to the next step.If one box is turned upside down,"
					+ " we will randomly click the box again until a number of squares are rotated together."
					+ "Now start thinking, where the number in the boxes on the edges is the number of mines adjacent to"
					+ " this box from the right and left and up and down and even on the angles of any of the eight"
					+ "and you have to open all these boxes with out opening the mines",ButtonType.OK);    
    
			bts.setOnAction(( ActionEvent e)-> {
				MainPane.setCenter(borderPane); isStart=true; 
			});
			bthp.setOnAction(( ActionEvent e)-> 
             	Hint.showAndWait()
					);
			bta.setOnAction(( ActionEvent e)-> 
				About.showAndWait()
					);
			btex.setOnAction(( ActionEvent e)-> 
            	System.exit(1)
					);    
			stage.setMinHeight(670);
			stage.setMinWidth(600);
			stage.setTitle("Booomb"); 
			stage.setScene(s); 
			stage.show(); 
			//stage.setResizable(false);
		}
		public void spawnbomb(int n) {
			for(int i=0 ; i<n; i++) {
				int r=new SecureRandom().nextInt(15);
				int c=new SecureRandom().nextInt(15);
				while(Booms[r][c]==true) {
					r=new SecureRandom().nextInt(15);
					c=new SecureRandom().nextInt(15);
				}
				Booms[r][c]=true;
			} //distrbute the bombs
		}
		public boolean check(int i, int j) {
			if (nums[i][j]!=0) {
				arrayButton[i][j].setText(""+nums[i][j]);
				if(nums[i][j]==1) {
					arrayButton[i][j].setStyle(" -fx-text-fill: #228b22");
				}
				else if(nums[i][j]==2) {
					arrayButton[i][j].setStyle(" -fx-text-fill: #00008b");
				}
				else {
					arrayButton[i][j].setStyle(" -fx-text-fill: #ff0000");
				}
				arrayButton[i][j].setDisable(true);
				opened[i][j]=true;
			}
			else if (opened[i][j]==false) {
				//this will be changed, s.t. the button is disabled (zero is not printed)
				arrayButton[i][j].setDisable(true);
				opened[i][j]=true;
				return true;
			}
			return false;
			}
		public void bulidstate() {
			for(int row=0;row<15;row++) { //row itration
				for(int culm=0;culm<15;culm++) { //culm itration
					int numberofboms=0;
					int[] points = new int[] {
							-1, -1,
							-1, 0,
							-1, 1,
							0, -1,
							0, 1,
							1, -1,
							1, 0,
							1, 1
					};//Index of boxes that we have to check
	        for (int i = 0; i < points.length; i++) {
	        	int dx = points[i];
	        	int dy = points[++i];
	        	int newX = row + dx;
	        	int newY = culm + dy;
	        if (newX >= 0 && newX < arrayButton.length && newY >= 0 && newY < arrayButton[row].length) {
	        	if(Booms[newX][newY]==true) 
	        		numberofboms++;
	        	}
	        }
	        if( Booms[row][culm]) {
	        	nums[row][culm]=9;
	        	continue;
	        }
		nums[row][culm]=numberofboms;
		//arrayButton[row][culm].setText(""+nums[row][culm]); 
				}
			} // Some of the bulidstate method statements are Quoted from Almas Baimagambetov
		}
	public void open(int i,int j) {
	int r=0,c=0;
	if (j==14 && i==14) {
	r=i-1;c=j;
	if (check(r,c))
	    open(r,c);
	r=i;c=j-1;
	if (check(r,c))
	    open(r,c);
	r=i-1;c=j-1;
	if (check(r,c))
	    open(r,c);
	}
	else if (j==0 && i==0) {
	r=i+1;c=j;
	if (check(r,c))
	open(r,c);
	r=i;c=j+1;
	if (check(r,c))
	open(r,c);
	r=i+1;c=j+1;
	if (check(r,c))
	open(r,c);
	}
				else if (j==0 && i==14) {
					r=i-1;c=j;
					if (check(r,c))
						open(r,c);
					r=i;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j+1;
					if (check(r,c))
						open(r,c);
				}
				else if (j==14 && i==0) {
					r=i+1;c=j;
					if (check(r,c))
						open(r,c);
					r=i;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j-1;
					if (check(r,c))
						open(r,c);
				}
				else if (j==0) {
					r=i-1;c=j;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j;
					if (check(r,c))
						open(r,c);
				}
				else if (i==0) {
					r=i;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j-1;
					if (check(r,c))
						open(r,c);
				}
				else if(j==14){
					r=i-1;c=j;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j-1;
					if (check(r,c))
						open(r,c);
				}
				else if (i==14) {
					r=i-1;c=j;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i;c=j-1;
					if (check(r,c))
						open(r,c);
				}
				else {
					r=i-1;c=j;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i-1;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i;c=j-1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j+1;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j;
					if (check(r,c))
						open(r,c);
					r=i+1;c=j-1;
					if (check(r,c))
						open(r,c);
				}
			}
			
	public boolean check_Win() {
		boolean isWin=false;
		for(int row1=0;row1<15;row1++) 
			for(int culm1=0;culm1<15;culm1++) {
				if(Booms[row1][culm1]==false) {
					if(opened[row1][culm1]) {
						isWin=true;
						}
					else {
						isWin=false;
					}
					}
				}
		return isWin;
			}
	
	public void CheckBooms(int row, int culm) {
		if(check_Win()==true) {
			for(int row1=0;row1<15;row1++) 
				for(int culm1=0;culm1<15;culm1++) {
					arrayButton[row1][culm1].setDisable(true);
				}
			stateField.setText("Win");
		}
		
		if (nums[row][culm]==9) {
			for(int row1=0;row1<15;row1++) 
				for(int culm1=0;culm1<15;culm1++) {
					arrayButton[row1][culm1].setDisable(true);
					if(nums[row1][culm1]==9) {
						arrayButton[row1][culm1].setStyle("-fx-background-color: #ff0000");
						arrayButton[row1][culm1].setText("X");
					}
				}
			
			stateField.setText("Game Over");
		}
		else {
			if (nums[row][culm]==0) {
				arrayButton[row][culm].setDisable(true);
				open(row,culm);
			}
			else 
				arrayButton[row][culm].setText(""+nums[row][culm]);
				arrayButton[row][culm].setDisable(true);
			}
	}
}
	