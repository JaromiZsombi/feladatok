import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

export const Home = () => {

    const navigate = useNavigate();
    
 

  return (
    <div className='homeBackground'>
        <h1>Névnap kereső</h1>
        <div className='gombok'>
            <button onClick={()=>navigate("/findbydate/")}>Keresés dátum szerint</button>
            <button onClick={()=>navigate("/findbyname/")}>Keresés név szerint</button>
        </div>
    </div>
  )
}