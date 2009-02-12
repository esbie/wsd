class Feature
{
    public String word;
    public Integer offset;
    
    public Feature(String word, Integer offset)
    {
        this.word = word;
        this.offset = offset;
    }
    
    // So we can evaluate the equivalence of stored features
    // and newly created features
    public boolean equals(Object f)
    {
        if (f instanceof Feature) {
            return (word.equals(((Feature)f).word) && offset.equals(((Feature)f).offset));
        }
        return false;
    }
    
    // Equal objects must return equal hashCodes.
    public int hashCode()
    {
        return word.hashCode() + offset.hashCode();
    }
    
    public String toString()
    {
        return word + "_" + offset.toString();
    }
}
