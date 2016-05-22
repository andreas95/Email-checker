package application;

/**
 * Created by Andreas on 5/16/16.
 */

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.xbill.DNS.TextParseException;

public class GUI {
    private static TextField mail;
    private static TextArea console;
    private static Button verify;
    private static GridPane pane;
    public static GridPane api(){
        pane=new GridPane();
        pane.setVgap(8);
        pane.setHgap(10);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(30,30,30,30));
        mail=new TextField();
        mail.setPrefWidth(300);
        mail.setPrefHeight(50);
        mail.requestFocus();
        mail.setPromptText("emailtoverify@domain.com");
        mail.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void  handle(KeyEvent keyEvent){
                if (keyEvent.getCode()==KeyCode.ENTER)
                    verify.fire();
            }
        });
        verify=new Button("Verify");
        verify.setOnAction(e->{
            try {
                action();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        verify.setPrefHeight(50);
        verify.setPrefWidth(80);
        verify.requestFocus();

        console=new TextArea();
        console.requestFocus();
        console.setEditable(false);
        console.setPrefHeight(300);
        GridPane.setColumnSpan(console, 2);
        pane.add(mail, 0, 0);
        pane.add(verify,1, 0);
        return pane;
    }
    public static void action() throws InterruptedException {

        if (!pane.getChildren().contains(console)) pane.add(console,0,1);
        mail.setPrefWidth(400);
        console.setText("Verifying Email Address: "+mail.getText()+"\n");
        step1();

    }

    public static void step1() {

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    for (int i=0;i<=70;i++) {
                        Thread.sleep(30);
                        console.appendText(".");
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                console.appendText("\nVerifying Email Patern\n");
                if (Pattern_validate.validate(mail.getText())) {
                    step2();
                } else {
                    stepOut();
                }
            }
        });
        new Thread(sleeper).start();
        }

    public static void step2() {

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    for (int i=0;i<=70;i++) {
                        Thread.sleep(30);
                        console.appendText(".");
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                console.appendText("\nStatus: OK!\n");
                console.appendText("Verifying MX record\n");
                try {
                    if (MX_record.validate(mail.getText())) {
                        step3();
                    } else {
                        stepOut();
                    }
                } catch (TextParseException e) {
                    e.printStackTrace();
                }

            }
            });
            new Thread(sleeper).start();
        }

    public static void step3()  {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    for (int i=0;i<=70;i++) {
                        Thread.sleep(30);
                        console.appendText(".");
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                console.appendText("\nStatus: OK!\n");
                console.appendText("Mail from: "+mail.getText()+"\n");
                if (Mail_package.validate(mail.getText())) {
                    stepFinal();
                } else {
                    stepOut();
                }
            }
        });
        new Thread(sleeper).start();
    }

    public static void stepFinal()  {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    for (int i=0;i<=70;i++) {
                        Thread.sleep(30);
                        console.appendText(".");
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                console.appendText("\nStatus: OK!\n");
                console.appendText(mail.getText()+" - Result: OK");
            }
        });
        new Thread(sleeper).start();
    }

    public static void stepOut() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    for (int i=0;i<=70;i++) {
                        Thread.sleep(30);
                        console.appendText(".");
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                console.appendText("\nStatus: ERROR!\n");
                console.appendText(mail.getText()+" - Result: BAD");
            }
            });
            new Thread(sleeper).start();
        }
}
