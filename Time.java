/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author USER
 */
public class Time {

    private int minute, second;//millisecond;

    public Time(int m, int s) {
        minute = m;
        second = s;
        //millisecond=ms;
    }

    @Override
    public String toString() {
        return ("" + minute + ":" + second);
    }

    public void increment() {
        //millisecond++;
        if (true) {
            second++;
            //millisecond=000;
            if (second == 60) {
                minute++;
                second = 0;
            }
        }
    }

    public void reset() {
        minute = 0;
        second = 0;
        //millisecond=0;
    }
}
