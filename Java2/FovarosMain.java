import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<Fovaros> fovarosok = new ArrayList<>();
        try(Scanner beolvasas = new Scanner(new File("fovaros.csv"))){
            beolvasas.nextLine();
            while (beolvasas.hasNextLine()){
                fovarosok.add(new Fovaros(beolvasas.nextLine()));
            }
        }catch (Exception e){
            System.out.printf("Hiba: "+e.getMessage());
        }

        System.out.printf("0) Összesen %d ország adata lett beolvasva.\n", fovarosok.size());

        //1. feladat
        int legtobb = fovarosok.getFirst().getLakossag();
        for (Fovaros obj:fovarosok){
            if (legtobb<obj.getLakossag()){
                legtobb = obj.getLakossag();
            }
        }
        for (Fovaros obj:fovarosok){
            if (obj.getLakossag()==legtobb){
                System.out.printf("1) Az ország, ahol a legtöbben élnek: %s, %,d fő\n", obj.getOrszag(), obj.getLakossag());
            }
        }
        int masodiklegtobb = 0;
        for (Fovaros obj:fovarosok){
            if (masodiklegtobb<obj.getLakossag()&&obj.getLakossag()!=legtobb){
                masodiklegtobb = obj.getLakossag();
            }
        }
        for (Fovaros obj:fovarosok){
            if (obj.getLakossag()==masodiklegtobb){
                System.out.printf("\tAz ország, ahol a legtöbben élnek: %s, %,d fő\n", obj.getOrszag(), obj.getLakossag());
            }
        }
        //2. feladat
        int budapestLakossag = 0;
        for (Fovaros obj:fovarosok){
            if (obj.getFovaros().equals("Budapest")){
                budapestLakossag = obj.getFovarosLakossag();
            }
        }
        int tobb = 0;
        for (Fovaros obj:fovarosok){
            if (obj.getFovarosLakossag()>budapestLakossag){
                tobb+= 1;
            }
        }
        System.out.printf("2) Összesen %d fővárosban élnek kevesebben, mint Budapesten!\n", tobb);

        //3. feladat
        List<String> cbetus = new ArrayList<>();
        for (Fovaros obj:fovarosok){
            if (obj.getRovidites().contains("C")){
                cbetus.add(obj.getRovidites());
            }
        }

        System.out.printf("3) Országjel, amiben szerepel 'C' betű: %s\n", String.join(", ", cbetus.stream().sorted().toList()));

        //4. feladat
        int huszmillio = 20000000;
        int osszFovaros = 0;
        for (Fovaros obj:fovarosok){
            if (obj.getLakossag()<huszmillio){
                osszFovaros += obj.getFovarosLakossag();
            }
        }

        System.out.printf("4) A 20 millió főnél kisebb országok fővárosainak össznépessége: %,d fő\n", osszFovaros);

        //5. feladat
        System.out.printf("5) Fővárosok népesség szerint csoportosítva (5 millió fő):\n");

        TreeMap<Integer, Integer> fovarosNepesseg = new TreeMap<>();
        for (Fovaros obj: fovarosok){
            int categ = obj.getFovarosLakossag() / 5000000;
            if(!fovarosNepesseg.containsKey(categ)){
                fovarosNepesseg.put(categ, 1);
            }else{
                fovarosNepesseg.put(categ, fovarosNepesseg.get(categ)+1);
            }
        }
        for (Integer kat : fovarosNepesseg.keySet()) {
            System.out.printf("   %,10d - %,10d: %d\n", kat*5000000, (kat+1)*5000000-1, fovarosNepesseg.get(kat));
        }

        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("nagyok.txt"), "utf-8");
            for (Fovaros obj:fovarosok){
                if (obj.getLakossag()>200000000){
                    ki.printf("%s (%d millió)\n", obj.getOrszag(), obj.getLakossag()/1000000);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            if (ki != null) ki.close();
        }

    }
}