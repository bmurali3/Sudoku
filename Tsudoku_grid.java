/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author USER
 */
public class Tsudoku_grid {

    private int flag = 0;
    private JFrame f;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JLabel l;
    private JLabel k;
    private JTextField t[] = new JTextField[81];
    private JButton step;
    private JButton enter;
    private JButton clear;
    private Timer timer;
    private int[][] complete_sudoku;
    String whatever;
    Time tm;

    public Tsudoku_grid() {
        f = new JFrame("Tsudoku Layout");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        step = new JButton("Step");
        enter = new JButton("Solve");
        clear = new JButton("Clear");
        timer = new Timer(1000, new MyTimer());
        timer.setInitialDelay(1000);
        tm = new Time(0, 0);
        for (int i = 0; i < t.length; i++) {
            t[i] = new JTextField(1);
            t[i].setForeground(Color.BLUE);
            t[i].setText(null);
        }
        l = new JLabel("Enter numbers :P");
        k = new JLabel("Timer " + tm.toString());
        enter.setActionCommand("kill");
        step.setActionCommand("injure");
        clear.setActionCommand("kay");
    }

    public void launchFrame() {
        p1.setLayout(new GridLayout(0, 9));
        for (int i = 0; i < t.length; i++) {
            p1.add(t[i]);
        }
        enter.addActionListener(new EnterHandler());
        step.addActionListener(new StepHandler());
        clear.addActionListener(new ClearHandler());
        p2.setLayout(new GridLayout(0, 1));
        p2.add(step);
        p2.add(enter);
        p2.add(clear);
        p3.setLayout(new GridLayout(0, 1));
        p3.add(l);
        p3.add(k);
        f.add(p3, BorderLayout.NORTH);
        f.add(p1, BorderLayout.CENTER);
        f.add(p2, BorderLayout.SOUTH);
        f.setSize(300, 400);
        f.setVisible(true);
    }

    class MyTimer implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            tm.increment();
            k.setText("Timer " + tm.toString());
        }
    }

    class ClearHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < t.length; i++) {
                if (t[i].getForeground() == Color.BLUE) {
                    t[i].setText(null);
                }
            }
            tm.reset();
            timer.restart();
            l.setText("Try Again!!");
        }
    }

    class EnterHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int[][] a = new int[9][9];
            if (flag == 0) {
                timer.start();
                for (int i = 0; i < t.length; i++) {
                    if (!t[i].getText().isEmpty()) {
                        t[i].setForeground(Color.red);
                        a[i / 9][i % 9] = Integer.valueOf(t[i].getText());
                    } else {
                        a[i / 9][i % 9] = 0;
                    }
                }
                flag = 1;
                complete_sudoku = SolveTsudoku.copyOf(a);
                whatever = SolveTsudoku.solve(complete_sudoku);
                a = complete_sudoku;
            } else {
                for (int i = 0; i < t.length; i++) {
                    if (!t[i].getText().isEmpty()) {
                        a[i / 9][i % 9] = Integer.valueOf(t[i].getText());
                    } else {
                        a[i / 9][i % 9] = 0;
                    }
                }
                whatever = SolveTsudoku.solve(a);
            }
            if (whatever.equals("Sudoku solved...")) {
                timer.stop();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tsudoku_grid.class.getName()).log(Level.SEVERE, null, ex);
                }
                tm.reset();
                k.setText("Timer " + tm.toString());
                l.setText(whatever);
                int k = 0;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (a[i][j] != 0) {
                            t[k++].setText(Integer.toString(a[i][j]));
                        } else {
                            t[k++].setText(null);

                        }
                    }
                }
            } else if (whatever.equals("Cannot be solved...")) {
                timer.stop();
                tm.reset();
                k.setText("Timer " + tm.toString());
                l.setText(whatever);
            }
        }
    }

    class StepHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int[][] a = new int[9][9];
            if (flag == 0) {
                timer.start();
                for (int i = 0; i < t.length; i++) {
                    if (!t[i].getText().isEmpty()) {
                        t[i].setForeground(Color.red);
                        a[i / 9][i % 9] = Integer.valueOf(t[i].getText());
                    } else {
                        a[i / 9][i % 9] = 0;
                    }
                }
                flag = 1;
            } else {
                for (int i = 0; i < t.length; i++) {
                    if (!t[i].getText().isEmpty()) {
                        a[i / 9][i % 9] = Integer.valueOf(t[i].getText());
                    } else {
                        a[i / 9][i % 9] = 0;
                    }
                }
            }
            complete_sudoku = SolveTsudoku.copyOf(a);
            whatever = SolveTsudoku.solve(complete_sudoku);
            if (whatever.equals("Sudoku solved...")) {
                int i = 0, j = 0, k = 0;
                Outer:
                for (i = 0; i < 9; i++) {
                    for (j = 0; j < 9; j++) {
                        if (a[i][j] == 0) {
                            k++;
                            break Outer;
                        }
                    }
                }
                if (k != 0) {
                    t[9 * i + j].setText(Integer.toString(complete_sudoku[i][j]));
                    l.setText("hmmm");
                } else {
                    l.setText("Sudoku solved...");
                }
            } else {
                l.setText(whatever);
            }
        }
    }
}
