/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oraclejava;

import java.sql.SQLException;
import java.text.ParseException;
import view.Menu;


public class Oraclejava {
    public static void main(String[] args) throws SQLException,ParseException{
        new Menu().show();
    }
}
