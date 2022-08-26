import java.util.Arrays;

public class TestMovieRentingSystem {
    public static void main(String[] args)
    {
        int[][] entries = {{0, 1, 5}, {0, 2, 6}, {0, 3, 7}, {1, 1, 4}, {1, 2, 7}, {2, 1, 5}};
        MovieRentingSystem movieRentingSystem = new MovieRentingSystem(3, entries);

        int[] res = {1, 0, 2};
        assert (Arrays.asList(res).equals(movieRentingSystem.search(1)));

        movieRentingSystem.rent(0, 1);
        movieRentingSystem.rent(1, 2);

        int[][] res2 = {{0, 1}, {1, 2}};
        assert (Arrays.asList(res2).equals(movieRentingSystem.report()));

        movieRentingSystem.drop(1, 2);
        int[] res3 = {0, 1};
        assert (Arrays.asList(res3).equals(movieRentingSystem.search(2)));

        System.out.println("ALL TESTS PASS");
    }
}
