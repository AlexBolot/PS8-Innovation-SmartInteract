package fr.polytech.pnsinnov.smartinteract;

import fr.polytech.pnsinnov.smartinteract.stubs.InteractionProcessor;
import fr.polytech.pnsinnov.smartinteract.stubs.InteractionProcessorHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class CorbaInterface {

    private InteractionObj interactionProcessor;
    private ORB broker;

    public void startORB(String[] args) {

        try {
            // create and initialize the ORB
            broker = ORB.init(args, null);
            POA adapter = POAHelper.narrow(broker.resolve_initial_references("RootPOA"));
            adapter.the_POAManager().activate();

            // create servant and register it with the ORB
            interactionProcessor = new InteractionObj(broker);

            // get object reference from the servant
            Object ref = adapter.servant_to_reference(interactionProcessor);
            InteractionProcessor href = InteractionProcessorHelper.narrow(ref);

            Object objRef = broker.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("ABC");
            ncRef.rebind(path, href);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startListenning() {

        System.out.println("InteractionProcessing Server ready and waiting ...");

        while (true) broker.run();
    }
}