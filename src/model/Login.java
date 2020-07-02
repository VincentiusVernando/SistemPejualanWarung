/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

/**
 *
 * @author fauzimakarim
 */
public class Login {
    private Integer Id_owner;
    private String Username;
    private String Password;

    public Integer getId_login() {
        return Id_owner;
    }

    public void setId_owner(Integer Id_owner) {
        this.Id_owner = Id_owner;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
