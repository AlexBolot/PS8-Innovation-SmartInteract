package fr.polytech.pnsinnov.smartinteract;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.polytech.pnsinnov.smartinteract.model.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

import static fr.polytech.pnsinnov.smartinteract.model.SpecificCategory.*;

public class SyntaxAnalyserImpl implements SyntaxAnalyser {
    private Socket soc;
    private BufferedReader in;
    DataOutputStream dout;

    public SyntaxAnalyserImpl() throws IOException {

        soc = new Socket("localhost", 9095);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        dout = new DataOutputStream(soc.getOutputStream());
    }

    @Override
    public Message analyseMessage(Message message) throws IOException {

        dout.writeBytes(formatting(message.content()));
        dout.flush();

        String line = in.readLine();
        System.out.println("received: " + line);
        setAnalyseMessage(message, line);

        return message;
    }

    private void setAnalyseMessage(Message message, String line) {
        boolean isPersonal = false;
        JsonParser jsonParser = new JsonParser();
        try {JsonObject objectFromString = jsonParser.parse(line).getAsJsonObject();
        Set<String> tokens = objectFromString.getAsJsonObject("tokens").keySet();
        String personalQuestion = objectFromString.getAsJsonObject("type").get("personalQuestion").getAsString();
        if(personalQuestion.equals("true")){
                isPersonal = true;
            }
            message.itsPersonal(isPersonal);
            String personal = objectFromString.getAsJsonObject("type").get("personal").getAsString();
            if (personal != null) {
                message.setPersonal(personal);
            }

            message.setTokens(new ArrayList<>(tokens));

            int numberfault = objectFromString.get("numberFault").getAsInt();
            message.setInPercentatageNumberFault(numberfault);

            String category = objectFromString.get("category").getAsString();
            if (category == null) return;
            switch (category) {
                case "what":
                    message.setSpecificCategory(WHAT);
                    break;
                case "where":
                    message.setSpecificCategory(WHERE);
                    break;
                case "how":
                    message.setSpecificCategory(HOW);
                    break;
                case "why":
                    message.setSpecificCategory(WHY);
                    break;
                case "who":
                    message.setSpecificCategory(WHO);
                    break;
                case "when":
                    message.setSpecificCategory(WHEN);
                    break;
                case "many":
                    message.setSpecificCategory(HOWMANY);
                    break;
                default:
                    message.setSpecificCategory(null);
                    break;
            }
        }catch (Exception e){
            System.out.println("Fail Parsing JSON ! :/");
        }
    }

    private String formatting(String content) {
        content = content.toLowerCase();
        if (!content.endsWith("\n")) {
            return content + "\n";
        } else {
            return content;
        }
    }

    @Override
    public void endDialogue() {
        try {
            dout.writeBytes(formatting("End dialogue!!!!!!!!!!!"));
            System.out.println("End Dialogue!!!!!!!!!!!!!");
            dout.flush();
            soc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

