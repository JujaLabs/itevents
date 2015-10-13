package org.itevents.model;

public class Authorization {

    private int id;
    private String email;
    private String password;
    private String activation;
    private boolean status;

    public Authorization() {
    }

    public Authorization (String email, String password, String activation, boolean status) {
        this.email = email;
        this.password = password;
        this.activation = activation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}