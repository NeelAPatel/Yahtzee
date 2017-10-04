package yahtzeeGUIVersion;

public class GameLogic {

	private Player player1;
	private Player player2;
	private Die[] dice;
	private int rollCount;
	private int currentPlayer;
    private int  turns;

    
    private int printCount = 0;
    
    /**
     * constructs a gamelogic object
     * creates two new players, once diceset, sets the current player,
     * how many turns they have taken (none), and how many rolls they have left (3)
     */
	public GameLogic() {
		player1 = new Player("P1");
		player2 = new Player("P2");
		rollCount = 3;
		dice = new Die[] { new Die(), new Die(), new Die(), new Die(),new Die() };
		currentPlayer = 1;
		turns = 0;
	}

	/**
	 * @return the current player as a int of its number
	 */
	public int getCurrentPlayerNumber()
	{
		return currentPlayer;
	}
	
	/**
	 * @return the current player as a player object
	 */
	public Player getCurrentPlayer()
	{
		if(currentPlayer == 1)
			return player1;
		else
			return player2;
	}

	/**
	 * switches who is currently playing
	 */
	public void switchCurrentPlayer() 
	{
		if (currentPlayer == 1)
			currentPlayer = 2;
		else 
			currentPlayer = 1;
		
		turns++;
	}

	/**
	 * decreases the amount of times the current player can roll
	 */
	public void decreaseTimesRolled() {
		rollCount--;
	}

	/**
	 * @return the number of rolls a current player has left
	 */
	public int getRollsLeft() // for current player
	{
		return rollCount;
	}
	
	/**
	 * resets the amount of times a player can roller back to 3
	 */
	public void resetRolls()
	{
		rollCount = 3;
	}

	/**
	 * @param typeOfScore the section/category where the score is in
	 * @return the score of the choice
	 */
	public String getScore(String typeOfScore) {
		String score = "";

		if (typeOfScore.equalsIgnoreCase("1"))
			return score += ones();
		if (typeOfScore.equalsIgnoreCase("2"))
			return score += twos();
		if (typeOfScore.equalsIgnoreCase("3"))
			return score += threes();
		if (typeOfScore.equalsIgnoreCase("4"))
			return score += fours();
		if (typeOfScore.equalsIgnoreCase("5"))
			return score += fives();
		if (typeOfScore.equalsIgnoreCase("6"))
			return score += sixes();
		if (typeOfScore.equalsIgnoreCase("3Kind"))
			return score += threeOfKind();
		if (typeOfScore.equalsIgnoreCase("4Kind"))
			return score += fourOfKind();
		if (typeOfScore.equalsIgnoreCase("LStraight"))
			return score += bigStraight();
		if (typeOfScore.equalsIgnoreCase("SStraight"))
			return score += smallStraight();
		if (typeOfScore.equalsIgnoreCase("Chance"))
			return score += chance();
		if (typeOfScore.equalsIgnoreCase("Yahtzee"))
			return score += yahtzee();
		if (typeOfScore.equalsIgnoreCase("FullHouse"))
			return score += fullHouse();

		return score;

	}

	/**
	 * tells whether or not the game is over
	 * @return true only if there is more than or equal to 26 rounds
	 */
	public boolean checkGameOver() {
		return (turns >= 26);
	}

	/**
	 * rolls all the die that are selected
	 */
	public void rollDice() 
	{
		for (int i = 0; i < 5; i++) 
		{
			if (!dice[i].isSelected())
			dice[i].roll();
			
		}
		
		addYahtzeeBonus();
	}
	
	/**
	 * returns the dice set which is being used in the game
	 * @return dice an array of die objects
	 */
	public Die[] getDice() {
		return dice;
	}

	/**
	 * returns the first player of the game
	 * @return player 1 a player object
	 */
	public Player getPlayerOne() {
		return player1;
	}

	/**
	 * returns the the second player of the game
	 * @return player2 a separate player object from player1
	 */
	public Player getPlayerTwo() {
		return player2;
	}
	
	/**
	 * Returns the total number of ones in the dice roll
	 * @return total of ones
	 */
	public int ones() {
		return sum(1);
	}

	/**
	 * Returns the total number of 2s in the dice roll
	 * @return total of 2s
	 */
	public int twos() {
		return sum(2);
	}

	/**
	 * Returns the total number of 3s in the dice roll
	 * @return total of 3s
	 */
	public int threes() {
		return sum(3);
	}

