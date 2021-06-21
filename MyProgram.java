public class MyProgram
{
    public static void main(String[] args)
    {
        randomJottos(1000);
        // oneJotto();
    }

    /**
     * Plays one AI game of non-silent Jotto
     */
    private static void oneJotto() {
        Jotto game = new Jotto (true, false);
        game.playGame ();
    }

    /**
     * Plays multiple games of Jotto silently, outputting the mean of the rounds for all of the games played.
     * @param tests The amount of tests to be run.
     */
    private static void randomJottos(int tests) {
        double mean = 0.0;
        for (int i = 0; i < tests; i++) {
            System.out.print("\rTest: "+(i+1));

            Jotto game = new Jotto (true, true);
            game.playGame ();
            mean += game.getRoundCount();
        }
        mean /= tests;
        System.out.println();
        System.out.println("Average rounds for "+tests+" tests: "+mean);
    }
}