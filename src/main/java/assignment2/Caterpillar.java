package assignment2;

import java.awt.Color;
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
		this.positionsPreviouslyOccupied.push(p);
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
		/*
		 * TODO: ADD YOUR CODE HERE
		 */
		// Check if position is orthogonal to the current head position
		int xHead = getHeadPosition().getX();
		int yHead = getHeadPosition().getY();
		int x = p.getX();
		int y = p.getY();
		if (x == xHead && Math.abs(y - yHead) == 1 || y == yHead && Math.abs(x - xHead) == 1) {
			// TODO
			return;
		} else {
			throw new IllegalArgumentException("The caterpillar can only move to an adjacent position.");
		}
	}



	// a segment of the fruit's color is added at the end
	public void eat(Fruit f) {
		// Create new segment at tail of caterpillar with position of tail and color of fruit
		Position tailPosition = positionsPreviouslyOccupied.peek(); // TODO Pop or Peek?
		Segment newSegment = new Segment(tailPosition, f.getColor());
		newSegment.next = tail;
		tail = newSegment;
		length++;
	}

	
	// the caterpillar moves one step backwards because of sourness
	public void eat(Pickle p) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */	
	}


	// all the caterpillar's colors shuffle around
	public void eat(Lollipop lolly) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */	
	}

	// brain freeze!!
	// It reverses and its (new) head turns blue
	public void eat(IceCream gelato) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */	
	}


	// the caterpillar embodies a slide of Swiss cheese loosing half of its segments. 
	public void eat(SwissCheese cheese) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */	
	} 


	
	public void eat(Cake cake) {
		/*
		 * TODO: ADD YOUR CODE HERE
		 */	
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