import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//do i need comboHolder and static method

/*
 *The HandEvaluator class has the function to interpret the player's and/or computer's hand. THe community
 *cards are added together with the hand cards of each player/computer and the hand methods are used to check
 *if certain hand combination are present
 */
public class HandEvaluator {
	static Set<Cards> totalHand = new TreeSet<>();
	static Map<Integer, Integer> comboHolder = new HashMap<>();

	/*
	 * Enum of different hand combinations. They have a arg constructor
	 * representing their own strength value
	 */
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

	/*
	 * adds the hand cards and community cards into totalHand treeList.
	 *
	 * @return totalHand treeSet
	 *
	 * @param hand LinkedList computers or players hand
	 */
	static Set<Cards> loadCards(List<Cards> someHand) {
		totalHand.clear();
		for (Cards o : someHand)
			totalHand.add(o);
		for (Cards b : GameController.communityCards) {
			totalHand.add(b);
		}
		return totalHand;
	}

	/*
	 * takes totalHand and directs it through the method of all the different
	 * HandCombination methods and retains the highest enum HandCombination
	 * points. After it had found the highest points, it goes iterates through
	 * the enum HandCombination to find the matching combination.
	 *
	 * @return enum HandCombination the highest handCombination found in between
	 * the hand and community Cards
	 *
	 * @param totalHand TreeSet the total hand is used as param
	 */
	static HandCombination checkHand(Set<Cards> hand) {
		int max = 0;
		max = royalFlush();
		if (max < StraightFlush())
			max = StraightFlush();
		if (max < fourFullThreeKindTwoPairOnePair())
			max = fourFullThreeKindTwoPairOnePair();
		if (max < flushCheck())
			max = flushCheck();
		if (max < straightCheck())
			max = straightCheck();
		if (max < highCard())
			max = highCard();

		// return max;
		for (HandCombination combo : HandCombination.values()) {
			if (combo.getPoints() == max) {
				return combo;
			}
		}
		return null;
	}

