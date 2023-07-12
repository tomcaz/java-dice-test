import java.util.*;
import java.util.concurrent.TimeUnit;

public class Dice {

    private int unscoreNumber;
    private int numberOfDice;

    private int numberOfSimulation;
    private SplittableRandom generator;

    public Dice() {
        this(3, 5, 100);
    }

    public Dice(int numberOfDice, int numberOfSimulation) {
        this(3, numberOfDice, numberOfSimulation);
    }
    /**
     *
     * @param unscoreNumber = number that woould not get score.
     * @param numberOfDice = number of dice will be used.
     * @param numberOfSimulation = number of simulations.
     */
    public Dice(int unscoreNumber, int numberOfDice, int numberOfSimulation) {
        this.unscoreNumber = unscoreNumber;
        this.numberOfDice = numberOfDice;
        this.numberOfSimulation = numberOfSimulation;
        generator = new SplittableRandom();
    }

    private int[] roll(int dice) {
        int index = 0;
        int occurrence = 0;
        int smallestNumber = 0;
        while (index < dice) {
            int roll = generator.nextInt(1,7);
            if (roll == unscoreNumber) {
                occurrence ++;
            }
            if(smallestNumber == 0) {
                smallestNumber = roll;
            } else if (smallestNumber < roll) {
                smallestNumber = roll;
            }
            index++;
        }
        return new int[]{occurrence > 0? 0: smallestNumber,
                occurrence == 0? 1: occurrence}; // to remove number of dice based on occurrence
    }

    /**
     *
     * @param scoreFound = how many times did someone score?
     */
    public void simulate(int scoreFound) {
        System.out.println("Number of simulations was " + numberOfSimulation + " using " + numberOfDice + " dice");
        int index = 0;
        int iteration = numberOfSimulation;
        Date startTime = new Date();
        while (iteration > 0) {
            int tempDice = numberOfDice;
            boolean scoreNotFound = true;
            int timeOfDiceInTotal = 0;
            for(int i = 0; i < iteration; i++) {
                int score = 0;
                while (tempDice > 0) {
                    int[] result = roll(Math.min(tempDice, iteration));
                    tempDice -= result[1]; // remove dice
                    score += result[0];
                    timeOfDiceInTotal++;
                }
                if(score == scoreFound) {
                    scoreNotFound = false;
                    break;
                }
                iteration -= timeOfDiceInTotal;
            }
            if (!scoreNotFound) {
                System.out.println("Total "+ index++ +" occurs "+ (iteration *1.0)/numberOfSimulation +" occurred "+ (iteration) +".0 times");
            }
        }

        System.out.println("Total simulation took " + getDateDiff(startTime, new Date(), TimeUnit.MILLISECONDS)/100 + " seconds.");
    }
    private static double getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public void setUnscoreNumber(int unscoreNumber) {
        this.unscoreNumber = unscoreNumber;
    }
}
