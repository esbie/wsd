import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NaiveBayes
{
    public ArrayList<Instance> instances;
    // hash { feature => hash { sense# => count } } = count for feature f for each sense
    public HashMap<Feature, HashMap<Integer, Integer>> countForFeatureForSense;
    // hash { head word => hash { sense# => count } } = count for # of times a sense occurs for a head word
    public HashMap<String, HashMap<Integer, Integer>> countForSenseForHeadWord;
    // hash { head word => count } = count for # of times a head word occurs
    public HashMap<String, Integer> countForHeadWord;
    
    public NaiveBayes(ArrayList<Instance> instances)
    {
        countForFeatureForSense = new HashMap<Feature, HashMap<Integer, Integer>>();
        countForSenseForHeadWord = new HashMap<String, HashMap<Integer, Integer>>();
        countForHeadWord = new HashMap<String, Integer>();
        this.instances = instances;
    }
    
    public void train()
    {
        for (Instance instance : instances) {
            // Update countForFeatureForSense
            for (int i = 0; i < 4; i++) {
                if (!instance.collocation[i].equals("")) {
                    Feature feature = new Feature(instance.collocation[i], i-2);
                    if (countForFeatureForSense.containsKey(feature)) {
                        HashMap<Integer, Integer> countForSense = countForFeatureForSense.get(feature);
                        for (int senseid : instance.senseids) {
                            if (countForSense.containsKey(senseid)) {
                                countForSense.put(senseid, countForSense.get(senseid)+1);
                            } else {
                                countForSense.put(senseid, 1);
                            }
                        }
                    } else {
                        HashMap<Integer, Integer> countForSense = new HashMap<Integer, Integer>();
                        for (int senseid : instance.senseids) {
                            countForSense.put(senseid, 1);
                        }
                        countForFeatureForSense.put(feature, countForSense);
                    }
                }
            }
            
            // Update countForSenseForHeadWord
            if (!countForSenseForHeadWord.containsKey(instance.target)) {
                countForSenseForHeadWord.put(instance.target, new HashMap<Integer, Integer>());
            }
            HashMap<Integer, Integer> countForSense = countForSenseForHeadWord.get(instance.target);
            for (int senseid : instance.senseids) {
                if (countForSense.containsKey(senseid)) {
                    countForSense.put(senseid, countForSense.get(senseid)+1);
                } else {
                    countForSense.put(senseid, 1);
                }
            }
            
            // Update countForHeadWord
            if (countForHeadWord.containsKey(instance.target)) {
                countForHeadWord.put(instance.target, countForHeadWord.get(instance.target)+instance.senseids.length);
            } else {
                countForHeadWord.put(instance.target, instance.senseids.length);
            }
        }
    }
}
