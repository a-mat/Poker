
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



	@Override
	public String toString() {
		 if(rank.equals("10")) return rank+" of "+ suit;
		 if(rank.equals("11")) return "Jack of "+ suit;
		 if(rank.equals("12")) return "Queen of "+ suit;
		 if(rank.equals("13"))  return "King of "+ suit;
		 if(rank.equals("14")) return  "Ace of "+ suit;
		 else return rank.substring(1)+" of "+ suit;
	}



	private final static String[] ranks = {"02","03","04","05","06","07","08","09","10","11","12",
			"13","14"};
	public static String[] getRanks(){
		return ranks;
	}

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
