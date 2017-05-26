
/*
 *
 * The card class defines the state of the cards in Poker. Each card contains a rank and a suit.
 *
 *
 */


public class Cards implements Comparable<Cards> {
	private String rank;
	private String suit;
	public  String getRank() {
		return rank;
	}



	public void setRank(String rank) {
		this.rank = rank;
	}


	public String getSuit() {
		return suit;
	}


	public void setSuit(String suit) {
		this.suit = suit;
	}


	Cards(String rank, String s){
		this.rank=rank;
		this.suit=s;
    }



	  Cards(Cards next) {
		this.rank = next.getRank();
		this.suit = next.getSuit();

	}

/*
 * A final string array of all the possible ranks
 * The royal cards have been given number value instead of their name for ease of this project.
 * Comparable puts 10 before 2-9 due to the first digit. Because of this all the numbers before 10 have been
 * given a zero value infront of it to preserve order.
 */
	private final static String[] ranks = {"02","03","04","05","06","07","08","09","10","11","12",
			"13","14"};
	/*
	 * toString have been overridden so that numbers 02-09 can be read as 2-9 by removing the first zero.
	 * Royal cards are also translated from 11-14 as Jack to Ace.
	 */

	@Override
	public String toString() {
		 if(rank.equals("10")) return rank+" of "+ suit;
		 if(rank.equals("11")) return "Jack of "+ suit;
		 if(rank.equals("12")) return "Queen of "+ suit;
		 if(rank.equals("13"))  return "King of "+ suit;
		 if(rank.equals("14")) return  "Ace of "+ suit;
		 else return rank.substring(1)+" of "+ suit;
	}

	public static String[] getRanks(){
		return ranks;
	}

	/*
	 * the suits are put in an enum to be read later as there are only four possible suits.
	 */
	enum suits {
		SPADE,
		HEART,
		DIAMOND,
		CLUB;
	}
	@Override
	public int compareTo(Cards o) {

		int n=getRank().compareTo(o.rank);
		if(n==0) return o.suit.compareTo(getSuit());
		else return n;
	}


}
