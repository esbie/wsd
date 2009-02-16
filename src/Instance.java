import java.util.ArrayList;

public class Instance {

	public String target;
	public String[] senseids;
	public String[] collocation;
	public String lexelt;
	public String instance_id;
	public ArrayList<String> word_set;

	public Instance(String target, String[] senseids, String[] collocation){
		this.target = target;
		this.senseids = senseids;
		this.collocation = collocation;
		this.lexelt = "";
		this.instance_id = "";
	}
	
	public Instance(String target, String[] senseids, String[] collocation, String lexelt, String instance_id){
	    this.target = target;
	    this.senseids = senseids;
	    this.collocation = collocation;
	    this.lexelt = lexelt;
	    this.instance_id = instance_id;
	}
	
	public Instance(String target, String[] senseids, String[] collocation, String lexelt, String instance_id, ArrayList<String> word_set){
	    this.target = target;
	    this.senseids = senseids;
	    this.collocation = collocation;
	    this.lexelt = lexelt;
	    this.instance_id = instance_id;
	    this.word_set = word_set;
	}
	
	public String headword(){
	    return lexelt.substring(0, lexelt.length()-2);
	}
}
