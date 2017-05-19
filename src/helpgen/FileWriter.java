package helpgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {	
	private File file;
	private String template;
	private String output;
	private String prefix;
	
	public FileWriter(String output, String prefix) {
		this.output = output;
		this.prefix = prefix;
	}
	
	public void WriteFile(String path) {
		file = new File(output + '\\' + prefix + path);

		FileOutputStream fop = null;
		
		try {
			fop=new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		if(file.exists()){
			try {
				fop.write(template.getBytes());
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally {
				try {
					fop.flush();
					fop.close();
				} catch (IOException e) {
					e.printStackTrace();
				}   
			}
		}
	}
	
	public void SetTemplate(String template) {
		this.template = template;
	}
}
