import com.sun.source.tree.Tree;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Diafilm> diafilmek = new ArrayList<>();
        try(Scanner beolvasas = new Scanner(new File("diafilm.csv"))){
            beolvasas.nextLine();
            while (beolvasas.hasNextLine()){
                diafilmek.add(new Diafilm(beolvasas.nextLine()));
            }
        }catch (Exception e){
            System.out.println("Hiba: "+e.getMessage());
        }

        System.out.printf("0) %d diafilm adata beolvasva\n", diafilmek.size());
        System.out.printf("Közülük %d még fekete-fehér\n", diafilmek.stream()
                .filter(obj->obj.getSzines().equals("N")).toList().size());

        //1. feladat
        int legidosebb = diafilmek.getLast().getEv();
        String legidosebbCim = "";
        for (Diafilm obj: diafilmek) {
            if (obj.getEv()<legidosebb){
                legidosebb = obj.getEv();
            }
        }
        for (Diafilm obj: diafilmek){
            if (obj.getEv()==legidosebb){
                System.out.printf("1) A legrégebbi diafilm: %s (%d)\n", obj.getCim(), obj.getEv());
                legidosebbCim = obj.getCim();
                break;
            }
        }
        System.out.println("De ugyanebben az évben készült még:");
        for (Diafilm obj:diafilmek){
            if (obj.getEv()==legidosebb&&!obj.getCim().equals(legidosebbCim)){
                System.out.printf("\t- %s (%d)\n", obj.getCim(), obj.getEv());
            }
        }

        //2. feladat
        double elott = 0;
        int elottDb = 0;
        double utan = 0;
        int utanDb = 0;
        for (Diafilm obj:diafilmek){
            if (obj.getEv()<2000){
                elott += obj.getKocka();
                elottDb += 1;
            }else if(obj.getEv()>=2000){
                utan += obj.getKocka();
                utanDb += 1;
            }
        }
        System.out.printf("2) A 2000 előtt készült diafilmek átlagos kockaszáma: %.1f\n", elott/elottDb);
        System.out.printf("\tA később készült diafilmeknél az áltag: %.1f\n", utan/utanDb);

        //3. feladat
        System.out.printf("3) Az egyes évtizedekben készült diafilmek száma:\n");

        TreeMap<Integer, Integer> evtizedek = new TreeMap<>();
        for (Diafilm obj: diafilmek){
            int categ = obj.getEv() / 10;
            if(!evtizedek.containsKey(categ)){
                evtizedek.put(categ, 1);
            }else{
                evtizedek.put(categ, evtizedek.get(categ)+1);
            }
        }
        for (Integer kat : evtizedek.keySet()) {
            System.out.printf("   %,1d - %,1d: %d darab\n", kat*10, (kat+1)*10-1, evtizedek.get(kat));
        }

        //4. feladat
        TreeMap<Integer, Integer> evesKockaszam = new TreeMap<>();
        for (Diafilm obj:diafilmek){
            int categ = obj.getEv();
            if(!evesKockaszam.containsKey(categ)){
                evesKockaszam.put(categ, obj.getKocka());
            }else{
                evesKockaszam.put(categ, evesKockaszam.get(categ)+obj.getKocka());
            }
        }
        int legtobbEv = diafilmek.getFirst().getEv();
        for (Integer ev : evesKockaszam.keySet()){
            if (evesKockaszam.get(ev) > evesKockaszam.get(legtobbEv)) legtobbEv = ev;
        }
        System.out.printf("4) A legtöbb kocka (%d db) készítésének éve: %d\n", evesKockaszam.get(legtobbEv), legtobbEv);
        int masodiklegtobbEv = diafilmek.getFirst().getEv();
        for (Integer ev : evesKockaszam.keySet()){
            if(evesKockaszam.get(ev)>evesKockaszam.get(masodiklegtobbEv) && evesKockaszam.get(ev) != evesKockaszam.get(legtobbEv)){
                masodiklegtobbEv = ev;
            }
        }
        System.out.printf("A második legtöbb kocka (%d db) éve: %d", evesKockaszam.get(masodiklegtobbEv), masodiklegtobbEv);

        //5. feladat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("200x.txt"), "utf-8");
            for (Diafilm obj:diafilmek){
                if (obj.getEv()>1999){
                    ki.printf("%s;%d;%d;%s\n", obj.getCim(), obj.getEv(), obj.getKocka(), obj.getSzines());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            if (ki != null) ki.close();
        }

    }
}