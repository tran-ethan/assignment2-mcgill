package assignment2;

import assignment2.food.Cake;
import assignment2.food.Fruit;
import assignment2.food.SwissCheese;

import java.awt.*;
import java.lang.reflect.Field;

public class Main {

    public static Caterpillar gus;
    public static void main(String[] args) {

        gus = new Caterpillar(new Position(0, 0), GameColors.YELLOW, 10);

        Position p1 = new Position(1, 0);
        Position p2 = new Position(2, 0);
        Position p3 = new Position(2, 1);
        Position p4 = new Position(1, 1);
//        addSegment(gus.tail, p1, GameColors.BLUE);
//        addSegment(gus.tail, p2, GameColors.ORANGE);
//        addSegment(gus.tail, p3, GameColors.GREEN);
//        addSegment(gus.tail, p4, GameColors.RED);
        gus.positionsPreviouslyOccupied.push(new Position(-1, -1));
        gus.positionsPreviouslyOccupied.push(new Position(-1, 0));
        gus.positionsPreviouslyOccupied.push(new Position(-1, 1));
        gus.positionsPreviouslyOccupied.push(new Position(-2, 1));
        gus.positionsPreviouslyOccupied.push(new Position(-2, 0));
        gus.positionsPreviouslyOccupied.push(new Position(-1, 0));
        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.eat(new Cake(5));
        System.out.println("2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);
        System.out.println("Gus evolution stage: " + gus.getEvolutionStage());
        System.out.println("Turns left to digest: " + gus.turnsNeededToDigest);
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
