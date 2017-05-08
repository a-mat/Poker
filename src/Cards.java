
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



	 @Override
	public String toString() {
		return rank+" of "+ suit;
	}



	private final static String[] ranks = {"1","2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
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
