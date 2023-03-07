package utils;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Soldat;
import entitats.Transport;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.ClassFactory;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Classe auxiliar perquè l'usuari pugui definir qualsevol de les 6 Classes
 * disponibles al joc: Missió, Dron, Combat, Transport, Pilot i Mecànic.
 *
 * @author Txell Llanas: Creació / Implementació
 */
public class GenerarClasse {

    private static Scanner in = new Scanner(System.in);
    private static final int MAX_NAUS_PER_MISSIO = 2;

    private static final Logger logger = LogManager.getLogger(GenerarClasse.class); // Crear Logs per aquesta classe
    private static final SingleSession singleton = SingleSession.getInstance(); // Recuperar instància de la Sessió única actual
    private static Session session;
    private static ClassFactory factory = new ClassFactory();

    // Còmputs i Xivatos
    private static int numMissions, aeronausPerMissio, aeronausTotals, numMecanicsPerAeronau,
            numPilotades, numAutonomes, numPilots, numMecanics,
            opcioAeronau, opcioPilotada,
            numPilotadesTotals, numAutonomesTotals, indexNau, indexPilotada, indexAutonoma, tipusPilotada, tipusAeronau = 0;
    private static boolean has_missions, has_aeronaus, has_nausTransport,
            has_nausCombat, has_drons, has_pilots, has_mecanics, contesta_usuari, correcte = false;

    // Textes detalls objectes creats
    private static String resum_missions, resum_aeronausTransport,
            resum_aeronausCombat, resum_aeronausDron, resum_pilots,
            resum_mecanics = "";

    // Instàncies
    private static List<Missio> llistaMissions = null;
    private static List<Aeronau> llistaAeronaus = null;
    private static List<Aeronau> llistaPilotades = null;
    private static List<Aeronau> llistaAutonomes = null;
    private static List<Mecanic> llistaMecanics = null;
    private static List<Pilot> llistaPilots = null;

    public static void iniciarGeneracions(int opcio) {

        try {

            // Obrir Sessió per iniciar transacció
            session = SingleSession.getInstance().getSessio();

            logger.info("\n" + "------------------------------------------------------------------------" + "\n");

            switch (opcio) {
                case 1:
                    logger.info("GENERADOR D'INSTÀNCIES: << MISSIONS >>\n");
                    crearMissio();
                    break;
                case 2:
                    logger.info("GENERADOR D'INSTÀNCIES: << AERONAU DE TRANSPORT >>\n");
                    tipusAeronau = 2;
                    crearAeronau();
                    break;
                case 3:
                    logger.info("GENERADOR D'INSTÀNCIES: << AERONAU DE COMBAT >>\n");
                    tipusAeronau = 3;
                    crearAeronau();
                    break;
                case 4:
                    logger.info("GENERADOR D'INSTÀNCIES: << AERONAU: DRON >>\n");
                    tipusAeronau = 4;
                    crearAeronau();
                    break;
                case 5:
                    logger.info("GENERADOR D'INSTÀNCIES: << PILOTS >>\n");
                    crearPilot();
                    break;
                case 6:
                    logger.info("GENERADOR D'INSTÀNCIES: << MECÀNICS >>\n");
                    crearMecanic();
                    break;
            }

            // Confirmar que tots els objectes s'han generat correctament
            logger.info("\nObjectes generats correctament! :D");

            // Resum instàncies generades
            logger.info("\n" + "------------------------------------------------------------------------" + "\n\n"
                    + "RESUM D'OBJECTES GENERATS: \n\n"
                    + resum_missions);
            if (has_nausTransport) {
                logger.info(resum_aeronausTransport);
            }
            if (has_nausCombat) {
                logger.info(resum_aeronausCombat);
            }
            if (has_drons) {
                logger.info(resum_aeronausDron);
            }
            if (has_pilots) {
                logger.info(resum_pilots);
            }
            if (has_mecanics) {
                logger.info(resum_mecanics);
            }

            // Resetejar variables ???
            llistaMissions.clear();
            llistaPilotades.clear();
            llistaAutonomes.clear();
            numMissions = 0;
            aeronausPerMissio = 0;
            aeronausTotals = 0;
            numPilots = 0;
            numMecanics = 0;
            tipusAeronau = 0;
            has_missions = false;
            has_nausTransport = false;
            has_nausCombat = false;
            has_drons = false;
            has_pilots = false;
            has_mecanics = false;
            correcte = false;

        } catch (HibernateException ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Excepció d'hibernate: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());

        } finally {

            //Finalitzem Hibernate
            session.close();
        }

    }

