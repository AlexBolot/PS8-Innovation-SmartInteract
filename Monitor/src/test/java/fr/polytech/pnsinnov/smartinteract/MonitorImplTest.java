package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import fr.polytech.pnsinnov.smartinteract.model.Knowledge;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MonitorImplTest {

    private static Monitor monitor;
    private static Archiver archiver;

    @Before
    public void before() {
        archiver = mock(Archiver.class);
        doNothing().when(archiver).save(any(Character.class));
        monitor = new MonitorImpl(archiver);
    }

    @Test
    public void getAllCharacter() {
        Character bob = new Character("Bob", "Hello There");
        Character alice = new Character("Alice", "Hello There");

        Knowledge knowledge1 = new Knowledge(Arrays.asList("Key1", "Key2"), Arrays.asList("Content1", "Content2"));
        Knowledge knowledge2 = new Knowledge(Arrays.asList("Key3", "Key4"), Arrays.asList("Content3", "Content4"));

        bob.addKnowledge(knowledge1);
        alice.addKnowledge(knowledge2);

        List<Character> characters = new ArrayList<>();
        characters.add(bob);
        characters.add(alice);
        when(archiver.getAllCharacter()).thenReturn(characters);

        archiver.save(bob);
        archiver.save(alice);

        List<Character> allCharacter = monitor.getAllCharacter();

        assertEquals(2, allCharacter.size());

        assertEquals(bob, allCharacter.get(0));
        assertEquals(alice, allCharacter.get(1));

        assertEquals(knowledge1, bob.knowledges().get(0));
        assertEquals(knowledge2, alice.knowledges().get(0));
    }
}