package com.gymdaus.core.service.impl;

import com.gymdaus.core.exception.EmptyException;
import com.gymdaus.core.model.*;
import com.gymdaus.core.service.GymActivityScheduleService;
import com.gymdaus.core.service.GymService;
import com.gymdaus.core.service.PdfService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service()
public class PdfServiceImpl implements PdfService {

    @Autowired
    private GymService gymService;
    @Autowired
    private GymActivityScheduleService gymActivityScheduleService;

    @Override
    public File createTournament(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {

        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), pdfModel, this.getClass());
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
            contentStream.newLineAtOffset( 100, page.getMediaBox().getHeight() - 80);
            if (pdfModel.isOwn()) {
                contentStream.showText("AUTORIZACIÓN DE MAYORES DE 18 AÑOS");
            } else if (pdfModel.isInclusive()) {
                contentStream.showText("AUTORIZACIÓN DE INSCRIPCIÓN INCLUSIVA");
            } else {
                contentStream.showText("AUTORIZACIÓN PARA MENORES DE 18 AÑOS");
            }
            contentStream.endText();

            List<String> paragraphList = new ArrayList<>();

            StringBuilder paragraph = new StringBuilder();
            int heightStartParagraph = 123;
            int salto = 33;
            int fontSize;
            paragraph.append("Yo ").append(pdfModel.getAuthorizerName()).append(" con DNI ").append(pdfModel.getAuthorizerIdCard());
            if (pdfModel.isOwn()) {
                paragraph.append(", fecha de nacimiento ").append(pdfModel.getAuthorizerBirthdate());
            } else {
                paragraph.append(", en calidad de ").append(pdfModel.getEnrollmentAs());
            }
            paragraph.append(" y domicilio en ").append(pdfModel.getAddress());
            paragraph.append(" en la localidad de ").append(pdfModel.getCity());

            if (!pdfModel.isOwn()) {

                paragraph.append(" AUTORIZO A ");
                paragraph.append(pdfModel.getMinorName()).append(!Utils.isNullOrEmpty(pdfModel.getMinorIdCard()) ? " con DNI " + pdfModel.getMinorIdCard() + " y" : "");
                paragraph.append(" con fecha de nacimiento ").append(pdfModel.getMinorBirthdate());
            }

            paragraph.append(" perteneciente a ").append(pdfModel.getGymName());
/*            paragraph.append(" actualmente con cinturón ").append(pdfModel.getCinturonActual()).append(" y categoría ").append(pdfModel.getCategoria());

            if (!pdfModel.isInclusive()) {
                paragraph.append(" (a dicha categoría le corresponde realizar: ").append(pdfModel.getPoomsae()).append(").");
            } else {
                paragraph.append(" (a dicha categoría le corresponde realizar el KICHO o POOMSAE que deseen).");
            }*/
            fontSize = 16;
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);

            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);

            heightStartParagraph += (paragraphList.size() * salto);
            salto = 15;
            fontSize = 14;
            paragraph = new StringBuilder();
            if (pdfModel.isOwn()) {
                paragraph.append("Informo que voy a participar en el ");
            } else {
                paragraph.append("Informo que AUTORIZO a participar en el ");
            }
            paragraph.append(pdfModel.getMoreRegistrationName()).append(" a celebrar el próximo ").append(pdfModel.getMoreRegistrationDate());
            paragraph.append(", en la dirección ").append(pdfModel.getMoreRegistrationAddress()).append(".");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), 14, null, true, false);

            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);

            heightStartParagraph += (paragraphList.size() * salto + 20);
            paragraph = new StringBuilder();
            paragraph.append("Por medio del presente escrito autorizo a los miembros de organización del ");
            paragraph.append("campeonato, la utilización de mi imagen en el país o en el extranjero por ");
            paragraph.append("cualquier medio ya sea impreso, electrónico o cualquier otro. ");
            paragraph.append("De igual manera, es mi deseo establecer que esta autorización es voluntaria y gratuita.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), 14, null, false, false);

            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);

            heightStartParagraph += (paragraphList.size() * salto + 10);
            paragraph = new StringBuilder();
            paragraph.append("En Cumplimento de la Ley Orgánica de Protección de Datos 15/1999, de ");
            paragraph.append("13 de Diciembre, indico que la información que facilito voluntariamente es ");
            paragraph.append("para la creación de un fichero al objeto de poder gestionar adecuadamente ");
            paragraph.append("el campeonato de taekwondo. Al facilitar mis datos, autorizo al Comité ");
            paragraph.append("Organizador a utilizar mis datos para realizar listados, sorteos, ");
            paragraph.append("publicaciones en medios u otros asuntos relacionados con el campeonato.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), 14, null, false, false);

            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);

