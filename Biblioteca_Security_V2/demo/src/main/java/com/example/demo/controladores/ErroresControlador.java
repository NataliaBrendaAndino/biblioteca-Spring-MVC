package com.example.demo.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
// implementamos interfaz de Spring ErrorController
public class ErroresControlador implements ErrorController {

    // Ponemos el RequestMapping a nivel de método, no clase
    // para indicar que todo recurso/error (sea Get o Post) ingrese por este método
    // recuperando el código de error que nos envié el servidor
    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        // trabaja parecido a un ModelMap, pero retorna el modelo y vista
        // "error" hace referencia al {error} de thymeleaf en error.html
        ModelAndView errorPage = new ModelAndView("error");

        // lo llenamos según el código
        String errorMsg = "";

        // método pruvado abajo
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "El recurso solicitado no existe.";
                break;
            }
            case 403: {
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 401: {
                errorMsg = "No se encuentra autorizado.";
                break;
            }
            case 404: {
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            }
            case 500: {
                errorMsg = "Ocurrió un error interno.";
                break;
            }
        }

        // renderiza código y msj
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    // recibe la petición, extrae de allí el código de estado y lo castea a entero
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    // public String getErrorPath() {
    // return "/error.html";
    // }

}
