package com.convivir.app.service;

import com.convivir.app.model.Pqr;
import com.convivir.app.model.Usuario; 
import com.convivir.app.repository.UsuarioRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarNotificacionEstado(Pqr pqr, String comentarioOficial) {
        try {
            String identificador = pqr.getResidenteId();
            Usuario residente = null;

            
            if (identificador != null && identificador.contains("@")) {
                
                residente = usuarioRepository.findByCorreo(identificador)
                        .orElse(null);
                
               
                if (residente == null) {
                    residente = new Usuario();
                    residente.setCorreo(identificador);
                }
            } else {
                
                residente = usuarioRepository.findById(identificador)
                        .orElseThrow(() -> new RuntimeException("Residente no encontrado con el ID: " + identificador));
            }

            
            String correoDestino = residente.getCorreo(); 

            if (correoDestino == null || correoDestino.isEmpty()) {
                throw new RuntimeException("El residente encontrado no tiene un correo electrónico válido configurado.");
            }

            
            SimpleMailMessage email = new SimpleMailMessage();
            
           
            email.setFrom("contacto@deltadevsystems.com"); 
            
            email.setTo(correoDestino);
            email.setSubject("CONVIVIRSMART - Actualización de tu PQR");
            email.setText("Estimado(a) residente,\n\n"
                    + "La administración de CONVIVIRSMART ha actualizado el estado de tu requerimiento.\n\n"
                    
                    + "📌 Asunto: " + pqr.getTitulo() + "\n"
                    + "📊 Nuevo Estado: " + pqr.getEstado() + "\n"
                    + "💬 Respuesta de la Administración:\n" + comentarioOficial + "\n"
                   
                    + "Atentamente,\n"
                    + "Administración CONVIVIRSMART");

            mailSender.send(email);
            System.out.println("✅ Correo enviado con éxito a: " + correoDestino);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
        }
    }
}