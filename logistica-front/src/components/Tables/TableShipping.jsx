import {
  Button,
  Link,
  Stack,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { instance } from "../../http/axiosConfig";
import { MdCreate, MdDelete } from "react-icons/md";
import { useAuth } from "../../hooks/AuthProvider";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

function TableShipping({ data, tableTitle, setDeleted ,tableHeaders }) {
  const { getToken } = useAuth();
  const navigate = useNavigate();  

  const deleteHandler = async (item) => {
    const option = confirm(`Desea eliminar el envio: ${item.id}`);
    if (option) {
      await instance.delete(`/envio/${item.id}`, {
        headers: {
          Authorization: getToken(),
        },
      });      
      setDeleted(true);
    }
  };

  return (
    <TableContainer>
      <Table variant="simple">
        <Thead>
          <Tr>
            {tableHeaders.map((item) => (
              <Th>{item}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {data.map((item) => (
            <Tr>
              <Td>{item.id}</Td>
              <Td>{item.tipoPorducto}</Td>
              <Td>{item.cantidad}</Td>
              <Td>{item.fechaRegistro.split("T")[0]}</Td>
              <Td>{item.fechaEntrega.split("T")[0]}</Td>
              <Td>{item.numeroGuia}</Td>
              <Td>{`${item.valorEnvio} $`}</Td>
              <Td>{item.placaFlota}</Td>
              <Td>{`${item.valorNeto} $`}</Td>
              <Td>{item.lugarEntrega.nombre}</Td>
              <Td>{item.lugarEntrega.ubicacion}</Td>
              <Td>{item.tipoEnvio.nombre}</Td>
              <Td>{item.cliente.nombre}</Td>
              <Td>{item.cliente.direccion}</Td>
              <Td>
                <Stack direction="row">
                  <Link href={`/envios/${item.id}`}>
                    <Button size="sm">
                      <MdCreate />
                    </Button>
                  </Link>
                  <Button
                    colorScheme="red"
                    size="sm"
                    onClick={() => deleteHandler(item)}
                  >
                    <MdDelete />
                  </Button>
                </Stack>
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
}

export default TableShipping;
