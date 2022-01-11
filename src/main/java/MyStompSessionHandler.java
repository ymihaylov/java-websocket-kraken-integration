import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("HELLLOOOOO IM HEEEEREEEEEEEE DEBADSADASDASDASDA");
//        session.subscribe("/", this);
//        session.send("/", getSampleMessage());

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception" + exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("here");
//        System.out.println("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    private Message getSampleMessage() {
        Message msg = new Message();
        return msg;
    }
}
