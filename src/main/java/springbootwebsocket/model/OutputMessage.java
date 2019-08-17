package springbootwebsocket.model;

public class OutputMessage extends Message {

    private String time;

    public OutputMessage(final String sender, final String content, final String time) {

        setSender(sender);
        setContent(content);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
