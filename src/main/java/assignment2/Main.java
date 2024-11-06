package assignment2;

import assignment2.food.Fruit;
import assignment2.food.Lollipop;

public class Main {
    public static void main(String[] args) {
        Position startingPoint = new Position(2, 3);
        Caterpillar gus = new Caterpillar(startingPoint, GameColors.RED, 10);

        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2,2));
        gus.eat(new Fruit(GameColors.YELLOW));
        System.out.println("\n2) Gus: " + gus);

        gus.move(new Position(2,1));
        gus.eat(new Fruit(GameColors.BLUE));
        System.out.println("\n3) Gus: " + gus);

        gus.move(new Position(3,1));
        System.out.println("\n4) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.eat(new Lollipop());
        System.out.println("\n5) Gus: " + gus);
    }
}
