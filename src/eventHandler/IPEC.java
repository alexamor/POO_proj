package eventHandler;

public interface IPEC {
	/**
	 * 
	 * @param ev - evento a adicionar
	 */
	public void addEvent(Object ev);
	/**
	 * 
	 * @return evento removido do topo da pilha
	 */
	public Object removeEvent();

}
