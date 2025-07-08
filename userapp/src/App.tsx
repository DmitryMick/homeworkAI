import React, { useEffect, useState } from 'react';
import styles from './App.module.css';
import UserTable from './components/UserTable';
import UserDetailModal from './components/UserDetailModal';

export interface Geo {
  lat: string;
  lng: string;
}

export interface Address {
  street: string;
  suite: string;
  city: string;
  zipcode: string;
  geo: Geo;
}

export interface Company {
  name: string;
  catchPhrase: string;
  bs: string;
}

export interface User {
  id: number;
  name: string;
  username: string;
  email: string;
  address: Address;
  phone: string;
  website: string;
  company: Company;
}

const App: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  useEffect(() => {
    fetch('https://jsonplaceholder.typicode.com/users')
      .then((res) => {
        if (!res.ok) throw new Error('Failed to fetch users');
        return res.json();
      })
      .then((data) => setUsers(data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleUserClick = (user: User) => setSelectedUser(user);
  const handleModalClose = () => setSelectedUser(null);
  const handleUserDelete = (id: number) => setUsers((prev) => prev.filter((u) => u.id !== id));

  return (
    <div className={styles.appContainer}>
      <h1 className={styles.title}>Users</h1>
      {loading && <div>Loading users...</div>}
      {error && <div className={styles.error}>{error}</div>}
      {!loading && !error && (
        <UserTable users={users} onUserClick={handleUserClick} onUserDelete={handleUserDelete} />
      )}
      <UserDetailModal user={selectedUser} onClose={handleModalClose} />
    </div>
  );
};

export default App;
