package utils;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Missio;
import entitats.Transport;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.App;
import main.ClassFactory;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Classe auxiliar perquè l'usuari pugui definir qualsevol de les 6 Classes
 * disponibles al joc: Missió, Dron, Combat, Transport, Pilot i Mecànic.
 *
 * @author Txell Llanas: Creació / Implementació
 * @author Izan Jimenez: Implementació
 */
public class GenerarClasse {

    private static Scanner in = new Scanner(System.in);

    private static final Logger logger = LogManager.getLogger(GenerarClasse.class); // Crear Logs per aquesta classe
    private static final SingleSession singleton = SingleSession.getInstance(); // Recuperar instància de la Sessió única actual
    private static Session session;
    private static ClassFactory factory = new ClassFactory();

    // Còmputs i Xivatos
    private static int numMissions, numAeronaus, numAeronausPerMissio, 
                       numPilotades, numAutonomes, numPilots, numMecanics,
                       opcioAeronau, opcioPilotada, numAeronausTotals, 
                       numPilotadesTotals, numAutonomesTotals = 0;
    private static boolean has_missions, has_aeronaus, has_nausTransport,
                    has_nausCombat, has_drons, has_pilots, has_mecanics = false;

    // Textes detalls objectes creats
    private static String resum_missions, resum_aeronausTransport, 
                          resum_aeronausCombat, resum_aeronausDron, resum_pilots, 
                          resum_mecanics = "";

    // Instàncies
    private static Missio unaMissio = null;
    private static List<Missio> llistaMissions = null;
    private static Aeronau unaPilotada = null;
    private static List<Aeronau> llistaPilotades = null;
    private static Aeronau unaAutonoma = null;
    private static List<Aeronau> llistaAutonomes = null;
    

    /**
     * Mètode per generar una o vàries instàncies de tipus Missió. Genera de forma
     * automàtica i en quantitats majors de zero a escollir per l’usuari, les
     * entitats associades a aquesta.
     *
     * @author Txell Llanas: Creació/ Implementació
     */
    public static void crearMissio() {

        // Obrir Sessió per iniciar transacció
        session = SingleSession.getInstance().getSessio();
        

        // *** PREGUNTA A L'USUARI ***
        logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                + "\n" + "GENERAR MISSIÓ" + "\n\n"
                + ">> Quantes Missions vols crear? [Introdueix 0 per cancel·lar]");
        numMissions = utils.ValidadorOpcioMenu.validador(in);
        

        if (numMissions < 0) {                                                  // Valor incorrecte            
            logger.info("[AVÍS!] Cal introduir un nombre enter superior a 1!");

        } else {
            
            if (numMissions == 0) {                                             // Tornar al Menú Principal
                App.menu(singleton);

            } else {                                                            // Valor correcte

                session.beginTransaction();
        
                // Generar Missions
                llistaMissions = new ArrayList<>();
                llistaMissions = factory.missionsFactory(numMissions);

                // Persistir dades dins la taula 'missio'
                llistaMissions.stream().forEach(x -> session.persist(x));                

                // Desar a BBDD
                session.getTransaction().commit();                
                
                resum_missions = "- Nom de les "+ numMissions +" Missions: ";
                // Desar detalls Missions generades
                for (int i = 0; i < llistaMissions.size(); i++) {

                    if ( llistaMissions.size() > 1 ) {                          // Hi ha vàries Missions
                        
                        if (i < llistaMissions.size() - 1) {
                            resum_missions += llistaMissions.get(i).getAtributString()
                                              +" (id:"+llistaMissions.get(i).getCosmicMissionCode()+")" + ", ";
                        } else {
                            resum_missions += llistaMissions.get(i).getAtributString()
                                              +" (id:"+llistaMissions.get(i).getCosmicMissionCode()+")";
                        }
                    
                    } else {                                                    // 1 sola Missió
                        resum_missions = "- Nom Missió: " 
                                     + llistaMissions.get(0).getAtributString() 
                                     +" (id:"+llistaMissions.get(0).getCosmicMissionCode()+")";                        
                    }
                }
            }
        }

