package com.textilechat.ingenious.textilechat.classes;

import java.io.Serializable;

public class serialize_msg_class implements Serializable {


    String massege;
    String u_id;

    public serialize_msg_class() {
    }

    String u_name;

    public serialize_msg_class(String massege, String u_id, String u_name, String created_at, String id_name, String c_id, String sc_id) {
        this.massege = massege;
        this.u_id = u_id;
        this.u_name = u_name;
        this.created_at = created_at;
        this.id_name = id_name;
        this.c_id = c_id;
        this.sc_id = sc_id;
    }

    String created_at;
    String id_name;
    String c_id;

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    String sc_id;

}
