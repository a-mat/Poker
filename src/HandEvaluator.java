import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// straight flush aint perfecrt cause there could be biggero ne
public class HandEvaluator {
	static Set<Cards> totalHand = new TreeSet<>();
	static Map<Integer, Integer> comboHolder = new HashMap<>();

	enum HandCombination {
		ROYAL_FLUSH(10), STRAIGHT_FLUSH(9), FOUR_OF_A_KIND(8), FULL_HOUSE(7), FLUSH(6), STRAIGHT(5), THREE_OF_A_KIND(
				4), TWO_PAIR(3), PAIR(2), HIGH_CARD(1);

		private int points;

		HandCombination(int points) {
			this.points = points;
		}

		public int getPoints() {
			return points;
		}
	}

	static Set<Cards> loadCards(List<Cards> someHand) {
		for (Cards o : someHand)
			totalHand.add(o);
		for (Cards b : GameController.communityCards) {
			totalHand.add(b);
		}
		return totalHand;
	}

	static int royalFlush() {
		double royalFlushCheck = 0;
		for (Cards.suits p : Cards.suits.values()) {
			System.out.println("p " + p);
			royalFlushCheck = 0;
			Iterator<Cards> it = totalHand.iterator();
			while (it.hasNext()) {
				Cards c = it.next();
				System.out.println("c :  " + c);
				if (c.getRank().equals("10") && c.getSuit().equals(String.valueOf(p)))
					royalFlushCheck += 2;

				if (c.getRank().equals("11") && c.getSuit().equals(String.valueOf(p)))
					royalFlushCheck += 3;
				if (c.getRank().equals("12") && c.getSuit().equals(String.valueOf(p)))
					royalFlushCheck += 7;
				if (c.getRank().equals("13") && c.getSuit().equals(String.valueOf(p)))
					royalFlushCheck += 11;
				if (c.getRank().equals("14") && c.getSuit().equals(String.valueOf(p)))
					royalFlushCheck += 15.2;
				System.out.println(royalFlushCheck);
			}
			if (royalFlushCheck == 38.2) {
				break;
			}

		}
		if (royalFlushCheck == 38.2)
			return HandCombination.ROYAL_FLUSH.getPoints();
		else
			return 0;

	}

	static int StraightFlush() {
		LinkedList<Cards> sfc = new LinkedList<>();
		LinkedList<Cards> sfc2 = new LinkedList<>();
		Label: for (Cards.suits p : Cards.suits.values()) {
			sfc = new LinkedList<>();
			System.out.println("p " + p);
			Iterator<Cards> it = totalHand.iterator();
			while (it.hasNext()) {
				Cards n = it.next();
				if (n.getSuit().equals(String.valueOf(p))) {
					sfc.add(n);
					System.out.println("equal");
				}
				System.out.println(sfc);
			}
			if (sfc.size() >= 5)
				break Label;
		}
		for (int i = sfc.size() - 1; i >= 0; i--) {
			if (Integer.parseInt(sfc.get(i).getRank()) - Integer.parseInt(sfc.get(i - 1).getRank()) == 1) {
				sfc2.add(sfc.get(i));
				if (sfc2.size() == 5)
					break;
				comboHolder.put(Integer.valueOf(HandCombination.STRAIGHT_FLUSH.getPoints()),
						Integer.parseInt(sfc2.get(0).getRank()));
				return HandCombination.STRAIGHT_FLUSH.getPoints();
			} else
				sfc2.clear();
		}
		return 0;
	}

