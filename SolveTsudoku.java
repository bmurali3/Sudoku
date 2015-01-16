/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 *
 * @author USER
 */
public abstract class SolveTsudoku {

    public static int[][] copyOf(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }

    private static void display(int[][] original) {
        for (int ic = 0; ic < 9; ic++) {
            for (int jc = 0; jc < 9; jc++) {
                System.out.print(original[ic][jc] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isInBox(int a[][], int num, int x, int y) {

        int m, n;
        if (x < 3) {
            m = 0;
        } else if (x > 5) {
            m = 2;
        } else {
            m = 1;
        }
        if (y < 3) {
            n = 0;
        } else if (y > 5) {
            n = 2;
        } else {
            n = 1;
        }

        for (int i = 3 * m; i < ((3 * m) + 3); i++) {
            for (int j = 3 * n; j < ((3 * n) + 3); j++) {
                if (a[i][j] == num) {
                    return (true);
                }
            }
        }
        return (false);

    }

    private static boolean isInRow(int a[][], int num, int i) {
        for (int j = 0; j < 9; j++) {
            if (a[i][j] == num) {
                return (true);
            }
        }
        return (false);

    }

    private static boolean isInColumn(int a[][], int num, int j) {
        for (int i = 0; i < 9; i++) {
            if (a[i][j] == num) {
                return (true);
            }
        }
        return (false);

    }

    private static int analyze(int a[][]) {
        int given = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (a[i][j] != 0) {
                    given++;
                }
            }
        }
        return (given);
    }

    private static String fill(int a[][]) {
        int b[][] = copyOf(a);
        int i, j, k = 0, num, count = 0, given = 0, y = 0, error = 0;
        given = analyze(a);
        while ((count + given) < 81) {
            for (num = 1; num < 10; num++) {
                for (i = 0; i < 9; i++) {
                    k = 0;
                    boolean fault = true;
                    for (j = 0; j < 9; j++) {
                        if (isInRow(a, num, i) == true) {
                            fault = false;
                            break;
                        }
                        if (a[i][j] != 0) {
                            continue;
                        }
                        if (isInBox(a, num, i, j) == true) {
                            continue;
                        }
                        if (isInColumn(a, num, j) == true) {
                            continue;
                        }
                        y = j;
                        k++;
                    }            //end of j loop
                    if ((k == 0) && (fault == true)) {
                        for (int ic = 0; ic < 9; ic++) {
                            System.arraycopy(b[ic], 0, a[ic], 0, 9);
                        }
                        return ("mistake");
                    }
                    if (k == 1) {
                        a[i][y] = num;
                        count++;
                    }
                }
            }
            if (count + given < 81) {
                for (num = 1; num < 10; num++) {
                    for (j = 0; j < 9; j++) {
                        k = 0;
                        boolean fault = true;
                        for (i = 0; i < 9; i++) {
                            if (isInColumn(a, num, j) == true) {
                                fault = false;
                                break;
                            }
                            if (a[i][j] != 0) {
                                continue;
                            }
                            if (isInBox(a, num, i, j) == true) {
                                continue;
                            }
                            if (isInRow(a, num, i) == true) {
                                continue;
                            }
                            y = i;
                            k++;
                        }            //end of i loop
                        if ((k == 0) && (fault == true)) {
                            for (int ic = 0; ic < 9; ic++) {
                                System.arraycopy(b[ic], 0, a[ic], 0, 9);
                            }
                            return ("mistake");
                        }
                        if (k == 1) {
                            a[y][j] = num;
                            count++;
                        }
                    }              //end of j loop
                }
            }               //end of if
            if (count == error) {
                return ("incomplete");
            } else {
                error = count;
            }
        }
        return ("solved");
    }

    public static String solve(int a[][]) {
        if (fill(a).equals("solved")) {
            return ("Sudoku solved...");
        } else if (fill(a).equals("mistake")) {
            return ("Cannot be solved...");
        } else {
            Stack stack = new Stack();
            Stack s = new Stack();
            Stack s1 = new Stack();
            Stack count = new Stack();
            while (true) {
                String sw = fill(a);
                if (sw.equals("solved")) {
                    break;
                } else if (sw.equals("incomplete")) {
                    int i = 0, j = 0;
                    Outer:
                    for (i = 0; i < 9; i++) {
                        Inner:
                        for (j = 0; j < 9; j++) {
                            if (a[i][j] != 0) {
                                continue;
                            } else {
                                break Outer;
                            }
                        }
                    }
                    int c = 0;
                    for (int num = 1; num <= 9; num++) {
                        if ((!isInRow(a, num, i) && !isInColumn(a, num, j) && !isInBox(a, num, i, j))) {
                            c++;
                            int b[] = new int[3];
                            b[0] = num;
                            b[1] = i;
                            b[2] = j;
                            stack.push(b);
                        }
                    }
                    if (c == 0) {
                        while ((int) ((Integer) count.peek()) == 0) {
                            int[][] ac = (int[][]) s1.pop();
                            for (int ic = 0; ic < 9; ic++) {
                                System.arraycopy(ac[ic], 0, a[ic], 0, 9);
                            }
                            count.pop();
                        }
                        int b[] = (int[]) stack.pop();
                        a[b[1]][b[2]] = b[0];
                        int p = (int) ((Integer) count.pop()) - 1;
                        count.push(p);
                    } else {
                        count.push(c - 1);
                        if (!s.isEmpty()) {
                            Object ob = s.pop();
                            s1.push(ob);
                        }
                        int ac[][] = copyOf(a);
                        s.push(ac);
                        int b[] = (int[]) stack.pop();
                        a[b[1]][b[2]] = b[0];
                    }
                } else if (sw.equals("mistake")) {
                    while ((int) ((Integer) count.peek()) == 0) {
                        int[][] ac;
                        try {
                            ac = (int[][]) s1.pop();
                        } catch (EmptyStackException e) {
                            System.out.print("Flaw");
                            return ("Cannot be solved...");
                        }
                        for (int ic = 0; ic < 9; ic++) {
                            System.arraycopy(ac[ic], 0, a[ic], 0, 9);
                        }
                        count.pop();
                    }
                    int b[] = (int[]) stack.pop();
                    a[b[1]][b[2]] = b[0];
                    int c = (int) ((Integer) count.pop()) - 1;
                    count.push(c);
                } else {
                    break;
                }
            }
            System.out.println("Credits:Bharath Murali");
            return ("Sudoku solved...");
        }
    }
}
