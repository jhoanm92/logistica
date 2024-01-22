import {
  Button,
  Link,
  Stack,
  Table,
  TableCaption,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { MdCreate, MdDelete } from "react-icons/md";
import { instance } from "../../http/axiosConfig";
import { useAuth } from "../../hooks/AuthProvider";
import { useNavigate } from "react-router-dom";

function TableDisplay({ data, tableTitle, setDeleted, tableHaders }) {

  const {getToken} = useAuth();
  const navigate = useNavigate();

  const deleteHandler = async (data) => {
    const option = confirm(`Desea eliminar el cliente: ${data.id}`);
    if(option){
      await instance.delete(`/cliente/${data.id}`, {
        headers:{
          'Authorization':getToken()
        }});      
      setDeleted(true);
      }      
  }

  return (
    <TableContainer>
      <Table variant="simple">
        <TableCaption>{tableTitle}</TableCaption>
        <Thead>
          <Tr>
            {tableHaders.map((data) => (
              <Th>{data}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {data.map((item) => (
            <Tr key={item.id}>
              <Td>{item.id}</Td>
              <Td>{item.nombre}</Td>
              <Td>{item.identificacion}</Td>
              <Td>{item.direccion}</Td>
              <Td>
                <Stack direction="row">
                  <Link href={`/envios/${item.id}`}>
                    <Button size="sm">
                      <MdCreate />
                    </Button>
                  </Link>
                  <Button colorScheme="red" size="sm" onClick={() => deleteHandler(item)}>
                    <MdDelete />{" "}
                  </Button>{" "}
                </Stack>
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
}

export default TableDisplay;
