import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<Utazok> utazokList = new ArrayList<>();
        try(Scanner beolvasas = new Scanner(new File("utazok.csv"))){
            while (beolvasas.hasNextLine()){
                utazokList.add(new Utazok(beolvasas.nextLine()));
            }
        }catch (Exception e){
            System.out.println("Hiba: "+e.getMessage());
        }

        System.out.printf("0) %d utazó adata beolvasva\n", utazokList.size());
        //1. feladat
        List<String> varosok = new ArrayList<>();
        for (Utazok obj: utazokList){
            if (!varosok.contains(obj.getVaros())){
                varosok.add(obj.getVaros());
            }
        }
        int r1 = (int)(Math.random() * varosok.size());
        System.out.printf("1) Összesen %d városba utaztak\n", varosok.size());
        System.out.printf("\tKözülük egy véletlenszerűen kiválasztott: %s\n", varosok.get(r1));
        int varosokSzam = 0;
        for (Utazok obj: utazokList){
            if (obj.getVaros().equals(varosok.get(r1))){
                varosokSzam += 1;
            }
        }
        System.out.printf("\tEbbe a városba %d utazó érkezett\n", varosokSzam);


        //2. feladat
        int leghamarabbiOra = utazokList.getFirst().getOra();
        int leghamarabbiPerc = utazokList.getFirst().getPerc();
        for (Utazok obj:utazokList){
            if (leghamarabbiOra>obj.getOra()){
                if (leghamarabbiPerc>obj.getPerc()){
                    leghamarabbiOra = obj.getOra();
                    leghamarabbiPerc = obj.getPerc();
                }
            }
        }
        System.out.printf("2) Legkorábbi indulás: %d:%d\n", leghamarabbiOra, leghamarabbiPerc);
        int delelottDb = 0;
        for (Utazok obj: utazokList){
            if (obj.getOra()<12){
                delelottDb += 1;
            }
        }
        System.out.printf("\tÖsszesen %d utazás kezdődött délelőtt\n", delelottDb);

        //3. feladat
        System.out.printf("3) Utazások száma hónaponként:\n");

        TreeMap<Integer, Integer> utazasByHonapok = new TreeMap<>();
        for (Utazok obj:utazokList){
            int categ = obj.getHonap();
            if(!utazasByHonapok.containsKey(categ)){
                utazasByHonapok.put(categ, 1);
            }else{
                utazasByHonapok.put(categ, utazasByHonapok.get(categ)+1);
            }
        }
        for (Integer kat : utazasByHonapok.keySet()) {
            System.out.printf("\t0%d.hó : %d utazás\n", kat, utazasByHonapok.get(kat));
        }

        //4. feladat
        System.out.printf("4) Többször szereplő vezetéknevek:\n\t");

        TreeMap<String, Integer> nevekSzama = new TreeMap<>();
        for (Utazok obj: utazokList){
            String categ = obj.getNev().split(" ")[0];
            if(!nevekSzama.containsKey(categ)){
                nevekSzama.put(categ, 1);
            }else{
                nevekSzama.put(categ, nevekSzama.get(categ)+1);
            }
        }
        for (String kat : nevekSzama.keySet()) {
            if (nevekSzama.get(kat)>1){
                System.out.printf("%s ", kat);
            }
        }

        //5. feladat
        System.out.printf("\n5) Ugyanazon a napon kettőnél több utazás: ");
        TreeMap<String, Integer> utazasokEgynapon = new TreeMap<>();
        for (Utazok obj: utazokList){
            String categ = obj.getHonap()+"."+obj.getNap();
            if(!utazasokEgynapon.containsKey(categ)){
                utazasokEgynapon.put(categ, 1);
            }else{
                utazasokEgynapon.put(categ, utazasokEgynapon.get(categ)+1);
            }
        }
        for (String kat : utazasokEgynapon.keySet()) {
            if (utazasokEgynapon.get(kat)>2){
                System.out.printf("%s(%d) ", kat, utazasokEgynapon.get(kat));
            }
        }

        //6. feladat
        System.out.printf("\n6) Szegedre utazók kiírva a szeged.txt fájlba");

        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("szeged.txt"), "utf-8");
            for (Utazok obj: utazokList) {
                if (obj.getVaros().equals("Szeged")){
                    ki.printf("%s (%d.%d %d:%d)\n", obj.getNev(), obj.getHonap(), obj.getNap(), obj.getOra(), obj.getPerc());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            if (ki != null) ki.close();
        }
    }
}