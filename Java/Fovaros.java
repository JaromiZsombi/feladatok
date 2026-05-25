public class Fovaros {
    String orszag;
    String rovidites;
    int lakossag;
    String fovaros;
    int fovarosLakossag;

            public Fovaros(String line){
                String[] lineArr = line.split(";");
                orszag = lineArr[0];
                rovidites = lineArr[1];
                lakossag = Integer.parseInt(lineArr[2]);
                fovaros = lineArr[3];
                fovarosLakossag = Integer.parseInt(lineArr[4]);
            }

    public String getOrszag() {
        return orszag;
    }

    public String getRovidites() {
        return rovidites;
    }

    public int getLakossag() {
        return lakossag;
    }

    public String getFovaros() {
        return fovaros;
    }

    public int getFovarosLakossag() {
        return fovarosLakossag;
    }
}
