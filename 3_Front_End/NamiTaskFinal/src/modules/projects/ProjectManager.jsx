import { useState, useEffect } from "react";
import ProjectList from "./ProjectList";
import { useContext } from "react";
import { AuthContext } from "../../components/AuthContext";
import ProjectContext from "./ProjectContext";
import axios from "axios";

const ProjectManager = () => {
  const { user } = useContext(AuthContext);

  const [showJoinForm, setShowJoinForm] = useState(false);
  const [invitationCode, setInvitationCode] = useState("");
  const [projects, setProjects] = useState([]);
  const [newProjectName, setNewProjectName] = useState("");
  const [showProjectForm, setShowProjectForm] = useState(false);
  
  const ownProjects = projects.filter((project) => project.idOwner === user.id);
  const createNewProject = async () => {
    try {
      // Verifica si el nombre del proyecto no está vacío
      if (!newProjectName) {
        alert("Ingrese un nombre para el proyecto.");
        return;
      }
      const userId = user.id;
      // Objeto que representa el nuevo proyecto a crear
      const newProject = {
        name: newProjectName,
        idOwner: userId,
        tasks: [], // Aquí podrías añadir las tareas del nuevo proyecto si las tienes disponibles
      };
      

      // Realiza la solicitud POST al servidor para crear el proyecto
      const response = await axios.post("http://localhost:8083/api/v2/proyect", newProject);

      // Verifica la respuesta del servidor
      if (response.status === 200) { // Cambio: Verificar el código de estado
        // Si la respuesta es exitosa, el proyecto se creó correctamente
        alert("El proyecto se creó correctamente.");
        // Actualiza la lista de proyectos en el estado local con el nuevo proyecto creado
        setProjects([...projects, response.data]); // Cambio: Usar el estado actualizado
        setNewProjectName("");
        setShowProjectForm(false);
      } else {
        // Si la respuesta es falsa, hubo un error al crear el proyecto
        alert("Hubo un error al crear el proyecto. Por favor, inténtelo de nuevo.");
      }
    } catch (error) {
      // Manejo de errores en caso de que ocurra alguna excepción
      alert("Error al crear el proyecto. Por favor, inténtelo de nuevo.");
      console.error(error);
      
    }
  };

  const { setSelectedProject, handleUpdateTasks } = useContext(ProjectContext);

  const handleSelectProject = (project) => {
    setSelectedProject(project);
    handleUpdateTasks(project.tasks);
  };

  useEffect(() => {
    const userId = user.id;
    axios
      .get(`http://localhost:8083/api/v2/proyects/${userId}`)
      .then((response) => {
        const data = response.data;
        const projects = data.map(projectData => ({
          id: projectData._id,
          name: projectData.name,
          idOwner: projectData.idOwner,
          authorization: projectData.authorization,
          dateStart: projectData.dateStart,
          dateFinish: projectData.dateFinish,
          codeInvitation: projectData.codeInvitation,
          users: projectData.users,
          tasks: projectData.tasks
        }));
        setProjects(projects);
        console.log(projects)
      });
  }, [user.id]); // Agregar user.id a la lista de dependencias


  const handleAddProjectClick = () => {
    setShowProjectForm(true);
    setShowJoinForm(false); // Asegurarse de que el formulario de unirse esté oculto
  };

  const handleProjectNameChange = (e) => {
    setNewProjectName(e.target.value);
  };

  const handleSaveProject = () => {
    createNewProject(); // Llama a la función para crear el nuevo proyecto
  };

  const handleCancelProject = () => {
    setNewProjectName("");
    setShowProjectForm(false);
  };

  const handleJoinProject = () => {
    setShowProjectForm(false); // Asegurarse de que el formulario de crear proyecto esté oculto
    setShowJoinForm(true);
  };

  const handleJoinProjectSubmit = async () => {
    try {
      const codeInvitationValue = invitationCode;
  
      const response = await axios.put(
        `http://localhost:8083/api/v2/proyect/${codeInvitationValue}/${user.id}`
      );
  
      if (response.status === 200) {
        // Si la respuesta es exitosa (código 200), entonces actualiza los proyectos compartidos
        const joinedProject = response.data;
        setProjects([...projects, joinedProject]);
  
        // Limpia el código de invitación después de unirse
        setInvitationCode("");
        setShowJoinForm(false);
      } else if (response.status === 400) {
        // Si la respuesta es un error de solicitud (código 400), muestra un mensaje de error
        console.error("Error al unirse al proyecto: Código de invitación incorrecto");
      } else {
        // Si ocurre algún otro tipo de error, muestra un mensaje genérico de error
        console.error("Error al unirse al proyecto: Error desconocido");
      }
    } catch (error) {
      console.error("Error al unirse al proyecto:", error);
    }
  };
  

  // Obtener las tareas asociadas a un proyecto
  {/*const getTasksForProject = (projectId) => {
    const project = projects.find((project) => project.id === projectId);
    return project ? project.tasks : [];
  };*/}

  // Filtrar los proyectos a los que te uniste
  const sharedProjects = projects.filter(
    (project) => project.idOwner !== user.id
  );
  return (
    <div className="w-1/4 bg-gray-200 p-4">
      <h2 className="text-xl font-bold mb-4">Proyectos</h2>
      <ProjectList projects={ ownProjects}  onSelect={handleSelectProject} />
     
      {!showProjectForm && (
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 focus:outline-none focus:shadow-outline"
          onClick={handleAddProjectClick}
        >
          Añadir
        </button>
      )}
      {/**Obtener la lista de proyectos compartidos y sus tareas */}
      <h3 className="text-lg font-bold mt-6 mb-4">Proyectos Compartidos</h3>
      
      <ProjectList projects={ sharedProjects}  onSelect={handleSelectProject}
       />
     
      
      <ul>
        {sharedProjects.map((project) => (
          <li
          key={`shared-${project.id}`} 
            className="cursor-pointer text-blue-500 font-bold mb-2"
            onClick={() =>
              setSelectedProject({ id: project.id, name: project.name })
              
            }
            
          > 
                
          </li>
          
        ))}
      </ul>
      
      {showJoinForm ? (
        <div className="mt-4">
          <input
            className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            type="text"
            placeholder="Código de invitación"
            value={invitationCode}
            onChange={(e) => setInvitationCode(e.target.value)}
          />
          <div className="flex justify-end mt-4">
            <button
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2 focus:outline-none focus:shadow-outline"
              onClick={handleJoinProjectSubmit}
            >
              Unirse
            </button>
            <button
              className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={() => setShowJoinForm(false)}
            >
              Cancelar
            </button>
          </div>
        </div>
      ) : (
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2 mt-4 focus:outline-none focus:shadow-outline"
          onClick={handleJoinProject}
        >
          Unirse a proyecto
        </button>
      )}
      {showProjectForm && (
        <div className="mt-4">
          <input
            className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            type="text"
            placeholder="Nombre del proyecto"
            value={newProjectName}
            onChange={handleProjectNameChange}
          />
          <div className="flex justify-end mt-4">
            <button
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-2 focus:outline-none focus:shadow-outline"
              onClick={handleSaveProject}
            >
              Guardar
            </button>
            <button
              className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={handleCancelProject}
            >
              Cancelar
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProjectManager;
