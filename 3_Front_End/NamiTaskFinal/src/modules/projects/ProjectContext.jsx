// createContext se utiliza para crear un contexto de datos que se puede compartir entre componentes
// useState hook que permite agregar estado a un componente funcional
import { createContext, useState } from "react";
import PropTypes from "prop-types";// para validar las propiedades (props)

//se crea un nuevo contexto ProjectContext utilizando la funcion createContex
//iniciamente no tiene ningun valor 
//se usa para compartir el estado y funciones entre componentes
const ProjectContext = createContext();

//exportamos el componente ProjectProvider
//es responsable de proporcionar el contexto creado anteriormente a sus componentes hijos
//con la propiedad children representa los elementos hijos que serán envueltos por el contexto
export const ProjectProvider = ({ children }) => {
  //se utiliza el hook state para definir dos variables de estado 
  const [selectedProject, setSelectedProject] = useState(null);
  const [tasks, setTasks] = useState([]);

  const handleUpdateTasks = (newTasks) => {
    setTasks(newTasks);
  };

  return (
    <ProjectContext.Provider value={{ selectedProject, setSelectedProject, tasks, handleUpdateTasks }}>
      {children}
    </ProjectContext.Provider>
  );
};
ProjectProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
export default ProjectContext;
