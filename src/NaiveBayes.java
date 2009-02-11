import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NaiveBayes
{
    // hash { feature => hash { sense# => count } } = count for feature f for each sense
    private HashMap<Feature, HashMap<Integer, Integer>> countForFeatureForSense;
    // hash { head word => hash { sense# => count } } = count for # of times a sense occurs for a head word
    private HashMap<String, HashMap<Integer, Integer>> countForSenseForHeadWord;
    // hash { head word => count } = count for # of times a head word occurs
    private HashMap<String, Integer> countForHeadWord;
    
    public NaiveBayes(ArrayList<Instance>)
    {
        trainingDoc = doc;
        countForContextWordForSense = new HashMap<String, HashMap<Integer, Integer>>();
        numberOfContextWordsForSense = new HashMap<Integer, Integer>();
        numberOfContextWordsForHeadWord = new HashMap<String, Integer>();
        countForSenseForHeadWord = new HashMap<String, HashMap<Integer, Integer>>();
        countForHeadWord = new HashMap<String, Integer>();
    }
    
    
}

class Feature
{
    public String word;
    public Integer offset;
    
    public Feature(String w, Integer o)
    {
        word = w;
        offset = o;
    }
    
    public boolean equals(Object f)
    {
        if (f instanceof Feature) {
            return (word.equals(f.word) && offset.equals(f.offset));
        }
        return false;
    }
    
    // Equal objects must return equal hashCodes.
    public int hashCode()
    {
        return word.hashCode() + offset.hashCode();
    }
}