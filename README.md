# Eddigi-feladatok
=============================

- [Java](#java)
- [JavaFx](#javafx)
- [Backend](#backend)
- [Frontend](#frontend)

# java

Egy másik valami.java fileba a classt hozd létre
```
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
}
```

A lista nevét, attribútumát át kell írni, illetve a soutot is, meg ha az első sorban nem adat van, csak akkor kell a beolvasas.nextLine

```
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
```

Treemap:

A categ az az ami alapján csinálja a kategóriákat, itt pl a magasság szerint
```
TreeMap<Integer, Integer> madarakMagassaga = new TreeMap<>();
        for (Madarak obj: madarak){
            int categ = obj.getAtlagMagassag();
            if(!madarakMagassaga.containsKey(categ)){
                madarakMagassaga.put(categ, 1);
            }else{
                madarakMagassaga.put(categ, madarakMagassaga.get(categ)+1);
            }
        }
```

Ha valami alapján kikéne íratni a treemapben a dolgokat: 
```
List<String> madarMagas = new ArrayList<>();
        madarakMagassaga.forEach((key, value)->{
            if (value>1){
                madarMagas.add(key+"cm"+" "+ "("+value+")");
            }
        });
        System.out.printf("%s\n", String.join(", ", madarMagas));
```
a .add-nál pluszal add hozzá a cuccokat, a String.join meg megoldja a vesszőzést (valszeg kell majd)
Külön is iderakom azért a String.join-t
```
System.out.printf("%s\n", String.join(", ", madarMagas));
```

# javafx

Menü, mindenhova kelleni fog, bármi is a feladat

Random Hello Controller biztonság kedvéért feladatból Hello Controllerbe:
```
private class Diafilm {
        public String cim;
        public int ev;
        public int kocka;
        public String szines;


        public Diafilm(String sor) {
            String[] s = sor.split(";");
            cim = s[0];
            ev = Integer.parseInt(s[1]);
            kocka = Integer.parseInt(s[2]);
            szines = s[3];


        }
```

toString ha valamelyik adatot átkellene írni valahogy:
```
@Override
        public String toString() {
            return String.format("%s (%d, %d kocka, %s)", cim, ev, kocka, szines.equals("I")?"színes":"fekete-fehér");
        }
```
Distinct:
```
randomLV.setItems(fxCollection.observableList(valamiLista.stream().map(valami->valami.getName().split(";")[0].Distinct().toList())))
```

Beolvasás:
```
private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) madarak.add(new Madarak(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

private ObservableList<Diafilm> diafilmek = FXCollections.observableArrayList();

private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        diafilmekLV.setItems(diafilmek);


    }
```

HelloControllerbe a menühöz:
```
public void onOpenClick() {
        File fajl = fc.showOpenDialog(diafilmekLV.getScene().getWindow());
        if(fajl != null) betolt(fajl);
        darab.setText(diafilmek.size() + " darab");
        ObservableList<Integer> evek = FXCollections.observableArrayList(diafilmek.stream().map(obj->obj.ev).distinct().sorted().toList());
        cbox.setItems(evek);
        cbox.getSelectionModel().select(0);
    }

    public void onCloseClick() {
        Platform.exit();
    }

    public void onAboutClick() {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Diafilmek v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }
```

# backend
```
pnpm init
```
<img width="507" height="279" alt="image" src="https://github.com/user-attachments/assets/69ab7166-e10e-4b7b-832d-663a6f2613ed" />

```
pnpm i express cors mysql2 
```
<img width="539" height="337" alt="image" src="https://github.com/user-attachments/assets/d25a652a-03bf-4ffc-ba25-28b9fa52eabb" />

```
"type":"module",
"dev": "node --watch index.js"
```
<img width="509" height="436" alt="image" src="https://github.com/user-attachments/assets/dcdb1fed-ba15-434f-8c08-b4654861ed57" />


# index.js eleje
```
import express from "express";
import cors from "cors";
import mysql from "mysql2/promise";

let con = await mysql.createConnection({
    host: "localhost",
    port: 3306,
    database: "viragbolt",
    user: "root",
    password: ""
 });

const app = express();
app.use(express.json());
app.use(cors());
```
# get funkció

```
async function getCategories(req, res) {
    let sql = "select * from kategoriak";
    try {
        const [ json ] = await con.query(sql);
        res.send(json);        
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}
```


 # index.js alja
 ```app.get("/", (req, res) => res.send("<h1>Virágbolt v1.0.0</h1>"));```
 
 többire példa:
 ```
 app.get("/api/categories", getCategories);
 app.put("/api/categories", putCategories);
 app.delete("/api/categories", deleteCategories);
 app.listen(8000, err => console.log(err ? err : "Server on :88"));
 ```
# frontend
cmd-be pnpm i, max egy pnpm i react-router-dom, ha az nincs

Main.jsx
```
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { Home } from './Home'
import { Books } from './Books'

function App() {

  return (
    <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/books/category/:categId" element={<Books />} />
              <Route path="/book/search/:searchedWord" element={<Books />} />
            </Routes>
        </BrowserRouter>
  )
}

export default App
```

useEffect backend beolvasáshoz:
```
useEffect(()=>{
        async function getKategoriak(){
            let resp = await fetch(`http://localhost:8000/api/categories`)
            let json = await resp.json()
            console.log(json)
            setKategoria(json)
        }
        getKategoriak()
    },[])
```

Home.jsx kialakítás csak ha kéne:
```
import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react';
import { FaFacebook,FaTwitter, FaInstagram,FaHome, FaLinkedin } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import { Header } from './components/Header';
import { Footer } from './components/Footer';

export const Home = () => {

  const [kategoria, setKategoria] = useState([]);
  const [word, setWord] = useState("");

  const navigate = useNavigate();

  useEffect(()=>{
        async function getKategoriak(){
            let resp = await fetch(`http://localhost:8000/api/categories`)
            let json = await resp.json()
            console.log(json)
            setKategoria(json)
        }
        getKategoriak()
    },[])

  return (
    <div className='főDiv'>
      <Header/>
      <div className='kepDiv'>
        <img src="header.png" className='kep' alt="" />
      </div>
      <div className='kozepDiv'>
        <div className='keresoDiv divdiv'><input type="text" /> <button></button></div>
        <div className='valamitextDiv divdiv'><p>Döntsd el, mit is olvass ezután?</p>
        <br />
        <p>Jó helyen jársz. Milyen műfajokat kedvelsz?</p></div>
        <div className='idezetDiv divdiv'><p className='szoveg'>Minden egyes könyv egy új világ, amit csak ki kell nyitnod.</p></div>
      </div>
      <div className='cardDiv'>
        {kategoria.map(x=><div key = {x.id} onClick={()=>navigate(`/books/category/${x.id}`)} className='valami'>{x.name}</div>)}
      </div>
      
      
      <Footer/>
    </div>
  )
}

```
