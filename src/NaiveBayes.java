import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayes
{
    public ArrayList<Instance> instances;
    
    // hash { feature => hash { sense# => count } } = count for feature f for each sense
    public HashMap<Feature, HashMap<String, Integer>> countForFeatureForSense;
    // hash { head word => hash { sense# => count } } = count for # of times a sense occurs for a head word
    public HashMap<String, Integer> countForSense;
    // hash { head word => count } = count for # of times a head word occurs
    public HashMap<String, Integer> countForHeadWord;
    
    public HashMap<String, ArrayList<String>> headwordSenseMap;
    
    public HashMap<Feature, HashMap<String, Double>> probFeatureGivenSense;
    public HashMap<String, Double> probSense;
    public HashMap<String, Double> defaultProbGivenSense;
    
    public NaiveBayes(ArrayList<Instance> instances)
    {
        countForFeatureForSense = new HashMap<Feature, HashMap<String, Integer>>();
        countForSense = new HashMap<String, Integer>();
        countForHeadWord = new HashMap<String, Integer>();
        
        headwordSenseMap = new HashMap<String, ArrayList<String>>();
        
        probFeatureGivenSense = new HashMap<Feature, HashMap<String, Double>>();
        probSense = new HashMap<String, Double>();
        defaultProbGivenSense = new HashMap<String, Double>();
        
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
                        HashMap<String, Integer> senseMap = countForFeatureForSense.get(feature);
                        for (String senseid : instance.senseids) {
                            if (senseMap.containsKey(senseid)) {
                                senseMap.put(senseid, senseMap.get(senseid)+1);
                            } else {
                                senseMap.put(senseid, 1);
                            }
                        }
                    } else {
                        HashMap<String, Integer> senseMap = new HashMap<String, Integer>();
                        for (String senseid : instance.senseids) {
                            senseMap.put(senseid, 1);
                        }
                        countForFeatureForSense.put(feature, senseMap);
                    }
                }
            }
            
            // Update countForSense
            for (String senseid : instance.senseids) {
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
            
            // Update headwordSenseMap
            if (!headwordSenseMap.containsKey(instance.target)) {
                headwordSenseMap.put(instance.target, new ArrayList<String>());
            }
            ArrayList<String> senses = headwordSenseMap.get(instance.target);
            for (String senseid : instance.senseids) {
                if (!senses.contains(senseid)) {
                    senses.add(senseid);
                }
            }
        }
        
        formulateProbabilites();
    }
    
    private void formulateProbabilites()
    {
        for (Feature feature : countForFeatureForSense.keySet()) {
            HashMap<String, Integer> senseMap = countForFeatureForSense.get(feature);
            for (String sense : senseMap.keySet()) {
                int count = senseMap.get(sense);
                if (!probFeatureGivenSense.containsKey(feature)) {
                    probFeatureGivenSense.put(feature, new HashMap<String, Double>());
                }
                HashMap<String, Double> probSenseMap = probFeatureGivenSense.get(feature);
                probSenseMap.put(sense, ((double)count)/((double)(countForSense.get(sense))));
            }
        }
        
        for (String headword : headwordSenseMap.keySet()) {
            for (String sense : headwordSenseMap.get(headword)) {
                probSense.put(sense, ((double)countForSense.get(sense))/((double)countForHeadWord.get(headword)));
                defaultProbGivenSense.put(sense, 1.0/((double)countForHeadWord.get(headword)));
            }
        }
    }
    
    public Double probability(Feature feature, String sense)
    {
        if (probFeatureGivenSense.containsKey(feature)) {
            if (probFeatureGivenSense.get(feature).containsKey(sense)) {
                return probFeatureGivenSense.get(feature).get(sense);
            } else {
                return defaultProbGivenSense.get(sense);
            }
        } else {
            return defaultProbGivenSense.get(sense);
        }
    }
    
    public Double probability(String sense)
    {
        return probSense.get(sense);
    }
    
    public boolean senseOccurredInTraining(String headword, String sense)
    {
        return headwordSenseMap.containsKey(headword) && headwordSenseMap.get(headword).contains(sense);
    }
}
