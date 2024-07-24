import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginPage';
import ProfileView from './components/ProfileView';
import CreateMessageForm from './components/NewMessage';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/registration" element={<RegistrationForm />} />
                <Route path="/profile/:personId" element={<ProfileView />} />
                <Route path="/newMessage/:personId" element={<CreateMessageForm />} />
                <Route path="/" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
};

export default App;
