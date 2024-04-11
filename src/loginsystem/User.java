/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.math.*;

/**
 *
 * @author denneyzhang
 */
public class User {
    private String username;
    private String email;
    private String password;
    private String age;
    private String fName; 
    private String delimeter = ",";
    private String salt;
    
    /**
     * creates the user
     * @param username
     * @param email
     * @param Password
     * @param fName
     * @param age as a string 
     */
    public User(String username, String email, String Password, String age, String fName){
        this.username = username;
        this.email = email;
        this.password = Password;
        this.fName = fName; 
        this.age = age;
        int saltInt = (int)(Math.random() * 100000);
        salt =  String.valueOf(saltInt);
    }
    
//    public User(){
//        
//    }
    
    
    /*
    creates the user based on a string (reads from file line by line) 
    */
    public User(String entireString){
        String[] split = entireString.split(delimeter);
        username = split[0];
        email = split[1];
        password = split[2];
        age = split[3];
        fName = split[4];
        salt = split[5];
    }
    
    /**
     * to string
     * @return displays entire user in an entire line separated with a delimeter
     */
    public String toString(){
        return(username + delimeter + email + delimeter + password + delimeter + age + delimeter + fName + delimeter + salt);
    }
    
    /**
     * get username
     * @return username
     */
    public String getUsername(){
        return username;
    }
    
    /**
     * get password
     * @return password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * adds a salt value to the end of password
     * @param saltValue salt value
     */
    public void addToEndOfPassword(String saltValue){
        password = password + saltValue;
    }
    
    /**
     * gets salt value
     * @return salt value
     */
    public String getSalt(){
        return salt;
    }
}
