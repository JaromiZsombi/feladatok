import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import { Foods } from './pages/Foods';
import { Search } from './pages/Search';

export default function App() {

  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/foods/:categId" element={<Foods/>}/>
          <Route path="/search/:searchedWord" element={<Search/>}/>
        </Routes>
    </BrowserRouter>
  )
}
