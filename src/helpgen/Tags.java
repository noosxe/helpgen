package helpgen;

import java.util.ArrayList;

public class Tags {
	private ArrayList<String> taglist;
	
	public Tags() {
		taglist = new ArrayList<String>();
		taglist.add("abstract");
		taglist.add("access");
		taglist.add("author");
		taglist.add("category");
		taglist.add("copyright");
		taglist.add("deprecated");
		taglist.add("example");
		taglist.add("final");
		taglist.add("filesource");
		taglist.add("global");
		taglist.add("ignore");
		taglist.add("internal");
		taglist.add("license");
		taglist.add("link");
		taglist.add("method");
		taglist.add("name");
		taglist.add("package");
		taglist.add("param");
		taglist.add("property");
		taglist.add("return");
		taglist.add("see");
		taglist.add("since");
		taglist.add("static");
		taglist.add("staticvar");
		taglist.add("subpackage");
		taglist.add("todo");
		taglist.add("tutorial");
		taglist.add("uses");
		taglist.add("var");
		taglist.add("version");
	}
	
	public boolean TagExist(String tagname) {
		return taglist.contains(tagname);
	}
}
