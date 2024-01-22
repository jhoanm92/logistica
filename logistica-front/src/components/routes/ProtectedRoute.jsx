import { Navigate } from "react-router-dom";
import { useAuth } from "../../hooks/AuthProvider";

function ProtectedRoute({children}) {
    const {user} = useAuth();

    if(!user){
        return <Navigate to="/login" />
    }

    return children;
}

export default ProtectedRoute;