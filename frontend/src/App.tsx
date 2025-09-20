import { useState } from 'react'
import React from 'react'
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Navigation from './components/Navigation'
import Dashboard from './components/Dashboard'
import MemberList from './components/MemberList'
import MemberForm from './components/MemberForm'
import MembershipList from './components/MembershipList'
import MembershipForm from './components/MembershipList'
import './App.css'

function App() {


  return (
      <Router>
        <div className="App">
          <Navigation />
          <main className="main-content">
            <Routes>
              <Route path='/' element={<Dashboard/> }/>
              <Route path='/members' element={<MemberList/>}/>
              <Route path='/members/new' element={<MemberForm/>}/>
              <Route path='/members/edit/:id' element={<MemberForm/>}/>
              <Route path='/membersip' element={<MembershipList/>}/>
              <Route path='/membership/new' element={<MembershipForm/>}/>
              <Route path='/membership/edit/:id' element={<MembershipForm/>}/>
            </Routes>
          </main>
        </div>
      </Router>
  );
}

export default App
