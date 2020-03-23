package ca.georgebrown.comp3074.pocketmealapp;

public class Chat {



    private String sender, receiver,message;

    public Chat(String sender, String message,String receiver){
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;


    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
