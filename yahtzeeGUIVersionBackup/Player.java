package yahtzeeGUIVersionBackup;

public class Player {

	private String name;
	private int[] upperSection = new int[6];
	private int[] lowerSection = new int[7];
	private int upperSubTotal, upperTotal, lowerTotal, grandTotal;
	private static final int UPPERBONUS = 35;
	private boolean[] allowedActions = new boolean[14];

	/**
	 * constructs a new player object
	 * upper and lower section values default to negative 1 because 0 is an accepted value
	 * @param name is the playerID name
	 */
	public Player(String name) {
	
		this.name = name;

		for (int i = 0; i < upperSection.length; i++)
			upperSection[i] = -1;

		for (int i = 0; i < lowerSection.length; i++)
			lowerSection[i] = -1;

		for (int i = 0; i < allowedActions.length; i++)
			allowedActions[i] = true;

		upperSubTotal = 0;
		upperTotal = 0;
		lowerTotal = 0;
	}
	/**
	 * Sets the name of the player
	 * @param name the desired name for the player
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * adds score to the correlating part of the upper/lower section array
	 * @param category which section the player has chosen
	 * @param score how many points they won in the section
	 */
	public void addScore(String category, int score) {
		if (category.equalsIgnoreCase("1")) {
			upperSection[0] = score;
		} else if (category.equalsIgnoreCase("2")) {
			upperSection[1] = score;
		} else if (category.equalsIgnoreCase("3")) {
			upperSection[2] = score;
		} else if (category.equalsIgnoreCase("4")) {
			upperSection[3] = score;
		} else if (category.equalsIgnoreCase("5")) {
			upperSection[4] = score;
		} else if (category.equalsIgnoreCase("6")) {
			upperSection[5] = score;
		}

		else if (category.equalsIgnoreCase("3Kind")) {
			lowerSection[0] = score;
		} else if (category.equalsIgnoreCase("4Kind")) {
			lowerSection[1] = score;
		} else if (category.equalsIgnoreCase("FullHouse")) {
			lowerSection[2] = score;
		} else if (category.equalsIgnoreCase("SStraight")) {
			lowerSection[3] = score;
		} else if (category.equalsIgnoreCase("LStraight")) {
			lowerSection[4] = score;
		} else if (category.equalsIgnoreCase("Chance")) {
			lowerSection[5] = score;
		} else if (category.equalsIgnoreCase("Yahtzee")) {
			lowerSection[6] = score;
		}

	}

	/**
	 * gets the scores from upper and lower Section to get a sum of the points
	 * @return sum the entire score
	 */
	public int currentScore() {
		int sum = 0;

		for (int i : upperSection)
			sum += i;
		for (int i : lowerSection)
			sum += i;

		return sum;
	}

	/**
	 * returns the name which was set in the constructor
	 * @return name a string name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns the sections you are allowed to use
	 * @return allowedActions a boolean array that is true if is allowed, otherwise false
	 */
	public boolean[] getAllowedActions() {
		return allowedActions;
	}

	/**
	 * the action is set to allowed or not
	 * @param index the integer correlating to a specific section
	 * @param state boolean which is true if allowed, false otherwise
	 */
	public void setAction(int index, boolean state) {
		allowedActions[index] = state;
	}

	// extra for testing do not remove
	public int[] getupperSection() {
		return upperSection;
	}

	public int[] getlowerSection() {
		return lowerSection;
	}

	/**
	 * sets the upper subtotal
	 * sets the lower total
	 * sets the entire upper total
	 * sets the grand total
	 */
	public void setScores() {
		upperSubTotal = 0;
		lowerTotal = 0;
		upperTotal = 0;

		for (int i : upperSection)
			upperSubTotal += i;

		for (int i : lowerSection)
			lowerTotal += i;
		
		upperTotal=upperSubTotal;
		if (upperSubTotal >= 63)
			upperTotal += UPPERBONUS;
		
		grandTotal = upperTotal + lowerTotal;
	}

	/**
	 * returns the points in the upper subtotal
	 * @return upperSubTotal
	 */
	public int getUpperSubTotal() {
		return upperSubTotal;
	}

	/**
	 * if upper subtotal is exceeds a point value there will be a bonus returned
	 * otherwise it is 0
	 * @return upper bonus
	 */
	public int getUpperBonus() {
		if (upperSubTotal >= 63)
			return UPPERBONUS;
		else
			return 0;
	}

	/**
	 * returns the number of points in the entire upper total
	 * @return upperTotal
	 */
	public int getUpperTotal() {
		return upperTotal;
	}

	/**
	 * returns the number of points in the lower total
	 * @return lowerTotal
	 */
	public int getLowerTotal() {
		return lowerTotal;
	}

	/**
	 * returns the number of points the player has overall
	 * @return grandTotal
	 */
	public int getGrandTotal() {
		return grandTotal;
	}
}
