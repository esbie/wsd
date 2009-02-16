import java.util.ArrayList;

public class NaiveBayesTester
{
    public ArrayList<Instance> instances;
    public NaiveBayes trainer;
    
    public NaiveBayesTester(ArrayList<Instance> instances, NaiveBayes trainer)
    {
        this.instances = instances;
        this.trainer = trainer;
    }
    
    public void test()
    {
        for (Instance instance : instances) {
            String senseAnswer = "";
            Double bestProb = 0.0;
            
            for (String sense : trainer.headwordSenseMap.get(instance.headword())) {
                Double curProb = 1.0;
                curProb *= trainer.probability(sense);
                if (trainer.USE_COLLOCATION) {
                    for (int i = 0; i < 4; i++) {
                        curProb *= trainer.probability(new Feature(instance.collocation[i], i-2), sense);
                    }
                }
                if (trainer.USE_COOCCURRENCE) {
                    for (String cooccur : instance.word_set) {
                        curProb *= trainer.probability(new Feature(cooccur), sense);
                    }
                }
                if (curProb > bestProb) {
                    bestProb = curProb;
                    senseAnswer = sense;
                }
            }
            
            System.out.println(instance.lexelt + " " + instance.instance_id + " " + senseAnswer);
        }
    }
}