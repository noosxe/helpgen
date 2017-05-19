package helpgen;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import helpgen.Parser;

public class MainForm {
	private static Shell shell;
	private static Button browsebtn;
	private static Button browsebtn2;
	private static Button browsebtn3;
	private static Button startbtn;
	private static Text inputtext;
	private static Text outputtext;
	private static Text templatename;
	private static Text prefixtext;
	
	private static String inputfolder = "";
	private static String outputfolder = "";
	private static String template = "";
	
	public static void main(String[] args) {
		Display display = new Display ();
	    shell = new Shell (display, SWT.CLOSE | SWT.TITLE | SWT.MIN );
	    shell.setText("phpdoc converter");
	    GridLayout gridLayout = new GridLayout(2, false);
	    shell.setLayout(gridLayout);

	    // browse button
	    
        browsebtn = new Button(shell, SWT.FLAT);
        browsebtn.setText("Browse for input folder");
        browsebtn.addMouseListener(new MouseListener() {	
			@Override
			public void mouseUp(MouseEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(shell);
				String folder = dlg.open();
				
				if(folder != null) {
					inputfolder = folder;
					inputtext.setText(folder);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
        
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 150;
        browsebtn.setLayoutData(gridData);
        
        // input text input
        
        inputtext = new Text(shell, SWT.SINGLE | SWT.BORDER);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 200;    
        inputtext.setLayoutData(gridData);
        
        //browse button 2
        
        browsebtn2 = new Button(shell, SWT.FLAT);
        browsebtn2.setText("Browse for output folder");
        browsebtn2.addMouseListener(new MouseListener() {	
			@Override
			public void mouseUp(MouseEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(shell);
				String folder = dlg.open();
				
				if(folder != null) {
					outputfolder = folder;
					outputtext.setText(folder);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 150;
        browsebtn2.setLayoutData(gridData);
        
        // output text 
        
        outputtext = new Text(shell, SWT.SINGLE | SWT.BORDER);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 200;
        
        outputtext.setLayoutData(gridData);
        
        // browse button 3
        
        browsebtn3 = new Button(shell, SWT.FLAT);
        browsebtn3.setText("Select template");
        browsebtn3.addMouseListener(new MouseListener() {	
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog dlg = new FileDialog(shell);
				String file = dlg.open();
				
				if(file != null) {
					template = file;
					templatename.setText(file);
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 150;
        browsebtn3.setLayoutData(gridData);
        
        // template text
        
        templatename = new Text(shell, SWT.SINGLE | SWT.BORDER);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 200;
        
        templatename.setLayoutData(gridData);
        
        // prefix label
        
        Label lab = new Label(shell, SWT.CENTER);
        lab.setText("Filename prefix");
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 150;        
        lab.setLayoutData(gridData);
        
        // prefix text
        
        prefixtext = new Text(shell, SWT.SINGLE | SWT.BORDER);
        
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 200;
        
        prefixtext.setLayoutData(gridData);
        
        // start button
        
        startbtn = new Button(shell, SWT.FLAT);
        startbtn.setText("Start conversion");
        startbtn.addMouseListener(new MouseListener() {	
			@Override
			public void mouseUp(MouseEvent e) {
				inputfolder = inputtext.getText();
				outputfolder = outputtext.getText();
				template = templatename.getText();
				
				File in = new File(inputfolder);
				File out = new File(outputfolder);
				File templ = new File(template);
				
				if(in.exists() && out.exists() && templ.exists()) {
					ProcessDir();
				} else {
					MessageBox mb= new MessageBox(shell, SWT.ICON_WARNING); 
					mb.setText("Warning");
					mb.setMessage("Some of inputs are not satisfied");
					mb.open();
				}
			}
			@Override
			public void mouseDown(MouseEvent e) {}
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.minimumWidth = 150;
        startbtn.setLayoutData(gridData);

        shell.pack();
		shell.open();

	    while (!shell.isDisposed()) {
	       if (!display.readAndDispatch())display.sleep();
	    }
	    display.dispose ();
	}
	
	private static void ProcessDir() {
		File dir = new File(inputfolder);
		
		File[] files = dir.listFiles();
		if (files == null) {
			
		} else {
		    for (int i=0; i<files.length; i++) {
		        
		        if(files[i].isFile()) {
		        	Parser ps = new Parser(template, outputfolder, prefixtext.getText() + dir.getName() + '.');
		        	ps.Parse(files[i].getAbsolutePath());
		        }
		    }
		    
		    MessageBox mb= new MessageBox(shell, SWT.ICON_INFORMATION); 
			mb.setText("Info");
			mb.setMessage("Conversion complete");
			mb.open();
		}
	}
}
