/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basefxpreloader;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author grossco
 */
public class BaseFxPreloader extends Preloader {
    
    Label label;
    ProgressBar bar;
    Stage basestage;
    
    
    @Override
    public void init(){
        System.setProperty("org.jboss.logging.provider", "log4j");
        //System.out.println("PRELOADER");
        
    }
    
    private Scene createPreloaderScene() {
        label = new Label();
        label.setText("Loading Application");
        bar = new ProgressBar();
        bar.setProgress(0.50);
        bar.setScaleY(2.25);
        BorderPane p = new BorderPane();
        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setAlignment(bar, Pos.CENTER);
        p.setCenter(bar);
        p.setTop(label);
        p.getStyleClass().add("base-pane");
        //both .toString() and .toExternalForm() will work in the following line
        //p.getStylesheets().addAll(this.getClass()
        //        .getResource("resources/BaseStyle.css").toString());
        return new Scene(p, 300, 150);        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.basestage = stage;
        //Use undecorated stage for preloader.
        basestage.initStyle(StageStyle.UNDECORATED);
        Scene sc = createPreloaderScene();
        //Could set the css resource for the scene as a whole here.
        sc.getStylesheets().addAll(this.getClass()
                .getResource("resources/BaseStyle.css").toString());
        stage.setScene(sc);        
        stage.show();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        //Fade the stage on BEFORE_START notification
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            if (basestage.isShowing()) {
            //fade out, hide stage at the end of animation
                FadeTransition ft = new FadeTransition(
                    Duration.millis(1000), basestage.getScene().getRoot());
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                final Stage s = basestage;
                EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        s.hide();
                    }
                };
                ft.setOnFinished(eh);
                ft.play();
            }
        }
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
        //System.out.println("PRELOADER PROGRESS NOTIFICATION: "+pn.getProgress());
    }    
    
    /* Custom handler to handle custom events from the main application.
     * Use this to handle loading messages from the main applications init()
     * function.
     */
    @Override
    public void handleApplicationNotification(PreloaderNotification pn){
        if( pn instanceof AppNotification){
            //System.out.println("PRELOADER STATE AppNotification: ");
            bar.setProgress( ((AppNotification) pn).getProgress() );
            label.setText(((AppNotification) pn).getMessage());
        }
        
    }
}
