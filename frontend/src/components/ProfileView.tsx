import React, { lazy, Suspense, useState, ChangeEvent } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './ProfileView.css';
import Search from "./Search.tsx"; // Импортируем CSS файл

const ProfileContent = lazy(() => import('./ProfileContent'));

interface MessageDTO {
    id: string;
    subject: string;
    content: string;
}

const ProfileView: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [messages, setMessages] = useState<MessageDTO[]>([]);
    const [editMessage, setEditMessage] = useState<MessageDTO | null>(null);
    const [viewMessage, setViewMessage] = useState<MessageDTO | null>(null);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const fetchMessages = async () => {
        try {
            const response = await axios.get(`/api/v1/persons/${personId}/messages/all`);
            if (response.data.length === 0) {
                setError('No messages found.');
            } else {
                setMessages(response.data);
                setError(null);
            }
        } catch (error) {
            setError('You do not have any messages. Try to create one.');
            console.error(error);
        }
    };

    // Вызов функции для загрузки сообщений при монтировании компонента
    React.useEffect(() => {
        fetchMessages();
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
            setViewMessage(null);
        } catch (error) {
            setError('Error deleting message. Please try again.');
            console.error(error);
        }
    };

    const handleEditMessage = (message: MessageDTO) => {
        setEditMessage(message);
        setViewMessage(null);
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

    const handleLogout = () => {
        navigate('/login');
    };

    const openViewMessage = (message: MessageDTO) => {
        setViewMessage(message);
        setEditMessage(null);
    };

    const closeViewMessage = () => {
        setViewMessage(null);
    };

    const handleChangeEditMessage = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        if (editMessage) {
            setEditMessage({ ...editMessage, [e.target.name]: e.target.value });
        }
    };

    return (
        <div className="profile-view-container">
            <div className="profile-view-content">
                <Suspense fallback={<p>Loading...</p>}>
                    <ProfileContent/>
                </Suspense>

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
                                        <button className="message-button"
                                                onClick={() => openViewMessage(message)}>Read
                                        </button>
                                        <button className="message-button"
                                                onClick={() => handleEditMessage(message)}>Edit
                                        </button>
                                        <button className="message-button"
                                                onClick={() => handleDeleteMessage(message.id)}>Delete
                                        </button>
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
                                <button className="message-button" onClick={() => handleEditMessage(viewMessage)}>Edit
                                </button>
                                <button className="message-button"
                                        onClick={() => handleDeleteMessage(viewMessage.id)}>Delete
                                </button>
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
                <Suspense fallback={<p>Loading...</p>}>
                    <Search/>
                </Suspense>
            </div>
            <div className="profile-actions">
                <button className="profile-button-logout" onClick={handleLogout}>Logout</button>
            </div>

        </div>
    );
};

export default ProfileView;
