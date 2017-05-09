import java.util.ArrayList;
import java.util.List;

public class GameController {
	public static void setCommunityCards(List<Cards> communityCards) {
		GameController.communityCards = communityCards;
	}
	static List<Cards> communityCards = new ArrayList<>();
	static private int bettingPool=0;
	static private int currentBet;

	public static int getBettingPool() {
		return bettingPool;
	}

	public static void setBettingPool(int bettingPool) {
		GameController.bettingPool = bettingPool;
	}

	public static int getCurrentBet() {
		return currentBet;
	}

	public static  void setCurrentBet(int currentBet) {
		GameController.currentBet = currentBet;
	}

	static List<Cards> getCommunityCards(){
		return communityCards;
	}
	static List<Cards> theFlop(){
		for(int i=0;i<3;i++){
			communityCards.add(Deck.getDeck().removeFirst());
		}
		return communityCards;
	}
	static List<Cards> theTurn(){
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}
	static List<Cards> theRiver(){
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}




}