    /**
     * Mètode per generar una o vàries instàncies de tipus Missió. Genera de
     * forma automàtica i en quantitats majors de zero a escollir per l’usuari,
     * les entitats associades a aquesta.
     *
     * @author Txell Llanas: Creació/ Implementació
     */
    public static void crearMissio() {

        try {

            // GENERAR MISSIÓ(NS)
            if (numMissions == 0) {
                logger.info(">> Quantes Missions vols crear? [mín.1]");
                numMissions = utils.ValidadorOpcioMenu.validador(in);
            }

            session.beginTransaction();

            // Inicialitzar + Generar llista de Missions
            llistaMissions = new ArrayList<>();
            llistaMissions = factory.missionsFactory(numMissions);

            // Persistir dades generades
            llistaMissions.stream().forEach(x -> session.persist(x));

            // Desar a BBDD
            session.getTransaction().commit();

            // Desar detalls Missions generades
            if (llistaMissions.size() > 1) {                                    // Vàries Missions

                resum_missions = numMissions + " Missions creades: ";

                for (int i = 0; i < llistaMissions.size(); i++) {
                    if (i < llistaMissions.size() - 1) {
                        resum_missions += llistaMissions.get(i).getAtributString()
                                + " (id:" + llistaMissions.get(i).getCosmicMissionCode() + ")" + ", ";
                    } else {
                        resum_missions += llistaMissions.get(i).getAtributString()
                                + " (id:" + llistaMissions.get(i).getCosmicMissionCode() + ")";
                    }
                }

            } else {                                                            // 1 sola Missió
                resum_missions = "- Nom Missió: "
                        + llistaMissions.get(0).getAtributString()
                        + " (id:" + llistaMissions.get(0).getCosmicMissionCode() + ")";
            }

            // GENERAR + ASSIGNAR AERONAU(S)
            if (aeronausPerMissio == 0) {                                    // Generar un tipus d'aeronau de forma random

                int max = 4;
                int min = 2;

                if (tipusAeronau == 0) {
                    tipusAeronau = (int) (Math.floor(Math.random() * (max - min + 1)) + min);
                }

                crearAeronau();
            }
//            
//            // GENERAR + ASSIGNAR MECÀNIC(S)
//            if ( !has_mecanics ) {
//                crearMecanic();
//            }
//            //assignarMecanics();

        } catch (HibernateException ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Excepció d'hibernate: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());

        }

        // Xivato: Existeixen 1 o més missions
        has_missions = true;

    }

