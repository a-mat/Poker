import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
	static int raiseCounter=0;
	static List<Player> allUsers = new ArrayList<>();


	static boolean betStatus=true;

	public static boolean isBetStatus() {
		return betStatus;
	}

	public static void setBetStatus(boolean betStatus) {
		GameController.betStatus = betStatus;
	}


	static List<Cards> communityCards = new ArrayList<>();
	static private int bettingPool=0;
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

	public static  void setCurrentBet(int currentBet) {
		GameController.currentBet = currentBet;
	}

	static List<Cards> getCommunityCards(){
		return communityCards;
	}
	static List<Cards> theFlop(){
		for(int i=0;i<3;i++){
			communityCards.add(Deck.getDeck().removeFirst());
		}
		return communityCards;
	}
	static List<Cards> theTurn(){
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}
	static List<Cards> theRiver(){
		communityCards.add(Deck.getDeck().removeFirst());
		return communityCards;
	}

	static void setAllUsers(){
		Deck.populateDeck();
		Deck.shuffleDeck();
		System.out.println(Deck.getDeck());
		Player player = new Player();
		player.setName("player");
		player.setCash(5000);
		player.setHand();
		System.out.println(player.getHand());
		allUsers.add(player);
		Computer june = new Computer( "June",20000);
		june.setHand();
		System.out.println(june.getHand());
		allUsers.add(june);
		Computer aaron = new Computer( "Aaron",500);
		aaron.setHand();
		System.out.println(aaron.getHand());
		allUsers.add(aaron);
		Computer joe = new Computer( "Joe",8000);
		joe.setHand();
		System.out.println(joe.getHand());
		System.out.println(Deck.getDeck());
		allUsers.add(joe);

	}
	static List<Player> getAllUsers(){
		return allUsers;
	}

	 static void round(Player user){
		Scanner scn = new Scanner(System.in);

		 System.out.println(user+"hand: "+ user.getHand());


		 if(user.isPlayerStatus()==true){
			 if(isBetStatus()==false){
					System.out.println("raise fold or call");
					switch(scn.nextInt()){
					case(0): ( user).raise(scn.nextInt());
								user.setUserCurrentBet(getCurrentBet());
								raiseCounter=1;
								System.out.println("what the user put the round "+user.getUserCurrentBet());
								System.out.println("the current bet of the round "+getCurrentBet());
								System.out.println("the total betting pool "+getBettingPool());
								break;
					case(1): user.setPlayerStatus(false);
							break;
					case(2):
							user.call();
							user.setUserCurrentBet(getCurrentBet());

							System.out.println("what the user put the round "+user.getUserCurrentBet());
							System.out.println("the current bet of the round "+getCurrentBet());
							System.out.println("the total betting pool "+getBettingPool());
								break;
					}

				}

			if(isBetStatus()==true){
				 System.out.println("bet fold or check");
				switch(scn.nextInt()){
				case(0): ( user).initialBet(scn.nextInt());
							setBetStatus(false);
							user.setUserCurrentBet(getCurrentBet());
							System.out.println("what the user put the round "+user.getUserCurrentBet());
							System.out.println("the current bet of the round "+getCurrentBet());
							System.out.println("the total betting pool "+getBettingPool());
							//System.out.println(isBetStatus());
							break;
				case(1): user.fold();
						break;
				case(2): user.check();
							break;

				}
			}

		}
	}

	 static void play(){
		 setAllUsers();
		 for(int i=0;i<getAllUsers().size();i++){
			 round(getAllUsers().get(i));
			 System.out.println("what the user put the round "+getAllUsers().get(i).getUserCurrentBet());
				System.out.println("the current bet of the round "+getCurrentBet());
				System.out.println("the total betting pool "+getBettingPool());
			 if(i==getAllUsers().size()-1){
				 for(Player user:getAllUsers()){
					 System.out.println(getCurrentBet());
					 System.out.println(user.getUserCurrentBet());
					 if(getCurrentBet()!=user.getUserCurrentBet()){
						 raiseCounter=1;
						 round(user);
					 }
				 }
			 }
		 }
		 setBetStatus(true);
		 System.out.println("betting pool: "+getBettingPool());

		 System.out.println("flop: "+ theFlop());
		 for(int i=0;i<getAllUsers().size();i++){
			 round(getAllUsers().get(i));
		 }
		 setBetStatus(true);
		 System.out.println("betting pool: "+getBettingPool());
		 System.out.println("turn: "+theTurn());
		 for(int i=0;i<getAllUsers().size();i++){
			 round(getAllUsers().get(i));
		 }
		 setBetStatus(true);
		 System.out.println("betting pool: "+getBettingPool());
		 System.out.println("river: "+theRiver());

	 }

	public static void main(String[] args){
		//setBettingPool(0);
		//play();
		/*setAllUsers();
		System.out.println(getAllUsers().get(2).hand);
		System.out.println(getAllUsers().get(0).hand);

		System.out.println(getAllUsers().get(1).hand);*/

		/*Deck.populateDeck();
		Deck.shuffleDeck();
		Deck.shuffleDeck();
		System.out.println(Deck.getDeck());
		Player poop = new Player();
		poop.setHand();
		poop.setName("heyyo");
		poop.setUserCurrentBet(10);
		System.out.println("user current bet:"+ poop.getUserCurrentBet());

		Player tom = new Computer("Tommy",5);
		Player hohn = new Computer("johm",5);
		tom.setUserCurrentBet(20);
		System.out.println("user current bet:"+ tom.getUserCurrentBet());
		hohn.setUserCurrentBet(25);
		System.out.println("user current bet:"+ hohn.getUserCurrentBet());
		tom.setHand();
		hohn.setHand();
		System.out.println(poop.getHand());
		System.out.println(tom.getHand());
		System.out.println(hohn.getHand());

		System.out.println(Deck.getDeck());
		System.out.println("player: "+poop.isPlayerStatus());
		poop.setPlayerStatus(false);
		System.out.println(poop.isPlayerStatus());

		System.out.println("tom: "+tom.isPlayerStatus());
		tom.setPlayerStatus(false);
		System.out.println(tom.isPlayerStatus());
		System.out.println("hohn: "+hohn.isPlayerStatus());
		hohn.setPlayerStatus(false);
		System.out.println(hohn.isPlayerStatus());


		tom.setUserCurrentBet(50);
		hohn.setUserCurrentBet(60);
		System.out.println(tom.getUserCurrentBet());
		System.out.println(hohn.getUserCurrentBet());

		poop.setUserCurrentBet(90);
		System.out.println(poop.getUserCurrentBet());

		System.out.println(tom.getCash());
		hohn.setCash(20);
		System.out.println(hohn.getCash());

		tom.initialBet(20);
		System.out.println(tom.getCash());
		System.out.println(tom.isPlayerStatus());*/

		//setAllUsers();
		play();



	}

}
