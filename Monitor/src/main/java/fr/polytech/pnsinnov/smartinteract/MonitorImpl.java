package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;

import java.util.List;
import java.util.Optional;

public class MonitorImpl implements Monitor {
    private final Archiver archiver;

    public MonitorImpl() {
        this(new ArchiverImpl());
    }

    public MonitorImpl(Archiver archiver) {
        this.archiver = archiver;
    }

    @Override
    public List<Character> getAllCharacter() {
        return archiver.getAllCharacter();
    }

    @Override
    public Character getByName(String pseudo) {
        Optional<Character> c = archiver.findCharacter(pseudo);
        if(c.isPresent())
            return c.get();
        throw new IllegalArgumentException();
    }

}
