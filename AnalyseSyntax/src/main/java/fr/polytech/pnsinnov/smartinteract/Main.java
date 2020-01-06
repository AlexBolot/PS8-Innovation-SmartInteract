package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Message;


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        SyntaxAnalyser sai = new SyntaxAnalyserImpl();

        Message msg = new Message();
        msg.setContent("ton age");
        Message msg2 = new Message();
        msg2.setContent("YOUR AGE !!!");
        try {
            Message newMsg = sai.analyseMessage(msg);
            Message newMsg2 = sai.analyseMessage(msg2);
            sai.endDialogue();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}




