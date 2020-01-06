package fr.polytech.pnsinnov.smartinteract;


import fr.polytech.pnsinnov.smartinteract.model.Message;
import fr.polytech.pnsinnov.smartinteract.model.SpecificCategory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SyntaxAnalyserImplTest {
    private SyntaxAnalyser sai;
    private Message msg;

    @Before
    public void before() throws IOException {
        sai = new SyntaxAnalyserImpl();
        msg = new Message();
    }

    @Test
    @Ignore
    // TODO mock or fix usage of Python Server duging @Before
    public void analyseMessage() throws IOException {
        msg.setContent("Where is bryan ?");
        msg = sai.analyseMessage(msg);
        assertEquals(SpecificCategory.WHERE, msg.specificCategory());
        assertEquals("bryan",msg.tokens().get(2));
    }
}