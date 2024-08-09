import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './ProfileView.css';

interface MessageDTO {
    id: string;
    subject: string;
    content: string;
}

interface PersonDTO {
    firstname: string;
    lastname: string;
    email: string;
}

const FriendProfileView: React.FC = () => {
    const navigate = useNavigate();
    const { personId, friendId } = useParams<{ personId: string, friendId: string }>();
    const [person, setPerson] = useState<PersonDTO | null>(null);
    const [messages, setMessages] = useState<MessageDTO[]>([]);
    const [viewMessage, setViewMessage] = useState<MessageDTO | null>(null);
    const [error, setError] = useState<string | null>(null);

    // save personId to localStorage for the function getBackToMe
    useEffect(() => {
        if (personId) {
            localStorage.setItem('personId', personId);
        }
    }, [personId]);

    // fetch info about friend
    const fetchPerson = async () => {
        try {
            const response = await axios.get(`/api/v1/persons/${personId}/friend/${friendId}`);
            setPerson(response.data);
        } catch (error) {
            console.error("Failed to fetch person's data", error);
            setError('Failed to fetch person information.');
        }
    };

    // fetch messages of friend
    const fetchMessages = async () => {
        try {
            const response = await axios.get(`/api/v1/persons/${personId}/messages/friend/${friendId}`);
            if (response.data.length === 0) {
                setError('No messages found.');
            } else {
                setMessages(response.data);
                setError(null);
            }
        } catch (error) {
            console.error("Your friend doesn't have any messages.", error);
            setError("Your friend doesn't have any messages.");
        }
    };

    useEffect(() => {
        fetchPerson();
        fetchMessages();
    }, [friendId, personId]);

    const handleBackToMe = () => {
        const storedPersonId = localStorage.getItem('personId'); // Извлекаем personId из localStorage
        if (storedPersonId) {
            navigate(`/profile/${storedPersonId}`);
        } else {
            console.error('personId is not available');
        }
    };

    const handleLogout = () => {
        navigate('/login');
    };

    const openViewMessage = (message: MessageDTO) => {
        setViewMessage(message);
    };

    const closeViewMessage = () => {
        setViewMessage(null);
    };

    return (
        <div className="profile-view-container">
            <div className="profile-view-content">
                {person && (
                    <div className="friend-person-info">
                        <h2>{person.firstname} {person.lastname}</h2>
                        <p>Email: {person.email}</p>
                    </div>
                )}
                <div className="messages-list-container">
                    <h3 className="messages-title">Messages</h3>
                    {error && <p className="error">{error}</p>}
                    <ul className="messages-list">
                        {messages.map((message) => (
                            <li key={message.id} className="message-item">
                                <div className="message-content">
                                    <h4 className="message-subject">{message.subject}</h4>
                                    <p className="message-snippet">{message.content.slice(0, 500)}</p>
                                    <div className="message-buttons">
                                        <button
                                            className="message-button"
                                            onClick={() => openViewMessage(message)}
                                        >
                                            Read
                                        </button>
                                    </div>
                                </div>
                            </li>
                        ))}
                    </ul>
                    {viewMessage && (
                        <div className="message-details">
                            <h3>Message Details</h3>
                            <h4 className="message-subject">{viewMessage.subject}</h4>
                            <p className="message-content">{viewMessage.content}</p>
                            <div className="edit-buttons">
                                <button className="message-button" onClick={closeViewMessage}>
                                    Close
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
            <div className="profile-actions">
                <button className="profile-button-back" onClick={handleBackToMe}>
                    Back to me
                </button>
                <button className="profile-button-logout" onClick={handleLogout}>
                    Logout
                </button>
            </div>
        </div>
    );
};

export default FriendProfileView;
