import express from "express";
import cors from "cors";
import mysql from "mysql2/promise";

let con = await mysql.createConnection({
    host: "localhost",
    port: 3306,
    database: "restaurant",
    user: "root",
    password: ""
 });

 const app = express();
 app.use(express.json());
 app.use(cors());

 async function getCategories(req, res) {
    let sql = "select * from categories order by name";
    try {
        const [ json ] = await con.query(sql);
        res.send(json);
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function getFoodsByCateg(req, res) {
    let {categId} = req.params;
    let sql = "select * from foods where categId = ?";
    try {
        const [ json ] = await con.query(sql, categId);
        res.send(json);
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function getFoodsBySearch(req, res) {
    let {searchedWord} = req.params;
    let sql = `select * from foods where title LIKE ?`;
    const values = [`%${searchedWord}%`]
    
    try {
        const [ json ] = await con.query(sql, values);
        if(json.length==0){
            res.status(404).send({msg:"The search returned no results"})
        }else{
            res.send(json);
        }
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function updatePrice(req, res) {
    let {id} = req.params;
    const {price} = req.body
    let sql = `update foods set price = ? WHERE id = ?`;
    try {
        const [ json ] = await con.query(sql, [price, id]);
        res.send({msg:"Sikeres beillesztés!"});
    } catch(err) { res.status(500).send({ error: "Adatbázis hiba!" })}
}

async function postCategory(req, res){
    const {name, photo, descr} = req.body
    const sql = "insert into categories (name, photo, descr) VALUES (?,?,?)"
    try{
        const [json] = await con.query(sql,[name, photo, descr]);
        res.send({msg:"Item successfully added!"})
    }catch(err){res.status(500).send({error:"Adatbázis hiba!"})}
}




 app.get("/", (req, res) => res.send("<h1>Étterem v1.0.0</h1>"));
 app.get("/api/categories", getCategories);
 app.get("/api/foodsbycateg/:categId", getFoodsByCateg)
 app.get("/api/foodsbysearch/:searchedWord", getFoodsBySearch)
 app.put("/api/food/:id", updatePrice)
 app.post("/api/categories", postCategory)
app.listen(8000, err => console.log(err ? err : "Server on :8000"));