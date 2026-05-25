import express from "express";
import cors from "cors";
import mysql from "mysql2/promise";

let con = await mysql.createConnection({
    host: "localhost",
    port: 3306,
    database: "konyvajanlo",
    user: "root",
    password: ""
});

const app = express();
app.use(express.json());
app.use(cors());

async function getBooks(req, res) {
    let sql = "select books.id, title, author, description, cover, rating, name from books, categories WHERE books.category_id = categories.id order by title";
    try {
        const [json] = await con.query(sql);
        res.send(json);
    } catch (err) { res.status(500).send({ error: "Adatbázis hiba!" }) }
}


async function getBooksByCategId(req, res) {
    let sql = "select * from books where category_id = ? order by title";
    let { categId } = req.params;
    try {
        const [json] = await con.query(sql, [categId]);
        res.send(json);
    } catch (err) { res.status(500).send({ error: "Adatbázis hiba!" }) }
}

async function getBooksBySearch(req, res) {
    let sql = "select * from books where title LIKE ? or description LIKE ?";
    let { searchedWord } = req.params;
    const value = `%${searchedWord}%`
    try {
        const [json] = await con.query(sql, [value, value]);
        if (json.length > 0) {
            res.send(json);
        } else {
            res.status(404).send({ error: "A keresés nem eredményez találatot!" })
        }

    } catch (err) { res.status(500).send({ err }) }
}

async function getCategories(req, res) {
    let sql = "select * from categories";
    try {
        const [json] = await con.query(sql);
        res.send(json);
    } catch (err) { res.status(500).send({ error: "Adatbázis hiba!" }) }
}

async function postCategories(req, res) {
    let sql = "insert into categories (name) VALUES (?)";
    let { name } = req.body
    try {
        const [json] = await con.query(sql, name);
        res.send(json);
    } catch (err) { res.status(500).send({ error: "Adatbázis hiba!" }) }
}

async function updateRating(req, res) {
    let sql = "update books set rating = ? WHERE id = ?";
    let { rating } = req.body
    let { id } = req.params
    if (rating > 5 || rating < 0) {
        { res.status(400).send({ error: "Bekért érték hiba" }) }
    } else {
        try {
            const [json] = await con.query(sql, [rating, id]);
            res.send(json);
        } catch (err) { res.status(500).send({ error: "Adatbázis hiba!" }) }
    }

}




app.get("/", (req, res) => res.send("<h1>Virágbolt v1.0.0</h1>"));
app.get("/api/books", getBooks);
app.get("/api/books/:categId", getBooksByCategId);
app.get("/api/book/:searchedWord", getBooksBySearch)
app.get("/api/categories", getCategories)
app.post("/api/categories", postCategories)
app.put("/api/book/:id", updateRating)
app.listen(8000, err => console.log(err ? err : "Server on :8000"));