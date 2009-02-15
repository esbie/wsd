import java.util.ArrayList;

public class WSD
{
    private static String[] strings(String... strings)
    {
        return strings;
    }
    
    public static void main(String[] args)
    {
        if (args.length < 2) {
            System.out.println("Need training and test file as arguments.");
        }
        String trainFile = args[0];
        String testFile = args[1];
        
        WSDParser trainParser = new WSDParser(trainFile);
        ArrayList<Instance> trainInstances = trainParser.parse(strings("audience", "disc", "plan", "shelter"));
        
        NaiveBayes naivebayesTrainer = new NaiveBayes(trainInstances);
        naivebayesTrainer.train();
        
        WSDParser testParser = new WSDParser(testFile);
        ArrayList<Instance> testInstances = testParser.parse(strings("audience", "disc", "plan", "shelter"));
        
        NaiveBayesTester naivebayesTester = new NaiveBayesTester(testInstances, naivebayesTrainer);
        naivebayesTester.test();
    }
}