package helpgen;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {
	private File file;
	private int lines = 0;
	private String error = "";
	private ArrayList<String> data;
	private int pointer = 0;

	public boolean ReadFile(String path) {
		file = new File(path);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			error = "File Not Found";
			return false;
		}
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
		String line;
		
		data = new ArrayList<String>();
		
		try {
			while((line = buf.readLine()) != null) {
				data.add(line);
				lines++;
			}
			
			buf.close();
		} catch (IOException e) {			
			error = e.toString();
			try {
				buf.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
			
		
		error = "";
		return true;
	}

	public  String readFileAsString(String filePath) throws java.io.IOException{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
	    f.read(buffer);
	    return new String(buffer);
	}

	public String LastError() {
		return error;
	}

	public int NumLines() {
		return lines;
	}

	public String GetLine(int line) throws ArrayIndexOutOfBoundsException {
		if(CanRead(line)) {
			return data.get(line);
		} else {
			 throw new ArrayIndexOutOfBoundsException();			
		}
	}

	public void ResetPointer() {
		pointer = 0;
	}

	public String GetNextLine() {
		return GetLine(pointer++);
	}

	private boolean CanRead(int line) {
		if(line < data.size() && line >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean CanRead() {
		return CanRead(pointer);
	}
	
	public void Reset() {
		lines = 0;
		error = "";
		data.clear();
		pointer = 0;
	}
}