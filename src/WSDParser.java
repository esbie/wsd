
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class WSDParser {
	
	public Element rootElement;
	public ArrayList<Instance> examples = new ArrayList<Instance>();

	public WSDParser(String filename){
		//initializing the xml parser
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(filename);
			rootElement = doc.getDocumentElement();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * returns an Instance array for the specified target word
	 * @param target should be an all lower case String, POS (".n") omitted
	 * @return
	 */
	public ArrayList<Instance> parseTarget(String target){
		//here we assume that we are parsing only nouns
		//and that there is only 1 lexelt per target
		NodeList lexelts = rootElement.getElementsByTagName("lexelt");
		for(int i = 0; i<lexelts.getLength(); i++){
			Element lexelt = (Element) lexelts.item(i);
			if(lexelt.getAttribute("item").equals(target+".n")){
				parseLexelt(target, lexelt);
			}
		}
		return examples;
	}
	
	/**
	 * convenience function, calls parseTarget multiple times
	 * @param targets
	 * @return
	 */
	public ArrayList<Instance> parseTargets(String[] targets){
		for(int i=0; i<targets.length; i++){
			parseTarget(targets[i]);
		}
		return examples;
	}
	
	private void parseLexelt(String target, Element lexelt){
		NodeList instances = lexelt.getElementsByTagName("instance");
		for(int i=0; i<instances.getLength(); i++){
			Element instance = (Element) instances.item(i);
			examples.add(parseInstance(target, instance));
		}
	}
	
	private Instance parseInstance(String target, Element instance){
		Element context = (Element) instance.getElementsByTagName("context").item(0);
		return new Instance(target, parseAnswers(instance), parseCollocation(context));
	}
	
	private String[] parseAnswers(Element instance){
		NodeList answers = instance.getElementsByTagName("answer");
		String[] senseids = new String[answers.getLength()];
		for(int i=0; i<senseids.length; i++){
			Element answer = (Element) answers.item(i);
			senseids[i] = answer.getAttribute("senseid");
		}
		return senseids;
	}
	
	private String[] parseCollocation(Element context){
		String preContext = context.getFirstChild().getNodeValue();
		String postContext = context.getLastChild().getNodeValue();
		preContext = preContext.trim().toLowerCase();
		postContext = postContext.trim().toLowerCase();
		String[] pre = preContext.split("\\s");
		String[] post = postContext.split("\\s");
		//getting rid of non alphanumeric characters
		int second = pre.length-1, third = 0;
		while(!pre[second].matches("\\p{Alnum}+")){
			second--;
		}
		int first = second - 1;
		while(!pre[first].matches("\\p{Alnum}+")){
			first++;
		}
		while(!post[third].matches("\\p{Alnum}+")){
			third++;
		}
		int fourth = third + 1;
		while(!post[fourth].matches("\\p{Alnum}+")){
			fourth++;
		}
		String[] collocation = new String[]{ pre[first],
				pre[second],
				post[third],
				post[fourth]};
		return collocation;
	}
}
