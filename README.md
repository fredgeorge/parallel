# bowling_w_kotlin
An implementation of the Bowling Kata in Kotlin by Fred George
at _fredgeorge@acm.org_ in August 2019.

This implemenation uses an idea from Chet Hendrickson in a conversation
with Ron Jeffries over
this code kata originally credited to Robert "Uncle Bob" Martin.
Chet suggested that each roll be passed to each frame, and the frame can
record, absorb, or ignore (or combinations of this). This resembles the
__Chain of Responsibility Pattern__ (GoF book).

So in this implementation, each roll is passed to each frame instance. A frame
can absorb the roll by returning _null_, or pass on the roll to the next frame
by returning the roll itself. Each frame tracks only the rolls it needs to
create its own score.

A __State Pattern__ (GoF book) is used to model the behavior of using, absorbing, or ignoring 
a roll. Only two _if_ statements are required: One to determine if a strike has
been rolled, and one to determine if a spare has been rolled. Additional states
model the behavior change in each case.

A BowlResult class handles the number of pins knocked down on a roll. This class
should prove useful in future enhancements to validate inputs, i.e. knocking 
down 11 pins is impossible.
