import { useState } from "react";
import PropTypes from 'prop-types';

// Subcomponente TaskFormToggle
const TaskFormToggle = ({ onToggleForm }) => {
  return (
    <button
      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
      onClick={onToggleForm}
    >
      Crear Tarea
    </button>
  );
};

// Subcomponente TaskFormFields
const TaskFormFields = ({ taskData, onInputChange, onSubmit, onCancel }) => {
  return (
    <form onSubmit={onSubmit} className="mt-4">
      <div className="mb-4">
        <label className="block text-gray-700 font-bold mb-2" htmlFor="name">
          Nombre de Tarea:
        </label>
        <input
          className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="name"
          name="name"
          type="text"
          placeholder="Nombre de la tarea"
          value={taskData.name}
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
          placeholder="Descripción de la tarea"
          value={taskData.description}
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
          value={taskData.dateStart}
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
          value={taskData.dateFinish}
          onChange={onInputChange}
        />
      </div>

      <div className="flex justify-end">
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Crear Tarea
        </button>
        <button
          type="button"
          className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          onClick={onCancel}
        >
          Cancelar
        </button>
      </div>
    </form>
  );
};

// Subcomponente TaskFormModal
const TaskFormModal = ({ taskData, onEditTask, onSaveTask, onCancel }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center z-10">
      <div className="bg-white rounded-lg p-8">
        <h2 className="text-xl font-bold mb-4">Tarea Creada</h2>

        <p>
          <span className="font-bold">Nombre de Tarea:</span> {taskData.name}
        </p>
        <p>
          <span className="font-bold">Descripción de Tarea:</span>{" "}
          {taskData.description}
        </p>
        <p>
          <span className="font-bold">Fecha de Inicio:</span>{" "}
          {taskData.dateStart}
        </p>

        <p>
          <span className="font-bold">Fecha de Entrega:</span>{" "}
          {taskData.dateFinish}
        </p>

        <div className="flex justify-end mt-4">
          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-2"
            onClick={onEditTask}
          >
            Editar
          </button>
          <button
            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            onClick={onSaveTask}
          >
            Guardar
          </button>
          <button
            type="button"
            className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            onClick={onCancel}
          >
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

const TaskForm = ({ onCreateTask }) => {
  const [taskData, setTaskData] = useState({
    name: "",
    description: "",
    dateStart: "",
    dateFinish: "",
  });


  const [showForm, setShowForm] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const onInputChange = (e) => {
    const { name, value } = e.target;
    setTaskData({ ...taskData, [name]: value });
  };

  const handleToggleForm = () => {
    setShowForm(!showForm);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setShowModal(true);
  };

  const handleEditTask = () => {
    setShowModal(false);
  };

  const handleSaveTask = () => {
    onCreateTask(taskData);
    setShowModal(false);
    setTaskData({
      name: "",
      description: "",
      dateStart: "",
      dateFinish: "",
    });
  };
  const handleCancel = () => {
    setShowForm(false);
    setShowModal(false);
    setTaskData({
      name: "",
      description: "",
      dateStart: "",
      dateFinish: "",
    });
  };

  return (
    <>
      <TaskFormToggle onToggleForm={handleToggleForm} />
      {showForm && (
        <TaskFormFields
          taskData={taskData}
          onInputChange={onInputChange}
          onSubmit={handleSubmit}
          onCancel={handleCancel}
        />
      )}
      {showModal && (
        <TaskFormModal
          taskData={taskData}
          onEditTask={handleEditTask}
          onSaveTask={handleSaveTask}
          onCancel={handleCancel}
        />
      )}
    </>
  );
};

TaskForm.propTypes = {
  tasks: PropTypes.arrayOf(
    PropTypes.shape({
      _id: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
      description: PropTypes.string.isRequired,
      dateStart: PropTypes.string.isRequired,
      dateFinish: PropTypes.string.isRequired,
    })
  ).isRequired,
  onCreateTask: PropTypes.func.isRequired,
};

TaskFormToggle.propTypes = {
  onToggleForm: PropTypes.func.isRequired,
};

TaskFormFields.propTypes = {
  taskData: PropTypes.shape({
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    dateStart: PropTypes.string.isRequired,
    dateFinish: PropTypes.string.isRequired,
  }).isRequired,
  onInputChange: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

TaskFormModal.propTypes = {
  taskData: PropTypes.shape({
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    dateStart: PropTypes.string.isRequired,
    dateFinish: PropTypes.string.isRequired,
  }).isRequired,
  onEditTask: PropTypes.func.isRequired,
  onSaveTask: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default TaskForm;
