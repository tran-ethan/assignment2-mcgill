package assignment2;

import java.awt.Color;
import java.util.EmptyStackException;
import java.util.Random;

import assignment2.food.*;


public class Caterpillar {
	// All the fields have been declared public for testing purposes
	public Segment head;
	public Segment tail;
	public int length;
	public EvolutionStage stage;

	public MyStack<Position> positionsPreviouslyOccupied;
	public int goal;
	public int turnsNeededToDigest;


	public static Random randNumGenerator = new Random(1);


	// Creates a Caterpillar with one Segment. It is up to students to decide how to implement this.
	public Caterpillar(Position p, Color c, int goal) {
		this.head = this.tail = new Segment(p, c);
		this.goal = goal;
		this.stage = EvolutionStage.FEEDING_STAGE;
		this.length = 1;
		this.positionsPreviouslyOccupied = new MyStack<Position>();
	}

	public EvolutionStage getEvolutionStage() {
		return this.stage;
	}

	public Position getHeadPosition() {
		return this.head.position;
	}

	public int getLength() {
		return this.length;
	}


	// returns the color of the segment in position p. Returns null if such segment does not exist
	public Color getSegmentColor(Position p) {
		Segment chk = this.head;
		for (int i = 0; i < length; i++) {
			if (chk.position.equals(p)) {
				return chk.color;
			}
			chk = chk.next;
		}
		return null;
	}


	// Methods that need to be added for the game to work
	public Color[] getColors(){
		Color[] cs = new Color[this.length];
		Segment chk = this.head;
		for (int i = 0; i < this.length; i++){
			cs[i] = chk.color;
			chk = chk.next;
		}
		return cs;
	}

	public Position[] getPositions(){
		Position[] ps = new Position[this.length];
		Segment chk = this.head;
		for (int i = 0; i < this.length; i++){
			ps[i] = chk.position;
			chk = chk.next;
		}
		return ps;
	}


