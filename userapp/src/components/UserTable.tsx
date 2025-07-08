import React from 'react';
import { User } from '../App';
import styles from './UserTable.module.css';

interface UserTableProps {
  users: User[];
  onUserClick: (user: User) => void;
  onUserDelete: (id: number) => void;
}

const UserTable: React.FC<UserTableProps> = ({ users, onUserClick, onUserDelete }) => {
  return (
    <div className={styles.tableWrapper}>
      <div className={styles.table}>
        <div className={styles.headerRow}>
          <div>NAME / EMAIL</div>
          <div>ADDRESS</div>
          <div>PHONE</div>
          <div>WEBSITE</div>
          <div>COMPANY</div>
          <div>ACTION</div>
        </div>
        {users.map((user) => (
          <div className={styles.dataRow} key={user.id}>
            <div onClick={() => onUserClick(user)} className={styles.clickable} style={{display:'flex',flexDirection:'column',overflow:'hidden'}}>
              <span className={styles.name}>{user.name}</span>
              <span className={styles.email}>{user.email}</span>
            </div>
            <div onClick={() => onUserClick(user)} className={styles.clickable} title={user.address.street + ', ' + user.address.suite + ', ' + user.address.city + ', ' + user.address.zipcode} style={{overflow:'hidden',textOverflow:'ellipsis'}}>
              {user.address.street}, {user.address.suite}, {user.address.city}, {user.address.zipcode}
            </div>
            <div onClick={() => onUserClick(user)} className={styles.clickable} title={user.phone} style={{overflow:'hidden',textOverflow:'ellipsis'}}>{user.phone}</div>
            <div onClick={() => onUserClick(user)} className={styles.clickable} title={user.website} style={{overflow:'hidden',textOverflow:'ellipsis'}}>{user.website}</div>
            <div onClick={() => onUserClick(user)} className={styles.clickable} title={user.company.name} style={{overflow:'hidden',textOverflow:'ellipsis'}}>{user.company.name}</div>
            <div>
              <button className={styles.deleteBtn} onClick={() => onUserDelete(user.id)} title="Delete user">✕</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UserTable; 