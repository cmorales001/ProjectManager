import { useContext } from "react";
import PropTypes from "prop-types";
import ProjectContext from "./ProjectContext";

const ProjectList = ({ projects, onSelect }) => {
  const { selectedProject } = useContext(ProjectContext); // Obtenemos el proyecto seleccionado desde el contexto

  return (
    <ul>
      {projects.map((project) => (
        <div
          key={project.id}
          className={`cursor-pointer mb-2 ${
            selectedProject === project
              ? "text-blue-500 font-bold"
              : "text-gray-700"
          }`}
          onClick={() => {
            onSelect(project); // Llama a la función onSelect con el proyecto seleccionado
          }}
        >
          {project.name}

          <div className=" border p-2 rounded bg-gray-100 text-xm text-gray-600">
            Código de Proyecto: {project.codeInvitation}
          </div>
        </div>
      ))}
    </ul>
  );
};
ProjectList.propTypes = {
  projects: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
  onSelect: PropTypes.func.isRequired,
};

export default ProjectList;
