import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react';
import { FaFacebook,FaTwitter, FaInstagram,FaHome, FaLinkedin } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';

export const Header = () => {

    const navigate = useNavigate();

  return (
    <div className='header'>
        <div className='homeContainer'><FaHome className='home' onClick={()=>navigate("/")}/></div>
        <h2 className='title'>Olvasni jó!</h2>
        <div className='title'></div>
        {/*Minden egyes könyv egy új világ, amit csak ki kell nyitnod.
        <br />
        Döntsd el, mit is olvass ezután?
        <br />
        Jó helyen jársz. Milyen műfajokat kedvelsz?
        <div>
            <FaHome/>
            <FaFacebook/>
            <FaTwitter/>
            <FaInstagram/>
            <FaLinkedin/>
        </div>*/}
    </div>
  )
}


