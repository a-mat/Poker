import java.util.ArrayList;
import java.util.List;

public class Computer extends Player {
	private  boolean playerStatus = true;


	public boolean isPlayerStatus() {
		return playerStatus;
	}

	public  void setPlayerStatus(boolean playerStatus) {
		this.playerStatus = playerStatus;
	}

	private   List<Cards> hand = new ArrayList<>(2);

	public   List<Cards> gethand() {
		return hand;
	}


	public  List<Cards> sethand(){
		for(int i=0;i<2;i++){
				hand.add(Deck.getDeck().removeFirst());
		}
			return hand;
	}



	private String name;
	private  int cash;
	private int userCurrentBet;

	@Override
	public int getUserCurrentBet() {
		return userCurrentBet;
	}
	@Override
	public void setUserCurrentBet(int userCurrentBet) {
		this.userCurrentBet = userCurrentBet;
	}


	@Override
	public String getName() {
		return name;
	}
	@Override
	public int getCash() {
		return cash;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void setCash(int cash) {
		this.cash = cash;
	}

	Computer(String name, int cash){
		this.name=name;
		this.cash=cash;
	}



	@Override
	void fold(){
		setPlayerStatus(false);
	}

	@Override
	void call(){
		System.out.println(GameController.raiseCounter);
		if(GameController.raiseCounter > 0){
			setCash(getCash()-(GameController.getCurrentBet()-getUserCurrentBet()));
			GameController.setBettingPool(GameController.getBettingPool()
					+(GameController.getCurrentBet()-getUserCurrentBet()));
		}
		else {
			setCash(getCash()-GameController.getCurrentBet());
			GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
		}
	}
	@Override
	void raise(int amount){
		GameController.setCurrentBet(GameController.getCurrentBet()+amount);
		setCash(getCash()-GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
	}
	@Override
	void initialBet(int initial){
		GameController.setCurrentBet(initial);//establish current bet
		setCash(getCash()-initial); // removes bet from cash
		GameController.setBettingPool(GameController.getBettingPool()+initial);

	}
	@Override
	boolean check(){
		if(GameController.getCurrentBet()==0) return true;
		else return false;
	}



	@Override
	public String toString() {
		return name;
	}


}
