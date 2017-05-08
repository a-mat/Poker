import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class Deck {
	static LinkedList<Cards> Decks = new LinkedList<Cards>();
	 public static void populateDeck(){
		for(int j=0;j<14;j++){
			for(Cards.suits s : Cards.suits.values()){
				Decks.add(new Cards(Cards.getRanks()[j],String.valueOf(s)));
			}
		}
	}

	static List<Cards> shuffleDeck(){
		for(int i=0;i<4;i++){
		Collections.shuffle(Decks);
		}
		return Decks;
	}
	static LinkedList<Cards> getDeck(){
		return Decks;
	}

	public static void main(String[] args){
		populateDeck();
		System.out.println(Decks);
		System.out.println( Decks.getLast());
		//System.out.println(Deck);
		//System.out.println(Cards.getRanks()[1]);
	}
}
