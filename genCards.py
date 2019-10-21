#!/usr/bin/python

def gencards():
    return ", ".join([f"new Card({rank}, Card.suit.{suit})"
    for suit in ["hearts", "diamonds", "clubs", "spades"]
    for rank in range(2, 15)])

print("Card[] cards = new Card[] {" + gencards() + "};")