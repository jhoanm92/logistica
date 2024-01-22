import { Box, Button, Flex, Heading, Link, Spacer } from "@chakra-ui/react";
import { useAuth } from "../../hooks/AuthProvider";

function Navbar() {
  const { isLogged, logout } = useAuth();
  
  const handleLogOut = () => {
    logout();
  }

  return (
    <Flex p={4} bg="teal.500" align="center">      
      <Spacer />
      {isLogged && (
        <Box>          
          <Link href="/clientes" color="white" mr={4}>
            Clientes
          </Link>
          <Link href="/envios" color="white">
            Envios
          </Link>
          <Button ml={4} onClick={handleLogOut}>Cerrar Sesion</Button>
        </Box>        
      ) }
    </Flex>
  );
}

export default Navbar;
