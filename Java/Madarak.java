public class Madarak {
    String magyarNev;
    String latinNev;
    int atlagSuly;
    int atlagMagassag;
    int atlagReptav;

    public Madarak(String line) {
        String[] lineArr = line.split(";");
        magyarNev = lineArr[0];
        latinNev = lineArr[1];
        atlagSuly = Integer.parseInt(lineArr[2]);
        atlagMagassag = Integer.parseInt(lineArr[3]);
        atlagReptav = Integer.parseInt(lineArr[4]);
    }


    public String getMagyarNev() {
        return magyarNev;
    }

    public String getLatinNev() {
        return latinNev;
    }

    public int getAtlagSuly() {
        return atlagSuly;
    }

    public int getAtlagMagassag() {
        return atlagMagassag;
    }

    public int getAtlagReptav() {
        return atlagReptav;
    }
}
