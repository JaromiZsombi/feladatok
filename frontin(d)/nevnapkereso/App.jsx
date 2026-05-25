import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { Home } from './pages/Home'
import { FindByDate } from './pages/FindDate'
import { FindByName } from './pages/findName'

function App() {
 
  return (
  <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/findbydate/" element={<FindByDate/>} />
          <Route path="/findbyname/" element={<FindByName />} />
        </Routes>
    </BrowserRouter>
  )
}

export default App
