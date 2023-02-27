/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Soldat;
import entitats.Transport;
import interficies.TesteableFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class ClassFactory implements TesteableFactory {

    public ClassFactory() {
    }

    @Override
    public Aeronau addMecanicsToPilotada(List<Soldat> lo, Pilotada p) throws Exception {
        try {
            List<Mecanic> lm = new ArrayList<>(lo.size());

            for (Soldat soldat : lo) {
                lm.add((Mecanic) soldat);
            }
            p.setMecanics(lm);
            return p;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    @Override
    public Aeronau addMissionsToAeronau(List<Missio> lm, Aeronau a) throws Exception {

        try {
            a.setMissions(lm);
            return a;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    @Override
    public Missio addAeronausToMissio(List<Aeronau> la, Missio m) throws Exception {
        try {
            m.setAeronaus(la);
            return m;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBod
        }

    }

    @Override
    public Aeronau addPilotToAeronauPilotada(Pilot p, Pilotada a) throws Exception {

        a.setPilotAeronau(p);
        return a;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Aeronau aeronauFactory(Class<?> tipus) {

        System.out.println(">>>Clase que arriba: " + tipus.getClass());

        Aeronau novaAeronau;

        //s'ha d'implementar javaFacker per fer caa clase diferent
        if (tipus.getClass().equals(Combat.class)) {
            novaAeronau = new Combat(true, 0, true, 0, "test1", 0, null, Boolean.FALSE, new Pilot(
                    "nikname1", 0.1f, null, true, Float.MAX_VALUE));
        } else if (tipus.getClass().equals(Transport.class)) {
            novaAeronau = new Transport(0, true, 0, "test1", 0, null, Boolean.FALSE, new Pilot(
                    "nikname1", 0.1f, null, true, Float.MAX_VALUE));
        } else if (tipus.getClass().equals(Transport.class)) {
            novaAeronau = new Dron(0, true, 0, "c1", 0, null, Boolean.FALSE);
        } else {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        }

        return novaAeronau;

    }

    @Override
    public List<Soldat> mecanicsFactory(int elements) {

        try {
            List<Soldat> llistaMecanics = new ArrayList<>(elements);
            for (int i = 0; i < llistaMecanics.size() - 1; i++) {
                llistaMecanics.add(soldatFactory(Mecanic.class));
            }
            return llistaMecanics;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        }

    }

    @Override
    public Missio missioFactory() {
        return new Missio(0, "m1", 0, null, true);
//      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Missio> missionsFactory(int elements) {
        try {
            List<Missio> llistaMissions = new ArrayList<Missio>(elements);

            for (int i = 0; i < llistaMissions.size() - 1; i++) {
                llistaMissions.add(missioFactory());
            }
            return llistaMissions;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        }

    }

    @Override
    public List<Soldat> pilotsFactory(int elements) {

        try {
            List<Soldat> llistaSoldats = new ArrayList<>(elements);
            for (int i = 0; i < llistaSoldats.size() - 1; i++) {
                llistaSoldats.add(soldatFactory(Pilot.class));
            }
            return llistaSoldats;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

        }

    }

    @Override
    public Soldat soldatFactory(Class<?> tipus) {
        System.out.println(">>>Clase que arriba: " + tipus.getClass());

        Soldat nouSoldat;

        //s'ha d'implementar javaFacker per fer caa clase diferent
        if (tipus.getClass().equals(Mecanic.class)) {
            nouSoldat = new Mecanic("e1", Float.NaN,
                    //no pot ser pilotada ja que es abstracta, no diu si ha de ser combat o transport
                    new Combat(true, 0, true, 0, "test1", 0, null, Boolean.FALSE, new Pilot(
                            "nikname1", 0.1f, null, true, Float.MAX_VALUE)),
                    "n1", 0, null, true);

        } else if (tipus.getClass().equals(Pilot.class)) {
            //un pilot no implementa una pilotada?
            nouSoldat = new Pilot("p1", 1.0f, null, true, Float.MAX_VALUE);
        } else {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        return nouSoldat;
    }

}
