import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

public class CardCollection {
    private Map<String, Collection<Card>> cardMap;

    public CardCollection() {
        cardMap = new HashMap<>();
    }

    public void addCard(Card card) {
        cardMap.putIfAbsent(card.getSuit(), new ArrayList<>());
        cardMap.get(card.getSuit()).add(card);
    }

    public Collection<Card> getCardsBySuit(String suit) {
        return cardMap.getOrDefault(suit, new ArrayList<>());
    }

    public static void main(String[] args) {
        CardCollection cardCollection = new CardCollection();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Card Collection Program!");
        System.out.println("Commands: add [rank] [suit], find [suit], exit");

        while (true) {
            System.out.print("Enter command: ");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = command.split(" ");
            if (parts.length < 2) {
                System.out.println("Invalid command. Please try again.");
                continue;
            }

            String action = parts[0].toLowerCase();
            if (action.equals("add") && parts.length == 3) {
                String rank = parts[1];
                String suit = parts[2];
                Card card = new Card(rank, suit);
                cardCollection.addCard(card);
                System.out.println("Added: " + card);
            } else if (action.equals("find") && parts.length == 2) {
                String suit = parts[1];
                Collection<Card> cards = cardCollection.getCardsBySuit(suit);
                if (cards.isEmpty()) {
                    System.out.println("No cards found for suit: " + suit);
                } else {
                    System.out.println("Cards in suit " + suit + ": " + cards);
                }
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting the Card Collection Program. Goodbye!");
    }
}
