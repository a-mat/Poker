import java.util.ArrayList;
import java.util.List;

public class Computer {
	 boolean compStatus = true;
	List<Cards> hand2 = new ArrayList<>(2);
	private String name;
	private int cash;


	public  boolean isCompStatus() {
		return compStatus;
	}

	public void setCompStatus(boolean compStatus) {
		this.compStatus = compStatus;
	}

	public String getName() {
		return name;
	}

	public int getCash() {
		return cash;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	Computer(String name){
		this.name=name;
	}

	void compGenerator(int x){
		switch(x){
		case(1):
			{
				Computer june = new Computer( "June");
				june.getHand();
			}
		case(2):
			{
			compGenerator(1);
			Computer aaron = new Computer( "Aaron");
			aaron.getHand();
			}
		case(3):
			{
			compGenerator(2);
			Computer joe = new Computer( "Joe");
			joe.getHand();
			}
		}


	}

	List<Cards> getHand(){
		Deck.populateDeck();
		Deck.shuffleDeck();
		hand2.clear();
		for(int i=0;i<2;i++){
			hand2.add(Deck.getDeck().removeFirst());
		}
		return hand2;
	}

	void fold(){
		compStatus=false;
	}

	void call(){
		setCash(getCash()-GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
	}

	void raise(int amount){
		GameController.setCurrentBet(GameController.getCurrentBet()+amount);
		setCash(getCash()-GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
	}

	void initialBet(int initial){
		GameController.setCurrentBet(initial);//establish current bet
		setCash(getCash()-initial); // removes bet from cash
		GameController.setBettingPool(initial);

	}

	boolean check(){
		if(GameController.getCurrentBet()==0) return true;
		else return false;
	}
	static void test(){
		Computer leeroy = new Computer( "Leeroy");
		Computer tom = new Computer( "tom");
		Computer joe = new Computer( "hoe");

		leeroy.getHand();
		tom.getHand();
		joe.getHand();

		System.out.println(leeroy.hand2);
		System.out.println(tom.hand2);
		System.out.println(joe.hand2);
		System.out.println(tom.hand2);

		leeroy.setCash(5);
		tom.setCash(20);
		joe.setCash(5);

		System.out.println(tom.getCash());
		System.out.println(leeroy.getCash());
		System.out.println(joe.getCash());
	}

	public static void main(String[] args){
		test();
	}
}
