import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { AuthProvider } from './components/AuthContext';
import Modal from 'react-modal';

// Define el elemento raíz de tu aplicación
Modal.setAppElement('#root'); // Asegúrate de que '#root' sea el selector del elemento raíz de tu aplicación

// Utiliza createRoot en lugar de render
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </React.StrictMode>
);
