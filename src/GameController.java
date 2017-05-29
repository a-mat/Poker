import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The purpose of this class is to control the mechanics of the game. This class deals with the the mechanics of
 * betting into the pool, instantiating players and computers, and community cards needed to play the game in UI class.
 * This class also contains the methods for playing the text based version of Texas Hold 'em as that
 * was the original intent of the project before GUI was considered. It is left in here,despite the change in
 * to accommodate other users that might want to play a text based version with 4 other players and not a computer AI.
 *
 *
 * @author June
 *
 */
public class GameController {
	static int raiseCounter = 0;

	static List<Player> allUsers = new ArrayList<>();

	static boolean betStatus = true;

	public static boolean isBetStatus() {
		return betStatus;
	}

	public static void setBetStatus(boolean betStatus) {
		GameController.betStatus = betStatus;
	}

	static List<Cards> communityCards = new ArrayList<>();

	public static void setCommunityCards(List<Cards> communityCards) {
		GameController.communityCards = communityCards;
	}

	static private int bettingPool = 0;
	static private int currentBet;

	public static int getBettingPool() {
		return bettingPool;
	}

	public static void setBettingPool(int bettingPool) {
		GameController.bettingPool = bettingPool;
	}

	public static int getCurrentBet() {
		return currentBet;
	}

	public static void setCurrentBet(int currentBet) {
		GameController.currentBet = currentBet;
	}

	static List<Cards> getCommunityCards() {
		return communityCards;
	}

	/*
	 * Flop removes three cards from the Decks and put it in the communityCards ArrayList
	 *
	 * @return List of communityCards
	 */
	static List<Cards> theFlop() {
		for (int i = 0; i < 3; i++) {
			communityCards.add(Deck.getDeck().removeFirst());
		}
		return communityCards;
	}

	/*
	 * theTurn() removes one additional card from Decks into communityCards ArrayList
	 *
	 * @return List of communityCards
	 */
	static List<Cards> theTurn() {
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}

	/*
	 * theRiver() removes one additional card from Decks into communityCards ArrayList
	 *
	 * @return List of communityCards
	 */

	static List<Cards> theRiver() {
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}

	/*
	 * instantiates the player and computer with cash, name, and hand. This is done after the Decks is populated
	 * and shuffled. This is where the default values for cash and name are stored. It also puts all the
	 * participants into a List called allUsers to easily retrive their information for the GUI
	 */
	static void setAllUsers() {
		Deck.populateDeck();
		Deck.shuffleDeck();

		Player player = new Player();
		player.setName("Player");
		player.setCash(5000);
		player.setHand();
		allUsers.add(player);

		Computer june = new Computer("June", 5000);
		june.setHand();
		allUsers.add(june);

		Computer aaron = new Computer("Aaron", 5000);
		aaron.setHand();
		allUsers.add(aaron);

		Computer joe = new Computer("Joe", 5000);
		joe.setHand();
		allUsers.add(joe);

	}

	static List<Player> getAllUsers() {
		return allUsers;
	}

	/*
	 * round() is essentially legacy code that is no longer needed for the GUI. round() function was to go through
	 * each player and computer with their options at each round.
	 * @param user
	 * 			Each user in the game is given a round() to figure out their next move
	 *
	 */
	static void round(Player user) {
		Scanner scn = new Scanner(System.in);

		System.out.println(user + "hand: " + user.getHand());

		if (user.isPlayerStatus() == true) {
			if (isBetStatus() == false) {
				System.out.println("raise fold or call");
				switch (scn.nextInt()) {
				case (0):
					(user).raise(scn.nextInt());
					user.setUserCurrentBet(getCurrentBet());
					raiseCounter = 1;
					System.out.println("what the user put the round " + user.getUserCurrentBet());
					System.out.println("the current bet of the round " + getCurrentBet());
					System.out.println("the total betting pool " + getBettingPool());
					break;
				case (1):
					user.setPlayerStatus(false);
					break;
				case (2):
					user.call();
					user.setUserCurrentBet(getCurrentBet());

					System.out.println("what the user put the round " + user.getUserCurrentBet());
					System.out.println("the current bet of the round " + getCurrentBet());
					System.out.println("the total betting pool " + getBettingPool());
					break;
				}

			}

			if (isBetStatus() == true) {
				System.out.println("bet fold or check");
				switch (scn.nextInt()) {
				case (0):
					(user).initialBet(scn.nextInt());
					setBetStatus(false);
					user.setUserCurrentBet(getCurrentBet());
					System.out.println("what the user put the round " + user.getUserCurrentBet());
					System.out.println("the current bet of the round " + getCurrentBet());
					System.out.println("the total betting pool " + getBettingPool());
					// System.out.println(isBetStatus());
					break;
				case (1):
					user.fold();
					break;
				case (2):
					user.check();
					break;

				}
			}

		}
		scn.close();
	}

