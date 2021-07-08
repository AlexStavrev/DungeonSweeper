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

    // The functional interface to define what will happen when finished
    public Finishable done;

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

    public void addFinishAction(Finishable e) {
        this.done = e;
    }

    /**
     * A method to start the timer
     */
    @Override
    public void startTimer() {
        resume();
        timerThread = new Thread(() -> {
            while (minutes >= 0) {
                try {
                    if(active) {
                        setText(((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds));
                        if (--seconds < 0) {
                            seconds = 59;
                            minutes--;
                        }
                    }
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (active) {
                finish();
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    /**
     * A method you need to Override which gets called when the timer hits 0
     * From {@link Finishable} functional interface
     */
    @Override
    public void finish() {
        done.finish();
    }

    /**
     * A method to activate the timer
     */
    @Override
    public void resume() {
        active = true;
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
