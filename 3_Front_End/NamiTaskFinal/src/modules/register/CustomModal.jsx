import Modal from 'react-modal';//crear el componente modal personalizado
import PropTypes from 'prop-types'; //definir los tipos de las propiedades del componente.

//objeto customStyles para definir el estilo de la ventana
const customStyles = {
  content: {
    width: '300px',
    height: '200px',
    margin: 'auto',
  },
};

//Se define nuetros componente CustomModal, recibiendo tres propiedades como argumentos
//isOpen booleano que indica si el modal está abierto o cerrado
//closeModal función para cerrar el modal
//message es mensaje a mostrar en el modal
const CustomModal = ({ isOpen, closeModal, message }) => {
  //retorna componente Modal dado por react-modal
   return (
    <Modal
    //se pasan las siguientes propiedades
      isOpen={isOpen}
      onRequestClose={closeModal}
      style={customStyles}//aplicar los estilos definidos en customStyles
      closeTimeoutMS={200} //establecer el tiempo de animación de cierre en 200 milisegundos
      contentLabel="Example Modal"//etiqueta al modal 
      ariaHideApp={false} //evitar que el modal modifique el atributo aria-hidden

    //se crea un boton y ala hacer se ejecutará la función closeModal
    >
      <button onClick={closeModal} className="absolute top-2 right-2 text-gray-500 hover:text-gray-700">
        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
{/** Se define el contenido del modal 
 * que muestra el mensaje pasado a través de la propiedad message
*/}
      <div className="p-4">
        <h2 className="text-xl font-semibold mb-4">Resultado</h2>
        <p>{message}</p> {/* Mostrar el mensaje recibido */}
      </div>
    </Modal>
  );
};

//se definen las propiedades requeridas para el componente CustomModal
//valida que las propiedades esperadas sean proporcionadas al componente y sean del tipo correcto
CustomModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  message: PropTypes.string.isRequired,
};
export default CustomModal;