	static int fourThreeKindTwoPairOnePair() {
		Map<Integer, String> handCombo = new HashMap<>();

		Iterator<Cards> it = ((TreeSet<Cards>) totalHand).descendingIterator();
		int counter = 0;
		int pairCounter = 0;
		Cards d = new Cards("test", "test");
		while (it.hasNext()) {
			Cards c = new Cards(it.next()); // this might be a problem, i added
											// another constructor to Cards
											// class

			counter=0;
			pairCounter = 0;
			System.out.println("c " + c);
			 for (Cards t : totalHand) {
				System.out.println("t: " + t);
				if (t.getRank().equals(c.getRank())) {

					pairCounter++;
					System.out.println("pair counter "+ pairCounter);

				}

			}
			if (pairCounter == (4)) {
				d = new Cards(c);
				handCombo.put(4, d.getRank());

			}
			if (pairCounter == (3)) {

				d = new Cards(c);

				if (handCombo.get(3) == null) {
					handCombo.put(3, d.getRank());
				}

			}
			if (pairCounter == (2) && counter!=2) {
				d = new Cards(c);
				System.out.println("went into 2");
				if (handCombo.get(2) == null) {
					System.out.println("added into map");
					handCombo.put(2, d.getRank());
					System.out.println("hand combo" +handCombo);
				} if(!(handCombo.get(2).equals(d.getRank())))
				counter++;
				System.out.println("counter+ "+counter);
			}
			if (counter == 1 && pairCounter == (2)) {
				System.out.println("we inside");
				d = new Cards(c);
				handCombo.put(1, d.getRank());
				System.out.println("second pair "+ handCombo);
			}

		}
		if (!(handCombo.get(4) == null)) {
			System.out.println("Highest card" + d.getRank());
			return HandCombination.FOUR_OF_A_KIND.getPoints();

		} else if (!(handCombo.get(3) == null) && !(handCombo.get(2) == null)) {
			//comboHol
			return HandCombination.FULL_HOUSE.getPoints();
		} else if (!(handCombo.get(3) == null) && (handCombo.get(2) == null)) {
			return HandCombination.THREE_OF_A_KIND.getPoints();
		} else if (!(handCombo.get(2) == null) && !(handCombo.get(1) == null)) {
			return HandCombination.TWO_PAIR.getPoints();
		} else if (!(handCombo.get(2) == null) && (handCombo.get(1) == null)) {
			return HandCombination.PAIR.getPoints();
		} else
			return 0;
	}

	static int flushCheck() {
		LinkedList<Cards> flushCheck = new LinkedList<>();
		for (Cards.suits p : Cards.suits.values()) {
			flushCheck = new LinkedList<>();
			Iterator<Cards> it = totalHand.iterator();
			while (it.hasNext()) {
				Object n = it.next();
				if (((Cards) n).getSuit().equals(String.valueOf(p))) {
					flushCheck.add((Cards) n);
				}
			}
			if (flushCheck.size() >= 5) {
				return HandCombination.FLUSH.getPoints();
			}
		}
		return 0;
	}

	static int straightCheck() {
		List<Cards> sc = new ArrayList<>();
		List<Cards> sc2 = new ArrayList<>();
		Iterator<Cards> it = totalHand.iterator();
		while (it.hasNext()) {
			sc.add(it.next());
		}
		System.out.println(sc);
		for (int i = sc.size() - 1; i > 0; i--) {
			if (Integer.parseInt(sc.get(i).getRank()) - Integer.parseInt(sc.get(i - 1).getRank()) == 1) {
				sc2.add(sc.get(i));
				if (sc2.size() == 5)
					break;
				comboHolder.put(Integer.valueOf(HandCombination.STRAIGHT.getPoints()),
						Integer.parseInt(sc2.get(0).getRank()));
				return HandCombination.STRAIGHT.getPoints();
			} else
				sc2.clear();

		}
		return 0;
	}
		static int highCard(){
			Iterator<Cards> it = ((TreeSet<Cards>) totalHand).descendingIterator();
			while(it.hasNext()){
				return HandCombination.HIGH_CARD.getPoints();
			} return 0;
		}


	public static void main(String[] args) {

		// Deck.populateDeck();// takes Card Objects from Card class and
		// populate it in a LinkedList of 52 objects(# of cards in a deck)
		// Deck.shuffleDeck();
		// System.out.println("deck of cards" +Deck.getDeck());
		// Player.getHand();
		// System.out.println("hand"+Player.hand);
		// System.out.println("deck after cards were given out"+Deck.getDeck());
		// System.out.println("community cards that are
		// down,3"+GameController.theFlop());
		// System.out.println("deck after comm cards are out"+Deck.getDeck());
		// System.out.println("total extended hand
		// (6cards)"+loadCards(Player.hand));
		// System.out.println();

		List<Cards> communityCards1 = new ArrayList<>();
		communityCards1.add(new Cards("07", "HEART"));
		communityCards1.add(new Cards("08", "DIAMOND"));
		communityCards1.add(new Cards("06", "HEART"));
		communityCards1.add(new Cards("10", "DIAMOND"));
		communityCards1.add(new Cards("14", "HEART"));
		GameController.setCommunityCards(communityCards1);
		List<Cards> handtest = new ArrayList<>();
		handtest.add(new Cards("09", "CLUB"));
		handtest.add(new Cards("09", "SPADE"));
		System.out.println(loadCards(handtest));
		// System.out.println(royalFlush());
		// System.out.println(StraightFlush());
		//System.out.println(fourThreeKindTwoPairOnePair());
		//System.out.println(flushCheck());
		//System.out.println(straightCheck());
		System.out.println(highCard());

	}

}
