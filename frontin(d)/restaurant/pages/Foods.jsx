import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { FaHome } from "react-icons/fa";
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Card2 } from '../components/Card2';

export const Foods = () => {
    const {categId} = useParams();
    const location = useLocation();
    const nev = location.state?.name
    const [kajak, setKajak] = useState([]);
    const navigate = useNavigate();



    useEffect(()=>{
        async function getKajak(){
            let resp = await fetch(`http://localhost:8000/api/foodsbycateg/${categId}`)
            let json = await resp.json()
            console.log(json)
            setKajak(json)
        }
        getKajak()
    },[])



  return (
    <div className='homeBackground'>
        <div className='header'>
                    <FaHome className='Házikó' onClick={()=>navigate('/')} style={{cursor:"pointer"}}/>
                    <h1 className='szoveg'>Our</h1>
                    <h1 className='szoveg2'>{nev}</h1>
        </div>
        <div className='közép'>
{kajak.map(x=><div key = {x.id} className='valami'><Card2 title={x.title} photo={x.photo} categId={x.categId} id={x.id} price={x.price}/></div>)}
        </div>
            
        <div className='footer'>
            <h1 className='szoveg'>Catering@gmail.com</h1>
        </div>
    </div>
  )
}