/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author denneyzhang
 */
public class RegistrationSystem {
    
    public String Delimeter = ",";
    User user;
    private ArrayList<User> userList= new ArrayList<User>();
    private File users = new File("users.txt");
    

    /**
     * constructor of new registration system
     */
    public RegistrationSystem(){
    }
    
    /**
     * saves users into the file
     * @param saveUser the user that will be saved into the file "users.txt" 
     */
    public void saveUsers(User saveUser){
        try {
        //use this so it is in append mode instead of overwriting
        PrintWriter printWriter = new PrintWriter(new FileWriter(users, true)); //???????????
        printWriter.println(saveUser);
        printWriter.close(); 

        }catch(IOException e){ //A general exception that can happen any time you try to read/write to a file
            System.out.println("Error Occurred. ");
        }
    }
    
    /**
     * reads the file line by line, creates a user based on each line, saves each user in the array
     */
    public void loadUsers(){
        try{
            Scanner scanner = new Scanner(users);
            while (scanner.hasNext()){
                //creates the user based on the entire line
                User tempUser = new User(scanner.nextLine());
                userList.add(tempUser);
            } 
            scanner.close();
        }catch(FileNotFoundException e){
            System.out.println("Error Occurred. ");
            }
    }
    
    /**
     * used to check if the password meets the requirements
     * @param password
     * @return returns -1 = password length not long enough; return -2 = password doesn't contain specific special characters; return -3 = password doesn't contain 1 upper and lower case;
     * return 0 = eligible password! 
     */
    public int checkPasswordEligibility(String password){
        int numUpper = 0;
        int numLower = 0;
        
        //checks for password length
        if (password.length() < 8)
            return -1;
        
        //checks if password contains !@#?,. characters
        if (password.contains("!")== false && password.contains("@")== false && password.contains("#") == false && password.contains("?") == false&& password.contains(".") == false)
            return -2;
        
        //checks if password has at lease 1 uppercase and 1 lowercase 
        for (int i = 0; i < password.length(); i++){
            char currentChar = password.charAt(i);
            if (Character.isUpperCase(currentChar))
                numUpper += 1;
            if (Character.isLowerCase(currentChar))
                numLower += 1;
        }
        if (numUpper == 0 || numLower == 0)
            return -3;
        
        //if everything is normal returns 0
        return 0;
    }
    
    
    /**
     * checks the username the user inputs and checks if there is a duplicate name
     * @param inputUsername the username inputted 
     * @return if it returns a positive number, it means there is a duplicate username
     */
    public int checkDuplicateUsername(String inputUsername){
        //load users first so the userlist is properly set up (just in case. loading it multiple times doesn't hurt)
        loadUsers();
        for (int userIndex=0; userIndex < userList.size(); userIndex++){
            User currentUser = userList.get(userIndex);
            //retrieve the username of user in current index
            String thisUsername = currentUser.getUsername();
            //if matching usernames, return the index the duplicate is found.
            if (inputUsername.equals(thisUsername)){
                return userIndex;
            }
        }
        //if no duplicate, returns -1
        return -1;
    }
    
    /**
     * checks wether the user inputted password matches with the password corresponding to the username
     * @param username the username that will be searched
     * @param password the inputted password that will be used to compare with the actual password
     * @return if the passwords match, return 0; if the passwords don't match but username match, return -1; 
     * if username doesn't match, return -2
     */
    public int checkPasswordMatch(String username, String password){  
        //retrives the matching user index in order to check for password match
        int userMatchIndex = checkDuplicateUsername(username);
        //current user: 
        
        //if the username actually exists in the list
        if (userMatchIndex >= 0){
            //store the current user in the list that is being compared
            User currentCompareUser = userList.get(userMatchIndex);
            
            //get the current user password (is encrypted and includes salt):
            String currentUserPassword = currentCompareUser.getPassword();

            //encrypt inputted password in text box, and add the assigned salt value that was assigned when creating the user
            String checkPassword = encrypt(password);
            checkPassword = checkPassword + currentCompareUser.getSalt( );
            if (checkPassword.equals(currentUserPassword))
                return 0;
            else
                return -1;
        }
        //if the username doesn't even exist in the list, 
        else
            return -2;
    }
    
    
    /**
     * encrypts password based on sha-256
     * @param password password
     * @return  encrypted password
     */
    public String encrypt(String password){
        try{
        //java helper class to perform encryption
        MessageDigest mesd = MessageDigest.getInstance("SHA-256");
        //give the helper function the password
        mesd.update(password.getBytes());
        //perform the encryption
        byte byteData[] = mesd.digest();
        //To express the byte data as a hexadecimal number (the normal way)
        String encryptedPassword = "";
        for (int i = 0; i < byteData.length; ++i) {
            encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF)|0x100).substring(1,3));
            }
        return encryptedPassword;
        } catch (NoSuchAlgorithmException e){
        System.out.println("Error Occurred");
        }
        //will never get to this return statement. adding it here just because netbeans thinks theres no return statement
        return password;
    }
    
    /**
     * the actions that will take place when register button is clicked.
     * takes parameters, checks the availability of the username(wether it has been taken or not), 
     * checks if the password meets the requirements, 
     * creates the user object and saves the user object into the file
     * @param username username
     * @param email email
     * @param password password
     * @param age age as a string
     * @param firstName first name
     * @returns the error occurred
     */
    public String register(String username, String email, String password, String age, String firstName){ 
        // load all users first so it can compare 
        loadUsers();
        //check if the username has already been used by another user
        if (checkDuplicateUsername(username) >= 0){
            return("Username already taken, please use a new one."); //change so it displays on systeminterface instead of consol
        }
        else{
        //check password eligibility
            if (checkPasswordEligibility(password) == -1){
                return("Password has to be longer than 8 characters. "); // change
            }
            else if (checkPasswordEligibility(password) == -2){
                return("Password must contain one of the following characters:    !@#?. ");
            }
            else if (checkPasswordEligibility(password) == -3){
                return("Password has to contain at least one upper case and one lower case letter. ");
            }
            else if (checkPasswordEligibility(password) == 0){
                //once password eligible, encrypt password
                password = encrypt(password);
                //create the user object
                User newUser = new User(username, email, password, age, firstName);
                //add the salt to the encrypted password
                newUser.addToEndOfPassword(newUser.getSalt());
                //store the new user object into the file
                saveUsers(newUser);
                //show user that the account has been successfully created
                return("Register Successful ");
                
            }
        }
        return ("Error Occurred. ");
    }
    /**
     * everything that will happen when login button is pressed
     * @param username username
     * @param password password
     */
    public String login(String username, String password){
        //load all users first so it can be used to compare
        loadUsers();
        //if it returns 0, means everything correct
        if (checkPasswordMatch(username,password) == 0)
            return("Login successful");
        //if returns -2, means username doesn't even match
        else if (checkPasswordMatch(username,password) == -2)
            return("Username does not exist. ");
        //if returns -1, means passwords does not match (put ths else if here just in case)
        else if (checkPasswordMatch(username,password) == -1)
            return("Passwords do not match. ");
        return ("Unknown Error Occurred ");
        }
        
}
    

