import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react';
import { FaFacebook,FaTwitter, FaInstagram,FaHome, FaLinkedin } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';

export const Footer = () => {

  const [kategoria, setKategoria] = useState([]);
  const [word, setWord] = useState("");

  const navigate = useNavigate();


  return (
    <div className='footer'>
            CONNECt
                <FaFacebook/>
                <FaTwitter/>
                <FaInstagram/>
                <FaLinkedin/>
        </div>
  )
}


