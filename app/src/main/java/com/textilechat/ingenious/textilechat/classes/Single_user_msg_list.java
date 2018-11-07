package com.textilechat.ingenious.textilechat.classes;

import java.io.Serializable;

public class Single_user_msg_list implements Serializable {
    private String from_user_id;
    private String messages;
    private String to_user_id;
    private String timestamp;


    public Single_user_msg_list() {
    }

    public Single_user_msg_list(String from_user_id, String messages, String to_user_id, String timestamp) {
        this.from_user_id = from_user_id;
        this.messages = messages;
        this.to_user_id = to_user_id;
        this.timestamp = timestamp;
    }


    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
