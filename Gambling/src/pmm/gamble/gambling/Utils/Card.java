package pmm.gamble.gambling.Utils;

public class Card implements Comparable<Card> {
	int number;
	Suits suit;
	
	public Card(int number, Suits suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	public Integer getValue() {
		return (13*suit.ordinal() + number);
	}
	
	
	
	public String toString() {
		String numberString = number + "";
		if(number == 11) {
			numberString = "jack";
		}
		if(number == 12) {
			numberString = "queen";
		}
		if(number == 13) {
			numberString = "king";
		}
		return numberString + " of " + suit.toString();// + " (value = "+getValue()+" )";
	}
	
    public int compareTo(Card o) {
        return this.getNumber().compareTo(o.getNumber());
    }
}
