package com.tienda.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tienda.model.Pago;
import com.tienda.model.Orden;
import com.tienda.repository.PagoRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    // Se mantienen los métodos
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Transactional
    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Transactional
    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        return pagoRepository.findById(id).map(pagoExistente -> {
            pagoExistente.setMonto(pagoActualizado.getMonto());
            pagoExistente.setMetodo(pagoActualizado.getMetodo());
            pagoExistente.setEstado(pagoActualizado.getEstado());
            pagoExistente.setOrden(pagoActualizado.getOrden());
            return pagoRepository.save(pagoExistente);
        }).orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    @Transactional
    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }

    // Métodos nuevos para Stripe
    @PostConstruct
    public void initStripe() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Transactional
    public String crearIntentoPagoStripe(Double monto, String moneda, Orden orden) throws StripeException {
        long amountInCents = (long) (monto * 100);

        PaymentIntent intent = PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency(moneda.toLowerCase())
                        .putMetadata("ordenId", orden.getId().toString())
                        .build());

        Pago pago = new Pago();
        pago.setMonto(monto);
        pago.setMetodo("Stripe");
        pago.setIdTransaccion(intent.getId());
        pago.setEstado("Pendiente");
        pago.setOrden(orden);
        pagoRepository.save(pago);

        return intent.getClientSecret();
    }

    @Transactional
    public void confirmarPagoStripe(String paymentIntentId) {
        Pago pago = pagoRepository.findByIdTransaccion(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setEstado("Completado");
        pagoRepository.save(pago);
    }

    public Optional<Pago> obtenerPorIdTransaccion(String idTransaccion) {
        return pagoRepository.findByIdTransaccion(idTransaccion);
    }
}