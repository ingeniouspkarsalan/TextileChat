package com.textilechat.ingenious.textilechat.classes;

public class chat_messages {
    private String ids;
    private String messages;
    private String user_name;
    private String timestamp;
    private String u_status;
    private String u_image;
    private String m_id;
    private String admin_msg;

    public String getU_status() {
        return u_status;
    }

    public void setU_status(String u_status) {
        this.u_status = u_status;
    }

    public String getU_image() {
        return u_image;
    }

    public void setU_image(String u_image) {
        this.u_image = u_image;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getAdmin_msg() {
        return admin_msg;
    }

    public void setAdmin_msg(String admin_msg) {
        this.admin_msg = admin_msg;
    }
}
