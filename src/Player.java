import java.util.ArrayList;
import java.util.List;

/**
 * The Player class defines the state( cash,name,playing state) of a player as
 * well as their methods (folding,betting,calling,etc). It is also the superclass of the Computer class
 *
 * @author June
 *
 */

public class Player {

	private String name;
	private int cash;

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

	/*
	 * userCurrentBet is a value that the player has bet in a given round. It is used to during play to check
	 * whether the players bet has matched the currentbet of the round in case that a raise was checked. If a
	 * raise has been made and the players userCurrentBet does not match with the currentBet, the player is expected
	 * to call the difference in order to play
	 */
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

	/*
	 * playerStatus determines if the player is "in" or "out". If a player has fold(), then the playerStatus
	 * is put to "false" and they are skipped for the remained of the game
	 */
	private boolean playerStatus = true;

	public boolean isPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(boolean playerStatus) {
		this.playerStatus = playerStatus;
	}

	/*
	 * the hand list refers to the players 2 cards that are given to them in the beginning of the game.
	 * setHand() removes two cards from the Decks list in Deck class and puts it in the hand list.
	 */
	private List<Cards> hand = new ArrayList<>(2);

	public List<Cards> getHand() {
		return hand;
	}

	public List<Cards> setHand() {
		for (int i = 0; i < 2; i++) {
			hand.add(Deck.getDeck().removeFirst());
		}
		return hand;
	}

	/*
	 * sets PlayerStatus to false so that they cannot play in the remainder of the game
	 */
	void fold() {
		setPlayerStatus(false);
	}

	/*
	 * call allows the user to match the currentBet. The call is subtracted from their cash and put in the
	 * betting pool. The players userCurrentBet is also set to equal with the currentBet as they constributed
	 * the same value. If however, a raise has been made, the call method invokes a different set of methods that
	 * allow them to call the difference between their last call and the raised amounts.
	 */
	void call() {
		if (GameController.raiseCounter > 0) {
			setCash(getCash() - (GameController.getCurrentBet() - getUserCurrentBet()));
			GameController.setBettingPool(
					GameController.getBettingPool() + (GameController.getCurrentBet() - getUserCurrentBet()));
		} else {
			setCash(getCash() - GameController.getCurrentBet());
			GameController.setBettingPool(GameController.getBettingPool() + GameController.getCurrentBet());
		}
	}

	/*
	 * if a player chooses to raise, the currentBet increases by a given amount and put in the betting pool.
	 *
	 * @param  amount
	 * 			the desired raise
	 */
	void raise(int amount) {
		GameController.setCurrentBet(GameController.getCurrentBet() + amount);
		setCash(getCash() - GameController.getCurrentBet());
		GameController.setBettingPool(GameController.getBettingPool() + GameController.getCurrentBet());
	}

	boolean check() {

		if (GameController.getCurrentBet() == 0)
			return true;
		else
			return false;
	}

	/* only available at the start of each round. Sets the desired bet for the round
	 * @param initial
	 * 			the starting bet in the beginning of the round
	 */
	void initialBet(int initial) {
		GameController.setCurrentBet(initial);// establish current bet
		setCash(getCash() - initial); // removes bet from cash
		GameController.setBettingPool(GameController.getBettingPool() + initial);

	}

}
