package fr.polytech.pnsinnov.smartinteract.model;

import java.util.*;

public class Knowledge {

    private final String ID = IDGenerator.generateID();
    private List<String> keywords;
    private List<String> contents;

    private HashMap<SpecificCategory, String> specificKnowledges;

    public Knowledge(){
        this(new ArrayList<>(), new ArrayList<>());
    }

    public Knowledge(List<String> keywords, List<String> contents) {
        this.specificKnowledges = new HashMap<>();
        SpecificCategory.forEach(specificCategory -> this.specificKnowledges.put(specificCategory, ""));
        this.keywords = keywords;
        this.contents = contents;
    }

    public Knowledge(List<String> keywords, List<String> contents, HashMap<SpecificCategory, String> specificKnowledges) {
        this(keywords,contents);
        specificKnowledges.forEach((category, value)->  this.specificKnowledges.put(category, value));
    }

    public String ID() {
        return ID;
    }

    public List<String> keywords() {
        return keywords;
    }

    public List<String> contents() {
        return contents;
    }

    public HashMap<SpecificCategory, String> specificKnowledges() {
        return specificKnowledges;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public boolean hasContent(List<String> otherContent){
        Collections.sort(otherContent);
        Collections.sort(contents());

        return contents().equals(otherContent);
    }

    public boolean hasKeywords(List<String> otherKeywords){
        Collections.sort(otherKeywords);
        Collections.sort(keywords());

        return keywords().equals(otherKeywords);
    }

    public String getSpecificKnowledge(SpecificCategory category) {
        return specificKnowledges.get(category);
    }

    public void addSpecificKnowledge(SpecificCategory category, String content) {
        specificKnowledges.put(category, content);
    }

    public String getAnyContent() {
        int i = new Random().nextInt(contents.size());
        if(i >= 0)
            return contents.get(i);
        return null;
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "keywords=" + keywords +
                ", contents=" + contents +
                ", specificKnowledges=" + specificKnowledges.entrySet() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Knowledge)) return false;
        Knowledge knowledge = (Knowledge) o;
        return Objects.equals(ID, knowledge.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
