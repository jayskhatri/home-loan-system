import './App.css';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <div >
      <Router>
        <Routes>
          <Route path="/login" element={<Login/>}/>
          <Route path="/dashboard" element={<Dashboard/>}/>
        </Routes>
      </Router>
     
    </div>
  );
}

export default App;