	/*
	 * iterates through the total Hand four times using the different suits.
	 * Each time that happens, iterator also looks for the 10,J,Q,K, and Ace
	 * cards. As each card is found, a sum is added for each value of
	 * 2,3,7,11,15.2, respectively. If the sum does not equal to 3.2 by the end
	 * of the iteration, then no royal flush was found in that given suit then
	 * the method switches to the next suit.
	 *
	 * @return int value coresponding to what was found. If a royal flush was
	 * present, the royal flush points are return. If nothing was found, then
	 * zero is returned
	 *
	 * @return HandCombination.ROYAL_FLUSH.getPoints()
	 */
	static int royalFlush() {
		double royalFlushCheck = 0;
		for (Cards.suits p : Cards.suits.values()) {
			royalFlushCheck = 0;
			Iterator<Cards> it = totalHand.iterator();
			while (it.hasNext()) {
				Cards c = it.next();
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

	/*
	 * iterates through the total Hand four times using the different suits.
	 * Each time that happens, iterator goes by each suit and adds the
	 * corresponding cards into a new linkedList sfc. If sfc does not contain 5
	 * or more cards of a given suit, the list is cleared and the next suit is
	 * checked. If it sfc does contain more than 5 cards, these 5 cards are then
	 * put in sfc2 in consecutive order. if it works then a straight flush is
	 * indicated
	 *
	 * @returnHandCombination.STRAIGHT_FLUSH.getPoints()
	 */
	static int StraightFlush() {
		int straightFlushAlert = 0;
		LinkedList<Cards> sfc = new LinkedList<>();
		LinkedList<Cards> sfc2 = new LinkedList<>();
		Label: for (Cards.suits p : Cards.suits.values()) {
			sfc = new LinkedList<>();
			Iterator<Cards> it = totalHand.iterator();
			while (it.hasNext()) {
				Cards n = it.next();
				if (n.getSuit().equals(String.valueOf(p))) {
					sfc.add(n);
				}
			}
			if (sfc.size() >= 5)
				straightFlushAlert = 1;
			break Label;
		}
		if (straightFlushAlert == 1) {
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
		}
		return 0;
	}

	/*
	 * This method looks for 4 of a kind, full house, 3 of a kind, two pairs,
	 * and one pair. This time Iterator, starts with from the top of totalHand and works down.
	 * While iterator is on one card, a nest for loop goes through to see if any other entries match the
	 * it.next(). For every match, pairCounter goes up one indicating the type of "kind" it is, whether its a 4,3,
	 * or 2 of a kind. All of these "kinds" are put in a hashmap to be retrived later. If 2 two of a kinds are detected
	 * counter goes up one indicating two pairs. At the end, the Hashmap is insepcted to find out the best matches
	 * starting from four of a kind,full house,three of a kind, two pairs, 1 pair.
	 *
	 * @return HandCombination.FOUR_OF_A_KIND.getPoints()
	 * @return HandCombination.FULL_HOUSE.getPoints()
	 * @return HandCombination.THREE_OF_A_KIND.getPoints()
	 * @return HandCombination.TWO_OF_A_KIND.getPoints()
	 * @return HandCombination.PAIR.getPoints()
	 *
	 *
	 */

	static int fourFullThreeKindTwoPairOnePair() {
		Map<Integer, String> handCombo = new HashMap<>();

		Iterator<Cards> it = ((TreeSet<Cards>) totalHand).descendingIterator();
		int counter = 0;
		int pairCounter = 0;
		Cards d = new Cards("test", "test");
		while (it.hasNext()) {
			Cards c = new Cards(it.next()); // this might be a problem, i added
											// another constructor to Cards
											// class

			counter = 0;
			pairCounter = 0;
			for (Cards t : totalHand) {
				if (t.getRank().equals(c.getRank())) {

					pairCounter++;

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
			if (pairCounter == (2) && counter != 2) {
				d = new Cards(c);
				if (handCombo.get(2) == null) {
					handCombo.put(2, d.getRank());
				}
				if (!(handCombo.get(2).equals(d.getRank())))
					counter++;
			}
			if (counter == 1 && pairCounter == (2)) {
				d = new Cards(c);
				handCombo.put(1, d.getRank());
			}

		}
		if (!(handCombo.get(4) == null)) {
			return HandCombination.FOUR_OF_A_KIND.getPoints();

		} else if (!(handCombo.get(3) == null) && !(handCombo.get(2) == null)) {

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

	/*
	 * Goes through totalHand and adds cards into List flushCheck by the matching suit. If flushCheck is >=
	 * to 5, it indicates a flush. Otherwise, flushCheck is emptied and the next suit is checked
	 *
	 * @HandCombination.FLUSH.getPoints();
	 */
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

	/*
     * Straight check uses iterator from the reverse direction. Iterator adds all cards into into sc List
     * Then a for loop is used to add each card consecutively into sfc2. If the consecutive number is broken,
     * then sfc2 is cleared and sfc iteration continues to the end. Duplicate numbers will not cause
     * sfc2 to be cleared, they will just be skipped over. If sfc2 is found to be 5, then a straight is indicated
     *
     * @return HandCombination.STRAIGHT.getPoints()
     */
	static int straightCheck() {
		List<Cards> sc = new ArrayList<>();
		List<Cards> sc2 = new ArrayList<>();
		Iterator<Cards> it = totalHand.iterator();
		while (it.hasNext()) {
			sc.add(it.next());
		}
		for (int i = sc.size() - 1; i > 0; i--) {
			if (Integer.parseInt(sc.get(i).getRank()) - Integer.parseInt(sc.get(i - 1).getRank()) == 1) {
				sc2.add(sc.get(i));
				if (sc2.size() == 5) {
					comboHolder.put(Integer.valueOf(HandCombination.STRAIGHT.getPoints()),
							Integer.parseInt(sc2.get(0).getRank()));
					return HandCombination.STRAIGHT.getPoints();

				}

			} else if (Integer.parseInt(sc.get(i).getRank()) - Integer.parseInt(sc.get(i - 1).getRank()) == 0) {

			} else
				sc2.clear();

		}
		return 0;
	}

	/*
	 * highCard() just finds the highest value card in the hand
	 *
	 * @return HandCombination.HIGH_CARD.getPoints()
	 */
	static int highCard() {
		Iterator<Cards> it = ((TreeSet<Cards>) totalHand).descendingIterator();
		while (it.hasNext()) {
			return HandCombination.HIGH_CARD.getPoints();
		}
		return 0;
	}

	public static void main(String[] args) {

		List<Cards> communityCards1 = new ArrayList<>();
		communityCards1.add(new Cards("08", "HEART"));
		communityCards1.add(new Cards("08", "DIAMOND"));
		communityCards1.add(new Cards("08", "CLUB"));
		communityCards1.add(new Cards("05", "HEART"));
		communityCards1.add(new Cards("07", "SPADE"));
		GameController.setCommunityCards(communityCards1);
		List<Cards> handtest = new ArrayList<>();
		handtest.add(new Cards("06", "SPADE"));
		handtest.add(new Cards("08", "SPADE"));
		List<Cards> handtest2 = new ArrayList<>();
		handtest2.add(new Cards("05", "HEART"));
		handtest2.add(new Cards("10", "HEART"));

		System.out.println(loadCards(handtest));
		//System.out.println(checkHand(loadCards(handtest)));
		// System.out.println(checkHand(loadCards(handtest2)));
		// if(checkHand(loadCards(handtest)).getPoints()>checkHand(loadCards(handtest2)).getPoints())
		// System.out.println(royalFlush());
		// System.out.println(StraightFlush());
		 System.out.println(fourFullThreeKindTwoPairOnePair());
		// System.out.println(flushCheck());
		// System.out.println(straightCheck());
		// System.out.println(highCard());

	}

}
