package fr.polytech.pnsinnov.smartinteract;

public class Main {

    /**
     * First you need to start ORBD using :
     * Windows : start orbd -ORBInitialPort 1050 -ORBInitialHost localhost
     * MacOs   : orbd -ORBInitialPort 1050 -ORBInitialHost localhost
     *
     * @param args -ORBInitialPort 1050 -ORBInitialHost localhost
     */
    public static void main(String[] args) {
        CorbaInterface corba = new CorbaInterface();
        corba.startORB(args);
        corba.startListenning();
    }
}
