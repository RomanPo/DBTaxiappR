package ua.artcode.taxi.to;

/**
 * Created by serhii on 05.06.16.
 */
public class Message {


    private String methodName;

    private MessageBody messageBody;

    public Message(MessageBody messageBody, String methodName) {
        this.messageBody = messageBody;
        this.methodName = methodName;
    }

    public Message() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }
}