        // Xivato: Existeixen 1 o més missions
        has_missions = true;
        logger.trace("\nMissió(ns) generada(es) correctament! :D");
        logger.info("\n" + "------------------------------------------------------------------------" + "\n");

        // Generar + persistir entitats relacionades...
        if (!has_aeronaus) {
            crearAeronau();
            assignarAeronausPerMissio();            
        }
        if (!has_pilots) {
            //crearPilot();
        }        
        if (!has_mecanics) {
            //crearMecanic();
        }
        
        session.close();                                                        // Finalitzem sessió
        
        // Resum instàncies generades
        logger.info("RESUM D'OBJECTES GENERATS: \n"
                + resum_missions);        
        if (has_aeronaus)
            logger.info("- Aeronaus: "+ (llistaMissions.size() * numAeronaus) +" (Total), "+ numAeronaus +" per Missió.");
        if (has_nausTransport)
            logger.info(resum_aeronausTransport);
        if (has_nausCombat)
            logger.info(resum_aeronausCombat);
        if (has_drons)
            logger.info(resum_aeronausDron);
        if (has_pilots)
            logger.info(resum_pilots);
        if (has_mecanics)
            logger.info(resum_mecanics);
        
        
        // Resetejar variables
        llistaMissions.clear();
        llistaPilotades.clear();
        llistaAutonomes.clear();
        numMissions  = 0;
        numAeronaus  = 0;
        numPilotades = 0;
        numAutonomes = 0;
        numAeronausTotals = 0;
        numPilotadesTotals = 0;
        numAutonomesTotals = 0;
        has_missions      = false;
        has_aeronaus      = false;
        has_nausTransport = false;
        has_nausCombat    = false;
        has_drons         = false;
        has_pilots        = false;
        has_mecanics      = false;
    }

    /**
     * Mètode per generar una o vàries instàncies de tipus Aeronau. Genera de forma
     * automàtica i en quantitats majors de zero a escollir per l’usuari, les
     * entitats associades a aquesta.
     *
     * @author Txell Llanas: Creació/ Implementació
     */
    public static void crearAeronau() {

        // Detectar si hi ha 1 o més aeronaus per personalitzar l'enunciat
        String txt = "";
        if (numMissions == 1)
            txt = "assignar-li a aquesta";
        else
            txt = "assignar a cada";
        
        
        // *** PREGUNTES A L'USUARI ***
        logger.info("GENERAR AERONAU\n\n" +
            ">> Quantes Aeronaus vols " + txt + " Missió? [Màx. 8 per missió]");
        numAeronaus = utils.ValidadorOpcioMenu.numAeronausMissio(in);
        numAeronausPerMissio = numAeronaus;
        
        if ( numAeronaus > 1 ) {                                                // Vàries Aeronaus
            
            logger.info(">> D'aquestes " + numAeronaus + " aeronaus, quantes en vols de tipus Pilotada?");            
            numPilotades = utils.ValidadorOpcioMenu.validador(in);
            
            if ( numPilotades == 1 ) {                                          // Només 1 Pilotada                
                logger.info(">> Vols que l'aeronau Pilotada sigui de tipus Transport[1] o Combat[2]?");
                opcioPilotada = utils.ValidadorOpcioMenu.validador(in);
                opcioAeronau = 1;
                
            } else if ( numPilotades > 1 ) {                                    // Vàries Pilotades
                logger.info(">> Vols que les aeronaus Pilotades siguin de tipus Transport[1] o Combat[2]?");
                opcioPilotada = utils.ValidadorOpcioMenu.validador(in);
                opcioAeronau = 1;                
            }           
            
        } else {                                                                // Una Aeronau
            
            logger.info(">> Vols que l'aeronau sigui de tipus Pilotada[1] o Autònoma[2]?");
            opcioAeronau = utils.ValidadorOpcioMenu.validador(in);

            if ( opcioAeronau == 1 ) {
                numPilotades = 1;
                logger.info(">> Vols que l'aeronau Pilotada sigui de tipus Transport[1] o Combat[2]?");
                opcioPilotada = utils.ValidadorOpcioMenu.validador(in);
            }
        }
        numAutonomes = numAeronaus - numPilotades;  //numAeronausPerMissio - numPilotades
        
        // Actualitzo còmput per persistir correctament a BD
        numAeronausTotals += numAeronaus * llistaMissions.size();
        numPilotadesTotals += numPilotades * llistaMissions.size();
        numAutonomesTotals += numAutonomes * llistaMissions.size();
        
        
        // Iniciar Transacció
        session.beginTransaction();
        
        // Inicialitzar Aeronaus
        llistaPilotades = new ArrayList<>();
        llistaAutonomes = new ArrayList<>();
            
        // Generar Aeronaus Totals
        if ( numPilotadesTotals >= 1 && opcioAeronau == 1 ) {                   // Tipus (Pilotada)
            
                if ( opcioPilotada == 1 ) {                                     // Nau de Transport
                    
                    for ( int i = 0; i < numPilotadesTotals; i++ ) {
                        llistaPilotades.add(factory.aeronauFactory(Transport.class));
                    }
                    resum_aeronausTransport = "  - Transport: " + numPilotades + " per missió.";
                    has_nausTransport = true;
                    
                } else {                                                        // Nau de Combat
                    
                    for ( int i = 0; i < numPilotadesTotals; i++ ) {
                        llistaPilotades.add(factory.aeronauFactory(Combat.class));
                    }
                    resum_aeronausCombat = "  - Combat: " + numPilotades + " per missió.";                        
                    has_nausCombat = true;                    
                }
        }
            
        if ( numAutonomesTotals >= 1 ) {                                        // Tipus (Autonoma)
            
            for ( int i = 0; i < numAutonomesTotals; i++ ) {                    // Dron                    
                llistaAutonomes.add(factory.aeronauFactory(Dron.class));
            }
            
            resum_aeronausDron = "  - Drons: " + numAutonomes + " per missió.";
            has_drons = true;
        }

        // Persistir dades Totals
        if ( numPilotadesTotals > 0 )
            llistaPilotades.stream().forEach(x -> session.persist(x));        
        if ( numAutonomesTotals > 0 )
            llistaAutonomes.stream().forEach(x -> session.persist(x));
        
        // Persistir a BBDD
        session.getTransaction().commit();
        
        // Xivato: Existeix 1 o més aeronaus
        has_aeronaus = true;
        logger.trace("\nAeronau(s) generada(es) correctament! :D");
        logger.info("\n" + "------------------------------------------------------------------------" + "\n");
        
        // Generar + persistir entitats relacionades...        
        if (!has_pilots) {
            //crearPilot();
        }        
        if (!has_mecanics) {
            //crearMecanic();
        }
        if (!has_missions) {
            crearMissio();
            assignarAeronausPerMissio();
        }
        
    }
    
    /**
     * Mètode per assignar una o vàries instàncies de tipus Aeronau a una Missió.
     *
     * @author Txell Llanas: Creació/ Implementació
     */
    public static void assignarAeronausPerMissio() {
        
        // Iniciar Transacció
        session.beginTransaction();
        
        // Enllaçar Aeronaus Pilotades a Missió(ns)
        int indexAeronau = 0;
        for ( int i = 0; i < llistaMissions.size(); i++ ) {                     // Nº de Missions
            List<Aeronau> temp = new ArrayList<>();
            int nausPilotades = numPilotades;
            int nausAutonomes = numAutonomes;
            
            //for (int j = indexAeronau; j < numAeronausPerMissio; j++) {       // Nº aeronaus x Missió     
            for (int j = indexAeronau; j < numAeronausTotals; j++) {            // Nº aeronaus x Missió     
               
                if ( nausPilotades > 0 ) { // Afegir si hi ha Pilotades (Transport/Combat)...
                    //if (llistaPilotades.get(j).getMissions().size() < 2) {    // Validar cardinalitat Max 2 missions x aeronau
                        temp.add(llistaPilotades.get(j));
                        nausPilotades--;
                    //}
                }
                if ( nausAutonomes > 0 ) { // Afegir si hi ha Drons...
                    temp.add(llistaAutonomes.get(j));
                    nausAutonomes--;
                }
                
            }
            llistaMissions.get(i).setAeronaus(temp);
            indexAeronau += temp.size();
        }
        
        // Persistir a BBDD
        session.getTransaction().commit();
        
    }
}
