package core.util;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<State>
{
	private List<State> states = new ArrayList<State>();
	private int selectedStateIndex = -1;
	
	public StateMachine() {}
	public StateMachine(final List<State> states) 
	{
		this.states = states;
	}
	
	public void addState(final State state)
	{
		this.states.add(state);
	}
	public void removeState(final State state)
	{
		this.states.remove(state);
	}
	public void removeState(final int stateIndex)
	{
		this.states.remove(stateIndex);
	}
	
	public void selectState(final State state)
	{
		this.selectedStateIndex = this.states.indexOf(state);
	}
	public void selectState(final int stateIndex)
	{
		this.selectedStateIndex = stateIndex;
	}
	public State getSelectedState()
	{
		//If nothing is selected
		if(this.selectedStateIndex < 0 || this.selectedStateIndex >= this.states.size())
			return null;
		
		return this.states.get(this.selectedStateIndex);
	}
	public int getSelectedStateIndex()
	{
		return this.selectedStateIndex;
	}
	
	public List<State> getStates()
	{
		return this.states;
	}
}