/*            if (pdfModel.isCinturonBlanco()) {
                heightStartParagraph += (paragraphList.size() * salto + 10);
                paragraph = new StringBuilder();
                paragraph.append("Por otra parte, al no tener licencia federativa, eximo de toda ");
                paragraph.append("responsabilidad al comité organizador de cualquier lesión o daño que ");
                paragraph.append("se produjera el competidor o la competidora durante el evento o daños ");
                paragraph.append("que pudiera realizar a personas o material del pabellón.");
                paragraphList = organizaRenglones(paragraphList, paragraph.toString(), 14, null, true, false);

                generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
            }*/




            /* Image
             PDImageXObject image = PDImageXObject.createFromFile("src/main/java/com/damian/objetivos/util/400.jpg", document);
             contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);
             */

            contentStream.close();
            return saveFile(document, pdfModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), e.getMessage(), PdfServiceImpl.class);
        }
        return null;

    }

    @Override
    public File createFederativeLicenseMandate(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {

        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), pdfModel, getClass());
        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            int heightStartParagraph = commonFederativeLicenseMandate(pdfModel, contentStream, page);

            if(withSignature) {
                commonSignature(pdfModel, contentStream, page, heightStartParagraph);
                heightStartParagraph += 85;

                List<String> paragraphList = new ArrayList<>();
                StringBuilder paragraph = new StringBuilder();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.newLineAtOffset( 80, page.getMediaBox().getHeight() - heightStartParagraph);
                contentStream.showText("----------------------------------------------------------------------------");
                contentStream.endText();
                heightStartParagraph += 30;

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.newLineAtOffset( 100, page.getMediaBox().getHeight() - heightStartParagraph);
                contentStream.showText("A rellenar por el MANDATARIO");
                contentStream.endText();
                heightStartParagraph += 30;

                //salto = 15;fontSize = 14;
                paragraph.append("Acepto el MANDATO conferido y me obligo a cumplirlo de conformidad a las instrucciones del MANDANTE, y declaro ");
                paragraph.append("bajo mi responsabilidad de la veracidad y actualización de los datos facilitados para la inscripción federativa.");
                paragraphList = lineOrganizer(paragraphList, paragraph.toString(), 14, 180.0, false, false);
                generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, 14, null, 15);
                heightStartParagraph += (paragraphList.size() * 35);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.newLineAtOffset( 100, page.getMediaBox().getHeight() - heightStartParagraph);
                contentStream.showText("En                  , a      de                de");
                contentStream.endText();


            /* Image
             PDImageXObject image = PDImageXObject.createFromFile("src/main/java/com/damian/objetivos/util/400.jpg", document);
             contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);
             */

            }

            contentStream.close();
