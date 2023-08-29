import { useState } from 'react'; //useState un hook que permite utilizar el estado 
import axios from 'axios';//librería para realizar peticiones HTTP,
import CustomModal from './CustomModal'; //componente de modal personalizado 

//Se define un componente funcional llamado RegistrationForm
const RegistrationForm = () => {
  //se crean las siguientes variables 
  //Se utilizan los hooks useState para definir varios estados locales en el componente
  const [firstName, setFirstName] = useState(''); //estado que almacena los valores de los campos del formulario
  const [lastName, setLastName] = useState('');//estado que almacena los valores de los campos del formulario
  const [nickName, setNickName] = useState('');//estado que almacena los valores de los campos del formulario
  const [email, setEmail] = useState('');//estado que almacena los valores de los campos del formulario
  const [password, setPassword] = useState(''); //estado que almacena los valores de los campos del formulario
  const [modalIsOpen, setModalIsOpen] = useState(false);// estado que indica si el modal está abierto o cerrado
  const [message, setMessage] = useState(''); // estado que almacena un mensaje para mostrar al usuario en el modal.

  //Se define la función closeModal que establece el estado modalIsOpen en false, lo que cierra el modal.
  const closeModal = () => {
    setModalIsOpen(false);
  };
  //Se definen funciones para manejar el cambio en los campos del formulario.
  // Cada función toma el evento e (evento de cambio) 
  //y actualiza el estado correspondiente con el valor ingresado por el usuario.
  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };

  const handleNickNameChange = (e) => {
    setNickName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  //Se define la función handleSubmit que se ejecuta cuando se envía el formulario.
  //la funcion se encarga de enviar una petición POST a la API para registrar al usuario con los datos ingresados 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8083/api/v2/user/', {
        firstName,
        lastName,
        nickName,
        email,
        password,
      });
      //creamos un if y el código de estado HTTP 200 significa "OK" 
      //y se devuelve cuando la solicitud se ha realizado correctamente 
      if (response.status === 200) {
        // Registro exitoso, puedes asumir que el usuario fue registrado correctamente
        //se ejecuta el codigo dentro del bloque if.
        // Puedes llamar a onRegister si necesitas realizar alguna acción adicional.
  
        // Resto del código para limpiar los campos del formulario, etc.
        setFirstName('');
        setLastName('');
        setNickName('');
        setEmail('');
        setPassword('');
  
        //  Mostrar mensaje al usuario para indicar que el registro fue exitoso.
        setMessage('Registro exitoso. ¡Bienvenido!');
        setModalIsOpen(true);
      } else {
        // Hubo un error en el registro, muestra un mensaje de error.
       
        // Opcional: Mostrar algún mensaje al usuario para indicar que el registro falló.
        setMessage('Error al registrar. Por favor, inténtalo nuevamente.');
        //se establece el mensaje en el estado message y se abre el modal.
        setModalIsOpen(true);
      }
    } catch (error) {
      // Manejar errores aquí si el registro falla
      
      // Opcional: Mostrar algún mensaje al usuario para indicar que el registro falló.
      setMessage('Error al registrar. Por favor, inténtalo nuevamente.');
      //se establece el mensaje en el estado message y se abre el modal.
      setModalIsOpen(true);
    }
  };

  return (
    // devuelve el JSX que representa el formulario de registro con los campos y botón de registro.
    <form onSubmit={handleSubmit} className="w-64 mx-auto mt-4 p-4 bg-white shadow-md rounded-lg">
      <div className="mb-4">
        <label htmlFor="firstName" className="block text-gray-700 font-bold mb-2">
          Nombre:
        </label>
        <input
          type="text"
          id="firstName"
          value={firstName}
          onChange={handleFirstNameChange}
          required
          className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="lastName" className="block text-gray-700 font-bold mb-2">
          Apellido:
        </label>
        <input
          type="text"
          id="lastName"
          value={lastName}
          onChange={handleLastNameChange}
          required
          className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="nickName" className="block text-gray-700 font-bold mb-2">
          Nickname:
        </label>
        <input
          type="text"
          id="nickName"
          value={nickName}
          onChange={handleNickNameChange}
          required
          className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="email" className="block text-gray-700 font-bold mb-2">
          Correo electrónico:
        </label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={handleEmailChange}
          required
          className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="password" className="block text-gray-700 font-bold mb-2">
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
        Registrarse
      </button>
      {/**Renderizamos el componente CustomModal pasandole propiedades de isOpen, closeModal y message
       * que controlan el estado del modal y el mensaje que se muestra al usuario.
       */}
      <CustomModal isOpen={modalIsOpen} closeModal={closeModal} message={message} />
    </form>
  );
};

export default RegistrationForm;