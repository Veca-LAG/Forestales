package com.example.forestales;

import java.util.Date;

public class FormData {

    // Singleton
    private static final FormData instance = new FormData();

    // Campos de ejemplo de arboles
    //MainActivity
    public int numeroAcceso;
    public String nombreFamilia;
    public String nombreComun;
    public String nombreCientificoGenero;
    public String nombreCientificoEspecie;
    public boolean especieOriginaria;
    public String ecologiaDistribucion;
    public String clasificaionTaxonomica;
    public String coordenadas;

    //MainActivity2
    public Date fecha;
    public String habitoCrecimiento;
    public String tipoCrecimiento;
    public float altura;
    public String medirAltura;
    public float diametroFuste;
    public String medirDiametroFuste;
    public String estadoSalud;

    public String disturbiosMeteorologicos;
    public String interacciones;
    public String organismoInteracciones;
    public boolean presenciaCombustibles;
    public String combustiblesFinos;
    public String combustiblesPesados;
    public String peligroIncendio;

    //MainActivity3
    public int hojas;
    public String estadoHojas;
    public int flores;
    public int frutos;
    public String estadoFrutos;
    public String Ramas;
    public String corteza;
    public String usos;
    public String observaciones;

    // BLOBs de imágenes y extensiones
    public byte[] archivoHojas;
    public String extensionHojas;
    public byte[] archivoFlores;
    public String extensionFlores;
    public byte[] archivoFrutos;
    public String extensionFrutos;
    public byte[] archivoRamas;
    public String extensionRamas;
    public byte[] archivoCorteza;
    public String extensionCorteza;
    public byte[] archivoGeneral;
    public String extensionGeneral;

    private FormData() { }

    public static FormData getInstance() {
        return instance;
    }

    // Builder interno para crear un objeto final RegistroCompleto
    public RegistroCompleto build() {
        RegistroCompleto r = new RegistroCompleto();
        // MainActivity
        r.numeroAcceso = numeroAcceso;
        r.nombreFamilia = nombreFamilia;
        r.nombreComun = nombreComun;
        r.nombreCientificoGenero = nombreCientificoGenero;
        r.nombreCientificoEspecie = nombreCientificoEspecie;
        r.especieOriginaria = especieOriginaria;
        r.ecologiaDistribucion = ecologiaDistribucion;
        r.clasificaionTaxonomica = clasificaionTaxonomica;
        r.coordenadas = coordenadas;

        // MainActivity2
        r.fecha = fecha;
        r.habitoCrecimiento = habitoCrecimiento;
        r.tipoCrecimiento = tipoCrecimiento;
        r.altura = altura;
        r.medirAltura = medirAltura;
        r.diametroFuste = diametroFuste;
        r.medirDiametroFuste = medirDiametroFuste;
        r.estadoSalud = estadoSalud;

        r.disturbiosMeteorologicos = disturbiosMeteorologicos;
        r.interacciones = interacciones;
        r.organismoInteracciones = organismoInteracciones;
        r.presenciaCombustibles = presenciaCombustibles;
        r.combustiblesFinos = combustiblesFinos;
        r.combustiblesPesados = combustiblesPesados;
        r.peligroIncendio = peligroIncendio;

        // MainActivity3
        r.hojas = hojas;
        r.estadoHojas = estadoHojas;
        r.flores = flores;
        r.frutos = frutos;
        r.estadoFrutos = estadoFrutos;
        r.Ramas = Ramas;
        r.corteza = corteza;
        r.usos = usos;
        r.observaciones = observaciones;

        // Imágenes
        r.archivoHojas = archivoHojas;
        r.extensionHojas = extensionHojas;
        r.archivoFlores = archivoFlores;
        r.extensionFlores = extensionFlores;
        r.archivoFrutos = archivoFrutos;
        r.extensionFrutos = extensionFrutos;
        r.archivoRamas = archivoRamas;
        r.extensionRamas = extensionRamas;
        r.archivoCorteza = archivoCorteza;
        r.extensionCorteza = extensionCorteza;
        r.archivoGeneral = archivoGeneral;
        r.extensionGeneral = extensionGeneral;
        // Asigna todos los campos
        return r;
    }

    public void clear() {
        // MainActivity
        numeroAcceso = 0;
        nombreFamilia = null;
        nombreComun = null;
        nombreCientificoGenero = null;
        nombreCientificoEspecie = null;
        especieOriginaria = false;
        ecologiaDistribucion = null;
        clasificaionTaxonomica = null;
        coordenadas = null;

        // MainActivity2
        fecha = null;
        habitoCrecimiento = null;
        tipoCrecimiento = null;
        altura = 0;
        medirAltura = null;
        diametroFuste = 0;
        medirDiametroFuste = null;
        estadoSalud = null;
        disturbiosMeteorologicos = null;
        interacciones = null;
        organismoInteracciones = null;
        presenciaCombustibles = false;
        combustiblesFinos = null;
        combustiblesPesados = null;
        peligroIncendio = null;

        // MainActivity3
        hojas = 0;
        estadoHojas = null;
        flores = 0;
        frutos = 0;
        estadoFrutos = null;
        Ramas = null;
        corteza = null;
        usos = null;
        observaciones = null;

        // Imágenes
        archivoHojas = null;
        extensionHojas = null;
        archivoFlores = null;
        extensionFlores = null;
        archivoFrutos = null;
        extensionFrutos = null;
        archivoRamas = null;
        extensionRamas = null;
        archivoCorteza = null;
        extensionCorteza = null;
        archivoGeneral = null;
        extensionGeneral = null;
        // Limpia todos los demás campos
    }

    // Clase interna que representará tu registro completo
    public static class RegistroCompleto  {
        public int numeroAcceso;
        public String nombreFamilia;
        public String nombreComun;
        public String nombreCientificoGenero;
        public String nombreCientificoEspecie;
        public boolean especieOriginaria;
        public String ecologiaDistribucion;
        public String clasificaionTaxonomica;
        public String coordenadas;

        public Date fecha;
        public String habitoCrecimiento;
        public String tipoCrecimiento;
        public float altura;
        public String medirAltura;
        public float diametroFuste;
        public String medirDiametroFuste;
        public String estadoSalud;

        public String disturbiosMeteorologicos;
        public String interacciones;
        public String organismoInteracciones;
        public boolean presenciaCombustibles;
        public String combustiblesFinos;
        public String combustiblesPesados;
        public String peligroIncendio;

        public int hojas;
        public String estadoHojas;
        public int flores;
        public int frutos;
        public String estadoFrutos;
        public String Ramas;
        public String corteza;
        public String usos;
        public String observaciones;

        public byte[] archivoHojas;
        public String extensionHojas;
        public byte[] archivoFlores;
        public String extensionFlores;
        public byte[] archivoFrutos;
        public String extensionFrutos;
        public byte[] archivoRamas;
        public String extensionRamas;
        public byte[] archivoCorteza;
        public String extensionCorteza;
        public byte[] archivoGeneral;
        public String extensionGeneral;
    }
}

