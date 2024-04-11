/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;

/**
 *
 * @author michael.roy-diclemen
 */
import java.io.*;

public class LoginSystem {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        // TODO code application logic here
        
        
        RegistrationSystem rs = new RegistrationSystem();
        System.out.println(rs.register("dog", "abcdefg@bmail.com", "Paaaaasw1!", "sixty", "Dhaniel"));
        
        
        rs.login("dog", "Paaaaasw1!");
//        rs.register("dfsahio", "dfsho", "fdF!2!!!sauf!!", "one", "ahndie");
        

    }
    
}
