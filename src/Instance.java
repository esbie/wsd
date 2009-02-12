
public class Instance {

	public String target;
	public String[] senseids;
	public String[] collocation;
	public String lexelt;
	public String instance_id;

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
}
