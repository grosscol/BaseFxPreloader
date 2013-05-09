/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basefxpreloader;

import javafx.application.Preloader;

/**
 *
 * @author grossco
 */
public class AppNotification
extends java.lang.Object
implements Preloader.PreloaderNotification
{
    private String msg;
    private Double prog;
    
    public AppNotification(String message, Double progress){
        msg = message;
        prog = progress;
    }
    
    public String getMessage(){ return msg; }
    public Double getProgress(){ return prog; }
    
}
