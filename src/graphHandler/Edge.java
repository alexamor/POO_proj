package graphHandler;

public class Edge
{
    int i_NODE;
    int j_NODE;
    int WEIGHT;
    int PH;

    public Edge (int i_NODE,int j_NODE, int WEIGHT, int PH)
    {
        this.i_NODE = i_NODE;
        this.j_NODE = j_NODE;
        this.WEIGHT = WEIGHT;
        this.PH = PH;
    }

    public int getiNODE()
    {
        return this.i_NODE;
    }

    public int getjNode()
    {
        return this.j_NODE;
    }

    public int getWEIGHT() 
    { 
        return this.WEIGHT; 
    }

    public int getPH()
    {
        return this.PH;
    }

    public String toString()
    {
    	return "[EDGE]\n\tiNode: " + this.i_NODE + 
    			"\n\tjNode: " + this.j_NODE + 
    			"\n\tWeight: " + this.WEIGHT + 
    			"\n\tPheromones: " + this.PH;  
    }

}