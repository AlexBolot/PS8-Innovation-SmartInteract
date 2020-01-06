package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.model.Character;

public class GamePreparerImpl implements GamePreparer {

    private final Archiver archiver;

    public GamePreparerImpl() {
        this(new ArchiverImpl());
    }

    public GamePreparerImpl(Archiver archiver) {
        this.archiver = archiver;
    }

    @Override
    public void registerCharacter(Character character) {

        boolean exists = archiver.getAllCharacter().contains(character);

        if (exists)
            throw new IllegalArgumentException("Le personnage existe déjà !");
        else
            archiver.save(character);
    }

    @Override
    public void updateCharacter(Character character) {
        archiver.save(character);
    }

    @Override
    public void deleteCharacter(Character character) {
        archiver.remove(character);
    }
}
