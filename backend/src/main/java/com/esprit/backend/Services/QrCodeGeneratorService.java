package com.esprit.backend.Services;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.esprit.backend.Entity.Reclamation;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
@Service
public class QrCodeGeneratorService {

    public byte[] generateQrCodeImage(Reclamation reclamation, int width, int height) throws IOException {
        // Récupérer les détails de la réclamation
        long reclamationId = reclamation.getIdReclamation();
        String firstname = reclamation.getUser().getFirstname();
        String lastname = reclamation.getUser().getLastname();
        Date dateCreation = reclamation.getDateCreation();
        String statReclamation = reclamation.getStatutReclamation().toString(); // Convertir le statut en String
String email = reclamation.getUser().getEmail();
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateCreationString = dateFormat.format(dateCreation);

        // Construire la chaîne de contenu à encoder dans le QR code, y compris le statut de la réclamation
        String content = "Reclamation ID: " + reclamationId + "\nFirstname: " + firstname + "\nLastname: " + lastname +"\nEmail: " + email + "\nDate Creation: " + dateCreationString + "\nStatut: " + statReclamation;

        // Créer le QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hintsMap = new HashMap<>();
        hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hintsMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code image.", e);
        }

        // Convertir l'image du QR code en tableau de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage qrImage = toBufferedImage(bitMatrix);
        try {
            ImageIO.write(qrImage, "png", outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write QR code image to output stream.", e);
        }

        return outputStream.toByteArray();
    }

    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }
        return image;
    }

}
