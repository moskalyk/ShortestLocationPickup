
/*
   In this simple drawing program, the user can draw lines by pressing
   the mouse button and moving the mouse before releasing the button.
   A line is drawn from the point where the mouse button is pressed to the
   point where it is released.  A choice of drawing colors is offered in a menu.
   Another menu offers a choice of background colors.  Drawings can be saved to
   files and reloaded from files.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;

public class SimpleDrawProgram extends Frame implements ActionListener {

   public static void main(String[] args) {
         // The "main program" simply creates a frame belonging to the class
         // SimpleDrawProgram.  From then on, the frame takes care of itself.
      new SimpleDrawProgram();
   }

   SimpleDrawCanvasWithFiles canvas;  // This is where the drawing is actually done.
                             // This frame displayes this canvas along with a menu bar.

   public SimpleDrawProgram() {
         // Constructor.  Create the menus and the canvas, and add them to the
         // frame.  Set the frames' size and location, and show it on the screen.

      super("Simple Draw");

      Menu fileMenu = new Menu("File",true);
      fileMenu.add("New");      
      fileMenu.add("Save...");      
      fileMenu.add("Load...");      
      fileMenu.addSeparator();
      fileMenu.add("Undo");   
      fileMenu.addSeparator();   
      fileMenu.add("Quit");
      fileMenu.addActionListener(this);
      
      Menu action = new Menu("Action Type",true);
      action.add("Dots");
      action.add("Connectors");
      action.add("Choose Path");
      action.addActionListener(this);
      
      MenuBar mb = new MenuBar();

      mb.add(action);
      
      setMenuBar(mb);
      
      canvas = new SimpleDrawCanvasWithFiles();
      add("Center",canvas);
      
      addWindowListener(
            new WindowAdapter() {  // Window listener object closes the window and ends the
                                   //   program when the user clicks the window's close box.
               public void windowClosing(WindowEvent evt) {
                  dispose();
                  System.exit(0);
               }
            }
        );
      
      pack();
      show();

   } // end constructor
   
   public void actionPerformed(ActionEvent evt) {
        // A menu command has bee given by the user.  Respond by calling 
        // the appropriate method in the canvas (except in the case of the
        // Quit command, which is handled by ending the program).

      String command = evt.getActionCommand();

      if (command.equals("Quit")) {
          dispose();  // Close the window, then end the program.
          System.exit(0);
      }      
      else if(command.equals("Dots"))
    	  canvas.setActionDot();
      else if(command.equals("Connectors"))
    	  canvas.setActionConnector();
      else if(command.equals("Choose Path"))
    	  canvas.setActionChoosePath();

   } // end actionPerformed
   

} // end class SimpleDrawApplet




class ColoredLine {  // an object of this class represents a colored line segment

   public static final Color[] colorList = {
              // List of available colors; colors are always indicated as
              // indices into this array.
          Color.black, Color.gray, Color.red, Color.green, Color.blue,
          new Color(200,0,0), new Color(0,180,0), new Color(0,0,180),
          Color.cyan, Color.magenta, Color.yellow, new Color(120,80,20),
          Color.white
       };

   int x1, y1;   // One endpoint of the line segment.
   int x2, y2;   // The other endpoint of the line segment.
   int colorIndex;  // The color of the line segment, given as an index in the colorList array.

} // end class ColoredLine




class SimpleDrawCanvasWithFiles extends Canvas implements MouseListener, MouseMotionListener {
       // A canvas where the user can draw lines in various colors.

   private int currentColorIndex;  // Color that is currently being used for drawing new lines,
                                   // given as an index in the ColoredLine.colorList array.
               
   private boolean connector;
   private boolean drawConnector;
   private boolean choosingPath;
   private int pointCount = 0;
   
   private Point source = null;
   private Point target = null;
   
   private Point start = null;
   private Point end = null;
   
   private HashMap<Point, Integer> nodeSet = new HashMap<Point, Integer>();
   private int nodes = 0;
   
   private int currentBackgroundIndex;  // Current background color, given as an index in the
                                        // ColoredLine.colorList array.
                                   
   private ColoredLine[] lines;    // An array to hold all the lines that have been
                                   //        drawn on the canvas.
   private int lineCount;   // The number of lines that are in the array.

   SimpleDrawCanvasWithFiles() {
         // Construct the canvas, and set it to listen for mouse events.
         // Also create an array to hold the lines that are displayed on
         // the canvas.
      setBackground(Color.white);
      currentColorIndex = 0;
      currentBackgroundIndex = 12;
      lines = new ColoredLine[1000];
      addMouseListener(this);
      addMouseMotionListener(this);
   }
   
   void setActionDot(){
	   System.out.println("Setting false");
	   this.connector = false;
   }
   void setActionConnector(){
	   this.connector = true;
   }
   
   void setActionChoosePath(){
	   this.choosingPath = true;
   }
   
   void setColorIndex(int c) {
         // Set the currentColorIndex, which is used for drawing, to c.
         // For safety, check first that it is in the range of legal indices
         // for the ColoredLine.colorList array.
      if (c >= 0 && c < ColoredLine.colorList.length)
         currentColorIndex = c;
   }

   void setBackgroundIndex(int c) {
         // Set the background color, and redraw the applet using the new background.
      if (c >= 0 && c < ColoredLine.colorList.length) {
         currentBackgroundIndex = c;
         setBackground(ColoredLine.colorList[c]);
         repaint();
      }
   }

   void doClear() {
         // Clear all the lines from the picture.
      if (lineCount > 0) {
         lines = new ColoredLine[1000];
         lineCount = 0;
         repaint();
      }
   }

   void doUndo() { 
        // Remove most recently added line from the picture.
      if (lineCount > 0) {
         lineCount--;
         repaint();
      }
   }
   
   void doSaveToFile(Frame parentFrame) {
        // Save all the data for the current drawing to a file.
        // The file is chosen by the user using a file dialog box.
        // The parentFrame parameter is requuired to open the
        // file dialog.
      
      FileDialog fd;  // A file dialog box that will let the user
                      // specify the output file.
      
      fd = new FileDialog(parentFrame, "Save to File", FileDialog.SAVE);
      fd.show();
      
      String fileName = fd.getFile();  // Get the file name specified by the user.
      
      if (fileName == null)
         return;  // User has canceled.
         
      String directoryName = fd.getDirectory();  // The name of the directory
                                                 //   where the specified file is located.
      
      File file = new File(directoryName, fileName);  // Combine the directory name with the
                                                      //  name to produce a usable file specification.
      
      PrintWriter out;  // Output stream for writing all the data for the current
                        //    drawing to the file.
      
      try {    // Open the file.
         out = new PrintWriter( new FileWriter(file) );
      }
      catch (IOException e) {
//         new MessageDialog(parentFrame, "Error while trying to open file \"" + fileName + "\": " + e.getMessage());
         return;
      }
      
      // Write the data for the drawing to the file...

      out.println(currentBackgroundIndex);         // The index of the current background color.
      out.println(lineCount);                      // The number of lines in the data array.
      for (int i = 0; i < lineCount; i++) {        // Write the data for each indifvidual line.
         out.print(lines[i].x1);
         out.print(" ");
         out.print(lines[i].y1);
         out.print(" ");
         out.print(lines[i].x2);
         out.print(" ");
         out.print(lines[i].y2);
         out.print(" ");
         out.print(lines[i].colorIndex);
         out.println();
      }
      
      out.close();  // Close the output file.
      
      // Note that a PrintWriter never throws an exception. In order to make sure that
      // the date was written successfully, call the PrintWriter's checkError() method.
      // If out.checkError() returns a value of true, then an error occured while writing
      // the data and the user should be informed.
      
      if (out.checkError()){
    	  
      }
//         new MessageDialog(parentFrame,"Some error occured while trying to save data to the file.");
      
   } // end doSaveToFile()

   

   void doLoadFromFile(Frame parentFrame) {
          // Read data for a drawing from a file specified by the user in a file dialog box.
          // Assuming that the data is read successfully, discard the current data and disply
          // the drawing that was read from the file.  If the user cancels or if an error
          // occurs while the data is being read, then the current drawing will NOT be
          // discarded or changed.
          
//        new MessageDialog(parentFrame, "Sorry, loading is not yet implemented.  (That's your job.)");
      
   } // end LoadFromFile()


   public void paint(Graphics g) {
         // Redraw all the lines.
      for (int i = 0; i < lineCount; i++) {
         int c = lines[i].colorIndex;
         g.setColor(ColoredLine.colorList[c]);
         g.drawLine(lines[i].x1,lines[i].y1,lines[i].x2,lines[i].y2);
      }
   }
   
   public Dimension getPreferredSize() {
        // Say what size this canvas wants to be.
      return new Dimension(500,400);
   }
   
   // ------------------------------------------------------------------------------------
   
   // The remainder of the class implements drawing of lines.  While the user
   // is drawing a line, the line is represented by a "rubber band" lines that
   // follows the mouse.  The rubber band line is drawn in XOR mode, which has
   // the property that drawing the same thing twice has no effect.  (That is,
   // the second draw operation undoes the first.)  When the user releases the
   // mouse button, the rubber band line is replaced by a regular line and is
   // added to the array.

   int startX, startY;  // When the user presses the mouse button, the
                        //   location of the mouse is stored in these variables.
   int prevX, prevY;    // The most recent mouse location; a rubber band line has
                        //    been drawn from (startX, startY) to (prevX, prevY).
                        
   boolean dragging = false;  // For safety, this variable is set to true while a
                              // drag operation is in progress.
                              
   Graphics gc;  // While dragging, gc is a graphics context that can be used to 
                 // draw to the canvas.  
   
   boolean inBounds(int mouseX, int mouseY, int width, int height) {
	   for(Point p : nodeSet.keySet()){
		   if((mouseX >= p.getX()) && (mouseY >= p.getY()) && (mouseX < p.getX() + width) && (mouseY < p.getY() + height)){
			   System.out.println("Inbounds!");
			   if(choosingPath){
				  gc = getGraphics();  // Get a graphics context for use while drawing.
			      gc.setColor(Color.RED);
			      gc.fillRect((int)Math.round(p.getX() - 3), (int)Math.round(p.getY() - 3), 7, 7);
			      if(this.start ==null){
					   this.start = p;
				  }else{
					   this.end = p;
				  }
			   }
			   if(this.source == null){
				   this.source = p;
			   }else{
				   this.target = p;
			   }
			   return true;
		   }
			   
	   }
	    return false;
	}
   
   public void computePathFunc(){
	   Point start = this.start;
	   Point end = this.end;
	   
	   Dijkstra dj = new Dijkstra();
	   dj.computePaths(start);
	   ArrayList<Point> path = (ArrayList<Point>) dj.getShortestPath(end);
       System.out.println("Path: " + path);
   }
   

   public void mousePressed(MouseEvent evt) {
//	   System.out.println(connector);
	   startX = evt.getX();
	   startY = evt.getY();
	   if(choosingPath & inBounds(startX, startY, 7,7) && this.pointCount < 2){
		   System.out.println("increment");
		   this.pointCount ++;
	   }
	   if(this.pointCount == 2){
		   
		   System.out.println("GO Calculate!");
		   computePathFunc();
	   }
	   
	   if(connector & inBounds(startX, startY, 7,7)){
		   System.out.println("Success, inside!");
		   
		   prevX = startX;
		   prevY = startY;
		   dragging = true;
//		   gc.drawLine(startX, startY, prevX, prevY);
		   this.drawConnector = true;
	   }else if(connector){
		  System.out.print("in here");

	      
	   }else{
		   //Adding nodes in Dots mode
		  prevX = startX;
		  prevY = startY;
//	      dragging = true;
	      gc = getGraphics();  // Get a graphics context for use while drawing.
	      gc.setColor(ColoredLine.colorList[currentColorIndex]);
	      gc.setXORMode(getBackground());
	      gc.drawLine(startX, startY, prevX, prevY);
	      gc.fillRect(startX - 3, startY - 3, 7, 7);
	      
	      gc.setColor(Color.BLACK);
		  gc.drawString(""+nodes,  startX - 5, startY -5); 
	      
	      //Add this to a list of Nodes
		  Point p = new Point(startX, startY);
	      nodeSet.put(p, nodes++);
	      System.out.println("x: " + startX + " y: "+ startY );
	      
	   }
         // This is called by the system when the user presses the mouse button.
         // Record the location at which the mouse was pressed.  This location
         // is one endpoint of the line that will be drawn when the mouse is
         // released.  This method is part of the MouseLister interface.
      
      
      
   }
   
   public void mouseDragged(MouseEvent evt) {
	   this.dragging = true;
         // This is called by the system when the user moves the mouse while holding
         // down a mouse button.  The previously drawn rubber band line is erased by
         // drawing it a second time, and a new rubber band line is drawn from the
         // start point to the current mouse location.
//	   System.out.println(connector);
      if (!this.drawConnector){
    	  System.out.println("erase");
          gc.drawLine(startX,startY,prevX,prevY);  // Erase the previous line.
          prevX = evt.getX();
          prevY = evt.getY();
          gc.drawLine(startX,startY,prevX,prevY);  // Draw the new line.
    	  // Make sure that the drag operation has been properly started.
      }
      prevX = evt.getX();
      prevY = evt.getY();
      
   }

   public void mouseReleased(MouseEvent evt) {
         // This is called by the system when the user releases the mouse button.
         // The previously drawn rubber band line is erased by drawing it a second
         // time.  Then a permanent line is drawn in the current drawing color,
         // and is added to the array of lines.
      if (inBounds(evt.getX(), evt.getY(), 7,7)){  // Make sure that the drag operation has been properly started.
    	  System.out.println("Release Inbounds");
//    	  gc.drawLine(startX,startY,prevX,prevY); 
    	  int endX = evt.getX();  // Where the mouse was released.
          int endY = evt.getY();
          gc.setPaintMode();
          gc.drawLine(startX, startY, endX, endY);  // Draw the permanent line in regular "paint" mode.
          if(this.connector)
        	  addDistanceLabel(startX, startY, endX, endY);
          if(this.dragging){
        	  Point p = this.source;
        	  Edge e = new Edge(this.target, Double.parseDouble(findLength(startX, startY, endX, endY)));
              this.source.addAdjacency(e);
//              this.nodeSet.put(this.source, this.nodeSet.get(p));
//              this.nodeSet.remove(p);
              
              this.source = null;
              this.target = null;
              
          }
          this.dragging = false;
          this.drawConnector = false;
    	  return;
      }else{
    	  System.out.println("Release Outbounds");
//    	  gc.drawLine(startX,startY,prevX,prevY);  // Erase the previous line.
          gc.dispose();
      }

   } // end mouseReleased

   private void addDistanceLabel(int startX2, int startY2, int endX, int endY) {
	   System.out.println("Added label");
	   gc.setColor(Color.BLACK);
	   
	   gc.drawString(findLength(startX2, startY2, endX, endY),  Math.min(startX2, endX) + findMidX(startX2, endX), Math.min(startY2, endY) + findMidY(startY2, endY)); 
   }

private int findMidY(int startY2, int endY) {
	// TODO Auto-generated method stub
	return (int)(Math.abs(startY2 - endY))/2;
}

private int findMidX(int startX2, int endX) {
	// TODO Auto-generated method stub
	return (int)(Math.abs(startX2 - endX))/2;
}

private String findLength(int startX2, int startY2, int endX, int endY) {
	// TODO Auto-generated method stub
	System.out.println((float) Math.sqrt( Math.pow(startX2 - endX, 2) + Math.pow(startY2 - endY, 2) ));
	return ""+(int) Math.sqrt( Math.pow(startX2 - endX, 2) + Math.pow(startY2 - endY, 2) );
}

public void mouseClicked(MouseEvent evt) { }  // Other methods in the MouseListener interface
   public void mouseEntered(MouseEvent evt) { }
   public void mouseExited(MouseEvent evt) { }
   public void mouseMoved(MouseEvent evt) { }  // Required by the MouseMotionListener interface.

} // end class SimpleDrawCanvas