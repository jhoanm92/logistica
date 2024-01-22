import React, { useEffect, useState } from "react";
import TableShipping from "../components/Tables/TableShipping";
import { instance } from "../http/axiosConfig";
import { useAuth } from "../hooks/AuthProvider";
import { Button, Container, Link, Stack } from "@chakra-ui/react";

function EnviosPage() {
  const [envios, setEnvios] = useState([]);
  const { getToken } = useAuth();
  const [deleted, setDeleted] = useState(false);

  const tableHeaders = [
    "id",
    "tipoProducto",
    "cantidad",
    "fechaRegistro",
    "fechaEntrega",
    "numeroGuia",
    "valorEnvio",
    "placaFlota",
    "valorNeto",
    "Lugar Entrega",
    "ubicacion",
    "Tipo envio",
    "cliente",
    "direccion",
    "acciones",
  ];
  useEffect(() => {
    setDeleted(false);
    instance
      .get("/envio", {
        headers: {
          Authorization: getToken(),
        },
      })
      .then((res) => setEnvios(res.data))
      .catch((e) => console.log(e));
  }, [deleted]);

  return (
    <Stack spacing={4} align="stretch" padding="12px">
      <Container maxW="xxl">
        <Link href="/envios/crear">
          <Button colorScheme="teal">Crear nuevo envio</Button>
        </Link>
      </Container>

      <TableShipping tableHeaders={tableHeaders} data={envios} setDeleted={setDeleted}/>
    </Stack>
  );
}

export default EnviosPage;
