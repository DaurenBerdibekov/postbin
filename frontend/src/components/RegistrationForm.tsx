import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './RegistrationForm.css';

const RegistrationForm: React.FC = () => {
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordError, setPasswordError] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const validatePassword = (password: string): boolean => {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasNumber = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        return password.length >= minLength && hasUpperCase && hasNumber && hasSpecialChar;
    };

    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newPassword = e.target.value;
        setPassword(newPassword);

        if (!validatePassword(newPassword)) {
            setPasswordError('Password must be at least 8 characters long and include at least one uppercase letter, one number, and one special character.');
        } else {
            setPasswordError(null);
        }
    };

    const handleRegistration = async (event: React.FormEvent) => {
        event.preventDefault();

        if (passwordError) {
            setError('Please fix the errors before submitting.');
            return;
        }

        try {
            await axios.post('/api/v1/persons', { firstname, lastname, email, password });
            navigate('/login');
        } catch (error) {
            setError('Error registering user. Please try again.');
            console.error(error);
        }
    };

    return (
        <div className="registration-container">
            <div className="registration-wrapper">
                <h2 className="registration-title">Register</h2>
                {error && <p className="error">{error}</p>}
                <form onSubmit={handleRegistration} className="registration-form">
                    <input
                        type="text"
                        placeholder="First Name"
                        value={firstname}
                        onChange={(e) => setFirstname(e.target.value)}
                        className="registration-input"
                        required
                    />
                    <input
                        type="text"
                        placeholder="Last Name"
                        value={lastname}
                        onChange={(e) => setLastname(e.target.value)}
                        className="registration-input"
                        required
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="registration-input"
                        required
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={handlePasswordChange}
                        className="registration-input"
                        required
                    />
                    {passwordError && <p className="error">{passwordError}</p>}
                    <button type="submit" className="registration-button">Register</button>
                    <button
                        type="button"
                        onClick={() => navigate('/login')}
                        className="registration-button registration-button-back"
                    >
                        Back to Login
                    </button>
                </form>
            </div>
        </div>
    );
};

export default RegistrationForm;
