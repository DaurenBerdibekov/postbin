import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import './ProfileView.css'; // Импортируем CSS файл

interface PersonDTO {
    firstname: string;
    lastname: string;
    email: string;
}

const ProfileContent: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [person, setPerson] = useState<PersonDTO | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const personResponse = await axios.get(`/api/v1/persons/${personId}`);
                setPerson(personResponse.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchProfile();
    }, [personId]);

    if (!person) {
        return <p>Loading...</p>;
    }

    const handleNewMessage = () => {
        navigate(`/newMessage/${personId}`);
    };

    return (
        <div className="profile-content">
            <h2 className="profile-title">{person.firstname} {person.lastname}</h2>
            <p className="profile-email">Email: {person.email}</p>
            <div className="profile-actions">
                <button className="profile-button" onClick={handleNewMessage}>New Message</button>
            </div>
        </div>
    );
};

export default ProfileContent;
