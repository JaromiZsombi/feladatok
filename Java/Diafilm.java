public class Diafilm {
    private String cim;
    private int ev;
    private int kocka;
    private String szines;

    public Diafilm(String line){
        String[] lineArr = line.split(";");
        cim = lineArr[0];
        ev = Integer.parseInt(lineArr[1]);
        kocka = Integer.parseInt(lineArr[2]);
        szines = lineArr[3];
    }

    public String getCim() {
        return cim;
    }

    public int getEv() {
        return ev;
    }

    public int getKocka() {
        return kocka;
    }

    public String getSzines() {
        return szines;
    }
}


