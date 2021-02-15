import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Gaiduchek Maxim
 */

public class Problem1 {

    private static final String INPUT_PATH = "C:/Users/Xiaomi/Desktop/Projects/Reply Code Challenge/2019/inputs/input-teleportation-b3dd.txt";
    private static final String OUTPUT_PATH = "C:/Users/Xiaomi/Desktop/Projects/Reply Code Challenge/2019/output.txt";

    public static void main(String[] args) throws IOException {
        //new FileOutputStream(OUTPUT_PATH).write(result().getBytes());
        System.out.println(result());
    }

    private static String result() throws FileNotFoundException {
        //Scanner scan = new Scanner(new FileInputStream(INPUT_PATH));
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        long count = 0;
        StringBuilder sb = new StringBuilder();

        for (int test = 1; test <= T; test++) {
            int gridX = scan.nextInt(), gridY = scan.nextInt();
            int[][] grid = new int[gridY][gridX];
            fillGrid(grid);
            int x = scan.nextInt(), y = scan.nextInt(); // qubit coords
            int teleportsCount = scan.nextInt();
            List<Teleport> teleports = new ArrayList<>();

            for (int i = 0; i < teleportsCount; i++) {
                int xin = scan.nextInt(), yin = scan.nextInt();
                int xout = scan.nextInt(), yout = scan.nextInt();

                teleports.add(new Teleport(xin, yin, xout, yout));

                grid[yin][xin] = 1;
                grid[yout][xout] = 3;
            }

            System.out.println("Qubit cords -> " + x + " " + y);

            grid[y][x] = 2;
            System.out.println(Arrays.deepToString(grid));
            grid[y][x] = 0;
            System.out.println(teleports);

            while (teleports.size() != 0) {
                Teleport teleport = getAimTeleport(teleports, x, y);
                int deltaX = Math.abs(teleport.xin - x), deltaY = Math.abs(teleport.yin - y);
                System.out.println("\nQubit cords -> " + x + " " + y);
                System.out.println("Teleport -> " + teleport.xin + " " + teleport.yin + " -> " + teleport.xout + " " + teleport.yout + " - " + teleport);
                System.out.println("Dist -> " + calcDist(teleport, x, y) + " (" + deltaX + " " + deltaY + ")" + " = " + (count + calcDist(teleport, x, y)));

                count += calcDist(teleport, x, y);
                x = teleport.xout;
                y = teleport.yout;

                grid[teleport.yin][teleport.xin] = 0;
                grid[teleport.yout][teleport.xout] = 0;
                grid[y][x] = 2;
                System.out.println(Arrays.deepToString(grid));
                grid[y][x] = 0;
                System.out.println("Qubit cords -> " + x + " " + y);

                teleports.remove(teleport);
            }

            sb.append("Case #").append(test).append(": ").append(count % 100003).append("\n");
        }

        return sb.toString();
    }

    private static void fillGrid(int[][] grid) {
        for (int[] row : grid) Arrays.fill(row, 0);
    }

    private static int calcDist(Teleport teleport, int x, int y) {
        return Math.abs(teleport.xin - x) + Math.abs(teleport.yin - y);
    }

    private static Teleport getAimTeleport(List<Teleport> teleports, int x, int y) {
        Teleport min = null;
        int minDist = 0;

        for (int i = 0; i < teleports.size(); i++) {
            Teleport teleport = teleports.get(i);
            int dist = calcDist(teleport, x, y);
            //System.out.println(dist + " " + minDist);

            if (i == 0) {
                min = teleport;
                minDist = dist;
            } else {
                if (dist < minDist) {
                    min = teleport;
                    minDist = dist;
                } else if (dist == minDist) {
                    if (teleport.xin < min.xin || (teleport.xin == min.xin && teleport.yin < min.yin)) {
                        min = teleport;
                        minDist = dist;
                    }
                }
            }
        }

        return min;
    }

    static class Teleport {

        int xin, yin;
        int xout, yout;

        private Teleport(int xin, int yin, int xout, int yout) {
            this.xin = xin;
            this.yin = yin;
            this.xout = xout;
            this.yout = yout;
        }

        @Override
        public String toString() {
            return "Teleport{" +
                    "xin=" + xin +
                    ", yin=" + yin +
                    ", xout=" + xout +
                    ", yout=" + yout +
                    '}';
        }
    }
}