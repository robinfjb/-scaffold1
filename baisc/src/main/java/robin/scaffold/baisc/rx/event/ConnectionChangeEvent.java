package robin.scaffold.baisc.rx.event;

public class ConnectionChangeEvent extends BaseEvent{
    private boolean isConnected;

    public ConnectionChangeEvent() {
    }

    public ConnectionChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
