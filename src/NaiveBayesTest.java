import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayesTest
{
    private static String[] strings(String... strings)
    {
        return strings;
    }
    
    public static void main(String[] args)
    {
        ArrayList<Instance> instances = new ArrayList<Instance>();
        instances.add(new Instance("bass", strings("fish"), strings("freshwater", "sea", "are", "becoming")));
        instances.add(new Instance("bass", strings("music"), strings("guitar", "and", "player", "were")));
        instances.add(new Instance("bass", strings("fish"), strings("did", "some", "fishing", "today")));
        instances.add(new Instance("tree", strings("plant"), strings("on", "a", "branch", "")));
        NaiveBayes nb = new NaiveBayes(instances);
        nb.train();
        
        for (Feature feature : nb.probFeatureGivenSense.keySet()) {
            HashMap<String, Double> senseMap = nb.probFeatureGivenSense.get(feature);
            for (String sense : senseMap.keySet()) {
                System.out.println("P(" + feature + " | " + sense + ") = " + nb.probability(feature, sense));
            }
        }
        System.out.println("P(did_-2 | music) = " + nb.probability(new Feature("did", -2), "music"));
        System.out.println("P(foobar_1 | fish) = " + nb.probability(new Feature("foobar", 1), "fish"));
        System.out.println("---");
        for (String sense : nb.probSense.keySet()) {
            System.out.println("P(" + sense + ") = " + nb.probability(sense));
        }
        System.out.println("---");
        System.out.println("Sense fish for bass " + (nb.senseOccurredInTraining("bass", "fish")?"occurred.":"did not occur."));
        System.out.println("Sense foobar for bass " + (nb.senseOccurredInTraining("bass", "foobar")?"occurred.":"did not occur."));
        
        instances.add(new Instance("bass", strings("??"), strings("freshwater", "sea", "player", ""), "bass.n", "bass.n.42", null));
        instances.add(new Instance("bass", strings("???"), strings("never", "seen", "before", ""), "bass.n", "bass.n.43", null));
        NaiveBayesTester nbt = new NaiveBayesTester(instances, nb);
        nbt.test();
    }
}