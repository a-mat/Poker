import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class takes the cards defined in Cards and puts them in List called
 * Decks.
 */

public class Deck {
	private static LinkedList<Cards> Decks = new LinkedList<Cards>();

	/*
	 * populateDeck instantiates a Cards object using the constructor found in
	 * the Cards class and puts them into a List called Deck
	 */
	public static void populateDeck() {
		for (int j = 0; j < 13; j++) {
			for (Cards.suits s : Cards.suits.values()) {
				Decks.add(new Cards(Cards.getRanks()[j], String.valueOf(s)));
			}
		}
	}

	/*
	 * shuffleDeck shuffles the ordered Decks list using Collections.shuffle
	 *
	 * @return Decks List
	 */
	static List<Cards> shuffleDeck() {
		for (int i = 0; i < 4; i++) {
			Collections.shuffle(Decks);
		}
		return Decks;
	}

	/*
	 * getter for Decks
	 */
	static LinkedList<Cards> getDeck() {
		return Decks;
	}

}
