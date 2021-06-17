package ui.custom;

public interface JTimerIF extends EventIF {
    void startTimer();
    void pause();
    void resume();
    int getSeconds();
    int getMinutes();
    int getTotalSeconds();
    void setSeconds(int seconds);
    void setMinutes(int minutes);
    boolean isActive();
}