/*            DocumentManagerModel documentManagerModel = new DocumentManagerModel();
            documentManagerModel.setNeedsSignature(Boolean.TRUE);
            nombreArchivo(documentManagerModel, pdfModel, true, Constantes.SECCION_MANDATO);
            return createFileAndSaveDM(document, documentManagerModel, createWithSignatureOrCreateFinalDocument);*/
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), e.getMessage(), PdfServiceImpl.class);
        }
        return null;

    }

    @Override
    public File createAuthorizationAdult(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {
        return commonCreateDocument(pdfModel, withSignature, true);
    }

    @Override
    public File createAuthorizationAuthorized(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {
        return commonCreateDocument(pdfModel, withSignature, false);
    }

    @Override
    public File createSepaDirectDebitForm(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {

        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), pdfModel, this.getClass());
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            List<String> paragraphList = new ArrayList<>();

            StringBuilder paragraph;
            int heightStartParagraph = 40;
            int salto;
            int fontSize;
            int dejoDeMargenPosterior = 0;
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy");
            String[] hoy = sdf.format(calendar.getTime()).split("/");
            // Definir las coordenadas y dimensiones del cuadrado
            final float x = 45;
            final float width = 510;

            salto = 18;
            fontSize = 20;
            paragraphList.add(pdfModel.getGymName().toUpperCase());
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, 170, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            salto = 22;
            fontSize = 16;
            paragraphList = new ArrayList<>();
            paragraphList.add("TRATAMIENTO DE DATOS DE CLIENTES");
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, 135, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            salto = 20;
            fontSize = 20;
            paragraphList = new ArrayList<>();
            paragraphList.add("NORMATIVA SEPA");
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, 205, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            //salto = 20;fontSize = 20;
            dejoDeMargenPosterior = 20;
            paragraphList = new ArrayList<>();
            paragraphList.add("MANDATO ADEUDO DIRECTO SEPA");
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, 120, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            salto = 15;
            fontSize = 14;
            dejoDeMargenPosterior = 10;
            paragraphList = new ArrayList<>();
            paragraphList.add("DATOS DEL DEPORTISTA (Quien entrena en el gimnasio)");
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            if (!Utils.isNullOrEmpty(pdfModel.getMinorName())) {

                //salto = 15;fontSize = 14;
                dejoDeMargenPosterior = 20;
                paragraphList = new ArrayList<>();
                paragraphList.add("Nombre: " + pdfModel.getMinorName());
                if(!Utils.isNullOrEmpty(pdfModel.getMinorIdCard())) {
                    paragraphList.add("DNI: " + pdfModel.getMinorIdCard() + "              " + "Fecha de nacimiento: " + pdfModel.getMinorBirthdate());
                } else {
                    paragraphList.add("Fecha de nacimiento: " + pdfModel.getMinorBirthdate());
                }
                generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
                heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

                frameParagraph(contentStream, page, paragraphList, heightStartParagraph, salto, dejoDeMargenPosterior, x, width, 0);

                //salto = 15;fontSize = 14;
                dejoDeMargenPosterior = 10;
                paragraph = new StringBuilder();
                paragraph.append("PERSONA AUTORIZADORA EN CALIDAD DE ").append(pdfModel.getEnrollmentAs().toUpperCase()).append(" (Para deportistas menores de edad)");
                paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
                generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
                heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            }

            heightStartParagraph = fillAdult(pdfModel, heightStartParagraph, contentStream, page);

            salto = 20;
            fontSize = 20;
            paragraph = new StringBuilder();
            paragraph.append("DATOS BANCARIOS");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, 205, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            salto = 15;
            fontSize = 14;
            dejoDeMargenPosterior = 20;
            paragraphList = new ArrayList<>();
            paragraphList.add("Titular: " + (pdfModel.isSepaDirectDebit() ? pdfModel.getSepaAccountPerson() : "Constants.ERROR_DATOS_BANCARIOS"));
            paragraphList.add("IBAN: " + (pdfModel.isSepaDirectDebit() ? pdfModel.getSepaAccountNumber() : "Constants.ERROR_DATOS_BANCARIOS"));
            paragraphList.add("CÓDIGO SWIFT/BIC: " + (pdfModel.isSepaDirectDebit() ? pdfModel.getSwift() : "Constants.ERROR_DATOS_BANCARIOS"));
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            frameParagraph(contentStream, page, paragraphList, heightStartParagraph, salto, dejoDeMargenPosterior, x, width, 0);

            float heightAgregado = 0;
            salto = 12;
            fontSize = 12;
            dejoDeMargenPosterior = 0;
            paragraph = new StringBuilder();
            paragraph.append("En nombre de la empresa tratamos la información que nos facilita con el fin de prestarles el servicio solicitado, ");
            paragraph.append("realizar la facturación del mismo. Los datos proporcionados se conservarán mientras se ");
            paragraph.append("mantenga la relación comercial o durante los años necesarios para cumplir con las obligaciones legales. ");
            paragraph.append("Los datos no se cederán a terceros salvo en los casos en que exista una obligación legal. Usted tiene ");
            paragraph.append("derecho a obtener confirmación sobre si en ".concat(pdfModel.getGymName()).concat(" estamos tratando sus datos "));
            paragraph.append("personales por tanto tiene derecho a acceder a sus datos personales, rectificar los datos inexactos o ");
            paragraph.append("solicitar su supresión cuando los datos ya no sean necesarios.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);
            heightAgregado += (paragraphList.size() * salto) + dejoDeMargenPosterior;

            //salto = 12;fontSize = 12;
            dejoDeMargenPosterior = 15;
            paragraph = new StringBuilder();
            paragraph.append("Asimismo solicito su autorización para ofrecerle productos y servicios relacionados con los solicitados y ");
            paragraph.append("fidelizarle como cliente.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);
            heightAgregado += (paragraphList.size() * salto) + dejoDeMargenPosterior;

            squareSelection(contentStream, page, heightStartParagraph);
            salto = 15;
            fontSize = 14;
            dejoDeMargenPosterior = 0;
            paragraph = new StringBuilder();
            paragraph.append("  SI");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);
            heightAgregado += (paragraphList.size() * salto) + dejoDeMargenPosterior;

            squareSelection(contentStream, page, heightStartParagraph);
            //salto = 15;fontSize = 14;
            paragraph = new StringBuilder();
            paragraph.append("NO");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);
            heightAgregado += (paragraphList.size() * salto) + dejoDeMargenPosterior;

            //salto = 25;fontSize = 14;
            dejoDeMargenPosterior = 30;
            paragraph = new StringBuilder();
            paragraph.append("Por favor indique SI o NO");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            frameParagraph(contentStream, page, paragraphList, heightStartParagraph, salto, dejoDeMargenPosterior, x, width, heightAgregado);

            //salto = 15;fontSize = 14;
            paragraph = new StringBuilder();
            paragraph.append("Por la presente autorizo a los titulares de ".concat(pdfModel.getGymName()).concat(" a enviar instrucciones a la "));
            paragraph.append("entidad arriba indicada para efectuar los adeudos en su cuenta bancaria y me comprometo a ");
            paragraph.append("realizar los pagos en los plazos establecidos responsabilizándome de los costes generados en ");
            paragraph.append("caso de devolución.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

            //salto = 35;
            fontSize = 18;
            paragraph = new StringBuilder();
            paragraph.append("Fecha ").append(hoy[0]).append(" de ").append(hoy[1]).append(" de ").append(hoy[2]).append("           FIRMA:");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
            //heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

            /* Image
             PDImageXObject image = PDImageXObject.createFromFile("src/main/java/com/damian/objetivos/util/400.jpg", document);
             contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);
             */

            contentStream.close();

            return saveFile(document, pdfModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), e.getMessage(), PdfServiceImpl.class);
        }
        return null;
    }

    @Override
    public File createWhatsAppAuthorization(PdfModel pdfModel, boolean withSignature, MessageSource messageSource, Locale locale) {

        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), pdfModel, this.getClass());
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            int heightStartParagraph;
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            heightStartParagraph = commonAuthorizationWhatsApp(pdfModel, contentStream, page);
            if(withSignature) {
                commonSignature(pdfModel, contentStream, page, heightStartParagraph);
            }
            contentStream.close();
            return saveFile(document, pdfModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), e.getMessage(), PdfServiceImpl.class);
        }
        return null;
    }

    /**
     *
     *
     *
     * INFORMACIÓN valores:
     * Constantes.SECCION_TORNEO
     * Constantes.SECCION_MANDATO
     * Constantes.SECCION_AUTORIZACION_MAYOR_18
     * Constantes.SECCION_AUTORIZACION_MENOR_18
     * Constantes.SECCION_NORMATIVA_SEPA
     */
    /*@Override
    public void descargarArchivo(PdfModel pdfModel, HttpServletResponse response, String seccion) {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + nombreArchivo(null, pdfModel, false, seccion);
        response.setHeader(headerKey, headerValue);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] file = Files.readAllBytes(Paths.get(nombreArchivo(null, pdfModel, true, seccion)));
            outputStream.write(file, 0, file.length);
            outputStream.close();
        } catch (IOException e) {
            LoggerMapper.log(Level.ERROR, Utils.obtenerNombreMetodo(), e.getMessage(), PdfServiceImpl.class);
        }
    }
    @Override
    public boolean subirArchivo(PdfModel pdfModel, MultipartFile file, String seccion) {
        boolean answer = false;
        if (!file.isEmpty()) {
            try {
                pdfModel.setExtension(getFileExtension(file));
                DocumentManagerModel documentManagerModel = new DocumentManagerModel();
                nombreArchivo(documentManagerModel, pdfModel, true, seccion);
                file.transferTo(new File(documentManagerService.getAbsolutePath() + documentManagerModel.getFullPath()));
                documentManagerService.add(documentManagerModel);
                answer = true;
            } catch (IOException e) {
                LoggerMapper.log(Level.ERROR, Utils.obtenerNombreMetodo(), e.getMessage(), PdfServiceImpl.class);
            }
        }
        return answer;
    }*/

    @Override
    public PdfModel getPdfModel (EnrollmentModel enrollmentModel, MessageSource messageSource, Locale locale) {

        PdfModel pdfModel = new PdfModel();
        GymModel gymModel = gymService.findById(enrollmentModel.getGymModel().getId());
        GymActivityScheduleModel gymActivityScheduleModel = gymActivityScheduleService.findById(enrollmentModel.getGymActivityScheduleModel().getId());
        GymAddressModel gymAddressModel = gymActivityScheduleModel.getGymAddressModel();
        pdfModel.setGymId(enrollmentModel.getGymModel().getId());
        pdfModel.setEnrollmentId(enrollmentModel.getId());
        pdfModel.setAuthorizerName(enrollmentModel.getAuthorizerEnrollmentName() + " " + enrollmentModel.getAuthorizerEnrollmentLastname()
                + (enrollmentModel.getAuthorizerEnrollmentSecondLastname() != null ? " " + enrollmentModel.getAuthorizerEnrollmentSecondLastname() : ""));
        pdfModel.setAuthorizerIdCard(enrollmentModel.getAuthorizerEnrollmentIdCard());
        pdfModel.setPhone(enrollmentModel.getPhone());
        pdfModel.setEmail(enrollmentModel.getEmail());
        pdfModel.setAuthorizerBirthdate(Utils.date2String(enrollmentModel.getAuthorizerEnrollmentBirthdate()));
        pdfModel.setGymName(gymModel.getName());
        pdfModel.setGymAddress(gymAddressModel.getAddressStreet() + " "
                + (Utils.isNullOrEmpty(gymAddressModel.getAddressNumber()) ? "" : gymAddressModel.getAddressNumber() + " ")
                + (Utils.isNullOrEmpty(gymAddressModel.getAddressOther()) ? "" : gymAddressModel.getAddressOther() + " ")
                + gymAddressModel.getAddressCity() + " (" + gymAddressModel.getAddressZip() + "), "
                + messageSource.getMessage(enrollmentModel.getAddressCountry(), null, locale));
        pdfModel.setGymEmail(enrollmentModel.getGymActivityModel().getGymAddressModel().getEmail());
        if (!Utils.isNullOrEmpty(enrollmentModel.getAuthorizerEnrollmentAddressStreet())) {
            pdfModel.setAddress(enrollmentModel.getAuthorizerEnrollmentAddressStreet() + " " + enrollmentModel.getAuthorizerEnrollmentAddressNumber()
                    + " " + enrollmentModel.getAuthorizerEnrollmentAddressOther());
            pdfModel.setCity(enrollmentModel.getAuthorizerEnrollmentAddressCity() + " (" + enrollmentModel.getAuthorizerEnrollmentAddressZip()
                    + "), " + messageSource.getMessage(enrollmentModel.getAuthorizerEnrollmentAddressCountry(), null, locale));
        }
        if (!Utils.isNullOrEmpty(enrollmentModel.getAuthorizerEnrollmentAs())) {
            pdfModel.setEnrollmentAs(messageSource.getMessage(enrollmentModel.getAuthorizerEnrollmentAs(), null, locale));
            pdfModel.setMinorName(enrollmentModel.getUserEnrollmentName() + " " + enrollmentModel.getUserEnrollmentLastname()
                    + (enrollmentModel.getUserEnrollmentSecondLastname() != null ? " " + enrollmentModel.getUserEnrollmentSecondLastname() : ""));
            pdfModel.setMinorIdCard(enrollmentModel.getUserEnrollmentIdCard());
            pdfModel.setMinorBirthdate(Utils.date2String(enrollmentModel.getUserEnrollmentBirthdate()));
        }
        pdfModel.setOwn(enrollmentModel.isOwn());
        pdfModel.setMinor(enrollmentModel.isMinor());
        pdfModel.setInclusive(enrollmentModel.isInclusive());
        if (enrollmentModel.isSepaDirectDebit()) {
            pdfModel.setSepaAccountPerson(enrollmentModel.getSepaAccountPerson());
            pdfModel.setSepaAccountNumber(enrollmentModel.getSepaAccountNumber());
            pdfModel.setSwift(enrollmentModel.getSwift());
        }
        pdfModel.setActivityName(messageSource.getMessage(enrollmentModel.getActivityName(), null, locale));
        pdfModel.setSigned(enrollmentModel.isSigned());
        pdfModel.setSignedDate(Utils.date2String(enrollmentModel.getSignedDate()));

        return pdfModel;
    }

    /*@Override
    public void deleteFilesTaekwondoRegistration(InscripcionTaekwondoModel inscripcionTaekwondoModel, User usuario) {
        if (inscripcionTaekwondoModel.isAutorizadoMenor()) {
            documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(inscripcionTaekwondoModel.getId(), Constantes.SECCION_AUTORIZACION_MENOR_18, usuario.getUsername());
        } else {
            documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(inscripcionTaekwondoModel.getId(), Constantes.SECCION_AUTORIZACION_MAYOR_18, usuario.getUsername());
        }
        if (inscripcionTaekwondoModel.isDomiciliacionSEPA()) {
            if (inscripcionTaekwondoModel.isDomiciliacionSEPAFirmada()) {
                documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(inscripcionTaekwondoModel.getId(), Constantes.SECCION_NORMATIVA_SEPA_FIRMADO, usuario.getUsername());
            } else {
                documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(inscripcionTaekwondoModel.getId(), Constantes.SECCION_NORMATIVA_SEPA, usuario.getUsername());
            }
        }
        if (inscripcionTaekwondoModel.isMayorAutorizaWhatsApp()) {
            documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(inscripcionTaekwondoModel.getId(), Constantes.SECCION_WHATSAPP, usuario.getUsername());
        }
    }*/

    /*@Override
    public void deleteByIdOriginalOperativeAndSectionAndIdCard(Integer idOriginalOperative, String section, String idCard) {
        documentManagerService.deleteByIdOriginalOperativeAndSectionAndIdCard(idOriginalOperative, section, idCard);
    }

    @Override
    public void eraseByIdOriginalOperativeAndSectionAndIdCard(Integer idOriginalOperative, String section, String idCard) {
        documentManagerService.eraseByIdOriginalOperativeAndSectionAndIdCard(idOriginalOperative, section, idCard);
    }*/


    private File commonCreateDocument (PdfModel pdfModel, boolean withSignature, boolean over18) {

        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), pdfModel, this.getClass());
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            int heightStartParagraph;
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            if (over18) {
                heightStartParagraph = commonAuthorizationOver18(pdfModel, contentStream, page);
            } else  {
                heightStartParagraph = commonAuthorizationUnder18(pdfModel, contentStream, page);
            }
            if(withSignature) {
                commonSignature(pdfModel, contentStream, page, heightStartParagraph);
            }
            contentStream.close();
            return saveFile(document, pdfModel);
        } catch (Exception e) {
            LoggerMapper.log(Level.ERROR, Utils.getMethodName(), e.getMessage(), PdfServiceImpl.class);
        }
        return null;
    }

    private File saveFile (PDDocument document, PdfModel pdfModel) throws IOException {
        String fullPath = Utils.getAbsolutePath() + Utils.getTempFolder() + "document" + pdfModel.getEnrollmentId()
                + Utils.formatFullDate2String(new Date()) + ".pdf";
        document.save(fullPath);
        return new File(fullPath);
    }

    private int fillAdult (PdfModel pdfModel, int heightStartParagraph, PDPageContentStream contentStream, PDPage page) throws IOException, EmptyException {

        int salto = 15;
        int fontSize = 14;
        int dejoDeMargenPosterior = 20;
        List<String> paragraphList = new ArrayList<>();
        paragraphList.add("Nombre: " + pdfModel.getAuthorizerName());
        if(pdfModel.getAuthorizerBirthdate() != null) {
            paragraphList.add("DNI: " + pdfModel.getAuthorizerIdCard() + "                 " + "Fecha de nacimiento: " + pdfModel.getAuthorizerBirthdate());
        } else {
            paragraphList.add("DNI: " + pdfModel.getAuthorizerIdCard());
        }
        paragraphList = lineOrganizer(paragraphList, "Dirección: " + pdfModel.getAddress() + " - "
                + pdfModel.getCity(), fontSize, null, false, true);
        paragraphList = lineOrganizer(paragraphList, "Teléfono: " + pdfModel.getPhone() + "          "
                + "Email: " + pdfModel.getEmail(), fontSize, null, false, true);

        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);

        heightStartParagraph += (paragraphList.size() * salto + dejoDeMargenPosterior);

        frameParagraph(contentStream, page, paragraphList, heightStartParagraph, salto, dejoDeMargenPosterior, 45, 510, 0);

        return heightStartParagraph;
    }

    private void generateParagraph(PDPageContentStream contentStream, PDPage page, List<String> paragraphList,
                                   int pageHeight, PDType1Font font, int fontSize, Integer rightMargin, int lineBreak) throws IOException {
        int i = 0;
        for (String paragraph: paragraphList) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset( (rightMargin == null ? 50 : rightMargin), page.getMediaBox().getHeight() - (pageHeight + i));
            contentStream.showText(paragraph);
            contentStream.endText();
            i=i+lineBreak;
        }
    }


    private List<String> lineOrganizer(List<String> paragraphList, String paragraph, int fontSize,
                                       Double paragraphWidthMillimeters, boolean bold, boolean add) throws EmptyException {

        if(!add) {
            paragraphList = new ArrayList<>();
        }
        StringBuilder line = new StringBuilder();
        final double dotInMillimeters = (bold ? 0.33 : 0.30);
        List<String> fontSize1 = Arrays.asList("i_í_I_Í_l_._-_:_\"_'_,_;_¡_!_º_ª_(_)".split("_"));
        List<String> fontSize4 = Arrays.asList("m_w_M_W".split("_"));
        if(paragraphWidthMillimeters == null || paragraphWidthMillimeters <= 0.0 || paragraphWidthMillimeters > 175.0) {
            paragraphWidthMillimeters = 175.0;
        }
        if (paragraph == null || paragraph.isEmpty()) {
            throw new EmptyException("EMPTY", "lineOrganizer - Object paragraph empty");
        }
        String[] words = paragraph.split(" ");
        double usedParagraph = 0.0;
        double sizeNewWord;
        double addCapitalLetter;
        for(String word: words) {
            sizeNewWord = 2.0; //sumo el espacio final que sería 2.0
            String letter;
            for (int i=0; i<word.length(); i++) {
                letter = word.substring(i, i + 1);
                addCapitalLetter = (letter.equals(letter.toUpperCase()) ? dotInMillimeters * 0.5 : 0.0);
                if (fontSize1.contains(letter)) {
                    sizeNewWord += (double) fontSize /4 * (dotInMillimeters + addCapitalLetter);
                } else if (fontSize4.contains(letter)) {
                    sizeNewWord += fontSize * (dotInMillimeters + addCapitalLetter);
                } else {
                    sizeNewWord += (double) fontSize /2 * (dotInMillimeters + addCapitalLetter);
                }
            }
            if (usedParagraph + sizeNewWord > paragraphWidthMillimeters) {
                paragraphList.add(line.toString());
                line = new StringBuilder();
                usedParagraph = sizeNewWord;
            } else {
                usedParagraph += sizeNewWord;
            }
            line.append(word.concat(" "));
        }
        paragraphList.add(line.toString());
        return paragraphList;
    }

    private void frameParagraph(PDPageContentStream contentStream, PDPage page, List<String> paragraphList, int heightStartParagraph, int lineBreak,
                                int marginBehind, float x, float width, float heightAdded) throws IOException {

        // Draw the square
        contentStream.addRect(x, page.getMediaBox().getHeight() - heightStartParagraph + marginBehind + 10,
                width, paragraphList.size() * lineBreak + 5 + heightAdded);
        contentStream.setLineWidth(2); // Border width
        contentStream.setStrokingColor(0, 0, 0); // Border color (black)
        contentStream.stroke();
    }

    private void squareSelection(PDPageContentStream contentStream, PDPage page, int heightStartParagraph) throws IOException {

        // Draw the square x=45f + 30f, width=10f, height=10f
        contentStream.addRect(75f, page.getMediaBox().getHeight() - heightStartParagraph, 10f, 10f);
        contentStream.setLineWidth(1); // Border width
        contentStream.setStrokingColor(0, 0, 0); // Border color (black)
        contentStream.stroke();
    }

    private int commonFederativeLicenseMandate(PdfModel pdfModel, PDPageContentStream contentStream, PDPage page) throws IOException, EmptyException {

        // Text
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
        contentStream.newLineAtOffset( 130, page.getMediaBox().getHeight() - 50);
        contentStream.showText("Mandato específico para la inscripción federativa");
        contentStream.endText();

        List<String> paragraphList = new ArrayList<>();

        StringBuilder paragraph;
        int heightStartParagraph = 90;
        int salto;
        int fontSize;
        String nombreMenor = "";
        Calendar calendar = GregorianCalendar.getInstance();

        if(!Utils.isNullOrEmpty(pdfModel.getEnrollmentAs())) {
            nombreMenor = " (" + pdfModel.getMinorName() + ")";
            salto = 15;
            fontSize = 14;
            paragraph = new StringBuilder();
            paragraph.append("Para los menores o solicitud inclusiva, son los datos de");
            paragraph.append(pdfModel.getEnrollmentAs().equalsIgnoreCase("Madre") ? " la " : "l ");
            paragraph.append(pdfModel.getEnrollmentAs()).append(" y entre paréntesis el nombre del deportista.");
            paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
            generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
            heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);
        }

        salto = 15;
        fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("D./Dña. ").append(pdfModel.getAuthorizerName()).append(nombreMenor).append(" con DNI ").append(pdfModel.getAuthorizerIdCard()).append(", en su propio ");
        paragraph.append("nombre y representación, con domicilio a efectos de notificaciones en ").append(pdfModel.getAddress()).append(" ");
        paragraph.append(pdfModel.getCity()).append(" en concepto de MANDANTE.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Dice y otorga");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Que confiere MANDATO CON REPRESENTACIÓN a favor del representante de ".concat(pdfModel.getGymName()).concat(" con domicilio "));
        paragraph.append("en ".concat(pdfModel.getGymAddress()).concat(", en concepto de MANDATARIO."));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Que el presente MANDATO, que se rige por los arts. 1709 a 1739 CC español se confiere para que se pueda llevar ");
        paragraph.append("a cabo la inscripción federativa del MANDANTE en la temporada ").append(Utils.calculateSeason(calendar.getTime()));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Que el presente MANDATO se confiere para su actuación ante las dependencias federativas, personalmente o ");
        paragraph.append("a través de recursos online, en relación exclusivamente del asunto citado como objeto del MANDATO.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Que este MANDATO tiene exclusiva vigencia para la inscripción federativa, finalizando la misma en el ");
        paragraph.append("momento en que se produzca la inscripción.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Que conoce y acepta el tratamiento de datos que llevará a cabo la federación, la cual legitima el mismo con ");
        paragraph.append("los requisitos normativos que cumplimenta el MANDATARIO por efecto de este MANDATO.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        return heightStartParagraph;
    }

    private int commonAuthorizationOver18(PdfModel pdfModel, PDPageContentStream contentStream, PDPage page) throws IOException, EmptyException {

        // Text
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
        contentStream.newLineAtOffset( 110, page.getMediaBox().getHeight() - 50);
        contentStream.showText("AUTORIZACIÓN DE MAYORES DE 18 AÑOS");
        contentStream.endText();

        List<String> paragraphList = new ArrayList<>();

        StringBuilder paragraph;
        int heightStartParagraph = 90;
        int salto;
        int fontSize;

        salto = 20;
        fontSize = 18;
        paragraph = new StringBuilder();
        paragraph.append("ACTIVIDAD: ".concat(pdfModel.getGymName().toUpperCase()));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        salto = 15;
        fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Yo D./Dña. ").append(pdfModel.getAuthorizerName()).append(" con DNI ").append(pdfModel.getAuthorizerIdCard());
        paragraph.append(", fecha de nacimiento ").append(pdfModel.getAuthorizerBirthdate()).append(" y domicilio en ").append(pdfModel.getAddress()).append(" ");
        paragraph.append(pdfModel.getCity().concat(" perteneciente a ").concat(pdfModel.getGymName()));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("INFORMO QUE ENTRENO EN ".concat(pdfModel.getGymName().toUpperCase()).concat(" Y PARTICIPO VOLUNTARIAMENTE EN LOS CAMPEONATOS Y "));
        paragraph.append("ENTRENAMIENTOS QUE PARTICIPEN LOS ALUMNOS DE ".concat(pdfModel.getGymName().toUpperCase()).concat("."));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Por medio del presente escrito autorizo a los miembros de ".concat(pdfModel.getGymName()).concat(", a la utilización "));
        paragraph.append("de mi imagen en el país o en el extranjero por cualquier medio ya sea impreso, electrónico ");
        paragraph.append("o cualquier otro. De igual manera, es mi deseo establecer que esta autorización es voluntaria ");
        paragraph.append("y gratuita.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("En cumplimiento con la Ley Orgánica de Protección de Datos 15/1999, de 13 de Diciembre, ");
        paragraph.append("indico que la información que facilito voluntariamente es para la creación de un fichero al ");
        paragraph.append("objeto de poder gestionar adecuadamente los datos. Al facilitar mis datos, autorizo a ");
        paragraph.append(pdfModel.getGymName().concat(" a utilizar mis datos para realizar listados, sorteos, publicaciones "));
        paragraph.append("en medios y otros asuntos relacionados con ".concat(pdfModel.getGymName()).concat("."));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Por otra parte, eximo de toda responsabilidad a ".concat(pdfModel.getGymName()).concat(" de cualquier lesión "));
        paragraph.append("o daño que se produjera el alumno o la alumna durante los entrenamientos y campeonatos ");
        paragraph.append("a los que acuda o daños que pudiera realizar a personas o materiales.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        return heightStartParagraph;
    }

    private int commonAuthorizationUnder18(PdfModel pdfModel, PDPageContentStream contentStream, PDPage page) throws IOException, EmptyException {

        // Text
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
        contentStream.newLineAtOffset( 110, page.getMediaBox().getHeight() - 50);
        contentStream.showText("AUTORIZACIÓN DE MENORES DE 18 AÑOS");
        contentStream.endText();

        List<String> paragraphList = new ArrayList<>();

        StringBuilder paragraph;
        int heightStartParagraph = 90;
        int salto;
        int fontSize;

        salto = 15;
        fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Yo D./Dña. ").append(pdfModel.getAuthorizerName()).append(" con DNI ").append(pdfModel.getAuthorizerIdCard()).append(" ");
        paragraph.append("en calidad de ").append(pdfModel.getEnrollmentAs()).append(" y domicilio en ").append(pdfModel.getAddress()).append(" ");
        paragraph.append(pdfModel.getCity());
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("INFORMO QUE AUTORIZO A ENTRENAR EN ".concat(pdfModel.getGymName()).concat(" Y A PARTICIPAR EN LOS CAMPEONATOS Y "));
        paragraph.append("ENTRENAMIENTOS QUE PARTICIPEN LOS ALUMNOS DE ".concat(pdfModel.getGymName()).concat(" A:"));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, true, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("D./Dña. ").append(pdfModel.getMinorName());
        paragraph.append(!Utils.isNullOrEmpty(pdfModel.getMinorIdCard()) ? " con DNI " + pdfModel.getMinorIdCard() + " y " : " ");
        paragraph.append("con fecha de nacimiento ").append(pdfModel.getMinorBirthdate());
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Por medio del presente escrito autorizo a los miembros de ".concat(pdfModel.getGymName()).concat(", a la utilización "));
        paragraph.append("de la imagen de ").append(pdfModel.getMinorName()).append(" en el país o en el extranjero ");
        paragraph.append("por cualquier medio ya sea impreso, electrónico o cualquier otro. De igual manera, es mi ");
        paragraph.append("deseo establecer que esta autorización es voluntaria y gratuita.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("En cumplimiento con la Ley Orgánica de Protección de Datos 15/1999, de 13 de Diciembre, ");
        paragraph.append("indico que la información que facilito voluntariamente es para la creación de un fichero al ");
        paragraph.append("objeto de poder gestionar adecuadamente los datos. Al facilitar mis datos, autorizo a ");
        paragraph.append(pdfModel.getGymName().concat(" a utilizar mis datos para realizar listados, sorteos, publicaciones "));
        paragraph.append("en medios y otros asuntos relacionados con ".concat(pdfModel.getGymName()).concat("."));
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Por otra parte, eximo de toda responsabilidad a ".concat(pdfModel.getGymName()).concat(" de cualquier lesión "));
        paragraph.append("o daño que se produjera el alumno o la alumna durante los entrenamientos y campeonatos ");
        paragraph.append("a los que acuda o daños que pudiera realizar a personas o materiales.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_BOLD, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        return heightStartParagraph;
    }

    private int commonAuthorizationWhatsApp(PdfModel pdfModel, PDPageContentStream contentStream, PDPage page) throws IOException, EmptyException {


        // Text
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
        contentStream.newLineAtOffset( 130, page.getMediaBox().getHeight() - 50);
        contentStream.showText("AUTORIZACIÓN GRUPO DE WHATSAPP");
        contentStream.endText();

        List<String> paragraphList = new ArrayList<>();

        StringBuilder paragraph;
        int heightStartParagraph = 90;
        int salto;
        int fontSize;

        salto = 15;
        fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Yo D./Dña. ").append(pdfModel.getAuthorizerName()).append(" con DNI ").append(pdfModel.getAuthorizerIdCard()).append(" y domicilio en ");
        paragraph.append(pdfModel.getAddress()).append(" ").append(pdfModel.getCity());
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("Por medio del presente escrito autorizo a los miembros de ".concat(pdfModel.getGymName()).concat(", a incluirme y "));
        paragraph.append("pertenecer al GRUPO DE WHATSAPP del gimnasio, para recibir las informaciones y publicaciones que envíen.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        //salto = 15;fontSize = 14;
        paragraph = new StringBuilder();
        paragraph.append("De igual manera, es mi deseo establecer que esta autorización es voluntaria y gratuita.");
        paragraphList = lineOrganizer(paragraphList, paragraph.toString(), fontSize, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, fontSize, null, salto);
        heightStartParagraph += (paragraphList.size() * salto + Constants.PARAGRAPH_LINE_BREAK);

        return heightStartParagraph;
    }

    private void commonSignature(PdfModel pdfModel, PDPageContentStream contentStream, PDPage page, int heightStartParagraph) throws IOException, EmptyException {

        String paragraph = "Y para que conste dejo firmado de forma electrónica este documento con fecha " + pdfModel.getSignedDate();
        List<String> paragraphList = lineOrganizer(null, paragraph, 14, null, false, false);
        generateParagraph(contentStream, page, paragraphList, heightStartParagraph, PDType1Font.TIMES_ROMAN, 14, null, 15);
    }

}
