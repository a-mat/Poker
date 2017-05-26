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
	public static void setCommunityCards(List<Cards> communityCards) {
		GameController.communityCards = communityCards;
	}


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
		player.setName("Player");
		player.setCash(5000);
		player.setHand();

		allUsers.add(player);
		Computer june = new Computer( "June",5000);
		june.setHand();

		allUsers.add(june);
		Computer aaron = new Computer( "Aaron",5000);
		aaron.setHand();

		allUsers.add(aaron);
		Computer joe = new Computer( "Joe",5000);
		joe.setHand();

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
		 scn.close();
	}

	 static void play(){
		  int winnerComboCounter=0;
		 String winner=new String("");
		 String winningHand = new String("");
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
		 System.out.println("turn: "+theTurn());
		 for(int i=0;i<getAllUsers().size();i++){
			 round(getAllUsers().get(i));
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
		 System.out.println("river: "+theRiver());
		 for(int i=0;i<getAllUsers().size();i++){
			 round(getAllUsers().get(i));
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
		 for(Player user: getAllUsers()){
			 System.out.println(user.getName()+" had the hand of "+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
			if(winnerComboCounter< HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints()){
				 winnerComboCounter=  HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints();
				 winner =new String( user.getName());
				 winningHand=new String( String.valueOf(HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand()))));

			}

		 } System.out.println("the winner is "+ winner+" with the hand of "+ winningHand+" and "+winnerComboCounter
				 +" points");

	 }

	public static void main(String[] args){

		play();



	}

}
