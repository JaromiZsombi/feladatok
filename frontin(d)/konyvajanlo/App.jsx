import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { Home } from './Home'
import { Books } from './Books'

function App() {
 

  return (
    <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/books/category/:categId" element={<Books />} />
              <Route path="/book/search/:searchedWord" element={<Books />} />
            </Routes>
        </BrowserRouter>
  )
}

export default App
