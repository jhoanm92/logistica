import { useEffect, useState } from "react";
import TableDisplay from "../components/Tables/TableContainer";
import {useAuth} from "../hooks/AuthProvider";
import { Button, Card, Center, Container, Link, Stack } from "@chakra-ui/react";
import { useLocalStorage } from "../hooks/useLocalStorage";
import { instance } from "../http/axiosConfig";


function ClientesPage() {
    const headers = ["id", "nombre", "identificacion", "direccion", "acciones"]
    const [clientes, setClientes] = useState([]);
    const [user, setValue] = useLocalStorage("user", null);
    const [deleted, setDeleted] = useState(false);
    const {getToken} = useAuth();

    useEffect(()=>{
        setDeleted(false);
        instance.get("/cliente" , {
            headers:{
                Authorization: getToken()
            }
        }).then(data => setClientes(data.data)).catch(e => console.log(e));
    },[deleted])

    return (         
        <Stack spacing={4} align="stretch" padding="12px">
            <Container maxW="xxl">                    
                    <Link href="/clientes/crear" ><Button colorScheme="teal">Crear nuevo cliente</Button></Link>                     
            </Container>

            <Container maxW="xxl">
                <Card>
                    <TableDisplay tableTitle="Tabla de clientes" data={clientes} tableHaders={headers} setDeleted={setDeleted}/>
                </Card>
            </Container>       
        </Stack>        
    );
}

export default ClientesPage;