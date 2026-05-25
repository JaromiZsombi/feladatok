import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react';
import { FaFacebook, FaTwitter, FaInstagram, FaHome, FaLinkedin } from "react-icons/fa";
import { useNavigate, useParams } from 'react-router-dom';
import { Header } from './components/Header';

export const Books = () => {

    const [konyvek, setKonyvek] = useState([]);
    const { categId, searchedWord } = useParams();
    const navigate = useNavigate();



    useEffect(() => {
        if (categId) {
            async function getKonyvekByCategory() {
                let resp = await fetch(`http://localhost:8000/api/books/${categId}`)
                let json = await resp.json()
                console.log(json)
                setKonyvek(json)
            }
            getKonyvekByCategory()

        } else if (searchedWord) {
            async function getKonyvekBySearch() {
                let resp = await fetch(`http://localhost:8000/api/book/${searchedWord}`)
                let json = await resp.json()
                console.log(json)
                setKonyvek(json)
            }
            getKonyvekBySearch()

        }
        console.log(categId, searchedWord)
    }, [])

    return (
        <div className='főDiv'>
            <Header/>
            {konyvek.map(x => <div key={x.id} className='valami'>{x.title}</div>)}
        </div>
    )
}


