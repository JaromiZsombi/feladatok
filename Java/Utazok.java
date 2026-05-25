public class Utazok {
    private String nev;
    private String varos;
    private int honap;
    private int nap;
    private int ora;
    private int perc;

    public Utazok(String line){
        String[] lineArr = line.split(";");
        nev = lineArr[0];
        varos = lineArr[1];
        honap = Integer.parseInt(lineArr[2].split("\\.")[0]);
        nap = Integer.parseInt(lineArr[2].split("\\.")[1]);
        ora = Integer.parseInt(lineArr[3].split(":")[0]);
        perc = Integer.parseInt(lineArr[3].split(":")[1]);
    }

    public String getNev() {
        return nev;
    }

    public String getVaros() {
        return varos;
    }

    public int getHonap() {
        return honap;
    }

    public int getNap() {
        return nap;
    }

    public int getOra() {
        return ora;
    }

    public int getPerc() {
        return perc;
    }
}
