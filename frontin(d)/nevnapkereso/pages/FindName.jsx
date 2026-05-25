import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { FaHome } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export const FindByName = () => {

  const navigate = useNavigate();
  const [nev, setNev] = useState("")
  const [datumok, setDatumok] = useState([]);
  const [infok, setInfok] = useState([]);
  const Honapok = ["Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"]


  async function getDatumByNevek() {
    if (nev.length>0) {
      let resp = await fetch(`http://localhost:8000/api/dates/${nev}`)
      let json = await resp.json()
      setDatumok(json)
      console.log(json)
      resp = await fetch(`http://localhost:8000/api/info/${nev}`)
      json = await resp.json()
      setInfok(json)
      console.log(json)
    } else {
      console.log(nev)
      console.log("Nincs ilyen név!")
    }

  }


  return (
    <div className='homeBackground'>
      FindDate
      <FaHome onClick={()=>navigate("/")}/>
      <input type="text" value={nev} onChange={(e) => setNev(e.target.value)} />
      <button onClick={() => getDatumByNevek()}>Keresés</button>
      {Array.isArray(datumok)? datumok.map(x => <div key={x.id}>{Honapok[x.month-1]}-{x.day}</div>):<div>Nincs ilyen név!</div>}
      {Array.isArray(infok)? infok.map(x => <div key={x.id}>{x.descr}</div>):<div>Nincs ilyen név!</div>}
    </div>
  )
}