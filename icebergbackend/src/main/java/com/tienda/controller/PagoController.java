package com.tienda.controller;

import com.stripe.exception.StripeException;
import com.tienda.dto.StripePagoRequest;
import com.tienda.model.Orden;
import com.tienda.service.OrdenService;
import com.tienda.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos/stripe")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private OrdenService ordenService;

    @PostMapping("/crear-intento")
    public ResponseEntity<?> crearIntentoPago(@RequestBody StripePagoRequest request) {
        try {
            Orden orden = ordenService.obtenerPorId(request.getOrdenId())
                    .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
            String clientSecret = pagoService.crearIntentoPagoStripe(
                    request.getMonto(),
                    request.getMoneda(),
                    orden);
            return ResponseEntity.ok(Map.of("clientSecret", clientSecret));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error al procesar el pago: " + e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarPago(@RequestParam String paymentIntentId) {
        pagoService.confirmarPagoStripe(paymentIntentId);
        return ResponseEntity.ok().build();
    }
}