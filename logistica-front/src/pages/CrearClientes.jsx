import { Button, Card, Center, Container, Input, Link, Stack, Text } from "@chakra-ui/react";
import { instance } from "../http/axiosConfig";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {updateCliente} from "../http/clienteActions";
import { useAuth } from "../hooks/AuthProvider";

function CrearCliente() {

    const [isEditable, setIsEditable] = useState(false);       
    const [cliente, setCliente] = useState({});
    const params = useParams();
    const navigate = useNavigate();
    const {getToken} = useAuth();

    useEffect(() => {
        if(params.id){        
            setIsEditable(!isEditable);
            instance.get(`/cliente/${params.id}`, {
                headers: {
                    Authorization: getToken()
                }
            }).then(d => setCliente(d.data)).catch(e => console.log(e));
        }        
    },[]);

    const handleChange = (e) => {
        setCliente({
            ...cliente,
            [e.target.name]:e.target.value
        })
    }

    const saveClient = async () => {
        const res = await instance.post(`/cliente`, cliente, {
            headers:{
                "Authorization": getToken()
            }
        })
        res.status == 201 ? navigate("/clientes") : console.log(res.data)
    }
    
    const updateClient = async () => {        
        setCliente({
            ...cliente,
            id:params.id
        })
        const res = await instance.put(`/cliente`, cliente, {
            headers:{
                "Authorization": getToken()
            }
        })

        res.status == 200 ? navigate("/clientes") : alert("Error en enviar solicitud");
    }

    return ( 
        <Container padding="12px">
            <Card padding="12px">                
                <Stack spacing={3}>
                    <Text fontSize="xl">Crear nuevo cliente</Text>
                    <Input placeholder="Identificacion" value={cliente.identificacion} onChange={handleChange} name="identificacion"/>
                    <Input placeholder="Nombre cliente" value={cliente.nombre} onChange={handleChange} name="nombre"/>
                    <Input placeholder="Direccion" value={cliente.direccion} onChange={handleChange} name="direccion"/>
                    <Stack direction="row" flexDir="row-reverse">
                        <Button colorScheme="teal" onClick={!isEditable ? saveClient : updateClient }>
                            { !isEditable ?  "Crear Cliente" : "Actualizar cliente"}
                        </Button>
                        <Link href="/clientes"><Button>Cancelar</Button></Link>
                    </Stack>
                </Stack>
            </Card>
        </Container> 
    );
}

export default CrearCliente;