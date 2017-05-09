import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
// straight flush aint perfecrt cause there could be biggero ne
public class HandEvaluator  {
	static Set<Cards> totalHand = new TreeSet<>();

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

	static Set<Cards> loadCards(List<Cards> someHand){
		for(Cards o : someHand) totalHand.add(o);
		for(Cards b: GameController.communityCards){
			totalHand.add(b);
		}
		return totalHand;
	}

	int royalFlush(){
		double royalFlushCheck=0;
		for(Cards.suits p: Cards.suits.values()){
			 royalFlushCheck=0;
			Iterator<Cards> it = totalHand.iterator();
			while(it.hasNext()){
				if(it.next().getRank().equals("10")&&it.next().getSuit().equals(p)) royalFlushCheck +=2;
				if(it.next().getRank().equals("Jack")&&it.next().getSuit().equals(p)) royalFlushCheck +=3;
				if(it.next().getRank().equals("Queen")&&it.next().getSuit().equals(p)) royalFlushCheck +=7;
				if(it.next().getRank().equals("King")&&it.next().getSuit().equals(p)) royalFlushCheck +=11;
				if(it.next().getRank().equals("Ace")&&it.next().getSuit().equals(p)) royalFlushCheck +=15.2;
			}
			if(royalFlushCheck==38.2) {
				break;
			}

		}
		if(royalFlushCheck==38.2) return  HandCombination.ROYAL_FLUSH.getPoints();
		else return 0;

	}
	static int StraightFlush(){
		LinkedList<Cards> straightFlushCheck = new LinkedList<>();
		  for(Cards.suits p: Cards.suits.values()){
			  straightFlushCheck = new LinkedList<>();
			  System.out.println("p "+p);
			Iterator<Cards> it = totalHand.iterator();
			while(it.hasNext()){
				Object n = it.next();
				if(((Cards) n).getSuit().equals(String.valueOf(p))){
					straightFlushCheck.add((Cards) n);
					System.out.println("equal");
				}
				System.out.println(straightFlushCheck);
			}  if (straightFlushCheck.size()<5) continue ;
			for(int i=(straightFlushCheck.size()-1);i>0;i--){//is this necessary
				System.out.println(i);
				if(straightFlushCheck.get(i).getRank().equals("King") &&
						straightFlushCheck.get(i-1).getRank().equals("Queen")&&
						straightFlushCheck.get(i-2).getRank().equals("Jack")&&
						straightFlushCheck.get(i-3).getRank().equals("10")&&
						straightFlushCheck.get(i-4).getRank().equals("9")){
					return  HandCombination.STRAIGHT_FLUSH.getPoints();

				}if(straightFlushCheck.get(i).getRank().equals("Queen") &&
						straightFlushCheck.get(i-1).getRank().equals("Jack")&&
						straightFlushCheck.get(i-2).getRank().equals("10")&&
						straightFlushCheck.get(i-3).getRank().equals("9")&&
						straightFlushCheck.get(i-4).getRank().equals("8")){
					return  HandCombination.STRAIGHT_FLUSH.getPoints();
				}if(straightFlushCheck.get(i).getRank().equals("Jack") &&
						straightFlushCheck.get(i-1).getRank().equals("10")&&
						straightFlushCheck.get(i-2).getRank().equals("9")&&
						straightFlushCheck.get(i-3).getRank().equals("8")&&
						straightFlushCheck.get(i-4).getRank().equals("7")){
					return  HandCombination.STRAIGHT_FLUSH.getPoints();
				}
			}
			for(int i=0;i<straightFlushCheck.size()-4;i++){
				System.out.println(i);
				System.out.println(straightFlushCheck.get(i));
				Integer check=1;
				Integer checksum=0;
				System.out.println(((Integer.parseInt(straightFlushCheck.get(i+1).getRank()))
						-(Integer.parseInt(straightFlushCheck.get(i).getRank())))==check);
				for(int j=0;i<=4;i++){
					if( ((Integer.parseInt(straightFlushCheck.get(i+1).getRank()))
					-(Integer.parseInt(straightFlushCheck.get(i).getRank())))==check){
						checksum +=((Integer.parseInt(straightFlushCheck.get(i+1).getRank()))
								-(Integer.parseInt(straightFlushCheck.get(i).getRank())));
						System.out.println(checksum);

					}
				}
				if(checksum>=(4)){       // how to get the highest card
					System.out.println("dis one"+ straightFlushCheck.getLast());
					return  HandCombination.STRAIGHT_FLUSH.getPoints();
				}

			}
		}
		return 0;
	}

	static int fourOfAKind(){
		Iterator<Cards> it = totalHand.iterator();
		int counter=0;
		int pairCounter=0;
		int match=0;
		Cards d = new Cards("test","test");
		while(it.hasNext()){
			Cards c = new Cards(it.next());   //this might be a problem, i added another constructor to Cards class

			counter++;
			pairCounter=0;
			System.out.println("c "+c);
			test: for(Cards t: totalHand){
				System.out.println("t: "+t);
				 if(t.getRank().equals(c.getRank())) {
					System.out.println("they are equal");
					System.out.println(pairCounter);
					pairCounter++;
				}

			}
			if(pairCounter==(4)) {
				match=4;
				 d = new Cards(c);
				 System.out.println("matched "+c.getRank());
				 System.out.println(pairCounter);
			}
		}
		if(match==4) {
			System.out.println("Highest card"+d.getRank());
			return HandCombination.FOUR_OF_A_KIND.getPoints();

		}
		else return 0;
	}

	static void twoOnePair(){
		Iterator<Cards> it = ((TreeSet<Cards>) totalHand).descendingIterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}


	public static void main(String[] args){


	//	Deck.populateDeck();// takes Card Objects from Card class and populate it in a LinkedList of 52 objects(# of cards in a deck)
		//Deck.shuffleDeck();
		//System.out.println("deck of cards" +Deck.getDeck());
		//Player.getHand();
		//System.out.println("hand"+Player.hand);
		//System.out.println("deck after cards were given out"+Deck.getDeck());
		//System.out.println("community cards that are down,3"+GameController.theFlop());
		//System.out.println("deck after comm cards are out"+Deck.getDeck());
		//System.out.println("total extended hand (6cards)"+loadCards(Player.hand));
		//System.out.println();

		List<Cards> communityCards1 = new ArrayList<>();
		communityCards1.add(new Cards("01","DIAMOND"));
		communityCards1.add(new Cards("02","DIAMOND"));
		communityCards1.add(new Cards("04","DIAMOND"));
		communityCards1.add(new Cards("02","HEART"));
		communityCards1.add(new Cards("04","HEART"));
		GameController.setCommunityCards(communityCards1);
		List<Cards> handtest = new ArrayList<>();
		handtest.add(new Cards("01","SPADE"));
		handtest.add(new Cards("04","CLUB"));
		handtest.add(new Cards("04","SPADE"));
		System.out.println(loadCards(handtest));
		//System.out.println(StraightFlush());
		twoOnePair();
	}





}
