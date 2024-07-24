import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RegistrationForm: React.FC = () => {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/v1/persons', { firstname, lastname, email, password });
            const personId = response.data.id;
            navigate(`/profile/${personId}`);
            setError(null);
        } catch (error) {
            setError('Error creating user. Please try again.');
            console.error(error);
        }
    };

    const handleLogin = () => {
        navigate('/login');
    };

    return (
        <div className="registration-container">
            <div className="registration-form">
                <h3 className="registration-title">Register</h3>
                {error && <p className="error">{error}</p>}
                <form onSubmit={handleRegister}>
                    <label htmlFor="fname">First Name</label>
                    <input
                        type="text"
                        id="fname"
                        name="firstname"
                        placeholder="Your first name.."
                        value={firstname}
                        onChange={(e) => setFirstname(e.target.value)}
                        required
                    />

                    <label htmlFor="lname">Last Name</label>
                    <input
                        type="text"
                        id="lname"
                        name="lastname"
                        placeholder="Your last name.."
                        value={lastname}
                        onChange={(e) => setLastname(e.target.value)}
                        required
                    />

                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Your email.."
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Your password.."
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <input type="submit" value="Register" className="submit-button" />
                </form>
                <button onClick={handleLogin} className="login-button">Login</button>
            </div>
        </div>
    );
};

export default RegistrationForm;
