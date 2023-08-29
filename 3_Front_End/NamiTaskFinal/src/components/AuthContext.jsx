//se importan las dependencias necesarias, incluyendo createContext para crear el contexto de autenticación
//useState para para gestionar el estado de autenticación y el usuario
//useEffect para realizar efectos secundarios en este caso para comprobar la sesión al cargar la aplicación
import { createContext, useState, useEffect } from "react";
//axios para realizar peticiones HTTP
import axios from "axios";
//js-cookie para manejar cookies.
import Cookies from "js-cookie";
//Exportamos AuthContext para que otros componentes puedan acceder a él y usar los valores dados por el proveedor de autenticación.
export const AuthContext = createContext();
import PropTypes from "prop-types";

//El componente AuthProvider es un proveedor de contexto que define dos estados
//con useState: isLoggedIn para rastrear si el usuario está autenticado
//y user para almacenar la información del usuario autenticado.
export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  // Función para comprobar la sesión del usuario al cargar la aplicación almacenada en la cookie
  const checkSession = async () => {
    // Leer los datos del usuario desde la cookie
    const userFromCookie = Cookies.get("user");
    // creamos un if Si existe obtiene los datos del usuario desde la cookie y establece los estados isLoggedIn y user  y autentica al usuario
    if (userFromCookie) {
      // Desempaquetar la cookie y convertir la cadena de texto en un objeto JavaScript
      const userObject = JSON.parse(userFromCookie);
      setIsLoggedIn(true);
      setUser(userObject);
    } else {
      // caso contrario devuelve un estado isLoggedIn en falso y user en nulo.
      setIsLoggedIn(false);
      setUser(null);
    }
  };

  // useEffect para comprobar la sesión del usuario al cargar la aplicación
  //se pasa un array vacío como segundo argumento, lo que significa que solo se ejecutará una vez cuando el componente se ejecute.
  useEffect(() => {
    checkSession();
  }, []);
  //metodo handleLogin para realizar una solicitud de inicio de sesión al servidor utilizando Axios
  //tomando el parametro userData y se encarga de realizar una autenticación de usuario utilizando Axios
  // para hacer una solicitud HTTP POST con nuestra API
  const handleLogin = async (userData) => {
    try {
      const response = await axios.post(
        "http://localhost:8083/api/v2/userAccess",
        {
          //parametros del userData
          emailOrNickname: userData.nickname,
          password: userData.password,
        }
      );
      // Verificar que la respuesta contiene los datos del usuario
      if (response.data) {
        // Lógica para autenticar al usuario, y si es exitoso:
        setIsLoggedIn(true); // indica que el usuario ha iniciado sesión.
        setUser(response.data);

        // Convertir el objeto userData a una cadena de texto JSON
        const userDataJson = JSON.stringify(response.data); // representa los datos del usuario en un formato que se puede guardar en la cookie
        // Guardar la cadena de texto JSON en la cookie
        Cookies.set("user", userDataJson);
      } else {
        // Manejar el caso en que la autenticación falla

    console.error("Error de autenticación:", response.data);
      }
    } catch (error) {
      // Manejar errores aquí si la autenticación falla

  console.error("Error de autenticación:", error);
    }
  };
  //Funcion handleLogout se utiliza para cerrar la sesión del usuario.
  const handleLogout = () => {
    //Establece los estados isLoggedIn y user en falso y nulo
    setIsLoggedIn(false);
    setUser(null);
    // Eliminar la cookie al cerrar sesión
    Cookies.remove("user");
  };
  //se retorna el componente AuthContext.Provider que nos entrega cuatro valores a todos los componentes
  // que estén envueltos por él: isLoggedIn, user, handleLogin, y handleLogout.
  return (
    <AuthContext.Provider
      value={{ isLoggedIn, user, handleLogin, handleLogout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
//se definen las propiedades requeridas para el componente AuthProvider
AuthProvider.propTypes = {
  children: PropTypes.node.isRequired, // Asegura que "children" es un nodo de React y es requerido.
};

