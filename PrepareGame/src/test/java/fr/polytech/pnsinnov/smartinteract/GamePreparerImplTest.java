package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GamePreparerImplTest {

    private List<Character> storage;
    private GamePreparer gamePreparer;
    private Character bob;

    @Before
    public void before() {
        storage = new ArrayList<>();
        bob = new Character("Bob", "Hello There");

        Archiver archiver = mock(ArchiverImpl.class);

        doAnswer((answer) -> addOrUpdateBob())
                .when(archiver)
                .save(any(Character.class));

        doAnswer((answer)-> storage.remove(bob))
                .when(archiver)
                .remove(bob);


        when(archiver.findCharacter("Bob")).thenReturn(Optional.of(bob));
        when(archiver.getAllCharacter()).thenReturn(storage);

        gamePreparer = new GamePreparerImpl(archiver);
    }

    @Test
    public void registerCharacter_Valid() {
        gamePreparer.registerCharacter(bob);

        // ->  Test success since no exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerCharacter_Fail() {
        gamePreparer.registerCharacter(bob);
        gamePreparer.registerCharacter(bob); // Second call fails
    }

    @Test
    public void updateCharacter() {
        assertEquals("Hello There", bob.catchPhrase());

        bob.setCatchPhrase("Hi :D");

        gamePreparer.updateCharacter(bob);

        Character bob2 = storage.get(0);

        assertEquals("Hi :D", bob2.catchPhrase());
    }

    @Test
    public void deleteCharacter() {
        storage.add(bob);

        gamePreparer.deleteCharacter(bob);

        assertTrue(storage.isEmpty());
    }


    private boolean addOrUpdateBob(){
        if (storage.contains(bob))
            storage.set(storage.indexOf(bob), bob);
        else
            storage.add(bob);

        return true;
    }
}