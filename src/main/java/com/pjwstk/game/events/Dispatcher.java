package com.pjwstk.game.events;

import org.apache.commons.lang3.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {

    public static Dispatcher instance = new Dispatcher();


    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Map<Class<?>, List<?>> map = new HashMap<>();

    private Dispatcher() {
    }


    public void registerObject(Object o) {
        List<Class<?>> interfacesImplementedByObject = ClassUtils.getAllInterfaces(o.getClass());

        for (Class<?> classtype : interfacesImplementedByObject) {
            System.out.println("Rejestruje obiekt: " + o + " - implementuje interfejs " + classtype.getName());
            List objects = map.get(classtype);
            if (objects == null) {
                objects = new ArrayList<>();
            }
            objects.add(o);
            map.put(classtype, objects);
        }
    }

    public <T> List<T> getAllObjectsImplementingInterface(Class<T> clas) {
        List<T> lista = (List<T>) map.get(clas);
        System.out.println("Szukam obiektów implementującyc interface : " + clas.getName() + " znalezione obiekty: ");
        for (T t : lista) {
            System.out.println(" ----> " + t);
        }
        System.out.println();

        return (List<T>) map.get(clas);
    }

    public void unregisterObject(Object o) {
        List<Class<?>> interfacesImplementedByObject = ClassUtils.getAllInterfaces(o.getClass());
        for (Class<?> classtype : interfacesImplementedByObject) {
            List objects = map.get(classtype);
            if (objects != null) {
                objects.remove(o);
            }

        }
    }

    public void dispatch(final IEvent e) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    e.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}