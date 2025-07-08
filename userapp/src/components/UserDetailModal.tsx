import React from 'react';
import { User } from '../App';
import styles from './UserDetailModal.module.css';

interface UserDetailModalProps {
  user: User | null;
  onClose: () => void;
}

const UserDetailModal: React.FC<UserDetailModalProps> = ({ user, onClose }) => {
  if (!user) return null;
  const mapUrl = `https://www.google.com/maps?q=${user.address.geo.lat},${user.address.geo.lng}`;
  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modal} onClick={e => e.stopPropagation()}>
        <button className={styles.closeBtn} onClick={onClose} title="Close">✕</button>
        <h2>{user.name}</h2>
        <span className={styles.email}>{user.email}</span>
        <div className={styles.sectionTitle}>Address</div>
        <div className={styles.section}>{user.address.street}, {user.address.suite}<br/>{user.address.city}, {user.address.zipcode}</div>
        <div className={styles.section}><a href={mapUrl} className={styles.mapLink} target="_blank" rel="noopener noreferrer"><span className={styles.mapPin}>📍</span>View on map</a></div>
        <div className={styles.sectionTitle}>Contact</div>
        <div className={styles.section}><strong>Phone:</strong> {user.phone}</div>
        <div className={styles.section}><strong>Website:</strong> <a href={`http://${user.website}`} target="_blank" rel="noopener noreferrer">{user.website}</a></div>
        <div className={styles.sectionTitle}>Company</div>
        <div className={styles.section}><strong>Name:</strong> {user.company.name}</div>
        <div className={styles.section}><strong>Catchphrase:</strong> {user.company.catchPhrase}</div>
        <div className={styles.section}><strong>Business:</strong> {user.company.bs}</div>
      </div>
    </div>
  );
};

export default UserDetailModal; 