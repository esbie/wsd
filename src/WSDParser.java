
import java.util.*;
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
	public ArrayList<Instance> parse(String target){
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
	 * convenience function, calls parse multiple times
	 * @param targets
	 * @return
	 */
	public ArrayList<Instance> parse(String[] targets){
		for(int i=0; i<targets.length; i++){
			parse(targets[i]);
		}
		return examples;
	}
	
	private void parseLexelt(String target, Element lexelt){
		ArrayList<Instance> targetExamples = new ArrayList<Instance>();
		NodeList instances = lexelt.getElementsByTagName("instance");
		for(int i=0; i<instances.getLength(); i++){
			Element instance = (Element) instances.item(i);
			targetExamples.add(parseInstance(target, instance));
		}
		examples.addAll(targetExamples);		
	}

	private Instance parseInstance(String target, Element instance){
		Element context = (Element) instance.getElementsByTagName("context").item(0);
		String id = instance.getAttribute("id");
		return new Instance(
				parseTarget(context), 
				parseAnswers(instance), 
				parseCollocation(context),
				target+".n",
				id,
				parseCooccurrence(context));
	}
	
	private String parseTarget(Element context){
		Element head = (Element) context.getChildNodes().item(1);
		return head.getTextContent().toLowerCase();
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
	
	private ArrayList<String> parseCooccurrence(Element context){
		ArrayList<String> inContext = new ArrayList<String>(); 
		String[] pre = parseTextNode(context.getFirstChild());
		String[] post = parseTextNode(context.getLastChild());
		inContext.addAll(Arrays.asList(pre));
		inContext.addAll(Arrays.asList(post));
		return inContext;
	}
	
	private String[] parseCollocation(Element context){
		String[] pre = parseTextNode(context.getFirstChild());
		String[] post = parseTextNode(context.getLastChild());
		String[] collocation = new String[]{ pre[pre.length-2],
				pre[pre.length-1],
				post[0],
				post[1]};
		return collocation;
	}
	
	private String[] parseTextNode(Node node){
		String text = node.getNodeValue();
		text = text.replaceAll("\\s+\\p{Punct}+\\s", "");
		text = text.trim().toLowerCase();
		String[] textArray = text.split("\\s");
		return textArray;
	}
	
}
