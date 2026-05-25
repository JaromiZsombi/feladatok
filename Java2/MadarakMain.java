import java.io.File;
import java.io.PrintWriter;
import java.util.*;

//1. feladat
public class Main {
    public static void main(String[] args) {
        List<Madarak> madarak = new ArrayList<>();
        try(Scanner beolvasas = new Scanner(new File("madarak.csv"))){
            beolvasas.nextLine();
            while (beolvasas.hasNextLine()){
                madarak.add(new Madarak(beolvasas.nextLine()));
            }
        }catch (Exception e){
            System.out.println("Hiba: "+e.getMessage());
        }
        System.out.printf("1) A madarak.csv fájlból %d madár adata beolvasva\n", madarak.size());

        //2. feladat
        int legmesszebb = madarak.getLast().atlagReptav;
        for (Madarak obj:madarak){
            if (legmesszebb<obj.getAtlagReptav()){
                legmesszebb = obj.getAtlagReptav();
            }
        }
        for (Madarak obj:madarak){
            if (legmesszebb==obj.getAtlagReptav()){
                System.out.printf("2) Legmesszebb (%d km) repül: %s\n", obj.getAtlagReptav(), obj.getMagyarNev());
                break;
            }
        }

        //3. feladat
        int konnyuMadarakDb = 0;
        double konnyuMadarakAtlagReptav = 0;
        for (Madarak obj: madarak){
            if (obj.getAtlagSuly()<100){
                konnyuMadarakDb += 1;
                konnyuMadarakAtlagReptav += obj.getAtlagReptav();
            }
        }
        System.out.printf("3) A 100g alatti madarak (%d darab) átlagos repülési távolsága: %.2f km\n", konnyuMadarakDb, konnyuMadarakAtlagReptav/konnyuMadarakDb);

        //4. feladat
        List<Madarak> egyszavasMadarak = new ArrayList<>();
        for (Madarak obj: madarak){
            if (obj.getMagyarNev().split(" ").length==1){
                egyszavasMadarak.add(obj);
            }
        }
        int randomNum = (int)(Math.random()*egyszavasMadarak.size());
        System.out.printf("4) Véletlen választott egyetlen szóból álló magyar nevű madár: %s\n", egyszavasMadarak.get(randomNum).getMagyarNev());

        //5. feladat
        System.out.printf("5) A latin név két egyforma szóból áll:\n");
        for (Madarak obj: madarak){
            if (obj.getLatinNev().split(" ")[0].equalsIgnoreCase(obj.getLatinNev().split(" ")[1])){
                System.out.printf("\t- %s (%s)\n", obj.getMagyarNev(), obj.getLatinNev());
            }
        }

        //6. feladat
        System.out.printf("6) Magasságok: ");
        TreeMap<Integer, Integer> madarakMagassaga = new TreeMap<>();
        for (Madarak obj: madarak){
            int categ = obj.getAtlagMagassag();
            if(!madarakMagassaga.containsKey(categ)){
                madarakMagassaga.put(categ, 1);
            }else{
                madarakMagassaga.put(categ, madarakMagassaga.get(categ)+1);
            }
        }
        List<String> madarMagas = new ArrayList<>();
        madarakMagassaga.forEach((key, value)->{
            if (value>1){
                madarMagas.add(key+"cm"+" "+ "("+value+")");
            }
        });
        System.out.printf("%s\n", String.join(", ", madarMagas));

        //7. feladat
        int fecskedb = 0;
        for (Madarak obj: madarak){
            if (obj.getMagyarNev().contains("fecske")){
                fecskedb+= 1;
            }
        }
        System.out.printf("7) Összesen %d féle fecske található az adatok között\n", fecskedb);

        //8. feladat
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("nagyok.txt"), "utf-8");
            for (Madarak obj: madarak){
                if (obj.getAtlagSuly()>500){
                    ki.printf("%s (%s): %d g\n", obj.getMagyarNev(), obj.getLatinNev(), obj.getAtlagSuly());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            if (ki != null) ki.close();
            System.out.println("8) A nagy madarak adatai a nagyok.txt fájlba elmentve");
        }

    }
}