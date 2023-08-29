import { useState } from "react";
import PropTypes from "prop-types";


const TaskListItem = ({
  //key
  task,
  onEditClick,
  onDeleteClick,
  isEditing,
  editedTaskData,
  onInputChange,
  onSaveClick,
  onCancelClick,
  
}) =>
 {
  return (
 
    <div key={task._id} className="bg-gray-100 rounded-lg p-4 mb-4">
      
      {isEditing ? (
        
        <div>
          <div className="mb-4">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="name"
            >
              Nombre de Tarea:
            </label>
            <input
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="name"
              name="name"
              type="text"
              value={editedTaskData.name}
              onChange={onInputChange}
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="description"
            >
              Descripción de Tarea:
            </label>
            <textarea
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="description"
              name="description"
              value={editedTaskData.description}
              onChange={onInputChange}
            ></textarea>
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="dateStart"
            >
              Fecha de Inicio:
            </label>
            <input
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="dateStart"
              name="dateStart"
              type="date"
              value={editedTaskData.dateStart}
              onChange={onInputChange}
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="dateFinish"
            >
              Fecha de Entrega:
            </label>
            <input
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="dateFinish"
              name="dateFinish"
              type="date"
              value={editedTaskData.dateFinish}
              onChange={onInputChange}
            />
          </div>

          <div className="flex justify-end">
            <button
              className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-2"
              onClick={onSaveClick} // Corregir aquí
            >
              Guardar
            </button>
            <button
              className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={onCancelClick} // Corregir aquí
            >
              Cancelar
            </button>
          </div>
        </div>
      ) : (
        <div>
          <h3 className="text-lg font-bold mb-2">{task.name}</h3>

          <p className="text-gray-700 mb-2">
            <span className="font-bold">Descripción de Tarea:</span>{" "}
            {task.description}
          </p>
          <p className="text-gray-700 mb-2">
            <span className="font-bold">Fecha de Inicio:</span> {task.dateStart}
          </p>

          <p className="text-gray-700 mb-2">
            <span className="font-bold">Fecha de Entrega:</span>{" "}
            {task.dateFinish}
          </p>

          <div className="flex justify-end">
            <button
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={() => onEditClick(task._id)} // Corregir aquí
            >
              Editar
            </button>
            <button
              className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              onClick={() => onDeleteClick(task._id)} // Corregir aquí
            >
              Eliminar
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

const TaskList = ({ tasks, onEditTask, onDeleteTask}) => {
  const [editingTaskId, setEditingTaskId] = useState(null);
  const [editedTaskData, setEditedTaskData] = useState({
    name: "",
    description: "",
    dateStart: "",
    dateFinish: "",
  });

  const handleEditClick = (taskId) => {
    const taskToEdit = tasks.find((task) => task._id === taskId);
    setEditingTaskId(taskId);
    setEditedTaskData({ ...taskToEdit });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedTaskData({ ...editedTaskData, [name]: value });
  };

  const handleSaveClick = () => {
    onEditTask(editingTaskId, editedTaskData);
    setEditingTaskId(null);
  };

  const handleCancelClick = () => {
    setEditingTaskId(null);
    setEditedTaskData({
      name: "",
      description: "",
      dateStart: "",
      dateFinish: "",
    });
  };

  const isTaskEditing = (taskId) => {
    return editingTaskId === taskId;
  };

  const handleDeleteTask = (taskId) => {
    onDeleteTask(taskId);
  };

  return (
    
    <div className="mt-4">
      
      {tasks.map((task) => (
        <TaskListItem
          key={task._id}
          task={task}
          onEditClick={handleEditClick}
          onDeleteClick={handleDeleteTask}
          isEditing={isTaskEditing(task._id)}
          editedTaskData={editedTaskData}
          onInputChange={handleInputChange}
          onSaveClick={handleSaveClick}
          onCancelClick={handleCancelClick}
        />
      ))}
    </div>
  );
};
TaskListItem.propTypes = {
  key: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
  task: PropTypes.shape({
    _id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    dateStart: PropTypes.string.isRequired,
    dateFinish: PropTypes.string.isRequired,
  }).isRequired,
  onEditClick: PropTypes.func.isRequired,
  onDeleteClick: PropTypes.func.isRequired,
  isEditing: PropTypes.bool.isRequired,
  editedTaskData: PropTypes.shape({
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    dateStart: PropTypes.string.isRequired,
    dateFinish: PropTypes.string.isRequired,
  }).isRequired,
  onInputChange: PropTypes.func.isRequired,
  onSaveClick: PropTypes.func.isRequired,
  onCancelClick: PropTypes.func.isRequired,
};
TaskList.propTypes = {
  tasks: PropTypes.arrayOf(
    PropTypes.shape({
      _id: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
      description: PropTypes.string.isRequired,
      dateStart: PropTypes.string.isRequired,
      dateFinish: PropTypes.string.isRequired,
    })
  ).isRequired,
  onEditTask: PropTypes.func.isRequired,
  onDeleteTask: PropTypes.func.isRequired,
};

export default TaskList;
