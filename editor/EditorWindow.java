package pobj.pinboard.editor;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.Clipboard;
import pobj.pinboard.editor.commands.CommandDelete;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener {
	
	private Board board;
	private Selection selection = new Selection();
	private CommandStack commandeStack = new CommandStack();
	private Tool tool = new ToolRect();
	private Canvas canvas;
	private Color color = Color.BLACK;
	private Clipboard clipboard = Clipboard.getInstance();
	private MenuItem copy, paste, delete, group, ungroup, undo, redo;
	
	public EditorWindow(Stage stage) {
		
		board = new Board();
		
		stage.setTitle("Editeur");
		
		VBox vbox = new VBox();
		
		MenuBar menu = new MenuBar();
		vbox.getChildren().add(menu);
		
		Menu file = new Menu("File");
		MenuItem New = new MenuItem("New");
		MenuItem Close = new MenuItem("Close");
		file.getItems().addAll(New, Close);
		
		Menu edit = new Menu("Edit");
		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		delete = new MenuItem("Delete");
		group = new MenuItem("Group");
		ungroup = new MenuItem("Ungroup");
		undo = new MenuItem("Undo");
		redo = new MenuItem("Redo");
		edit.getItems().addAll(copy, paste, delete, group, ungroup, undo, redo);
		
		Menu tools = new Menu("Tools");
		MenuItem rectangle = new MenuItem("Rectangle");
		MenuItem ell = new MenuItem("Ellipse");
		tools.getItems().addAll(rectangle, ell);
		
		menu.getMenus().addAll(file, edit, tools);
		
		Button box = new Button("Box");
		Button ellipse = new Button("Ellipse");
		Button image = new Button("Image");
		Button select = new Button("Select");
		ToolBar toolBar = new ToolBar(box, ellipse, image, select);
		vbox.getChildren().add(toolBar);
		
		canvas = new Canvas(800, 600);
		vbox.getChildren().add(canvas);
	
		board.draw(canvas.getGraphicsContext2D());
		
		Separator separateur = new Separator();
		vbox.getChildren().add(separateur);
		
		Label label = new Label();
		vbox.getChildren().add(label);
		
		
		New.setOnAction(new EventHandler<ActionEvent>() {
			
		    @Override 
		    public void handle(ActionEvent e) {
		    	new EditorWindow(new Stage());
		    	clipboard.updateListener();
		    }
		});

		Close.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	stage.close();
		    	clipboard.updateListener();
		    }
		});
		
		box.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	tool = new ToolRect();
		    }
		});
		
		rectangle.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	tool = new ToolRect();
		    }
		});
		
		ellipse.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	tool = new ToolEllipse();
		    }
		});
		
		ell.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	tool = new ToolEllipse();
		    }
		});
		
		image.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	FileChooser f = new FileChooser();
				f.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
				File fichier = f.showOpenDialog(stage);
				if(fichier != null) {
					tool = new ToolImage(fichier);
				}
		    }
		});
		
		select.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	tool = new ToolSelection();
		    	clipboard.updateListener();
		    }
		});
		
		copy.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	if(!selection.getContents().isEmpty()) {
		    		clipboard.copyToClipboard(selection.getContents());
		    		clipboard.updateListener();
		    	}
		    }
		});
		
		paste.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	board.addClip(clipboard.copyFromClipboard());
				board.draw(canvas.getGraphicsContext2D());
		    }
		});
		
		delete.setOnAction((e)-> {
			CommandDelete comDel = new CommandDelete(board, canvas, selection);
			comDel.execute();
			commandeStack.addCommand(comDel);
		});
		
		group.setOnAction((e)-> {
			CommandGroup comGr = new CommandGroup(this, selection.getContents());
			comGr.execute();
			commandeStack.addCommand(comGr);
		});
		
		ungroup.setOnAction((e)-> {
			for(Clip clip : selection.getContents())
				if(clip instanceof ClipGroup ) {
					CommandUngroup comUn = new CommandUngroup(this, (ClipGroup) clip);
					comUn.execute();
					commandeStack.addCommand(comUn);
				}
		});
		
		undo.setOnAction((e) -> {
			commandeStack.undo();
			clipboard.updateListener();
			board.draw(canvas.getGraphicsContext2D());
			
		});
		
		redo.setOnAction((e) -> {
			commandeStack.redo();
			clipboard.updateListener();
			board.draw(canvas.getGraphicsContext2D());
				
		});
		
		canvas.setOnMousePressed((e) -> {
			this.tool.press(this, e);
		});
		
		canvas.setOnMouseDragged((e) -> {
			this.tool.drag(this, e);
			board.draw(canvas.getGraphicsContext2D());
			this.tool.drawFeedback(this, canvas.getGraphicsContext2D());
		});

		canvas.setOnMouseReleased((e) -> {
			this.tool.release(this, e);
			this.tool.drawFeedback(this, canvas.getGraphicsContext2D());
			this.board.draw(canvas.getGraphicsContext2D());
		});
		
		clipboard.addListener(this);
		clipboardChanged();
		
		stage.setScene(new javafx.scene.Scene(vbox));
		stage.show();
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Selection getSelection() {
		return selection;
	}

	@Override
	public CommandStack getUndoStack() {
		return commandeStack;
	}

	@Override
	public void clipboardChanged() {
		
		if(clipboard.isEmpty())
			paste.setDisable(true);
		
		else
			paste.setDisable(false);
		
		if(commandeStack.isUndoEmpty())
			undo.setDisable(true);
		
		else
			undo.setDisable(false);

		if(commandeStack.isRedoEmpty())
			redo.setDisable(true);
		
		else
			redo.setDisable(false);
	}

}
