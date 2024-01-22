import { DatePicker } from "@orange_digital/chakra-datepicker";

function DatePickers({ envios }) {
  console.log(envios);

  if (envios == null) {
    return <h1>Cargando...</h1>;
  }
  return (
    <>
      
    </>
  );
}

export default DatePickers;