	// shift all Segments to the previous Position while maintaining the old color
	// the length of the caterpillar is not affected by this
	public void move(Position p) throws IllegalArgumentException {
		// Check if position is orthogonal to the current head position
		int xHead = getHeadPosition().getX();
		int yHead = getHeadPosition().getY();
		int x = p.getX();
		int y = p.getY();

		// Check for adjacent position
		if ((x == xHead && Math.abs(y - yHead) == 1) || (y == yHead && Math.abs(x - xHead) == 1)) {

			// Check for previously occupied position
			Position[] positions = getPositions();

			// Do not check tail position, since it will be moved to the previous position
			for (int i = 0; i < this.length - 1; i++) {
				if (positions[i].equals(p)) {
					this.stage = EvolutionStage.ENTANGLED;
					System.out.println("The caterpillar has entangled itself.");
					break;
				}
			}

			// Move caterpillar by moving each segment to next position
			Segment chk = this.head;
			for (int i = 0; i < length; i++) {
				Position tmp = chk.position;
				chk.position = p;
				p = tmp;
				chk = chk.next;
			}

			// Check edge case if caterpillar moves to tail position
			if (p.equals(tail.position)) {
				return;
			}

			// Add previously occupied position to stack
			positionsPreviouslyOccupied.push(p);

			if (turnsNeededToDigest == 0 && stage != EvolutionStage.ENTANGLED) {
				stage = EvolutionStage.FEEDING_STAGE;
			}

			// Check if caterpillar is still digesting the cake
			if (stage == EvolutionStage.GROWING_STAGE) {
				turnsNeededToDigest--;

				// Generate a random color for the new segment
				int rand = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);
				Color color = GameColors.SEGMENT_COLORS[rand];

				// Add new segment to the tail
				this.tail.next = new Segment(positionsPreviouslyOccupied.pop(), color);
				this.tail = this.tail.next;
				length++;

				// Check if caterpillar has reached goal
				if (length == goal) {
					stage = EvolutionStage.BUTTERFLY;
				}
			}
		} else {
			// Position is not adjacent and orthogonal to head
			throw new IllegalArgumentException("The caterpillar can only move to an adjacent position.");
		}
	}



	// a segment of the fruit's color is added at the end
	public void eat(Fruit f) {
		// Create new segment at tail of caterpillar with position of tail and color of fruit
		Position tailPosition = positionsPreviouslyOccupied.pop();
		tail.next = new Segment(tailPosition, f.getColor());
		tail = tail.next;

		// Adjust length and stage
		length++;
		if (length == goal) {
			stage = EvolutionStage.BUTTERFLY;
		}
	}


	// the caterpillar moves one step backwards because of sourness
	public void eat(Pickle p) {
		Position previous = positionsPreviouslyOccupied.pop();
		Segment chk = this.head;
		for (int i = 0; i < this.length; i++) {
			if (i == this.length - 1) {
				// Since chk.next would be null, we move the tail to the previous position occupied
				chk.position = previous;
			} else {
				// Move every segment to its previous position (next segment in the list)
				chk.position = chk.next.position;
			}
			chk = chk.next;
		}
	}


	// all the caterpillar's colors shuffle around
	public void eat(Lollipop lolly) {
		Color[] colors = this.getColors();

		// Shuffle colors
		for (int i = this.length - 1; i > 0; i--) {
			int j = randNumGenerator.nextInt(i + 1);
			Color tmp = colors[i];
			colors[i] = colors[j];
			colors[j] = tmp;
		}

		// Update colors
		Segment chk = this.head;
		for (int i = 0; i < this.length; i++) {
			chk.color = colors[i];
			chk = chk.next;
		}
	}

	// brain freeze!!
	// It reverses and its (new) head turns blue
	public void eat(IceCream gelato) {
		// Reverse linked list with two pointers
		Segment prev = null;
		Segment chk = this.head;

		while (chk != null) {
			Segment tmp = chk.next;
			chk.next = prev;
			prev = chk;
			chk = tmp;
		}

		this.tail = this.head;
		this.head = prev;
		this.head.color = GameColors.BLUE;

		// Clear previous positions
		positionsPreviouslyOccupied.clear();
	}


	// the caterpillar embodies a slide of Swiss cheese loosing half of its segments.
	public void eat(SwissCheese cheese) {
		// Size of new caterpillar the ceiling of length / 2
		int newLength = (length + 1) / 2;
		// Colors array contains every other color of the caterpillar
		Color[] colors = new Color[newLength];
		Segment chk = this.head;

		// Create otherHalf stack to store unoccupied positions after eating cheese, since we cannot directly push
		// them to the stack of previously occupied positions because they will be in reverse order
		MyStack<Position> otherHalf = new MyStack<>();
		for (int i = 0; i < length; i++){
			// Even integers are colors of the caterpillar
			if (i % 2 == 0) {
				colors[i / 2] = chk.color;
			}
			// Add unoccupied position to stack
			if (i >= newLength) {
				otherHalf.push(chk.position);
			}
			chk = chk.next;
		}

		// Push unoccupied positions to stack of previously occupied positions
		while (!otherHalf.empty()) {
			positionsPreviouslyOccupied.push(otherHalf.pop());
		}

		// Traverse caterpillar and update colors
		chk = this.head;
		for (int i = 0; i < newLength; i++){
			if (i == newLength - 1) {
				// Set tail to last segment
				this.tail = chk;
			}
			chk.color = colors[i];
			chk = chk.next;
		}

		this.tail.next = null;
		this.length = newLength;
	}



	public void eat(Cake cake) {
		this.stage = EvolutionStage.GROWING_STAGE;

		int energy = cake.getEnergyProvided();
		// 1 energy = 1 segment
		for (int i = 0; i < energy; i++) {
			Position toAdd = null;
			try {
				// Could throw an error if stack is empty
				toAdd = positionsPreviouslyOccupied.pop();

				// Check for if the position to add is already being occupied by caterpillar
				Segment chk = this.head;
				while (chk != null) {
					if (chk.position.equals(toAdd)) {
						// Throw exception if position is already occupied
						throw new IllegalStateException();
					}
					chk = chk.next;
				}
				// If no exception was thrown at this point, that means we can add the segment to the tail

				// Generate a random color for the new segment
				int rand = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);
				Color color = GameColors.SEGMENT_COLORS[rand];

				// Add new segment to the tail
				this.tail.next = new Segment(toAdd, color);
				this.tail = this.tail.next;
				length++;

				// Check if caterpillar has reached goal
				if (length == goal) {
					stage = EvolutionStage.BUTTERFLY;
					return;
				}
			} catch (IllegalStateException e) {
				// Exception is thrown if all the energy of the cake cannot be consumed in this function
				// Set turns to remaining energy that has not yet been consumed
				turnsNeededToDigest = energy - i;
				positionsPreviouslyOccupied.push(toAdd);
				break;
			} catch (EmptyStackException e) {
				// If stack is empty, then we cannot add any more segments
				turnsNeededToDigest = energy - i;
				break;
			}
		}

		// Check if all energy has been consumed
		if (turnsNeededToDigest == 0) {
			stage = EvolutionStage.FEEDING_STAGE;
		}
	}



	// This nested class was declared public for testing purposes
	public class Segment {
		private Position position;
		private Color color;
		private Segment next;

		public Segment(Position p, Color c) {
			this.position = p;
			this.color = c;
		}

	}


	public String toString() {
		Segment s = this.head;
		String snake = "";
		while (s!=null) {
			String coloredPosition = GameColors.colorToANSIColor(s.color) +
					s.position.toString() + GameColors.colorToANSIColor(Color.WHITE);
			snake = coloredPosition + " " + snake;
			s = s.next;
		}
		return snake;
	}



	public static void main(String[] args) {
		Position startingPoint = new Position(3, 2);
		Caterpillar gus = new Caterpillar(startingPoint, GameColors.GREEN, 10);

		System.out.println("1) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

		gus.move(new Position(3,1));
		gus.eat(new Fruit(GameColors.RED));
		gus.move(new Position(2,1));
		gus.move(new Position(1,1));
		gus.eat(new Fruit(GameColors.YELLOW));


		System.out.println("\n2) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

		gus.move(new Position(1,2));
		gus.eat(new IceCream());

		System.out.println("\n3) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

		gus.move(new Position(3,1));
		gus.move(new Position(3,2));
		gus.eat(new Fruit(GameColors.ORANGE));


		System.out.println("\n4) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

		gus.move(new Position(2,2));
		gus.eat(new SwissCheese());

		System.out.println("\n5) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

		gus.move(new Position(2, 3));
		gus.eat(new Cake(4));

		System.out.println("\n6) Gus: " + gus);
		System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
	}
}