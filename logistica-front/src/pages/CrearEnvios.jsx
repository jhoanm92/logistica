import {
  Button,
  Card,
  Container,
  Input,
  Link,
  Select,
  Stack,
  Text,
} from "@chakra-ui/react";
import { instance } from "../http/axiosConfig";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../hooks/AuthProvider";

function CrearEnvios() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { getToken } = useAuth();

  const [isEditable, setIsEditable] = useState(!!id);
  const [dates, setDates] = useState({ fechaEntrega: '', fechaRegistro: '' });
  const [envios, setEnvios] = useState({});
  const [lugarEntrega, setLugarEntrega] = useState([]);
  const [lugarSelec, setLugarSelec] = useState({});
  const [tipoEnvio, setTipoEnvio] = useState([]);
  const [tipoSelec, setTipoSelec] = useState({});
  const [clientes, setClientes] = useState([]);
  const [clienteSelect, setClienteSelect] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (id) {
          setIsEditable(true);
          const response = await instance.get(`/envio/${id}`, {
            headers: { Authorization: getToken() },
          });
          const data = response.data;

          setEnvios(data);
          setLugarSelec(data.lugarEntrega.id);
          setTipoSelec(data.tipoEnvio.id);
          setDates({
            fechaEntrega: data.fechaEntrega.split('T')[0],
            fechaRegistro: data.fechaRegistro.split('T')[0],
          });
        } else {
          const response = await instance.get(`/cliente`, {
            headers: { Authorization: getToken() },
          });
          const data = response.data;

          setClienteSelect(data[0].id);
          setClientes(data);
        }

        loadTipoEnvio();
        loadLugarEntrega();
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [id, getToken]);

  const loadTipoEnvio = async () => {
    try {
      const response = await instance.get(`/lugar-entrega`, {
        headers: { Authorization: getToken() },
      });
      const data = response.data;

      setLugarEntrega(data);
      setTipoSelec(envios.lugarEntrega.id);
    } catch (error) {
      console.log(error);
    }
  };

  const loadLugarEntrega = async () => {
    try {
      const response = await instance.get(`/tipo-envio`, {
        headers: { Authorization: getToken() },
      });
      const data = response.data;

      setTipoEnvio(data);
      setLugarSelec(envios.lugarEntrega.id);
    } catch (error) {
      console.log(error);
    }
  };

  
  const handleOptionChange = (e) => {
    const nuevoIdLugarEntrega = parseInt(e.target.value, 10);

    setLugarSelec(nuevoIdLugarEntrega);
    setEnvios({
      ...envios,
      lugarEntrega: { id: nuevoIdLugarEntrega },
    });
  };

  const handleTipoChange = (e) => {
    const nuevoIdTipoEnvio = parseInt(e.target.value, 10);

    setTipoSelec(nuevoIdTipoEnvio);
    setEnvios({
      ...envios,
      tipoEnvio: { id: nuevoIdTipoEnvio },
    });
  };

  const handleClienteChange = (e) => {
    const nuevoIdCliente = parseInt(e.target.value, 10);

    setClienteSelect(nuevoIdCliente);
    setEnvios({
      ...envios,
      cliente: { id: nuevoIdCliente },
    });
  };

  const handleChange = (e) => {
    setEnvios({
      ...envios,
      [e.target.name]: e.target.value,
    });
  };

  const calcularDescuento = () => {
    let cantidad = Number(envios.cantidad);
    let descuento = 0;

    if (cantidad > 10) {
      descuento =
        envios.tipoEnvio.id === 1
          ? Number(envios.valorEnvio) - Number(envios.valorEnvio * 0.05)
          : Number(envios.valorEnvio) - Number(envios.valorEnvio * 0.03);
    }

    return descuento;
  };


  const handleUpdate = async () => {
    try {
      const descuento = calcularDescuento();

      const body = {
        ...envios,
        descuento,
      };

      const response = await instance.patch('/envio', body, {
        headers: { Authorization: getToken() },
      });

      if (response.status === 200) navigate('/envios');
      else alert('Algo salió mal');
    } catch (error) {
      console.log(error);
    }
  };

  const handleSubmit = async () => {
    try {
      const descuento = calcularDescuento();

      const body = {
        ...envios,
        fechaEntrega: envios.fechaEntrega + 'T00:00:00',
        fechaRegistro: envios.fechaEntrega + 'T00:00:00',
        valorEnvio: Number(envios.valorEnvio),
        valorNeto: Number(envios.valorEnvio),
        descuento,
        cantidad: Number(envios.cantidad),
      };

      const response = await instance.post('/envio', body, {
        headers: { Authorization: getToken() },
      });

      if (response.status !== 201) {
        alert('Algo salió mal');
        console.log(response.data);
      } else {
        navigate('/envios');
      }
    } catch (error) {
      console.log(error);
    }
  };

  if (envios == undefined) return <h1>Cargando...</h1>;

  return (
    <Container padding="12px">
      <Card padding="12px">
        <Stack spacing={3}>
          <Text fontSize="xl">Crear nuevo cliente</Text>
          <Input
            placeholder="Tipo Producto"
            onChange={handleChange}
            value={envios.tipoPorducto}
            name="tipoPorducto"
          />
          {!isEditable && (
            <Input
              placeholder={"Valor del envio"}
              onChange={handleChange}
              name="valorEnvio"
              type="number"
            />
          )}
          <Input
            placeholder="Cantidad"
            onChange={handleChange}
            value={envios.cantidad}
            name="cantidad"
          />
          <Input
            type="date"
            value={dates.fechaRegistro ? dates.fechaRegistro : null}
            onChange={handleChange}
            name="fechaRegistro"
          />
          <Input
            type="date"
            value={dates.fechaEntrega ? dates.fechaEntrega : null}
            onChange={handleChange}
            name="fechaEntrega"
          />
          <Select
            onChange={handleOptionChange}
            value={isEditable ? lugarSelec : null}
          >
            <option>Selecciona una opcion</option>
            {lugarEntrega.map((item) => (
              <option value={item.id}>{item.nombre}</option>
            ))}
          </Select>
          <Select
            onChange={handleTipoChange}
            value={isEditable ? tipoSelec : null}
          >
            <option>Selecciona una opcion</option>
            {tipoEnvio.map((item) => (
              <option value={item.id}>{item.nombre}</option>
            ))}
          </Select>
          {!isEditable && (
            <Select onChange={handleClienteChange}>
              <option>Selecciona una opcion</option>
              {clientes.map((item) => (
                <option value={item.id}>{item.nombre}</option>
              ))}
            </Select>
          )}
          <Stack direction="row" flexDir="row-reverse">
            <Button
              colorScheme="teal"
              onClick={!isEditable ? handleSubmit : handleUpdate}
            >
              {!isEditable ? "Crear Envio" : "Actualizar Envio"}
            </Button>
            <Link href="/envios">
              <Button>Cancelar</Button>
            </Link>
          </Stack>
        </Stack>
      </Card>
    </Container>
  );
}

export default CrearEnvios;
