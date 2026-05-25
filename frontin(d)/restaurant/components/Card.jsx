import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

export const Card = ({id, name, photo, descr}) => {
    const navigate = useNavigate();

  return (
    <div className='cardDiv' onClick={()=>navigate(`/foods/${id}`, {state: {name}})}>
        <div className='imgDiv'>
           <img className='cardImg' src={photo} alt={name} /> 
        </div>
        <div style={{fontWeight:"bold", marginBottom:"10px"}}>
            {name}
        </div>
        <div className="leiras">
          {descr}  
        </div>
    </div>
  )
}