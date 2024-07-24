import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const LoginForm: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/v1/login', { email, password });
            const personId = response.data.id;
            navigate(`/profile/${personId}`);
            setError(null);
        } catch (error) {
            setError('Invalid email or password');
            console.error(error);
        }
    };

    const handleRegistration = () => {
        navigate('/registration');
    };

    return (
        <div className="login-container">
            <div className="login-wrapper">
                <h2 className="login-title">Login</h2>
                {error && <p className="error">{error}</p>}
                <form onSubmit={handleLogin}>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="login-input"
                        required
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="login-input"
                        required
                    />
                    <button type="submit" className="login-button">Login</button>
                </form>
                <button onClick={handleRegistration} className="register-button">Register</button>
            </div>
        </div>
    );
};

export default LoginForm;