	/**
	 * Returns the total number of 4s in the dice roll
	 * @return total of 4s
	 */
	public int fours() {
		return sum(4);
	}

	/**
	 * Returns the total number of 5s in the dice roll
	 * @return total of 5s
	 */
	public int fives() {
		return sum(5);
	}

	/**
	 * Returns the total number of 6s in the dice roll
	 * @return total of 6s
	 */
	public int sixes() {
		return sum(6);
	}

	/**
	 * Checks if the dice has a 3 of a kind or greater
	 * @return the sum of all the dice if there is a 3 of a kind or greater
	 */
	public int threeOfKind() {
		if (containsAmt(3))
			return sumAll();
		return 0;
	}

	/**
	 * Checks if the dice has a 4 of a kind or greater
	 * @return the sum of all the dice if there is a 4 of a kind or greater
	 */
	public int fourOfKind() {
		if (containsAmt(4))
			return sumAll();
		return 0;
	}

	/**
	 * Checks if there is a full house
	 * @return if there is a full house, returns 25
	 */
	public int fullHouse() {
		boolean two = false, three = false;
		int[] values = new int[6];
		for (int i = 0; i < dice.length; i++) {
			values[dice[i].getValue() - 1]++;
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i] == 2) {
				two = true;
			}
			if (values[i] == 3)
				three = true;
		}

		if (two && three)
			return 25;
		return 0;
	}

	/**
	 * Checks if there is a small straight
	 * @return 30 if there is one
	 */
	public int smallStraight() {
		if (contains(1) && contains(2) && contains(3) && contains(4)
				|| contains(2) && contains(3) && contains(4) && contains(5)
				|| contains(3) && contains(4) && contains(5) && contains(6)) {
			return 30;
		}

		return 0;
	}

	/**
	 * Checks if there is a big straight
	 * @return 40 if there is one
	 */
	public int bigStraight() {
		if (contains(1) && contains(2) && contains(3) && contains(4)
				&& contains(5) || contains(2) && contains(3) && contains(4)
				&& contains(5) && contains(6)) {
			return 40;
		}

		return 0;
	}

	/**
	 * Checks if there is a yahtzee
	 * @return 50 if there is a yahtzee
	 */
	public int yahtzee() {
		if(containsAmt(5))
			return 50;

		return 0;
	}

	/**
	 * @return sum of all the dice because chance
	 */
	public int chance() {
		return sumAll();
	}

	private boolean contains(int num) {
		for (int i = 0; i < dice.length; i++) {
			if (dice[i].getValue() == num)
				return true;
		}
		return false;
	}
 
	/**
	 * Checks to see if diceArray contains a certain amount of any value
	 * @param amt the certain amount of any value
	 * @return whether or not it contains the certain amount
	 */
	private boolean containsAmt(int amt) {
		int[] values = new int[6];
		
		for (int i = 0; i < dice.length; i++) {
			values[dice[i].getValue() - 1]++;
		}

		for (int i = 0; i < dice.length+1; i++) {
			if (values[i] >= amt)
				return true;
		}
		return false;
		
	}

	/**
	 * Returns the sum of a certain number in the array
	 * @param num the certain number
	 * @return the sum of the amount of certain numbers
	 */
	private int sum(int num) {
		int sum = 0;

		for (int i = 0; i < dice.length; i++) {
			if (dice[i].getValue() == num)
				sum += num;
		}
		return sum;
	}

	/**
	 * Returns the sum of all numbers in the dice array
	 * @return the sum of all the dice
	 */
	private int sumAll() {
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			sum += dice[i].getValue();
		}
		return sum;
	}
	
	
	/**
	 * checks if you can have yahtzee bonus
	 * if so, adds to yahtzee bonus
	 */
	public void addYahtzeeBonus(){
		if(getCurrentPlayer().getYahtzee()>0 && containsAmt(5))
			getCurrentPlayer().addYahtzeeBonus();
	}
	
	/**
	 * sets the total scores for both players
	 */
	public void calculateTotals() {
		player1.setTotals();
		player2.setTotals();
	}

	/**
	 * compare the points each player has
	 * whoever has the higher point value is the winner
	 * @return the number of the player who wins
	 * if it is a tie return 0
	 */
	public int getWinner()
	{
		if (player1.getGrandTotal() > player2.getGrandTotal())
			return 1;
		else if (player1.getGrandTotal() < player2.getGrandTotal())
			return 2;
		else 
			return 0;
	}
}