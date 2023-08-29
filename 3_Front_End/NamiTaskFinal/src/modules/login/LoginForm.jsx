import { useState } from "react"; //dependencia useState que se sirve para manejar estados dentro del componente funcional.
import PropTypes from "prop-types"; // definir los tipos de las propiedades del componente.
import CustomModal from "../register/CustomModal";
//Se define el componente LoginForm que recibe una propiedad onLogin como argumento.
//onLogin es una función que se utilizará para manejar el inicio de sesión cuando el formulario sea enviado.
const LoginForm = ({ onLogin }) => {
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");

  const [authError, setAuthError] = useState(""); // Nuevo estado para el mensaje de error de autenticación
  const [modalOpen, setModalOpen] = useState(false);

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);

    setAuthError(""); // Limpiar el mensaje de error de autenticación al cambiar el nombre de usuario
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);

    setAuthError(""); // Limpiar el mensaje de error de autenticación al cambiar la contraseña
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (nickname === "") {
      setNickname("Este campo es requerido");
      return;
    }

    if (password === "") {
      setPassword("Este campo es requerido");
      return;
    }

    try {
      const userData = {
        nickname: nickname,
        password: password,
      };

      // Llamada a la función de inicio de sesión desde el contexto

      const success = await onLogin(userData);

      if (success) {
        //inicio sesion exitoso
        setNickname("");
        setPassword("");
      } else {
        // Abrir el modal automáticamente
        setAuthError("Credenciales inválidas"); // Mostrar mensaje de error si el inicio de sesión falla
        openModal();
      }
    } catch (error) {
      console.error("Error de autenticación:", error);
    }
  };

  return (
    <div>
      <form
        onSubmit={handleSubmit}
        className="w-64 mx-auto mt-4 p-4 bg-white shadow-md rounded-lg"
      >
        <div className="mb-4">
          <label
            htmlFor="nickname"
            className="block text-gray-700 font-bold mb-2"
          >
            Nombre de usuario:
          </label>
          <input
            type="text"
            id="nickname"
            value={nickname}
            onChange={handleNicknameChange}
            required
            className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-4">
          <label
            htmlFor="password"
            className="block text-gray-700 font-bold mb-2"
          >
            Contraseña:
          </label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            required
            className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Iniciar sesión
        </button>
      </form>
      <CustomModal
        isOpen={modalOpen}
        closeModal={closeModal}
        message={authError}
      />
    </div>
  );
};

//Se definen las propiedades requeridas para el componente LoginForm
LoginForm.propTypes = {
  onLogin: PropTypes.func.isRequired,
};

export default LoginForm;
