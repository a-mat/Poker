import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HandEvaluator {

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

		private int rankValue;
		HandCombination(int rankValue){
			this.rankValue=rankValue;
		}
		public  int getRankValue(){
			return rankValue;
		}
	}
	static Set<Cards> totalHand = new TreeSet<>();
	
	static Set<Cards> loadCards(List<Cards> someHand){
		for(int i=0;i<someHand.size();i++){
			//System.out.println(someHand.get(i));
			totalHand.add(someHand.get(i));
		}

		//totalHand.addAll(someHand);
		//totalHand.addAll(GameController.getCommunityCards());
		return totalHand;
	}

	public static void main(String[] args){
		/*for(HandCombination type: HandCombination.values()){
		System.out.println(type.rankValue);
		}*/

		Deck.populateDeck();
		Deck.shuffleDeck();
		System.out.println("deck of cards" +Deck.getDeck());
		Player.getHand();
		System.out.println("hand"+Player.hand);
		System.out.println("deck after cards were given out"+Deck.getDeck());
		System.out.println("community cards that are down,3"+GameController.theFlop());
		System.out.println("deck after comm cards are out"+Deck.getDeck());
		System.out.println("total extended hand (6cards)"+loadCards(Player.hand));

	}
}
