import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HandEvaluator  {

	enum HandCombination{
		ROYAL_FLUSH(10),
		STRAIGHT_FLUSH(9),
		FOUR_OF_A_KIND(8),
		FULL_HOUSE(7),
		FLUSH(6),
		STRAIGHT(5),
		THREE_OF_A_KIND(4),
		TWO_PAIR(3),
		PAIR(2),
		HIGH_CARD(1);

		private int points;

		HandCombination(int points){
			this.points=points;
		}
		public  int getPoints(){
			return points;
		}
	}
	static Set<Cards> totalHand = new TreeSet<>();


	static Set<Cards> loadCards(List<Cards> someHand){
		for(Cards o : someHand) totalHand.add(o);
		for(Cards b: GameController.communityCards){
			System.out.println(b);
			totalHand.add(b);
		}
		return totalHand;
	}

	public static void main(String[] args){

		Deck.populateDeck();// takes Card Objects from Card class and populate it in a LinkedList of 52 objects(# of cards in a deck)
		Deck.shuffleDeck();
		//System.out.println("deck of cards" +Deck.getDeck());
		Player.getHand();
		System.out.println("hand"+Player.hand);
		System.out.println("deck after cards were given out"+Deck.getDeck());
		System.out.println("community cards that are down,3"+GameController.theFlop());
		System.out.println("deck after comm cards are out"+Deck.getDeck());
		System.out.println("total extended hand (6cards)"+loadCards(Player.hand));
		System.out.println();
	}





}
