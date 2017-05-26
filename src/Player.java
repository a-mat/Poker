import java.util.ArrayList;
import java.util.List;

public class Player {
	private int userCurrentBet;
	public int getUserCurrentBet() {
		return userCurrentBet;
	}

	public void setUserCurrentBet(int userCurrentBet) {
		this.userCurrentBet = userCurrentBet;
	}
	@Override
	public String toString() {
		return name;
	}

	private  boolean playerStatus = true;
	public  boolean isPlayerStatus() {
		return playerStatus;
	}

	public   void setPlayerStatus(boolean playerStatus) {
		this.playerStatus = playerStatus;
	}

	private  List<Cards> hand = new ArrayList<>(2);
	public  List<Cards> getHand() {
		return hand;
	}

	public  List<Cards> setHand(){
		for(int i=0;i<2;i++){
				hand.add(Deck.getDeck().removeFirst());
		}
			return hand;
	}


	private  String name;
	private  int cash;

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




	void fold(){
		setPlayerStatus(false);
	}

	void call(){
		if(GameController.raiseCounter>0){
			setCash(getCash()-(GameController.getCurrentBet()-getUserCurrentBet()));
			GameController.setBettingPool(GameController.getBettingPool()
					+(GameController.getCurrentBet()-getUserCurrentBet()));
		}
		else{setCash(getCash()-GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
		}
	}

	void raise(int amount){
		GameController.setCurrentBet(GameController.getCurrentBet()+amount);
		setCash(getCash()-GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool()+GameController.getCurrentBet());
	}
	boolean check(){

		if(GameController.getCurrentBet()==0) return true;
		else return false;
	}
	void initialBet(int initial){
		GameController.setCurrentBet(initial);//establish current bet
		setCash(getCash()-initial); // removes bet from cash
		GameController.setBettingPool(GameController.getBettingPool()+initial);

	}






}
