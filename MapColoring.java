import java.util.*;

public class MapColoring {

    static String[] states = {
            "Maharashtra",
            "Gujarat",
            "Madhya Pradesh",
            "Chhattisgarh",
            "Telangana",
            "Karnataka",
            "Goa"
    };

    static int[][] graph = {

            //MH GU MP CH TG KA GO
            {0, 1, 1, 0, 1, 1, 1}, // Maharashtra
            {1, 0, 1, 0, 0, 0, 0}, // Gujarat
            {1, 1, 0, 1, 0, 0, 0}, // Madhya Pradesh
            {0, 0, 1, 0, 1, 0, 0}, // Chhattisgarh
            {1, 0, 0, 1, 0, 1, 0}, // Telangana
            {1, 0, 0, 0, 1, 0, 1}, // Karnataka
            {1, 0, 0, 0, 0, 1, 0}  // Goa
    };

    static String[] colors = {"Red", "Green", "Blue"};

    static int[] result = new int[states.length];

    static boolean isSafe(int state, int color) {

        for (int i = 0; i < states.length; i++) {

            if (graph[state][i] == 1 && result[i] == color)
                return false;
        }

        return true;
    }

    static boolean solve(int state) {

        if (state == states.length)
            return true;

        for (int c = 0; c < colors.length; c++) {

            if (isSafe(state, c)) {

                result[state] = c;

                if (solve(state + 1))
                    return true;

                result[state] = -1;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Arrays.fill(result, -1);

        if (solve(0)) {

            System.out.println("Map Coloring Solution:\n");

            for (int i = 0; i < states.length; i++) {

                System.out.println(states[i] + " --> " + colors[result[i]]);
            }

        } else {

            System.out.println("No solution exists.");
        }
    }
}