import java.util.ArrayList;

public class test {

	public static String trainingFile = "training/EnglishLStrain.xml";
	
	public static void main(String[] args){
		WSDParser parser = new WSDParser(trainingFile);
		ArrayList<Instance> x = parser.parseTarget("argument");
		Instance i = x.get(4);
		System.out.println("|" + i.collocation[0] + "|" + i.collocation[1] + 
				"|" + i.target + "|" + i.collocation[2] + "|" + i.collocation[3] + "|");
	}
}
