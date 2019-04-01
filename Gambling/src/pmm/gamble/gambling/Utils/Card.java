package pmm.gamble.gambling.Utils;

public class Card {
	int number;
	Suits suit;
	
	public Card(int number, Suits suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public String toString() {
		if(number == 11) {
			return "jack of " + suit.toString();
		}
		if(number == 12) {
			return "queen of " + suit.toString();
		}
		if(number == 13) {
			return "king of " + suit.toString();
		}
		return number + " of " + suit.toString();
	}
}
