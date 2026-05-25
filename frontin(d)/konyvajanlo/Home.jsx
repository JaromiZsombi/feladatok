import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react';
import { FaFacebook,FaTwitter, FaInstagram,FaHome, FaLinkedin } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import { Header } from './components/Header';
import { Footer } from './components/Footer';

export const Home = () => {

  const [kategoria, setKategoria] = useState([]);
  const [word, setWord] = useState("");

  const navigate = useNavigate();

  useEffect(()=>{
        async function getKategoriak(){
            let resp = await fetch(`http://localhost:8000/api/categories`)
            let json = await resp.json()
            console.log(json)
            setKategoria(json)
        }
        getKategoriak()
    },[])

  return (
    <div className='főDiv'>
      <Header/>
      <div className='kepDiv'>
        <img src="header.png" className='kep' alt="" />
      </div>
      <div className='kozepDiv'>
        <div className='keresoDiv divdiv'><input type="text" /> <button></button></div>
        <div className='valamitextDiv divdiv'><p>Döntsd el, mit is olvass ezután?</p>
        <br />
        <p>Jó helyen jársz. Milyen műfajokat kedvelsz?</p></div>
        <div className='idezetDiv divdiv'><p className='szoveg'>Minden egyes könyv egy új világ, amit csak ki kell nyitnod.</p></div>
      </div>
      <div className='cardDiv'>
        {kategoria.map(x=><div key = {x.id} onClick={()=>navigate(`/books/category/${x.id}`)} className='valami'>{x.name}</div>)}
      </div>
      
      
      <Footer/>
















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