	/*
	 * play() controls the flow of the game. It goes through each round and once everyone is done, community cards
	 * are placed and everything is repeated untill the theRiver() method puts the last communityCards.
	 * THen the winner is determined by loading the users cards into HandEvalualtor's checkHand() method.
	 */
	static void play() {
		int winnerComboCounter = 0;
		String winner = new String("");
		String winningHand = new String("");
		setAllUsers();
		for (int i = 0; i < getAllUsers().size(); i++) {
			round(getAllUsers().get(i));
			System.out.println("what the user put the round " + getAllUsers().get(i).getUserCurrentBet());
			System.out.println("the current bet of the round " + getCurrentBet());
			System.out.println("the total betting pool " + getBettingPool());
			if (i == getAllUsers().size() - 1) {
				for (Player user : getAllUsers()) {
					System.out.println(getCurrentBet());
					System.out.println(user.getUserCurrentBet());
					if (getCurrentBet() != user.getUserCurrentBet()) {
						raiseCounter = 1;
						round(user);
					}
				}
			}
		}
		setBetStatus(true);
		System.out.println("betting pool: " + getBettingPool());

		System.out.println("flop: " + theFlop());
		for (int i = 0; i < getAllUsers().size(); i++) {
			round(getAllUsers().get(i));
			if (i == getAllUsers().size() - 1) {
				for (Player user : getAllUsers()) {
					System.out.println(getCurrentBet());
					System.out.println(user.getUserCurrentBet());
					if (getCurrentBet() != user.getUserCurrentBet()) {
						raiseCounter = 1;
						round(user);
					}
				}
			}
		}
		setBetStatus(true);
		System.out.println("betting pool: " + getBettingPool());
		System.out.println("turn: " + theTurn());
		for (int i = 0; i < getAllUsers().size(); i++) {
			round(getAllUsers().get(i));
			if (i == getAllUsers().size() - 1) {
				for (Player user : getAllUsers()) {
					System.out.println(getCurrentBet());
					System.out.println(user.getUserCurrentBet());
					if (getCurrentBet() != user.getUserCurrentBet()) {
						raiseCounter = 1;
						round(user);
					}
				}
			}
		}
		setBetStatus(true);
		System.out.println("betting pool: " + getBettingPool());
		System.out.println("river: " + theRiver());
		for (int i = 0; i < getAllUsers().size(); i++) {
			round(getAllUsers().get(i));
			if (i == getAllUsers().size() - 1) {
				for (Player user : getAllUsers()) {
					System.out.println(getCurrentBet());
					System.out.println(user.getUserCurrentBet());
					if (getCurrentBet() != user.getUserCurrentBet()) {
						raiseCounter = 1;
						round(user);
					}
				}
			}
		}
		setBetStatus(true);
		for (Player user : getAllUsers()) {
			System.out.println(user.getName() + " had the hand of "
					+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
			if (winnerComboCounter < HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints()) {
				winnerComboCounter = HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints();
				winner = new String(user.getName());
				winningHand = new String(
						String.valueOf(HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand()))));

			}

		}
		System.out.println("the winner is " + winner + " with the hand of " + winningHand + " and " + winnerComboCounter
				+ " points");

	}

	public static void main(String[] args) {

		play();

	}

}
