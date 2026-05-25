import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

export const Card2 = ({title, photo, categId, id, price}) => {
    const navigate = useNavigate();

  return (
    <div className='card2Div'>
        <div className='img2Div'>
           <img className='card2Img' src={photo} /> 
        </div>
        <div className='title'>
            {title}
        </div>
        <div className="leiras">
          {price} $
        </div>
    </div>
  )
}