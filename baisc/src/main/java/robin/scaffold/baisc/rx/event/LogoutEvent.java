package robin.scaffold.baisc.rx.event;

public class LogoutEvent extends BaseEvent{
    public int eventCode;//返回的Code

    public LogoutEvent(int eventCode) {
        this.eventCode = eventCode;
    }
}
