import React, { useState, useEffect } from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import '../style/PersonListView.css';

interface Person {
    id: number;
    firstname: string;
    lastname: string;
    email: string;
    createdAt: string;
}

const PersonListView: React.FC = () => {
    const { personId } = useParams<{ personId: string }>(); // Получаем personId из URL
    const [persons, setPersons] = useState<Person[]>([]);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Сохраняем текущее personId в localStorage
        if (personId) {
            localStorage.setItem('personId', personId);
        }

        const fetchPersons = async () => {
            try {
                const response = await fetch(`/api/v1/persons/${personId}/allPersons`, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }

                const data = await response.json();
                setPersons(data);
                setLoading(false);
            } catch (error) {
                setError('Failed to fetch persons');
                setLoading(false);
            }
        };

        fetchPersons();
    }, [personId]);

    const handleAddFriend = async (friendPersonId: string) => {
        try {
            const response = await fetch(`/api/v1/persons/${personId}/friend/${friendPersonId}/addFriend`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }

            alert(`Friend was added successfully!`);
        } catch (error) {
            alert('Failed to add friend');
        }
    };

    const handleBackToMe = () => {
        const storedPersonId = localStorage.getItem('personId');
        if (storedPersonId) {
            navigate(`/profile/${storedPersonId}`);
        } else {
            console.error('personId is not available');
        }
    };

    const filteredPersons = persons.filter((person) =>
        `${person.firstname} ${person.lastname}`
            .toLowerCase()
            .includes(searchTerm.toLowerCase())
    );

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div className="person-list-view">
            <h1>Person List</h1>
            <input
                type="text"
                placeholder="Search by name..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <table className="person-table">
                <thead>
                <tr>
                    <th>Nr</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Add friend</th>
                </tr>
                </thead>
                <tbody>
                {filteredPersons.map((person, index) => (
                    <tr key={person.id}>
                        <td>{index + 1}</td>
                        <td>{`${person.firstname} ${person.lastname}`}</td>
                        <td>{person.email}</td>
                        <td>
                            <button onClick={() => handleAddFriend(person.id.toString())}>
                                Add to friends
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="profile-actions">
                <button className="profile-button-back" onClick={handleBackToMe}>
                    Back to me
                </button>
            </div>
        </div>
    );
};

export default PersonListView;
