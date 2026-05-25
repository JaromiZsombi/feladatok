import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { FaHome } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

export const FindByDate = () => {

    const navigate = useNavigate();
    const [datum, setDatum] = useState("");
    const [nevek, setNevek] = useState([]);
    let nap = datum?.split("-")[2];
    let honap = datum?.split("-")[1];

    async function getNevekByDatum(){
      if(nap.length>0&&honap.length>0){
        let resp = await fetch(`http://localhost:8000/api/names/${nap}/${honap}`)
        let json = await resp.json()
        setNevek(json)
      }else{
        console.log(nap, honap)
      }
      
    }
    
  return (
    <div className='homeBackground'>
        FindDate
        <FaHome onClick={()=>navigate("/")}/>
        <input type="date" value={datum} onChange={(e)=>setDatum(e.target.value)} />
        <button onClick={()=>getNevekByDatum()}>Keresés</button>
        {nevek.map(x=><div key={x.id}>{x.name}</div>)}
    </div>
  )
}