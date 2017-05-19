package helpgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helpgen.Tags;
import helpgen.FileReader;
import helpgen.TemplateWriter;

public class Parser {
	private Tags tl;
	private FileReader fr;
	private TemplateWriter tw;
	private boolean inblock = false;
	private boolean afterend = false;
	private ArrayList<String> body;
	private HashMap<String, ArrayList<String>> tags;
	private String template;
	
	public Parser(String template, String output, String prefix) {
		this.template = template;
		tl = new Tags();
		fr = new FileReader();
		tw = new TemplateWriter(output, prefix);
		body = new ArrayList<String>();
		tags = new HashMap<String, ArrayList<String>>();
		
	}

	private void BlockStart() {
		inblock = true;
	}

	private void BlockEnd() {
		inblock = false;
		afterend = true;
	}

	private void ParseLine(String line)
	{
		if(line.startsWith("@")) {
			TagFound(line.replaceFirst("@", "").trim());
			return;
		}
		
		BodyFound(line);
	}

	private void TagFound(String line) {
		String key = line.substring(0, line.indexOf(" ")).trim();
		if(!tl.TagExist(key)) {
			return;
		}
		String val = line.substring(line.indexOf(" ")+1).trim();
		if(!tags.containsKey(key))
		{
			tags.put(key, new ArrayList<String>());
		}
		tags.get(key).add(val);
	}
	
	private void BodyFound(String line) {
		body.add(line);
	}
	
	private void MethodFound(String line) {
		afterend = false;
		
		line = line.replaceFirst("function", "").trim();
		
		String method = line.substring(0, line.indexOf('(')).toLowerCase();
		
		//System.out.println(tags.toString());
		
		tw.WriteTemplate(method, body, tags, template);
		
		ClearVars();
	}
	
	private void ClearVars() {
		body.clear();
		tags.clear();
	}
	
	public void Parse(String path)
	{
		if(!fr.ReadFile(path)) {
			System.out.println("Can't read the file");
			return;
		}
		while(fr.CanRead()) {
			String line = fr.GetNextLine().trim();		
			
			if(line.equals("/**") && !inblock) {
				BlockStart();				
				continue;
			}
			
			if(line.equals("*/") && inblock) {
				BlockEnd();
				continue;
			}
			
			if(line.startsWith("*") && inblock) {
				ParseLine(line.replaceFirst("\\*", "").trim());
				continue;
			}
			
			if(line.startsWith("public") && afterend) {
				MethodFound(line.replaceFirst("public", "").trim());
				continue;
			}
		}
	}
}