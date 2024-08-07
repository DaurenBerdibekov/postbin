import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './Search.css';

interface PersonDTO {
    id: string;
    firstname: string;
    lastname: string;
    email: string;
}

const ProfileView: React.FC = () => {
    const { personId } = useParams<{ personId: string }>();
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [searchResults, setSearchResults] = useState<PersonDTO[]>([]);

    // Функция для поиска пользователей
    const searchPersons = async () => {
        const [firstname, lastname] = searchTerm.split(' ');
        try {
            const response = await axios.get(`/api/v1/persons/search`, { params: { firstname, lastname } });
            setSearchResults([response.data]);
        } catch (error) {
            console.error('Error searching for persons:', error);
        }
    };

    // Функция для добавления в друзья
    const addFriend = async (friendId: string) => {
        try {
            await axios.put(`/api/v1/persons/${personId}/friend/${friendId}/addFriend`);
            alert('Friend added successfully!');
        } catch (error) {
            console.error('Error adding friend:', error);
        }
    };

    const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(e.target.value);
    };

    const handleSearchSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        searchPersons();
    };

    return (
            <div className="search-add-friend">
                <h3>Search and Add Friend</h3>
                <form onSubmit={handleSearchSubmit} className="search-form">
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={handleSearchChange}
                        className="search-input"
                        placeholder="Search by firstname or lastname"
                    />
                    <button type="submit" className="search-button">Search</button>
                </form>
                {searchResults.length > 0 && (
                    <ul className="search-results">
                        {searchResults.map((person) => (
                            <li key={person.id} className="search-result-item">
                                <p>{person.firstname} {person.lastname}</p>
                                <p>Email: {person.email}</p>
                                <button
                                    className="add-friend-button"
                                    onClick={() => addFriend(person.id)}
                                >
                                    Add to friends
                                </button>
                            </li>
                        ))}
                    </ul>
                )}
            </div>

    );
};

export default ProfileView;
