import { useState } from "react";
import {
  BrowserRouter,
  createBrowserRouter,
  Route,
  RouterProvider,
  Routes,
} from "react-router-dom";
import HomePage from "./pages/Home.jsx";
import "./App.css";
import EnviosPage from "./pages/Envios.jsx";
import Clientes from "./pages/Clientes.jsx";
import Navbar from "./components/Navigation/Navbar.jsx";
import CrearCliente from "./pages/CrearClientes.jsx";
import LoginPage from "./pages/Login.jsx";
import ProtectedRoute from "./components/routes/ProtectedRoute.jsx";
import { AuthProvider } from "./hooks/AuthProvider.jsx";
import CrearEnvios from "./pages/CrearEnvios.jsx";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Navbar />
        <Routes>
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <HomePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/login"
            element={              
                <LoginPage />              
            }
          />
          <Route
            path="/clientes"
            element={
              <ProtectedRoute>
                <Clientes />
              </ProtectedRoute>
            }
          />
          <Route
            path="/clientes/:id"
            element={
              <ProtectedRoute>
                <CrearCliente />
              </ProtectedRoute>
            }
          />
          <Route
            path="/clientes/crear"
            element={
              <ProtectedRoute>
                <CrearCliente />
              </ProtectedRoute>
            }
          />
          <Route path="/envios" element= {
            <ProtectedRoute>
              <EnviosPage />
            </ProtectedRoute>
          } />          
          <Route path="/envios/crear" element= {
            <ProtectedRoute>
              <CrearEnvios />
            </ProtectedRoute>
          } />
          <Route path="/envios/:id" element= {
            <ProtectedRoute>
              <CrearEnvios />
            </ProtectedRoute>
          } />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
