import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * This class is responsible in running the whole UI. There are two scenes, the first being an introductory scene
 * while the second scene allows the users to play
 *
 * There are also two methods to control the AI in the game.
 *
 */
public class UI extends Application {

	Scene firstScene;
	Scene secondScene;
	Player player = new Player();

	Label playa;
	GridPane rootNode2 = new GridPane();
	Button dealCrd = new Button("Deal");
	Button betBtn = new Button("Bet");
	// Button checkBtn = new Button("Check");
	Button foldBtn = new Button("Fold");
	Button raiseBtn = new Button("Raise");
	Button callBtn = new Button("Call");
	TextField betText = new TextField();
	Label potLabel = new Label("Pot: " + String.valueOf(GameController.getBettingPool()));
	Label currentBetLabel = new Label("bet: " + String.valueOf(GameController.getCurrentBet()));
	Label bottomInfo = new Label("");
	Button playAgainBtn = new Button("Play Again");

	int commCard = 0;
	Label communityCard = new Label("");
	int commCheck = 0;
	String compAction = new String("");
	Label juneStatus = new Label("");
	Label aaronStatus = new Label("");
	Label joeStatus = new Label("");
	Label juneCash;
	Label aaronCash;
	Label joeCash;
	String winner = new String("");

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage myStage) {

		myStage.setTitle("Poker");
		GridPane rootNode = new GridPane();
		rootNode.setHgap(10);
		rootNode.setVgap(10);
		rootNode.setPadding(new Insets(0, 10, 0, 10));
		Scene firstScene = new Scene(rootNode, 600, 200);
		myStage.setScene(firstScene);

		// first Scene

		Label name = new Label("Welcome to Poker!");
		rootNode.add(name, 23, 1);

		Button sbBtn = new Button("Play");
		rootNode.add(sbBtn, 23, 5);

		sbBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				myStage.setScene(secondScene);

			}
		});

		/*
		 * //second scene where the game is actually played
		 */

		rootNode2 = new GridPane();
		rootNode2.setHgap(10);
		rootNode2.setVgap(10);
		rootNode2.setPadding(new Insets(0, 10, 0, 10));
		secondScene = new Scene(rootNode2, 900, 400);

		// player info

		GameController.setAllUsers();
		Label playa = new Label(String.valueOf(GameController.getAllUsers().get(0)));
		Label playaCash = new Label(String.valueOf(GameController.getAllUsers().get(0).getCash()));
		Label playaStatus = new Label("");
		rootNode2.add(playa, 28, 11);
		rootNode2.add(playaCash, 28, 12);
		rootNode2.add(playaStatus, 53, 0);

		// default comp setup

		Label june = new Label(String.valueOf(GameController.getAllUsers().get(1)));
		juneCash = new Label(String.valueOf(GameController.getAllUsers().get(1).getCash()));
		juneStatus = new Label("");
		rootNode2.add(june, 0, 5);
		rootNode2.add(juneCash, 0, 6);
		rootNode2.add(juneStatus, 53, 2);
		Label aaron = new Label(String.valueOf(GameController.getAllUsers().get(2)));
		aaronCash = new Label(String.valueOf(GameController.getAllUsers().get(2).getCash()));
		aaronStatus = new Label("");
		rootNode2.add(aaron, 28, 0);
		rootNode2.add(aaronCash, 28, 1);
		rootNode2.add(aaronStatus, 53, 4);
		Label joe = new Label(String.valueOf(GameController.getAllUsers().get(3)));
		joeCash = new Label(String.valueOf(GameController.getAllUsers().get(3).getCash()));
		joeStatus = new Label("");
		rootNode2.add(joe, 45, 5);
		rootNode2.add(joeCash, 45, 6);
		rootNode2.add(joeStatus, 53, 6);

		/*
		 * Info on the screen. Current Bet, current pool, users Hand
		 */
		Label crdLabel = new Label("Hand: " + String.valueOf(GameController.getAllUsers().get(0).getHand()));
		Label currentBetLabel = new Label("bet: " + String.valueOf(GameController.getCurrentBet()));
		Label potLabel = new Label("Pot: " + String.valueOf(GameController.getBettingPool()));
		rootNode2.add(currentBetLabel, 0, 0);
		rootNode2.add(potLabel, 45, 0);
		rootNode2.add(bottomInfo, 25, 19, 20, 1);

		// text box that appears if user raises or bets
		TextField betText = new TextField();
		betText.setPromptText("How much?");
		TextField raiseText = new TextField();

		/*
		 * Deal card is the first button the user sees on the second screen.
		 * When clicked, the user gets his hand, and the game begins. Bet and
		 * fold is put
		 */
		rootNode2.add(dealCrd, 0, 12);
		dealCrd.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				rootNode2.getChildren().remove(dealCrd);
				rootNode2.add(crdLabel, 0, 11, 30, 12);

				bottomInfo.setWrapText(true);
				rootNode2.add(betBtn, 25, 15);
				// rootNode2.add(checkBtn, 30, 14);
				rootNode2.add(foldBtn, 35, 15);

			}
		});

		/*
		 * Bet checks removes the other buttons and puts the text box
		 *
		 */
		betBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				// rootNode2.getChildren().remove(checkBtn);
				rootNode2.getChildren().remove(foldBtn);
				rootNode2.getChildren().remove(betBtn);
				rootNode2.add(betText, 27, 15, 2, 1);
			}
		});

		/*
		 *try and catch to make sure the only a number is added
		 *the player invokes initialBet() method from the player class and all fields in the UI second scene
		 *are updated to reflect those changes( player.cash, bettingpool, currentBet, etc)
		 *The computer AI is invoked to make the computers choose their actions based on chances
		 *if a raise was detected ( if players userCurrentBet != currentBet), then the raise and call btns are added
		 * and the program stops. If not , commCards are put on the screen based on a commCard counter and the bet
		 * button is put back to restart. commCard acts as a "round" counter
		 */
		betText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Integer.parseInt(betText.getText());

					if (Integer.parseInt(betText.getText()) > GameController.getAllUsers().get(0).getCash())
						bottomInfo.setText("Enter a bet less than your total cash");
					else {
						GameController.raiseCounter = 0;
						bottomInfo.setText("");
						GameController.getAllUsers().get(0).initialBet(Integer.valueOf(betText.getText()));
						GameController.setBetStatus(false);
						GameController.getAllUsers().get(0).setUserCurrentBet(GameController.getCurrentBet());
						System.out.println("player current" + GameController.getAllUsers().get(0).getUserCurrentBet());

						currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
						potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
						playaCash.setText(String.valueOf(GameController.getAllUsers().get(0).getCash()));
						playaStatus.setText("Player Bet " + betText.getText() + " dollars");
						System.out.println("should be 10 less " + GameController.getAllUsers().get(0).getCash());

						rootNode2.getChildren().remove(betText);
						computerAi();

						if (GameController.getCurrentBet() != GameController.getAllUsers().get(0).getUserCurrentBet()) {

							rootNode2.add(raiseBtn, 30, 14);
							rootNode2.add(callBtn, 35, 14);
							rootNode2.add(foldBtn, 38, 14);
						} else {
							commCheck++;
							System.out.println("commcheck after it has risen in bet" + commCheck);
							if (commCheck == 1) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theFlop()).replace("[", "").replace("]", ""));
								communityCard.setWrapText(true);
								rootNode2.add(communityCard, 4, 5, 40, 1);
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 2) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theTurn()).replace("[", "").replace("]", ""));
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 3) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theRiver()).replace("[", "").replace("]", ""));
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 4) {
								System.out.println(GameController.getCommunityCards());
								GameController.raiseCounter = 0;
								int winnerComboCounter = 0;

								String winningHand = new String("");
								for (Player user : GameController.getAllUsers()) {
									if (user.isPlayerStatus() == true) {
										if (user.getName().equals("Player"))
											playaStatus.setText("Player had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("June"))
											juneStatus.setText("June had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("Aaron"))
											aaronStatus.setText("Aaron had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("Joe"))
											joeStatus.setText("Joe had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										System.out.println(user.getName() + " had the hand of "
												+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (winnerComboCounter < HandEvaluator
												.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints()) {
											winnerComboCounter = HandEvaluator
													.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints();
											winner = new String(user.getName());
											winningHand = new String(String.valueOf(
													HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand()))));

										}
									}
								}
								System.out.println("the winner is " + winner + " with the hand of " + winningHand
										+ " and " + winnerComboCounter + " points");
								bottomInfo.setText("the winner is " + winner + " with the hand of " + winningHand);
								rootNode2.add(playAgainBtn, 53, 12);

							}
						}
						juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
						aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
						joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));
						currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
						potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));

					}
				} catch (NumberFormatException e) {
					bottomInfo.setText("please put a valid number");
				}
			}
		});
		/*
		 * the user sets his status as false and the computer AI finishes the rest of the game
		 */
		foldBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				while (commCheck != 4) {
					GameController.getAllUsers().get(0).setPlayerStatus(false);
					rootNode2.getChildren().remove(foldBtn);
					rootNode2.getChildren().remove(betBtn);
					// rootNode2.getChildren().remove(checkBtn);
					playaStatus.setText("Player folded");
					computerAi();

					if (GameController.getCurrentBet() != GameController.getAllUsers().get(1).getUserCurrentBet()) {
						// need to do more algo
					} else
						commCheck++;

					juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
					aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
					joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));
					currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
					potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
					if (commCheck == 1) {
						System.out.println(GameController.getCommunityCards());
						GameController.raiseCounter = 0;
						communityCard
								.setText(String.valueOf(GameController.theFlop()).replace("[", "").replace("]", ""));
						communityCard.setWrapText(true);
						rootNode2.add(communityCard, 4, 5, 40, 1);

					}
					if (commCheck == 2) {
						System.out.println(GameController.getCommunityCards());
						GameController.raiseCounter = 0;
						communityCard
								.setText(String.valueOf(GameController.theTurn()).replace("[", "").replace("]", ""));

					}
					if (commCheck == 3) {
						System.out.println(GameController.getCommunityCards());
						GameController.raiseCounter = 0;
						communityCard
								.setText(String.valueOf(GameController.theRiver()).replace("[", "").replace("]", ""));

					}
					if (commCheck == 4) {
						GameController.raiseCounter = 0;
						int winnerComboCounter = 0;

						String winningHand = new String("");
						for (Player user : GameController.getAllUsers()) {
							if (user.isPlayerStatus() == true) {
								if (user.getName().equals("Player"))
									playaStatus.setText("Player had"
											+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
								if (user.getName().equals("June"))
									juneStatus.setText("June had"
											+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
								if (user.getName().equals("Aaron"))
									aaronStatus.setText("Aaron had"
											+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
								if (user.getName().equals("Joe"))
									joeStatus.setText("Joe had"
											+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
								System.out.println(user.getName() + " had the hand of "
										+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
								if (winnerComboCounter < HandEvaluator
										.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints()) {
									winnerComboCounter = HandEvaluator
											.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints();
									winner = new String(user.getName());
									winningHand = new String(String
											.valueOf(HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand()))));

								}
							}
						}
						System.out.println("the winner is " + winner + " with the hand of " + winningHand + " and "
								+ winnerComboCounter + " points");
						bottomInfo.setText("the winner is " + winner + " with the hand of " + winningHand);
						rootNode2.add(playAgainBtn, 53, 12);

					}

				}
			}
		});
		raiseBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				GameController.raiseCounter++;
				if (GameController.raiseCounter > 2)
					bottomInfo.setText("Raise cant be used twice");
				else {
					rootNode2.getChildren().remove(raiseBtn);
					rootNode2.getChildren().remove(callBtn);
					rootNode2.getChildren().remove(foldBtn);
					rootNode2.add(raiseText, 27, 16, 2, 1);
				}
			}
		});
		raiseText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Integer.parseInt(betText.getText());

					if (Integer.parseInt(betText.getText()) > GameController.getAllUsers().get(0).getCash())
						bottomInfo.setText("Enter a bet less than your total cash");
					else {
						bottomInfo.setText("");
						GameController.getAllUsers().get(0).raise(Integer.valueOf(raiseText.getText()));
						GameController.getAllUsers().get(0).setUserCurrentBet(GameController.getCurrentBet());
						playaCash.setText(String.valueOf(GameController.getAllUsers().get(0).getCash()));
						currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
						potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
						rootNode2.getChildren().remove(raiseText);
						for (int i = 1; i < GameController.getAllUsers().size(); i++) {
							if (i == 2) {
								round(GameController.getAllUsers().get(i), 2, 3);
								System.out.println(GameController.getAllUsers().get(i) + "called");
								GameController.getAllUsers().get(i).setUserCurrentBet(GameController.getCurrentBet());
							} else {
								round(GameController.getAllUsers().get(i), 2, 3); // make
																					// alhorithim
								System.out.println(GameController.getAllUsers().get(i) + "called");
								GameController.getAllUsers().get(i).setUserCurrentBet(GameController.getCurrentBet());
							}
							juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
							aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
							joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));
							currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
							potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
						}

					}
				} catch (NumberFormatException e) {
					bottomInfo.setText("please put a valid number");
				}
			}
		});
		callBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				GameController.raiseCounter++;
				GameController.getAllUsers().get(0).call();
				GameController.getAllUsers().get(0).setUserCurrentBet(GameController.getCurrentBet());
				playaCash.setText(String.valueOf(GameController.getAllUsers().get(0).getCash()));
				currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
				potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));

				rootNode2.getChildren().remove(raiseBtn);
				rootNode2.getChildren().remove(foldBtn);
				rootNode2.getChildren().remove(callBtn);
				for (int i = 1; i < GameController.getAllUsers().size(); i++) {
					if (GameController.getAllUsers().get(i).isPlayerStatus() == true) {
						if (GameController.getCurrentBet() == GameController.getAllUsers().get(i).getUserCurrentBet()) {
							System.out.println("call method. it becomes equals so comm cards come out");
							commCheck++;
							System.out.println("commcheck after it has called" + commCheck);
							if (commCheck == 1) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theFlop()).replace("[", "").replace("]", ""));
								communityCard.setWrapText(true);
								rootNode2.add(communityCard, 4, 5, 40, 1);
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 2) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theTurn()).replace("[", "").replace("]", ""));
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 3) {

								GameController.raiseCounter = 0;
								communityCard.setText(
										String.valueOf(GameController.theRiver()).replace("[", "").replace("]", ""));
								rootNode2.add(betBtn, 25, 14);
								// rootNode2.add(checkBtn, 30, 14);
								rootNode2.add(foldBtn, 35, 14);
								System.out.println(GameController.getCommunityCards());
							}
							if (commCheck == 4) {
								System.out.println(GameController.getCommunityCards());
								GameController.raiseCounter = 0;
								int winnerComboCounter = 0;

								String winningHand = new String("");
								for (Player user : GameController.getAllUsers()) {
									if (user.isPlayerStatus() == true) {
										if (user.getName().equals("Player"))
											playaStatus.setText("Player had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("June"))
											juneStatus.setText("June had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("Aaron"))
											aaronStatus.setText("Aaron had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (user.getName().equals("Joe"))
											joeStatus.setText("Joe had"
													+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										System.out.println(user.getName() + " had the hand of "
												+ HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand())));
										if (winnerComboCounter < HandEvaluator
												.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints()) {
											winnerComboCounter = HandEvaluator
													.checkHand(HandEvaluator.loadCards(user.getHand())).getPoints();
											winner = new String(user.getName());
											winningHand = new String(String.valueOf(
													HandEvaluator.checkHand(HandEvaluator.loadCards(user.getHand()))));

										}
									}
								}
								System.out.println("the winner is " + winner + " with the hand of " + winningHand
										+ " and " + winnerComboCounter + " points");
								bottomInfo.setText("the winner is " + winner + " with the hand of " + winningHand);
								rootNode2.add(playAgainBtn, 53, 12);

							}
							break;
						}
						if (i == 2) {
							round(GameController.getAllUsers().get(i), 2, 3);
							GameController.getAllUsers().get(i).setUserCurrentBet(GameController.getCurrentBet());
						} else {

							round(GameController.getAllUsers().get(i), 2, 3); // make
																				// alhorithim
							GameController.getAllUsers().get(i).setUserCurrentBet(GameController.getCurrentBet());
						}
						juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
						aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
						joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));
						currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
						potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
					}
				}
			}
		});

		playAgainBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				Deck.getDeck().clear();
				Deck.populateDeck();
				Deck.shuffleDeck();
				for (Player user : GameController.getAllUsers()) {

					if (user.getName().equals(winner)) {
						user.setCash(user.getCash() + GameController.getBettingPool());
					}
					System.out.println(user + " " + user.getHand());
					user.setUserCurrentBet(0);
					user.getHand().clear();
					user.setHand();
					System.out.println(user + " " + user.getHand());
				}
				GameController.setCurrentBet(0);
				GameController.setBettingPool(0);
				currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
				potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
				playaCash.setText(String.valueOf(GameController.getAllUsers().get(0).getCash()));
				juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
				aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
				joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));

				playaStatus.setText("");
				juneStatus.setText("");
				aaronStatus.setText("");
				joeStatus.setText("");
				bottomInfo.setText("");
				communityCard.setText("");
				GameController.getCommunityCards().clear();
				commCheck = 0;
				rootNode2.getChildren().remove(playAgainBtn);
				crdLabel.setText("Hand: " + String.valueOf(GameController.getAllUsers().get(0).getHand()));
				rootNode2.getChildren().remove(crdLabel);
				rootNode2.add(dealCrd, 0, 12);

			}
		});
		myStage.show();

	}

	/*
	 * allows computers to make moves depending on the parameters put in
	 * @param user
	 * 			the computer that is making the decision
	 * @choice
	 * 			whether the computer raise, call or fold
	 * @raise
	 * 			the raise amount the computer wants to raise. If call or fold is selected, then raise gets ignored
	 *
	 */
	void round(Player user, int choice, int raise) {

		if (user.isPlayerStatus() == true) {
			if (GameController.isBetStatus() == false) {

				switch (choice) {
				case (0):
					(user).raise(raise);
					GameController.raiseCounter++;
					user.setUserCurrentBet(GameController.getCurrentBet());
					GameController.raiseCounter++;
					currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
					potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
					System.out.println("what the user put the round " + user.getUserCurrentBet());
					System.out.println("the current bet of the round " + GameController.getCurrentBet());
					System.out.println("the total betting pool " + GameController.getBettingPool());
					compAction = "raise " + raise;
					break;
				case (1):
					user.setPlayerStatus(false);
					compAction = "fold";
					break;
				case (2):
					user.call();
					System.out.println(GameController.getCurrentBet());
					user.setUserCurrentBet(GameController.getCurrentBet());
					currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
					potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
					compAction = "Call";
					break;
				}

			}



		}
	}

	/*
	 * the computerAi() decides what move to make. THis is more chance rather than actual intelligence.
	 * A computer is set to pick call more likely than raise and fold.
	 */
	void computerAi() {
		int chance;
		int choice = 1;
		int raiseChoice = 1;
		for (int i = 1; i < GameController.getAllUsers().size(); i++) {
			if (GameController.getAllUsers().get(i).isPlayerStatus() == true) {
				if (GameController.raiseCounter > 2) {
					System.out.println("not raise");
					chance = (int) (Math.random() * 101);
					if (chance < 90)
						choice = 2;
					if (chance >= 90)
						choice = 1;
				} else {
					System.out.println(" raise");
					chance = (int) (Math.random() * 101);
					if (chance < 75)
						choice = 2;
					if (chance >= 75 && chance < 95) {

						choice = 0;
						if (GameController.getCurrentBet() <= 500)
							raiseChoice = (int) ((GameController.getCurrentBet()) * .90);
						if (GameController.getCurrentBet() > 500 && GameController.getCurrentBet() < 1000)
							raiseChoice = (int) ((GameController.getCurrentBet()) * .50);
						if (GameController.getCurrentBet() >= 1000)
							raiseChoice = (int) ((GameController.getCurrentBet()) * .20);
					}
					if (chance >= 95)
						choice = 1;

				}

				round(GameController.getAllUsers().get(i), choice, raiseChoice);
				GameController.getAllUsers().get(i).setUserCurrentBet(GameController.getCurrentBet());
				currentBetLabel.setText("bet: " + String.valueOf(GameController.getCurrentBet()));
				potLabel.setText("Pot: " + String.valueOf(GameController.getBettingPool()));
				if (i == 1) {
					juneStatus.setText("June " + compAction);
					juneCash.setText(String.valueOf(GameController.getAllUsers().get(1).getCash()));
				}
				if (i == 2) {
					aaronStatus.setText("Aaron " + compAction);
					aaronCash.setText(String.valueOf(GameController.getAllUsers().get(2).getCash()));
				}
				if (i == 3) {
					joeStatus.setText("Joe" + compAction);
					joeCash.setText(String.valueOf(GameController.getAllUsers().get(3).getCash()));
				}
			}
		}

	}

}
