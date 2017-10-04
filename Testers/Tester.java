package Testers;

import yahtzeeGUIVersion.Die;
import yahtzeeGUIVersion.GameLogic;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


	
	
		
		GameLogic logic = new GameLogic();
		
		printDice(logic.getDice());
		
		System.out.println(logic.ones());
		System.out.println(logic.twos());
		System.out.println(logic.threes());
		System.out.println(logic.fours());
		System.out.println(logic.fives());
		System.out.println(logic.sixes());
		
		System.out.println(logic.threeOfKind());
		System.out.println(logic.fourOfKind());
		System.out.println(logic.fullHouse());
		System.out.println(logic.smallStraight());
		System.out.println(logic.bigStraight());
		System.out.println(logic.yahtzee());
		System.out.println(logic.chance());

	}
	
	
	
	public static void printDice(Die[] dice){
		
			for(Die die: dice){
				System.out.print(die.getValue()+ ",");
			}
		
		    System.out.println();
		
	}

}
