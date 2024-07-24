import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

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
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="subject">Subject:</label>
                <input
                    type="text"
                    id="subject"
                    value={subject}
                    onChange={(e) => setSubject(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="content">Content:</label>
                <textarea
                    id="content"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                ></textarea>
            </div>
            {error && <p>{error}</p>}
            <button type="submit">Create Message</button>
            <button type="button" onClick={() => navigate(`/profile/${personId}`)}>Back to Profile</button>
        </form>
    );
};

export default CreateMessageForm;
