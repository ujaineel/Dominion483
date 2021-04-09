package dominionAgents;

import java.util.ArrayList;

public class PlayerStats {

	protected ArrayList<Card> cardsGainedFirstTwoTurns = new ArrayList<Card>();
	//Used for statistics at game end
	private int[] cardsOwned = {
		//Province
		0,
		//Duchy
		0,
		//Estate
		0,
		//Copper
		0,
		//Silver
		0,
		//Gold
		0,
		//Curse
		0,
		//Kingdom Cards
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0
	};
	
	public void addCardFirstTwoTurns(Card c) {
		cardsGainedFirstTwoTurns.add(c);
	}
	
	public void addCardOwned(int index) {
		cardsOwned[index] ++;
	}
	
	public int[] getCardsOwned() {
		return cardsOwned;
	}
	
	public ArrayList<String> getCardsFirstTwoTurns(){
		ArrayList<String> al = new ArrayList<String>();
		for(Card c:cardsGainedFirstTwoTurns) {
			al.add(c.getName());
		}
		return al;
	}
	
}
