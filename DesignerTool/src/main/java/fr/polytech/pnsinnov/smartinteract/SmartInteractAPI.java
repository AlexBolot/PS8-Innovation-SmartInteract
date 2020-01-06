package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import fr.polytech.pnsinnov.smartinteract.model.SpecificCategory;
import fr.polytech.pnsinnov.smartinteract.stubs.InteractionProcessor;
import fr.polytech.pnsinnov.smartinteract.stubs.InteractionProcessorHelper;
import fr.polytech.pnsinnov.smartinteract.stubs.Replique;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.*;

import static fr.polytech.pnsinnov.smartinteract.Main.broker;
import static fr.polytech.pnsinnov.smartinteract.model.SpecificCategory.*;

public class SmartInteractAPI {

    private final GamePreparer preparer;
    private InteractionProcessor processor;
    private final Monitor monitor;

    public SmartInteractAPI() {
        this.preparer = new GamePreparerImpl();
        this.monitor = new MonitorImpl();

        if (true) {
            String catchPhrase = "What can I do for you ?";

            List<Knowledge> knowledges = new ArrayList<>();
            List<String> keywords = Arrays.asList("beer","vodka","wine","drink");
            List<String> contents = Arrays.asList("Good choice !", "Love that !","Here is your drink");
            Knowledge k = new Knowledge(keywords, contents);
            knowledges.add(k);

            keywords = Arrays.asList("tavern","bar");
            contents = Arrays.asList("What do you want to know about that ?");
            HashMap<SpecificCategory, String> specificKnowledges = new HashMap<SpecificCategory, String>() {{
                put(WHO, "It belongs to my familly for many years.");
                put(WHEN, "We are here since 1240 ! \nAnd nobody we will make me move out !!!");
                put(WHY, "I love this job, I could do it all my life.");
            }};

            k = new Knowledge(keywords, contents, specificKnowledges);
            knowledges.add(k);

            keywords = Arrays.asList("mission","job");
            contents = Arrays.asList("Yes, there are a few ongoing contrats");
            specificKnowledges = new HashMap<SpecificCategory, String>() {{
                put(WHO, "The guard over there can tell you.");
                put(WHAT, "The guard over there can tell you.");
                put(WHERE, "The guard over there can tell you.");
                put(WHY, "A group of criminals lives nearby");
            }};

            k = new Knowledge(keywords, contents, specificKnowledges);
            knowledges.add(k);

            registerCharacter(new Character("Barman", catchPhrase, knowledges, 1.2, 35, "Nice"));

            catchPhrase = "Hola stranger, any way I can help you ?";
            knowledges = new ArrayList<>();
            keywords = Arrays.asList("yes");
            contents = Arrays.asList("Ok, I'm listening, ask a question !","How ?");
            k = new Knowledge(keywords, contents);
            knowledges.add(k);

            keywords = Arrays.asList("mission","job");
            contents = Arrays.asList("There are few contrats indeed : \n -joe \n -Marcus \n -Elisabeth");
            k = new Knowledge(keywords, contents);
            knowledges.add(k);

            keywords = Arrays.asList("joe");
            contents = Arrays.asList("Yeah, it's a bad guy !","You can earn 1000$ if you catch him");
            specificKnowledges = new HashMap<SpecificCategory, String>() {{
                put(WHO, "Joe is only 25 years, but he have allready hold-up 5 banks and so much\n people");
                put(WHERE, "Joe stay generaly arround in the forest, to be cover from the cops");
                put(WHAT, "Joe have allready hold-up 5 banks and so much people");
            }};
            k = new Knowledge(keywords, contents, specificKnowledges);
            knowledges.add(k);

            keywords = Arrays.asList("marcus");
            contents = Arrays.asList("Yeah, it's a bad guy !","You can earn 1000$ if you catch him");
            specificKnowledges = new HashMap<SpecificCategory, String>() {{
                put(WHO, "Marcus is only 25 years, but he have allready hold-up 5 banks and so much\n people");
                put(WHERE, "Marcus stay generaly arround in the forest, to be cover from the cops");
                put(WHAT, "Marcus have allready hold-up 5 banks and so much people");
            }};
            k = new Knowledge(keywords, contents, specificKnowledges);
            knowledges.add(k);

            keywords = Arrays.asList("elisabeth");
            contents = Arrays.asList("Yeah, it's a bad girl !","You can earn 1000$ if you catch her");
            specificKnowledges = new HashMap<SpecificCategory, String>() {{
                put(WHO, "Elisabeth is only 25 years, but she have allready hold-up 5 banks and so much\n people");
                put(WHERE, "Elisabeth stay generaly arround in the forest, to be cover from the cops");
                put(WHAT, "Elisabeth have allready hold-up 5 banks and so much people");
            }};
            k = new Knowledge(keywords, contents, specificKnowledges);
            knowledges.add(k);

            registerCharacter(new Character("Guard", catchPhrase, knowledges, 1.2, 40, "Nice"));

        }

        try {
            Object nameServiceReference = broker.resolve_initial_references("NameService");
            NamingContextExt namingContext = NamingContextExtHelper.narrow(nameServiceReference);
            processor = InteractionProcessorHelper.narrow(namingContext.resolve_str("ABC"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------- Game Preparer Proxy --------- //

    public void saveChanges(Character character) {
        preparer.updateCharacter(character);
    }

    public void registerCharacter(Character character) {
        preparer.registerCharacter(character);
    }

    public void deleteCharacter(Character character) {
        preparer.deleteCharacter(character);
    }

    // ----- Interaction Processor Proxy ----- //

    public String sendMessage(String message, String dialogueID) {

        Replique toSend = new Replique(message, dialogueID);
        Replique reply = processor.sendMessage(toSend);

        return reply.content;
    }

    public String beginDialogue(String characterName) {
        return processor.beginDialogue(characterName);
    }

    // ----------- Monitor Proxy ------------ //

    public List<Character> getCharacters() {
        return monitor.getAllCharacter();
    }

    public Character getCharacter(String pseudo) {
        return monitor.getByName(pseudo);
    }
}
