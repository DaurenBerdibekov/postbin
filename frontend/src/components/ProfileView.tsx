import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

interface PersonDTO {
    firstname: string;
    lastname: string;
    email: string;
}

interface MessageDTO {
    id: string;
    subject: string;
    content: string;
}

const ProfileView: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [person, setPerson] = useState<PersonDTO | null>(null);
    const [messages, setMessages] = useState<MessageDTO[]>([]);
    const [editMessage, setEditMessage] = useState<MessageDTO | null>(null);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const personResponse = await axios.get(`/api/v1/persons/${personId}`);
                setPerson(personResponse.data);

                const messagesResponse = await axios.get(`/api/v1/persons/${personId}/messages/all`);
                setMessages(messagesResponse.data);

                setError(null);
            } catch (error) {
                setError('Error fetching profile or messages. Please try again.');
                console.error(error);
            }
        };

        fetchProfile();
    }, [personId]);

    const handleDeleteMessage = async (messageId: string) => {
        if (!messageId) {
            setError('Message ID is undefined');
            return;
        }

        try {
            await axios.delete(`/api/v1/persons/${personId}/messages/${messageId}`);
            setMessages(messages.filter(message => message.id !== messageId));
            setError(null);
        } catch (error) {
            setError('Error deleting message. Please try again.');
            console.error(error);
        }
    };

    const handleEditMessage = (message: MessageDTO) => {
        setEditMessage(message);
    };

    const handleUpdateMessage = async () => {
        if (!editMessage) return;

        try {
            const response = await axios.put(`/api/v1/persons/${personId}/messages/${editMessage.id}`, editMessage);
            setMessages(messages.map(message => message.id === editMessage.id ? response.data : message));
            setEditMessage(null);
            setError(null);
        } catch (error) {
            setError('Error updating message. Please try again.');
            console.error(error);
        }
    };

    const handleNewMessage = () => {
        navigate(`/newMessage/${personId}`);
    };

    const handleLogout = () => {
        navigate('/login');
    };

    return (
        <div className="profile-container">
            {error && <p className="error">{error}</p>}
            {person ? (
                <div className="profile-wrapper">
                    <h2 className="profile-title">{person.firstname} {person.lastname}</h2>
                    <p className="profile-email">Email: {person.email}</p>
                    <button className="profile-button" onClick={handleNewMessage}>New Message</button>
                    <button className="profile-button" onClick={handleLogout}>Logout</button>
                    <h3 className="messages-title">Messages</h3>
                    <ul className="messages-list">
                        {messages.map(message => (
                            <li key={message.id} className="message-item">
                                {editMessage && editMessage.id === message.id ? (
                                    <div>
                                        <input
                                            className="message-input"
                                            type="text"
                                            value={editMessage.subject}
                                            onChange={(e) => setEditMessage({ ...editMessage, subject: e.target.value })}
                                        />
                                        <textarea
                                            className="message-textarea"
                                            value={editMessage.content}
                                            onChange={(e) => setEditMessage({ ...editMessage, content: e.target.value })}
                                        ></textarea>
                                        <button className="message-button" onClick={handleUpdateMessage}>Save</button>
                                        <button className="message-button" onClick={() => setEditMessage(null)}>Cancel</button>
                                    </div>
                                ) : (
                                    <div>
                                        <h4 className="message-subject">{message.subject}</h4>
                                        <p className="message-content">{message.content}</p>
                                        <button className="message-button" onClick={() => handleEditMessage(message)}>Edit</button>
                                        <button className="message-button" onClick={() => handleDeleteMessage(message.id)}>Delete</button>
                                    </div>
                                )}
                            </li>
                        ))}
                    </ul>
                </div>
            ) : (
                <p className="loading">Loading...</p>
            )}
        </div>
    );
};

export default ProfileView;
