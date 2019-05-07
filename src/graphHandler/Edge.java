package graphHandler;

public class Edge
{
    protected int iNode;
    protected int jNode;
    protected int weight;

    public Edge (int iNode,int jNode, int weight)
    {
        this.iNode = iNode;
        this.jNode = jNode;
        this.setWeight(weight);
    }
    
    /**
     * 
     * @return primeiro nó da aresta
     */
    public int getiNode()
    {
        return this.iNode;
    }
    
    /**
     * 
     * @return segundo nó da aresta
     */
    public int getjNode()
    {
        return this.jNode;
    }

    public String toString()
    {
    	return "[EDGE]\n\tiNode: " + this.iNode + 
    			"\n\tjNode: " + this.jNode + 
    			"\n\tWeight: " + this.getWeight();  
    }
    
    /**
     * 
     * @return peso da aresta
     */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * 
	 * @param weight - peso da aresta
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

}