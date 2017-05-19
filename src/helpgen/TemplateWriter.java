package helpgen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import helpgen.FileReader;
import helpgen.FileWriter;

public class TemplateWriter {
	private FileReader fr;
	private FileWriter fw;
	private String template;
	private String templatefile;
	private String method;
	
	public TemplateWriter(String output, String prefix) {
		fr = new FileReader();
		fw = new FileWriter(output, prefix);
	}

	private void ReadTemplate() {
		try {
			template = fr.readFileAsString(templatefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String ConstrcutBody(ArrayList<String> body) {
		String newbody = "";
		
		for(int i=0; i<body.size(); i++) {
			newbody+="<p>";
			newbody+=body.get(i);
			newbody+="</p>";
		}

		return newbody;
	}

	private void ReplaceTags(ArrayList<String> body, HashMap<String, ArrayList<String>> tags) {
		if(body.size()>0){
			template = template.replace("[body]", ConstrcutBody(body));
		}
		
		
		
		for (Entry<String, ArrayList<String>> entry : tags.entrySet()) {
			//template = template.replace("["+entry.getKey()+"]", entry.getValue());
			for(int i = 0; i<entry.getValue().size(); i++) {
				template = template.replaceFirst("\\[" + entry.getKey() + "\\]", entry.getValue().get(i).replace("$", "&#36;"));	
			}
		}
		
		fw.SetTemplate(template);
		fw.WriteFile(method.toLowerCase() + ".html");
	}

	public void WriteTemplate(String method, ArrayList<String> body, HashMap<String, ArrayList<String>> tags, String template) {
		this.templatefile = template;
		this.method = method;
		
		ReadTemplate();
		
		ReplaceTags(body, tags);
	}
}