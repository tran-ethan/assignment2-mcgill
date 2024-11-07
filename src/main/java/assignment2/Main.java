package assignment2;

import assignment2.food.Fruit;
import assignment2.food.SwissCheese;

import java.awt.*;
import java.lang.reflect.Field;

public class Main {

    public static Caterpillar gus;
    public static void main(String[] args) {
        Position startingPoint = new Position(0, 0);
        gus = new Caterpillar(startingPoint, GameColors.BLUE, 10);

        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        Position p1 = new Position(0, 1);
        Position p2 = new Position(1, 1);
        Position p3 = new Position(1, 2);
        Position p4 = new Position(2, 2);
        addSegment(gus.tail, p1, GameColors.RED);
        addSegment(gus.tail, p2, GameColors.ORANGE);
        addSegment(gus.tail, p3, GameColors.YELLOW);
        addSegment(gus.tail, p4, GameColors.GREEN);
        System.out.println("\n3) Gus: " + gus);

        gus.eat(new SwissCheese());
        System.out.println("\n2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
    }


    public static void addSegment(Caterpillar.Segment tail, Position p, Color c) {
        // adds a segment to the caterpillar
        try {
            Field tailNext = tail.getClass().getDeclaredField("next");
            tailNext.setAccessible(true);
            Caterpillar.Segment toAdd = gus.new Segment(p, c);
            tailNext.set(tail, toAdd);
            gus.tail = toAdd;
            gus.length++;
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}
