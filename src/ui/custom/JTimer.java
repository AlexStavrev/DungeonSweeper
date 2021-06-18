package ui.custom;

import javax.swing.*;

import java.lang.Thread;

/**
 * A Timer with minutes & seconds that counts down
 */
public class JTimer extends JLabel implements JTimerIF {
    // the minutes left
    int minutes;
    // the seconds left
    int seconds;
    // if it's active
    boolean active;
    // the thread it runs on
    Thread timerThread;

    /**
     * Standard constructor
     * @param minutes minutes
     * @param seconds seconds
     */
    public JTimer(int minutes, int seconds) {
        super();
        setMinutes(minutes);
        setSeconds(seconds);
    }

    /**
     * A method to start the timer
     */
    @Override
    public void startTimer() {
        active = true;
        timerThread = new Thread(() -> {
            while (minutes >= 0 && active) {
                try {
                    setText(((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds));
                    Thread.sleep(1000L);
                    if (--seconds < 0) {
                        seconds = 59;
                        minutes--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (active) {
                done();
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    /**
     * A method to Override which gets called when the timer hits 0
     */
    @Override
    public void done() {}

    /**
     * A method to activate the timer
     */
    @Override
    public void resume() {
        startTimer();
    }

    /**
     * A method to pause the timer
     */
    @Override
    public void pause() {
        active = false;
    }

    //Other interface methods
    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getTotalSeconds() {
        return minutes*60 + seconds;
    }

    @Override
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
