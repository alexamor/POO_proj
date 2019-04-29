package graphHandler;

public class Edge
{
    int iNode;
    int jNode;
    int weight;
    int pheromoneLevel;

    public Edge (int i_NODE,int j_NODE, int WEIGHT, int PH)
    {
        this.iNode = i_NODE;
        this.jNode = j_NODE;
        this.weight = WEIGHT;
        this.pheromoneLevel = PH;
    }

    public int getiNODE()
    {
        return this.iNode;
    }

    public int getjNode()
    {
        return this.jNode;
    }

    public int getWEIGHT() 
    { 
        return this.weight; 
    }

    public int getPH()
    {
        return this.pheromoneLevel;
    }

    public String toString()
    {
    	return "[EDGE]\n\tiNode: " + this.iNode + 
    			"\n\tjNode: " + this.jNode + 
    			"\n\tWeight: " + this.weight + 
    			"\n\tPheromones: " + this.pheromoneLevel;  
    }

}