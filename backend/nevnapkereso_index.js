import express from "express";
import cors from "cors";
import mysql from "mysql2/promise";

let con = await mysql.createConnection({
    host: "localhost",
    port: 3306,
    database: "nevnapkereso",
    user: "root",
    password: ""
 });

const app = express();
app.use(express.json());
app.use(cors());

async function getNevnapokByDay(req, res) {
    let sql = "select * from name_days WHERE day = ? AND month = ?";
    let {day, month} = req.params;
    if(day, month){
        try {
        const [ json ] = await con.query(sql,[day, month]);
        if(json.length>0){
            res.send(json);
        }else{
            res.status(404).send({error:"Nincs ilyen születési dátumú ember"})
        }
                
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
    }else{
        res.status(404).send({error:"Nincs elég paraméter"})
    }
}

async function getDateByName(req, res) {
    let sql = "select * from name_days WHERE name LIKE ?";
    let {name} = req.params;
    let [value] = ["%"+name+"%"]
       try {
        const [ json ] = await con.query(sql, value);
        if(json.length>0){
            res.send(json);
        }else{
            res.status(404).send({error:"Nincs ilyen nevű ember"})
        }
                
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function getOriginByName(req, res) {
    let sql = "select * from name_info WHERE name = ?";
    let {name} = req.params;
       try {
        const [ json ] = await con.query(sql, name);
        if(json.length>0){
            res.send(json);
        }else{
            res.status(404).send({error:"Nincs találat!"})
        }
                
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function postOrigin(req, res) {
    let sql = "select * from name_info WHERE name = ?";
    let sql2 = "insert into name_info (name, gender, descr) VALUES (?,?,?)"
    let {name, gender, descr} = req.body;
       try {
        console.log(name)
        const [ json ] = await con.query(sql, name);
        if(json.length>0){
            res.status(409).send({error:"Már van ilyen név a táblázatban!"})
        }else{
            const [json2] = await con.query(sql2, [name, gender, descr])
            if(name&&gender&&descr){
                res.send(json2)
            }else{
                res.status(404).send({error:"Nincs elég paraméter"})
            }
        }
                
    } catch(err) { res.status(500).send({ error: err })}
}

async function deleteByName(req, res) {
    let sql = "DELETE FROM name_info where name = ?";
    let {name} = req.params;
       try {
        const [ json ] = await con.query(sql, [name]);
        res.status(200).send({msg:"Sikeres törlés!"});
                
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}







app.get("/", (req, res) => res.send("<h1>Névnapkereső v1.0.0</h1>"));
app.get("/api/names/:day/:month", getNevnapokByDay);
app.get("/api/dates/:name", getDateByName);
app.get("/api/info/:name", getOriginByName);
app.post("/api/info", postOrigin);
app.delete("/api/info/:name", deleteByName);
app.listen(8000, err => console.log(err ? err : "Server on :88"));

