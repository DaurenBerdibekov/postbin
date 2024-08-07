import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import './NewMessage.css';

const CreateMessageForm: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [subject, setSubject] = useState('');
    const [content, setContent] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        try {
            await axios.post(`/api/v1/persons/${personId}/messages`, { subject, content });
            setSubject('');
            setContent('');
            setError(null);
            navigate(`/profile/${personId}`);
        } catch (error) {
            setError('Error creating message. Please try again.');
            console.error(error);
        }
    };

    return (
        <div className="new-message-container">
            <div className="new-message-info">
                <h2 className="profile-title">Create New Message</h2>
                <form onSubmit={handleSubmit} className="new-message-form">
                    <div className="form-group">
                        <label htmlFor="subject" className="form-label">Subject:</label>
                        <input
                            type="text"
                            id="subject"
                            value={subject}
                            onChange={(e) => setSubject(e.target.value)}
                            className="form-input"
                            placeholder="Enter subject"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="content" className="form-label">Content:</label>
                        <textarea
                            id="content"
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            className="form-textarea"
                            placeholder="Enter content"
                            required
                        ></textarea>
                    </div>
                    {error && <p className="error">{error}</p>}
                    <div className="form-buttons">
                        <button type="submit" className="form-button">Create Message</button>
                        <button
                            type="button"
                            onClick={() => navigate(`/profile/${personId}`)}
                            className="form-button form-button-back"
                        >
                            Back to Profile
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default CreateMessageForm;
