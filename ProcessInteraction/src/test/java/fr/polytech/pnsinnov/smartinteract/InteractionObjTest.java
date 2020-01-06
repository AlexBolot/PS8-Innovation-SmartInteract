package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import fr.polytech.pnsinnov.smartinteract.stubs.Replique;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InteractionObjTest {

    private final String validID = "ID.dialogue.1";
    private final String emptyID = "ID.dialogue.2";

    private final String nameCharacter = "Bob";
    private final Character bob = new Character(nameCharacter, "My name is Bob :)");

    private InteractionObj processor;

    @Before
    public void setUp() {
        Archiver archiver = mock(Archiver.class);
        Dialogue d = new Dialogue();
        d.setSpeaker(nameCharacter);
        when(archiver.findDialogue(validID)).thenReturn(Optional.of(d));
        when(archiver.findDialogue(emptyID)).thenReturn(Optional.empty());
        when(archiver.findCharacter(nameCharacter)).thenReturn(Optional.of(bob));

        doNothing().when(archiver).save(any(Dialogue.class));

        processor = new InteractionObj(null, archiver);
    }

    @Test
    @Ignore
    //TODO fix or mock this -> its using python server from analyse syntax
    public void sendMessage_success() {
        Replique result = processor.sendMessage(new Replique("Blob", validID));
        assertTrue(equals(new Replique("Je n'ai pas trouvé de réponse associée aux mots clés renseignés.\nVeuillez être plus précis.", validID), result));
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    //TODO fix or mock this -> its using python server from analyse syntax
    public void sendMessage_fail() {
        processor.sendMessage(new Replique("Blob", emptyID));
    }

    @Test
    @Ignore
    public void beginDialogue() {
        processor.beginDialogue("Bob");
    }

    private boolean equals(Replique r1, Replique r2) {
        return r1.content.equals(r2.content) && r1.idDia.equals(r2.idDia);
    }
}