/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EloPisteLaskuriModel;

/**
 *
 * @author Lauri
 * 12.4.2018
 */
public class PlayAgainstSelfException extends Exception{
    
    public PlayAgainstSelfException(){
        super("itseään vastaan ei voi pelata.");
    }
    
    public PlayAgainstSelfException(String msg){
        super(msg);
    }
}
