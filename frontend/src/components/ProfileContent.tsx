import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Link, useNavigate, useParams} from 'react-router-dom';

interface FriendDTO {
    id : string
    firstname: string;
    lastname: string;
    email: string;
}

const ProfileContent: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [person, setPerson] = useState<{ firstname: string; lastname: string; email: string } | null>(null);
    const [friends, setFriends] = useState<FriendDTO[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const personResponse = await axios.get(`/api/v1/persons/${personId}`);
                setPerson(personResponse.data);

                const friendsResponse = await axios.get(`/api/v1/persons/${personId}/friends`);
                setFriends(friendsResponse.data);
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

            <div className="friends-list">
                <h3>Friends</h3>
                {friends.length > 0 ? (
                    <ul>
                        {friends.map((friend, index) => (
                            <li key={index} className="friend-item">
                                <Link to={`/profile/${personId}/friend/${friend.id}`}>
                                    {friend.firstname} {friend.lastname}
                                </Link>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No friends found.</p>
                )}
            </div>
        </div>
    );
};

export default ProfileContent;
