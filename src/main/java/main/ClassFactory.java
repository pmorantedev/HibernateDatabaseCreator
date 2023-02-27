/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.github.javafaker.Faker;
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
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author victor
 */
public class ClassFactory implements TesteableFactory {

    private static final Faker faker = new Faker();
    private static final Random r = new Random();

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
        try {
            a.setPilotAeronau(p);
            return a;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    @Override
    public Aeronau aeronauFactory(Class<?> tipus) {
        Aeronau novaAeronau;

        //s'ha d'implementar javaFacker per fer caa clase diferent
        if (tipus.getClass().equals(Combat.class)) {
//            novaAeronau = new Combat(true, 0, true, 0, "test1", 0, null, Boolean.FALSE, new Pilot(
//                    "nikname1", 0.1f, null, true, Float.MAX_VALUE));

            novaAeronau = new Combat(faker.bool().bool(), faker.number().numberBetween(0, 10000), faker.bool().bool(),
                    r.nextFloat(500), faker.company().name(), r.nextFloat(100),
                    new Date(faker.date().future(r.nextInt(), TimeUnit.DAYS).getTime()),
                    faker.bool().bool(), (Pilot) soldatFactory(Pilot.class)
            );
        } else if (tipus.getClass().equals(Transport.class)) {
            novaAeronau = new Transport(r.nextInt(1000), faker.bool().bool(), r.nextFloat(500), faker.company().name(), r.nextFloat(100),
                    new Date(faker.date().future(r.nextInt(10), TimeUnit.DAYS).getTime()),
                    faker.bool().bool(), (Pilot) soldatFactory(Pilot.class));
        } else if (tipus.getClass().equals(Transport.class)) {
            novaAeronau = new Dron(r.nextFloat(5000), faker.bool().bool(), faker.number().numberBetween(0, 5000), faker.company().name(),
                    r.nextFloat(100), new Date(faker.date().future(r.nextInt(10), TimeUnit.DAYS).getTime()),
                    faker.bool().bool());
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
        return new Missio(r.nextInt(999999), faker.food().spice(), r.nextFloat(10000),
                new Date(faker.date().future(r.nextInt(10), TimeUnit.DAYS).getTime()),
                faker.bool().bool());
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

        Soldat nouSoldat;
        Pilotada pilotada;
        if (r.nextInt(1) == 0) {
            pilotada = (Pilotada) aeronauFactory(Combat.class);
        } else {
            pilotada = (Pilotada) aeronauFactory(Transport.class);
        }

        //s'ha d'implementar javaFacker per fer caa clase diferent
        if (tipus.getClass().equals(Mecanic.class)) {
            nouSoldat = new Mecanic(faker.company().profession(),
                    r.nextFloat(100), pilotada,
                    faker.funnyName().name(), r.nextFloat(10),
                    new Date(faker.date().past(r.nextInt(10), TimeUnit.DAYS).getTime()),
                    faker.bool().bool());

        } else if (tipus.getClass().equals(Pilot.class)) {
            //un pilot no implementa una pilotada?
            nouSoldat = new Pilot(faker.funnyName().name(), r.nextFloat(10),
                    new Date(faker.date().past(r.nextInt(10), TimeUnit.DAYS).getTime()),
                    faker.bool().bool(), r.nextFloat(7));
        } else {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        return nouSoldat;
    }

}
