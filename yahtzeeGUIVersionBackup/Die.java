package yahtzeeGUIVersionBackup;

public class Die {
	private int value;
	private boolean selected;
	private final int SIDES_ON_DICE;
	
	/**
	 * constructs a die object
	 */
	public Die(){
		SIDES_ON_DICE = 6;
		value=(int) (Math.random()*SIDES_ON_DICE)+1;
		selected=false;
	}
	//tester for value
	public Die(int number){
		SIDES_ON_DICE = 6;
		value=number;
		selected=false;
	}
	/**
	 * returns the value of the die
	 * @return value random int from 1-SIDES_ON_DICE
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * selects and unselects the die
	 * @param select a boolean that is true if selected, false otherwise
	 */
	public void setSelect(boolean select){
		selected=select;
	}
	
	/**
	 * returns whether or not the die is selected
	 * @return selected a boolean that is true only if it is selected
	 */
	public boolean isSelected(){
		return selected;
	}
	
	/**
	 * "rolls the die"
	 *  assigns the die a new value from 1 to SIDES_ON_DICE
	 */
	public void roll(){
		value=(int) (Math.random()*SIDES_ON_DICE)+1;
	}
}