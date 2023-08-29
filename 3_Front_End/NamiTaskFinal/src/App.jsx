import {  useContext } from 'react';
import { AuthContext } from './components/AuthContext';
import TaskManager from './modules/tasks/TaskManager';
import Navbar from './components/Navbar'
import LoginForm from './modules/login/LoginForm';
import RegistrationForm from './modules/register/RegistrationForm'
import ProjectManager from './modules/projects/ProjectManager';
import { ProjectProvider } from './modules/projects/ProjectContext';

const App = () => {
  const { isLoggedIn, user, handleLogin, handleLogout, setIsLoggedIn, setUser } =
    useContext(AuthContext);

  // Función para manejar el registro
  const handleRegister = (userData) => {
    // Aquí puedes realizar la lógica para registrar al nuevo usuario en el servidor y guardar la información en una base de datos
    // Por simplicidad, asumiremos que el registro es exitoso y almacenaremos los datos del usuario en el estado
    setIsLoggedIn(true);
    setUser(userData);
  };

  return (
    <div>
      <div>
        {isLoggedIn && user ? (
          <>
            <h2 className="text-black p-4 text-2xl font-bold text-center">
              Bienvenido a NamiTask, {user.nickName}!
            </h2>
            <button
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2 focus:outline-none focus:shadow-outline"
              onClick={handleLogout}
            >
              Cerrar sesión
            </button>
          </>
        ) : (
          <>
            <h2 className="bg-gray-800 text-white p-4 text-2xl font-bold text-center ba">
              Nami Task
            </h2>
            <h2 className="text-black p-4 text-2xl font-bold text-center">
              Iniciar sesión
            </h2>
            <LoginForm onLogin={handleLogin} />
            <h2 className="text-black p-4 text-2xl font-bold text-center">
              Registrarse
            </h2>
            <RegistrationForm onRegister={handleRegister} />
          </>
        )}
      </div>
      {isLoggedIn && (
        <>
          <Navbar></Navbar>
          <div className="flex">
            <ProjectProvider>
              <ProjectManager />
              <div className="w-3/4 p-4">
                <TaskManager />
              </div>
            </ProjectProvider>
          </div>
        </>
      )}
    </div>
  );
};

export default App;
