package com.project.textadventure.game.RubiksCubeSimulator;
import java.util.Random;

public class ScrambleGenerator {
    public static String generateScramble() {

        // array of random ints. Dimension determines number of turns in scramble algorithm
        int[] randomInts = new int[25];
        // number to be added to array if conditions are met
        int randomNumber;
        for(int i = 0; i < randomInts.length; i++) {
            randomNumber = new Random().nextInt(18) + 1;
            // continue if it's the same number as previous
            if(i != 0 && randomNumber == randomInts[i - 1]) {
                i--;
                continue;
            }
            // continue if current is U, U', or U2 and previous one is either U U' U2 or previous two are any of UD U'D U2D U'D U'D' U2D' UD2 U'D2 U2D2
            else if(i != 0 && (randomNumber == 1 || randomNumber == 2 || randomNumber == 3) && ((randomInts[i - 1] == 1 || randomInts[i - 1] == 2 || randomInts[i - 1] == 3)
                    || (i != 1 && (randomInts[i - 2] == 1 || randomInts[i - 2] == 2 || randomInts[i - 2] == 3) && (randomInts[i - 1] == 16 || randomInts[i - 1] == 17 || randomInts[i - 1] == 18)))) {
                i--;
                continue;
            }
            // continue if current is F, F' or F2 and previous one is either F F' F2 or previous two are any of FB F'B F2B FB' F'B' F2B' FB2 F'B2 F2B2
            else if(i != 0 && (randomNumber == 4 || randomNumber == 5 || randomNumber == 6) && ((randomInts[i - 1] == 4 || randomInts[i - 1] == 5 || randomInts[i - 1] == 6)
                    || (i != 1 && (randomInts[i - 2] == 4 || randomInts[i - 2] == 5 || randomInts[i - 2] == 6) && (randomInts[i - 1] == 10 || randomInts[i - 1] == 11 || randomInts[i - 1] == 12)))) {
                i--;
                continue;
            }
            // continue if current is R, R' or R2 and previous one is either R R' R2 or previous two are any of RL R'L R2L RL' R'L' R2L' RL2 R'L2 R2L2
            else if(i != 0 && (randomNumber == 7 || randomNumber == 8 || randomNumber == 9) && ((randomInts[i - 1] == 7 || randomInts[i - 1] == 8 || randomInts[i - 1] == 9)
                    || (i != 1 && (randomInts[i - 2] == 7 || randomInts[i - 2] == 8 || randomInts[i - 2] == 9) && (randomInts[i - 1] == 13 || randomInts[i - 1] == 14 || randomInts[i - 1] == 15)))) {
                i--;
                continue;
            }
            // continue if current is B, B' or B2 and previous one is either B B' B2 or previous two are any of BF B'F B2F BF' B'F' B2F' BF2 B'F2 B2F2
            else if(i != 0 && (randomNumber == 10 || randomNumber == 11 || randomNumber == 12) && ((randomInts[i - 1] == 10 || randomInts[i - 1] == 11 || randomInts[i - 1] == 12)
                    || (i != 1 && (randomInts[i - 2] == 10 || randomInts[i - 2] == 11 || randomInts[i - 2] == 12) && (randomInts[i - 1] == 4 || randomInts[i - 1] == 5 || randomInts[i - 1] == 6)))) {
                i--;
                continue;
            }
            // continue if current is L, L' or L2 and previous one is either L L' L2 or previous two are any of LR L'R L2R LR' L'R' L2R' LR2 L'R2 L2R2
            else if(i != 0 && (randomNumber == 13 || randomNumber == 14 || randomNumber == 15) && ((randomInts[i - 1] == 13 || randomInts[i - 1] == 14 || randomInts[i - 1] == 15)
                    || (i != 1 && (randomInts[i - 2] == 13 || randomInts[i - 2] == 14 || randomInts[i - 2] == 15) && (randomInts[i - 1] == 7 || randomInts[i - 1] == 8 || randomInts[i - 1] == 9)))) {
                i--;
                continue;
            }
            // continue if current is D, D' or D2 and previous one is either D D' D2 or previous two are any of DU D'U D2U DU' D'U' D2U' DU2 D'U2 D2U2
            else if(i != 0 && (randomNumber == 16 || randomNumber == 17 || randomNumber == 18) && ((randomInts[i - 1] == 16 || randomInts[i - 1] == 17 || randomInts[i - 1] == 18)
                    || (i != 1 && (randomInts[i - 2] == 16 || randomInts[i - 2] == 17 || randomInts[i - 2] == 18) && (randomInts[i - 1] == 1 || randomInts[i - 1] == 2 || randomInts[i - 1] == 3)))) {
                i--;
                continue;
            }
            randomInts[i] = randomNumber;
        }
        return new ScrambleGenerator().intArrayToString(randomInts);
    }

    private String intArrayToString(int[] numbers) {
        StringBuilder scrambleAlg = new StringBuilder();
        for (int number : numbers) {
            switch (number) {
                case 1 -> scrambleAlg.append("U ");
                case 2 -> scrambleAlg.append("U' ");
                case 3 -> scrambleAlg.append("U2 ");
                case 4 -> scrambleAlg.append("F ");
                case 5 -> scrambleAlg.append("F' ");
                case 6 -> scrambleAlg.append("F2 ");
                case 7 -> scrambleAlg.append("R ");
                case 8 -> scrambleAlg.append("R' ");
                case 9 -> scrambleAlg.append("R2 ");
                case 10 -> scrambleAlg.append("B ");
                case 11 -> scrambleAlg.append("B' ");
                case 12 -> scrambleAlg.append("B2 ");
                case 13 -> scrambleAlg.append("L ");
                case 14 -> scrambleAlg.append("L' ");
                case 15 -> scrambleAlg.append("L2 ");
                case 16 -> scrambleAlg.append("D ");
                case 17 -> scrambleAlg.append("D' ");
                case 18 -> scrambleAlg.append("D2 ");
            }
        }
        return scrambleAlg.toString();
    }
}
