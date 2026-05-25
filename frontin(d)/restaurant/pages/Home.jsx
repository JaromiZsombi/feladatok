import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { Card } from '../components/Card';
import { FaHome } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';

export const Home = () => {

    const [kategoriak, setKategoriak] = useState([]);
    const navigate = useNavigate();
    const [kereses, setKereses] = useState("");
    
 
    useEffect(()=>{
        async function getKategoriak(){
            let resp = await fetch("http://localhost:8000/api/categories")
            let json = await resp.json()
            setKategoriak(json)
        }
        getKategoriak()
    },[])

  return (
    <div className='homeBackground'>
        <div className='header'>
            <FaHome className='Házikó' onClick={()=>navigate('/')} style={{cursor:"pointer"}}/>
            <h1 className='szoveg'>Catering Service</h1>
        </div>
        <div className='keresoMezo'>
            <input type="text" placeholder='Search...' value={kereses} onChange={(e) => setKereses(e.target.value)}/>
            <button onClick={()=>navigate(`/search/${kereses}`)}>Keresés</button>
        </div>
        <div className='közép2'>
            {kategoriak.map(x=><div key = {x.id}> <Card name={x.name} photo={x.photo} descr={x.descr} id={x.id}/></div>)}
        </div>
        
        <div className='footer'>
            <h1 className='szoveg'>Catering@gmail.com</h1>
        </div>
    </div>
  )
}