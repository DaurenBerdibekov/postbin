import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './ProfileView.css'; // Импортируем CSS файл

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
    const [viewMessage, setViewMessage] = useState<MessageDTO | null>(null);
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
                setError('No messages found. Try to create new one.');
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
            setViewMessage(null); // Закрываем просмотр сообщения после удаления
        } catch (error) {
            setError('Error deleting message. Please try again.');
            console.error(error);
        }
    };

    const handleEditMessage = (message: MessageDTO) => {
        setEditMessage(message);
        setViewMessage(null); // Закрываем просмотр сообщения при редактировании
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

    const openViewMessage = (message: MessageDTO) => {
        setViewMessage(message);
        setEditMessage(null); // Закрываем редактирование сообщения при просмотре
    };

    const closeViewMessage = () => {
        setViewMessage(null);
    };

    const handleChangeEditMessage = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        if (editMessage) {
            setEditMessage({ ...editMessage, [e.target.name]: e.target.value });
        }
    };

    return (
        <div className="profile-view-container">
            <div className="profile-view-content">
                <div className="profile-info">
                    {person ? (
                        <div>
                            <h2 className="profile-title">{person.firstname} {person.lastname}</h2>
                            <p className="profile-email">Email: {person.email}</p>
                            <div className="profile-buttons">
                                <button className="profile-button" onClick={handleNewMessage}>New Message</button>
                                <button className="profile-button" onClick={handleLogout}>Logout</button>
                            </div>
                        </div>
                    ) : (
                        <p className="loading">Loading...</p>
                    )}
                </div>
                <div className="messages-list-container">
                    <h3 className="messages-title">Messages</h3>
                    {error && <p className="error">{error}</p>}
                    <ul className="messages-list">
                        {messages.map(message => (
                            <li key={message.id} className="message-item">
                                <div className="message-content">
                                    <h4 className="message-subject">{message.subject}</h4>
                                    <p className="message-snippet">{message.content.slice(0, 500)}</p>
                                    <div className="message-buttons">
                                        <button className="message-button" onClick={() => openViewMessage(message)}>Read</button>
                                        <button className="message-button" onClick={() => handleEditMessage(message)}>Edit</button>
                                        <button className="message-button" onClick={() => handleDeleteMessage(message.id)}>Delete</button>
                                    </div>
                                </div>
                            </li>
                        ))}
                    </ul>
                    {viewMessage && !editMessage && (
                        <div className="message-details">
                            <h3>Message Details</h3>
                            <h4 className="message-subject">{viewMessage.subject}</h4>
                            <p className="message-content">{viewMessage.content}</p>
                            <div className="edit-buttons">
                                <button className="message-button" onClick={() => handleEditMessage(viewMessage)}>Edit</button>
                                <button className="message-button" onClick={() => handleDeleteMessage(viewMessage.id)}>Delete</button>
                                <button className="message-button" onClick={closeViewMessage}>Close</button>
                            </div>
                        </div>
                    )}
                    {editMessage && (
                        <div className="message-edit-form">
                            <h3>Edit Message</h3>
                            <input
                                type="text"
                                name="subject"
                                value={editMessage.subject}
                                onChange={handleChangeEditMessage}
                                className="message-input"
                                placeholder="Subject"
                            />
                            <textarea
                                name="content"
                                value={editMessage.content}
                                onChange={handleChangeEditMessage}
                                className="message-textarea"
                                placeholder="Content"
                            ></textarea>
                            <button className="message-button" onClick={handleUpdateMessage}>Save</button>
                            <button className="message-button" onClick={() => setEditMessage(null)}>Cancel</button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProfileView;