    /**
     * Mètode per generar una o vàries instàncies de tipus Transport. Genera de
     * forma automàtica i en quantitats majors de zero a escollir per l’usuari,
     * les entitats associades a aquesta.
     *
     * @author Txell Llanas: Creació/ Implementació
     */
    public static void crearAeronau() {

        try {

            // GENERAR + ASSIGNAR AERONAU(S)
            if (aeronausPerMissio == 0) {
                logger.info(">> Quantes Aeronaus vols assignar a una Missió? [Mín. 1 - Màx. 8]");
                //logger.info(">> Quantes Aeronaus d'aquest tipus vols crear?");
                aeronausPerMissio = utils.ValidadorOpcioMenu.numAeronausMissio(in);

            }

            if (numMissions == 0) {
                // Calcular el nº de missions necessàries per cobrir la proporció de màx. 2 naus per missió
                numMissions = (int) Math.ceil((double) aeronausPerMissio / MAX_NAUS_PER_MISSIO);
                crearMissio();
                logger.info("::: S'han autogenerat " + numMissions + " Missions amb " + aeronausPerMissio + " naus assignades cadascuna :::");
            }

            // Calcular les naus necessàries a generar (respectant cardinalitat de 2 Missions màx.)
            if ((numMissions > 1) && (aeronausPerMissio >= 2)) {  // Sobretot és aeronausPerMissio el filtre important!
                //aeronausTotals = 2 + aeronausPerMissio * (int) Math.floor((numMissions-1)/2);
                aeronausTotals = aeronausPerMissio + aeronausPerMissio * (int) Math.floor((numMissions - 1) / 2);
            } else {
                aeronausTotals = aeronausPerMissio * numMissions;
            }

            // Iniciar Transacció
            session.beginTransaction();

            // Inicialitzar llistat d'Aeronaus
            llistaPilotades = new ArrayList<>();
            llistaAutonomes = new ArrayList<>();

            // Generar Aeronaus Totals
            for (int i = 0; i < aeronausTotals; i++) {

                switch (tipusAeronau) {
                    case 2:
                        llistaPilotades.add(factory.aeronauFactory(Transport.class));
                        break;
                    case 3:
                        llistaPilotades.add(factory.aeronauFactory(Combat.class));
                        break;
                    case 4:
                        llistaAutonomes.add(factory.aeronauFactory(Dron.class));
                        break;
                }
            }

            // Persistir dades generades
            if (!llistaPilotades.isEmpty()) {

                llistaPilotades.stream().forEach(x -> session.persist(x));

            } else {

                llistaAutonomes.stream().forEach(x -> session.persist(x));

            }

            // Desar a BBDD
            session.getTransaction().commit();

            // ASSIGNAR: AERONAUS <--> MISSIONS
            assignarAeronaus();

//            // GENERAR + ASSIGNAR PILOT(S)
//            if ( !has_pilots ) {
//                crearPilot();
//                assignarPilot();
//            } else {
//                assignarPilot();
//            }
//
//            // GENERAR + ASSIGNAR MECÀNIC(S)
//            if ( !has_mecanics ) {
//                crearMecanic();
//                assignarMecanics();
//            } else {
//                assignarMecanics();
//            }
            // Desar detalls Aeronaus generades
            switch (tipusAeronau) {
                case 2:
                    resum_aeronausTransport = "- Generades " + (aeronausTotals)
                            + " aeronaus de Transport per cobrir " + numMissions
                            + " Missió(ns), [1 aeronau participa a 2 Missions com a màxim]";
                    has_aeronaus = true;
                    has_nausTransport = true;
                    break;
                case 3:
                    resum_aeronausCombat = "- Generades " + (aeronausTotals)
                            + " aeronaus de Combat per cobrir " + numMissions
                            + " Missió(ns), [1 aeronau participa a 2 Missions com a màxim]";
                    has_aeronaus = true;
                    has_nausCombat = true;
                    break;
                case 4:
                    resum_aeronausDron = "- Generats " + (aeronausTotals)
                            + " Drons per cobrir " + numMissions
                            + " Missió(ns), [1 aeronau participa a 2 Missions com a màxim]";
                    has_aeronaus = true;
                    has_drons = true;
                    break;
            }

        } catch (HibernateException ex) {

            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.error("Excepció d'hibernate: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }

    /**
     * Mètode per assignar una o vàries instàncies de tipus Aeronau a una
     * Missió.
     *
     * @author Txell Llanas: Creació/ Implementació
     * @author Izan Jimenez: Implementació
     * @author Pablo Morante: Implementació
     * @author Víctor García: Implementació
     */
    public static void assignarAeronaus() {

        int i = 0, indexAutonoma = 0, indexPilotada = 0;

        if (tipusAeronau != 4) {
            assignarMecanics();
        }

        while (i < llistaMissions.size()) {

            session.beginTransaction();

            llistaMissions.stream().forEach(x -> session.persist(x));

            List<Aeronau> tempNau = new ArrayList<>();

            // Seleccionar aeronaus x cada Missió
            for (int j = 0; j < aeronausPerMissio; j++) {

                if (!llistaPilotades.isEmpty() && indexPilotada < llistaPilotades.size()) {  // Evitar desbordament de l'índex  // Afegir si hi ha Pilotades (Transport/Combat)...                    
                    tempNau.add(llistaPilotades.get(indexPilotada));
                    indexPilotada++;                                        // Evitar assignar índexs repetits: avanço al següent del total d'elements de la llista                         
                }
                if (!llistaAutonomes.isEmpty() && indexAutonoma < llistaAutonomes.size()) {  // Evitar desbordament de l'índex  // Afegir si hi ha Drons...
                    tempNau.add(llistaAutonomes.get(indexAutonoma));
                    indexAutonoma++;                                        // Evitar assignar índexs repetits: avanço al següent del total d'elements de la llista                            
                }
            }
//                llistaAutonomes.stream().forEach(x -> session.persist(x));
            // Assignar Aeronaus a una Missió (màx.8)          
            try {
                if (i % 2 == 0) { // si el índice es par
                    factory.addAeronausToMissio(tempNau, llistaMissions.get(i));
                    //llistaMissions.get(i).setAeronaus(tempNau);
                    if (i + 1 < llistaMissions.size()) {
                        factory.addAeronausToMissio(tempNau, llistaMissions.get(i + 1));
                        //llistaMissions.get(i + 1).setAeronaus(tempNau);
                    }
                } else { // si el índice es impar
                    factory.addAeronausToMissio(tempNau, llistaMissions.get(i - 1));
                    factory.addAeronausToMissio(tempNau, llistaMissions.get(i));
                    //llistaMissions.get(i - 1).setAeronaus(tempNau);
                    //llistaMissions.get(i).setAeronaus(tempNau);
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            // HO POSEM TOT AQUÍ PER UNIFICAR FUNCIONALITATS, o SEPAREM EN MÈTODES...? 
            // Assignar Pilot i Mecanics a Aeronau
            if (tipusAeronau != 4) {  // Si l'aeronau està pilotada (No és un Dron)
                //factory.addPilotToAeronauPilotada((Pilot) factory.pilotsFactory(1), (Pilotada) pilotada);  //(Pilotada)factory.aeronauFactory(Transport.class) 
                for (Aeronau p : tempNau) {
                    llistaMecanics = new ArrayList<>();
                    List<Soldat> s = new ArrayList<>();
                    s = factory.mecanicsToAeronauFactory(numMecanics, (Pilotada) p);
                    for (Soldat m : s) {
                        llistaMecanics.add((Mecanic) m);
                    }
                    llistaMecanics.stream().forEach(x -> session.persist(x));
                }

//                tempNau.stream().forEach(x -> {
//                    try {
//                        factory.mecanicsToAeronauFactory(numMecanics, (Pilotada) x);
//                    } catch (Exception ex) {
//                        java.util.logging.Logger.getLogger(GenerarClasse.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                });
                //factory.addMecanicsToPilotada(mecanics, (Pilotada) tempNau.get(indexPilotada));  //(Pilotada)factory.aeronauFactory(Transport.class)
            }

//                llistaMissions.stream().forEach(x -> session.persist(x));
            //llistaMissions.stream().forEach(x -> x.setAeronaus(tempNau));
            session.getTransaction().commit();
            i += 2;
        }
//        }
    }

    public static void crearPilot() {

        if (numPilots == 0) {
            // GENERAR PILOT(S)            
            logger.info(">> Quants Pilots vols crear?");
            numPilots = utils.ValidadorOpcioMenu.validador(in);
        }
        aeronausPerMissio = numPilots;

        has_pilots = true;

    }

    public static void crearMecanic() {

        if (numMecanics == 0) {
            // GENERAR MECÀNIC(S)            
            logger.info(">> Quants Mecànics vols crear?");
            numMecanics = utils.ValidadorOpcioMenu.validador(in);

//            if (numMecanics % 2 == 0) {
//                numMecanicsPerAeronau = 2;
//                if (!llistaAutonomes.isEmpty())
//                    factory.addMecanicsToPilotada(factory.mecanicsFactory(numMecanicsPerAeronau), tempNaus);
//                llistaMecanics.stream().forEach(x -> x.setPilotada(llistaAeronaus));
//            } else {
//                numMecanicsPerAeronau = 1;
//            }
        }
        has_mecanics = true;
    }

    public static void assignarMecanics() {

        logger.info(">> Quants Mecànics vols assignar per Aeronau? [Mín. 1 - Màx. 2]");
        numMecanics = utils.ValidadorOpcioMenu.numAeronausMissio(in);

        has_mecanics = true;

    }

    public static void assignarPilot() {

        has_pilots = true;

    }
}
