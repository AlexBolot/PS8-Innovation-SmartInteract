package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.exceptions.CannotMatchKeywordsException;
import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Dialogue;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import fr.polytech.pnsinnov.smartinteract.model.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnswerGeneratorImplTest {

    private Dialogue dialog;
    private Message message;
    private Knowledge knowledge;
    private Character player, speaker;

    private AnswerGenerator answerGenerator;

    @Before
    public void before() {
        Archiver archiver = mock(Archiver.class);

        answerGenerator = new AnswerGeneratorImpl(archiver);

        List<String> keywords = new ArrayList<>(Arrays.asList("trésor", "où"));
        this.knowledge = new Knowledge(keywords, Collections.singletonList("Il se trouve dans le chateau."));
        speaker = new Character("Bot1", "Hello I'm Bot1");
        speaker.addKnowledge(knowledge);
        player = new Character("Joueur1", "Hello I'm Joueur1");
        this.dialog = new Dialogue(new ArrayList<>(), speaker.pseudo(), player.pseudo());
        this.message = new Message("où est le trésor ?", true, keywords, player);

        when(archiver.findCharacter("Bot1")).thenReturn(Optional.of(speaker));
        when(archiver.findCharacter("Joueur1")).thenReturn(Optional.of(player));
    }

    @Test
    public void generateAnswerTest() throws CannotMatchKeywordsException {
        assertEquals(player, this.message.sender());
        assertEquals("où est le trésor ?", this.message.content());

        Message received = answerGenerator.generateAnswer(this.dialog, this.message);
        this.dialog.addMessage(received);

        assertEquals("Il se trouve dans le chateau.", received.content());
        assertEquals(speaker.pseudo(), received.sender().pseudo());
    }
}
